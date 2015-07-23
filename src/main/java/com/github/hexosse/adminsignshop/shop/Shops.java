/*
 * Copyright 2015 Hexosse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.hexosse.adminsignshop.shop;

import com.github.hexosse.adminsignshop.Utils.StringUtil;
import com.github.hexosse.adminsignshop.configuration.Config;
import com.meowj.langutils.lang.LanguageHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import com.github.hexosse.adminsignshop.AdminSignShop;
import org.bukkit.plugin.Plugin;
import org.wargamer2010.signshop.SignShop;
import org.wargamer2010.signshop.configuration.SignShopConfig;
import org.wargamer2010.signshop.configuration.Storage;
import org.wargamer2010.signshop.events.SSCreatedEvent;
import org.wargamer2010.signshop.events.SSEventFactory;
import org.wargamer2010.signshop.operations.SignShopArguments;
import org.wargamer2010.signshop.operations.SignShopArgumentsType;
import org.wargamer2010.signshop.operations.SignShopOperationListItem;
import org.wargamer2010.signshop.player.SignShopPlayer;
import org.wargamer2010.signshop.util.clicks;
import org.wargamer2010.signshop.util.economyUtil;
import org.wargamer2010.signshop.util.itemUtil;
import org.wargamer2010.signshop.util.signshopUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class Shops
{
	private final static AdminSignShop plugin = AdminSignShop.getPlugin();
	private final static Config config = AdminSignShop.getConfiguration();

    private static Plugin langUtils = plugin.getServer().getPluginManager().getPlugin("LangUtils");

	public ShopCreators creators;


    /**
     * Constructeur
     */
    public Shops()
    {
    	creators = new ShopCreators();
    }

    /**
     * @param signBlock
     * @param player
     */
 	public void CreateSignShop(Block signBlock, Player player)
	{
        Sign sign = (Sign)signBlock.getState();
        SignShopPlayer ssPlayer = new SignShopPlayer(player);
        String[] sLines;
        String sOperation;
        World world = player.getWorld();

        // 0 - Préparation du sign
        // --> Opération, désignatio, prix, ...
        if(!PrepareSign(sign,player))
            ssPlayer.sendMessage(SignShopConfig.getError("invalid_operation", null));


        // 1 - retrouver les informations et opérations permettant la création du shop
        sLines = sign.getLines();
        sOperation = signshopUtil.getOperation(sLines[0]);
        if(SignShopConfig.getBlocks(sOperation).isEmpty()) {
            ssPlayer.sendMessage(SignShopConfig.getError("invalid_operation", null));
            return;
        }

        List<String> operation = SignShopConfig.getBlocks(sOperation);
        List<SignShopOperationListItem> SignShopOperations = signshopUtil.getSignShopOps(operation);
        if(SignShopOperations == null) {
            ssPlayer.sendMessage(SignShopConfig.getError("invalid_operation", null));
            return;
        }
        for(SignShopOperationListItem ssOperation : SignShopOperations) {
            ssOperation.getParameters().add("allowNoChests");
        }

        // 2 - Création des arguments et parcours des opérations
        List<Block> containables = new LinkedList<Block>();
        List<Block> activatables = new LinkedList<Block>();
        ItemStack[] itemStack = new ItemStack[1];
        itemStack[0] = player.getItemInHand();


        SignShopArguments ssArgs = new SignShopArguments(economyUtil.parsePrice(sLines[3]), itemStack, containables, activatables,
                ssPlayer, ssPlayer, signBlock, sOperation, BlockFace.DOWN, Action.LEFT_CLICK_BLOCK, SignShopArgumentsType.Setup);

        Boolean bSetupOK = false;
        for(SignShopOperationListItem ssOperation : SignShopOperations) {
            ssArgs.setOperationParameters(ssOperation.getParameters());
            bSetupOK = ssOperation.getOperation().setupOperation(ssArgs);
            if(!bSetupOK)
                return;
        }
        if(!bSetupOK)
            return;

        // 3 - Création de l'évènement SSCreatedEvent afin de permettre la cancellation du shop
        if(!signshopUtil.getPriceFromMoneyEvent(ssArgs))
            return;

        SSCreatedEvent createdevent = SSEventFactory.generateCreatedEvent(ssArgs);
        SignShop.scheduleEvent(createdevent);
        if(createdevent.isCancelled()) {
            itemUtil.setSignStatus(signBlock, ChatColor.BLACK);
            return;
        }

        // 4 - Création du shop
        Storage.get().addSeller(ssPlayer.getIdentifier(), world.getName(), ssArgs.getSign().get(), ssArgs.getContainables().getRoot(), ssArgs.getActivatables().getRoot()
                , ssArgs.getItems().get(), createdevent.getMiscSettings());
        if(!ssArgs.bDoNotClearClickmap)
            clicks.removePlayerFromClickmap(player);
	}

    /**
     * Prepare the sign for the shop creation
     *
     * @param sign to prepare
     * @param player Player creating the shop
     */
    protected boolean PrepareSign(Sign sign, Player player)
    {
        // Item à vendre
        ItemStack item = player.getItemInHand();
        if(item==null) return false;

        // Créateur du shop
        Creator creator = creators.get(player);
        if(creator == null) return false;

        // La quantité vendu (ou achetée) est la quantité de l'item en main
        int quantity = player.getItemInHand().getAmount();

        // Item à vendre
        String itemName = GetItemName(item);

        // Prix unitaire de l'item
        double purchasePrice = getWorth(item);
        double sellPrice = creator.sellFactor * purchasePrice;

        // Prix total de l'item
        float totalPurchasePrice = (float) (quantity * purchasePrice);
        float totalSellPrice = (float) (quantity * sellPrice);
        totalPurchasePrice = (float)Math.round(totalPurchasePrice * 100)/100;  // 2 decimal
        totalSellPrice = (float)Math.round(totalSellPrice * 100)/100;  // 2 decimal
        if(totalPurchasePrice==(int)totalPurchasePrice) totalPurchasePrice = (int)totalPurchasePrice;
        if(totalSellPrice==(int)totalSellPrice) totalSellPrice = (int)totalSellPrice;

        // Nom du vendeur : Admin Shop
        String buySign = "[" + config.buySign + "]";
        String sellSign = "[" + config.sellSign + "]";
        String shopType = creator.buy ? buySign : sellSign;

        // L'item doit avoir un prix
        if(totalPurchasePrice == 0)
        {
            player.sendMessage(ChatColor.RED + "[" + plugin.getDescription().getName() + "]" + ChatColor.GOLD + " This item has no price!");
            return false;
        }
        else
        {
            // Création des lignes
            String[] line = new String[4];
            line[0] = shopType;
            line[1] = config.line1;
            line[2] = config.line2;
            line[3] = "$" + (creator.buy ? totalPurchasePrice : totalSellPrice);

            // Mise à jour des lignes
            line[1] = line[1].replaceAll("%quantity%", "" + quantity).replaceAll("%name%", itemName);
            line[2] = line[2].replaceAll("%quantity%", "" + quantity).replaceAll("%name%", itemName);

            sign.setLine(0, line[0]);
            sign.setLine(1, line[1]);
            sign.setLine(2, line[2]);
            sign.setLine(3, line[3]);
            sign.update();
        }

        return true;
    }

    protected String GetItemName(ItemStack item)
    {
        if(langUtils!=null)
            return LanguageHelper.getItemName(item, config.locale);
        else
            return StringUtil.capitalizeFirstLetter(item.getType().name().replace("_", " "));
    }

    /**
     * @param is
     * @return
     */
    protected float getWorth(ItemStack is)
    {
        float price = 0;

        String materialName = is.getType().name().replace("_", "").toLowerCase();

        YamlConfiguration worth = config.getWorth();

        price = (float) worth.getDouble("worth." + materialName + "." + is.getDurability());
        if (price == 0) {
            price = (float) worth.getDouble("worth." + materialName);
        }

        return price;
    }

}
