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

package com.github.hexosse.adminsignshop.grounditem;

import com.github.hexosse.adminsignshop.AdminSignShop;
import com.github.hexosse.adminsignshop.shop.Creator;
import com.github.hexosse.baseplugin.BaseObject;
import com.github.hexosse.baseplugin.utils.LocationUtil;
import com.github.hexosse.baseplugin.utils.block.BlockUtil;
import com.gmail.filoghost.holographicdisplays.disk.HologramDatabase;
import com.gmail.filoghost.holographicdisplays.object.NamedHologram;
import com.gmail.filoghost.holographicdisplays.object.NamedHologramManager;
import me.nighteyes604.ItemStay.FrozenItem;
import me.nighteyes604.ItemStay.ItemStayListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

import static com.github.hexosse.adminsignshop.utils.plugin.HolographicDisplaysUtil.getHolographicDisplaysPlugin;
import static com.github.hexosse.adminsignshop.utils.plugin.ItemStayUtil.getItemStayPlugin;


/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class GroundItem extends BaseObject<AdminSignShop>
{
    private static Plugin holographicDisplays = getHolographicDisplaysPlugin();
    private static Plugin itemStay = getItemStayPlugin();

	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public GroundItem(AdminSignShop plugin) {
		super(plugin);
	}


	public static void create(Creator creator, ItemStack displayStack, Location displayLocation)
	{
		if(holographicDisplays!=null && creator.holographicDisplays)
			createHolographicDisplay(creator.getPlayer(),displayStack,displayLocation);

		if(itemStay!=null && creator.itemStay)
			createItemStay(creator.getPlayer(),displayStack,displayLocation);
}

	private static void createHolographicDisplay(Player player, ItemStack displayStack, Location displayLocation)
	{
		// Nom de l'hologram
    	String name = LocationUtil.locationToString(displayLocation);
		
		if(NamedHologramManager.isExistingHologram(name))
		{
			NamedHologram hologram = NamedHologramManager.getHologram(name);
			hologram.delete();
			NamedHologramManager.removeHologram(hologram);
			HologramDatabase.deleteHologram(hologram.getName());
			HologramDatabase.trySaveToDisk();
		}
		else
		{
			NamedHologram hologram = new NamedHologram(displayLocation.add(0.5, 0.9 - (BlockUtil.isHalfBlock(LocationUtil.bottom(displayLocation).getBlock())?0.5:0), 0.5), name);
			hologram.appendItemLine(displayStack);
			NamedHologramManager.addHologram(hologram);
			HologramDatabase.saveHologram(hologram);
			HologramDatabase.trySaveToDisk();
		}
	}

	private static void createItemStay(Player player, ItemStack displayStack, Location displayLocation)
	{
		// Dans le cas d'un block il faut le supprimer
		// pour pouvoir afficher l'entitée
		LocationUtil.RemoveBlock(displayLocation);

		// Test si il n'y a pas le même item à proximité 
		// pour éviter qu'ils ne se regroupent
		boolean add = true;
		int removeId = -1;

		// récupère la liste des items du plugin itemstay
		List<FrozenItem> frozenItems = ItemStayListener.plugin.frozenItems;
		
		// Parcours toutes les entitées 
        for (int x = 0; x < frozenItems.size(); x++)
        {
            FrozenItem frozenItem = frozenItems.get(x);
            
            // Distance minimal = 3
            if(LocationUtil.distance(displayLocation,frozenItem.location)<=3
            	&& frozenItem.material==displayStack.getType())
            {
                removeId = x;
                if(LocationUtil.equals(frozenItem.location, displayLocation))
                	add = false;
                continue;
            }
        }

        if(removeId>=0)
        {
        	StringBuilder sb = new StringBuilder();
        	sb.append("itemstay delete ");
        	sb.append(Integer.toString(removeId));

        	player.performCommand(sb.toString());
        }

        if(add)
        {
	        frozenItems.add(
	                    new FrozenItem(player.getName().toLowerCase(),
	                    		displayLocation,
	                    		displayStack.getType().name(),
	                    		displayStack.getType(),
	                    		displayStack.getDurability())
	            );
        }
	}
}
