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
import com.google.common.collect.Iterables;
import com.meowj.langutils.lang.LanguageHelper;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

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
import java.util.Map;


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
     * @param signBlock Sign block used for the shop
     * @param player Player creating the shop
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
        {
            ssPlayer.sendMessage(SignShopConfig.getError("invalid_operation", null));
            return;
        }


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
     * @return Indicate if the shop can be created
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
        String bookName = getBookName(item);
        String itemName = getItemName(item);
        String enchanted_item = getItemName(new ItemStack(item.getType()));
        String enchanted_name = getEnchantName(item.getEnchantments());
        if(item.getEnchantments().size()>0)
            itemName = "";
        else enchanted_item = "";

        // Prix unitaire de l'item
        double purchasePrice = getWorth(item, creator);
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
            // Formatage des lignes
            String line1 = config.line1.replaceAll("%quantity%", itemName.length()>0?"" + quantity:"").replaceAll("%name%", itemName).replaceAll("%book%", bookName).replaceAll("%enchanted_item%", enchanted_item).replaceAll("%enchanted_name%", enchanted_name).replaceAll("\\s+", " ").trim();
            String line2 = config.line2.replaceAll("%quantity%", itemName.length()>0?"" + quantity:"").replaceAll("%name%", itemName).replaceAll("%book%", bookName).replaceAll("%enchanted_item%", enchanted_item).replaceAll("%enchanted_name%", enchanted_name).replaceAll("\\s+", " ").trim();
            String line12 = config.line12.replaceAll("%quantity%", itemName.length()>0?"" + quantity:"").replaceAll("%name%", itemName).replaceAll("%book%", bookName).replaceAll("%enchanted_item%", enchanted_item).replaceAll("%enchanted_name%", enchanted_name).replaceAll("\\s+", " ").trim();
            if(!line12.isEmpty())
            {
                String[] splitsLine = StringUtil.breakLinesSplit(line12, 16);
                line1 = (splitsLine.length > 0) ? splitsLine[0] : "";
                line2 = (splitsLine.length > 1) ? splitsLine[1] : "";
            }

            // Création des lignes
            String[] line = new String[4];
            line[0] = shopType;
            line[1] = line1;
            line[2] = line2;
            line[3] = config.currencySymbol + " " + (creator.buy ? totalPurchasePrice : totalSellPrice);

            // Mise à jour des lignes
            line[1] = line[1].replaceAll("%quantity%", itemName.length()>0?"" + quantity:"").replaceAll("%name%", itemName).replaceAll("%book%", bookName).replaceAll("%enchanted_item%", enchanted_item).replaceAll("%enchanted_name%", enchanted_name);
            line[2] = line[2].replaceAll("%quantity%", itemName.length()>0?"" + quantity:"").replaceAll("%name%", itemName).replaceAll("%book%", bookName).replaceAll("%enchanted_item%", enchanted_item).replaceAll("%enchanted_name%", enchanted_name);

            sign.setLine(0, line[0]);
            sign.setLine(1, line[1]);
            sign.setLine(2, line[2]);
            sign.setLine(3, line[3]);
            sign.update();
        }

        return true;
    }

    protected String getBookName(ItemStack item)
    {
        if(item.getType()==Material.BOOK || item.getType()==Material.ENCHANTED_BOOK)
        {
            ItemStack book = new ItemStack(Material.BOOK);
            if(langUtils != null)
                return LanguageHelper.getItemName(book, config.locale);
            else
                return StringUtil.capitalizeFirstLetter(book.getType().name().replace("_", " "));
        }
        return "";
    }

    protected String getItemName(ItemStack item)
    {
        // Cas des livres enchantés
        if(item.getType()==Material.ENCHANTED_BOOK)
        {
            // Echantments
            EnchantmentStorageMeta book = (EnchantmentStorageMeta) item.getItemMeta();
            // List of enchantments
            Map<Enchantment, Integer> enchantments = book.getStoredEnchants();

            return  getEnchantName(enchantments);
        }
        else
        {
            if(langUtils != null)
                return LanguageHelper.getItemName(item, config.locale);
            else
                return StringUtil.capitalizeFirstLetter(item.getType().name().replace("_", " "));
        }
    }

    protected String getEnchantName(Map<Enchantment, Integer> enchantments)
    {
        // Un seul enchantement
        if(enchantments.size() == 1)
        {
            Enchantment enchantment = Iterables.getFirst(enchantments.keySet(), null);
            int level = Iterables.getFirst(enchantments.values(), 0);
            if(langUtils != null)
                return LanguageHelper.getEnchantmentName(enchantment, level, config.locale);
            else
            {
                String ench = StringUtil.capitalizeFirstLetter(enchantment.getName().replace("_", " "));
                String enchLevel = Integer.toString(level);
                return ench + (enchLevel.length() > 0 ? " " + enchLevel : "");
            }
        }
        // Plusieurs enchantements
        else
        {
            String name = "";
            for(Map.Entry<Enchantment, Integer> enchantmentLevel : enchantments.entrySet())
            {
                String tempName = (langUtils != null)
                        ? LanguageHelper.getEnchantmentName(enchantmentLevel.getKey(), config.locale)
                        : (enchantmentLevel.getKey().getName());
                String tempLevel = (langUtils != null)
                        ? LanguageHelper.getEnchantmentLevelName(enchantmentLevel.getValue(), config.locale)
                        : (enchantmentLevel.getValue() > 0 ? " " + enchantmentLevel.getValue() : "");

                name += WordUtils.initials(tempName) + tempLevel + " ";
            }

            return name.trim();
        }
    }

    protected double getWorth(ItemStack is, Creator creator)
    {
        if(creator.defWorth != 0 )
            return creator.defWorth;

        YamlConfiguration worth = config.getWorth();
        double worthItem = 0;
        String materialName = is.getType().name().replace("_", "").toLowerCase();

        if(is.getType()==Material.ENCHANTED_BOOK)
        {
            // Echantments
            EnchantmentStorageMeta book = (EnchantmentStorageMeta) is.getItemMeta();
            // List of enchantments
            Map<Enchantment, Integer> enchantments = book.getStoredEnchants();

            for(Map.Entry<Enchantment, Integer> enchantmentLevel : enchantments.entrySet())
            {
                Enchantment enchantment = enchantmentLevel.getKey();
                int level = enchantmentLevel.getValue();

                double worthEnch = getWorth(enchantment,creator);
                worthEnch = worthEnch + (level-1)*(1+creator.enchantmentFactor)*worthEnch;
                worthItem += worthEnch;
            }

            return worthItem;
        }

        worthItem = (double) worth.getDouble("worth." + materialName + "." + is.getDurability());
        if (worthItem == 0) {
            worthItem = (double) worth.getDouble("worth." + materialName);
        }

        if(is.getEnchantments().size()>0)
        {
            for(Map.Entry<Enchantment, Integer> enchantmentLevel : is.getEnchantments().entrySet())
            {
                Enchantment enchantment = enchantmentLevel.getKey();
                int level = enchantmentLevel.getValue();

                double worthEnch = getWorth(enchantment,creator);
                worthEnch = worthEnch + (level-1)*(1+creator.enchantmentFactor)*worthEnch;
                worthItem += worthEnch;
            }
        }

        return worthItem;
    }

    protected double getWorth(Enchantment ench, Creator creator)
    {
        if(creator.defWorth != 0 )
            return creator.defWorth;

        YamlConfiguration worth = config.getWorth();
        double worthItem = 0;


        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        book.addUnsafeEnchantment(ench,1);

        String materialName = book.getType().name().replace("_", "").toLowerCase();

        /* todo : Voir pour ajouter les enchantements à MemWorth */
        double enchantmentWorth = (double) worth.getDouble("worth." + materialName + "." + ench.getId());

        return enchantmentWorth;
    }

}
