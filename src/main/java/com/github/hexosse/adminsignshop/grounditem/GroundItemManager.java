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
import com.github.hexosse.grounditemapi.GroundItemApi;
import com.github.hexosse.grounditemapi.grounditem.GroundItem;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

import static com.github.hexosse.adminsignshop.utils.plugin.GroundItemUtil.getGroundItem;


/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class GroundItemManager extends BaseObject<AdminSignShop>
{
    private static Plugin groundItem = getGroundItem();

	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public GroundItemManager(AdminSignShop plugin) {
		super(plugin);
	}


	public static void create(AdminSignShop plugin, Creator creator, ItemStack displayStack, Location displayLocation)
	{
		if(groundItem!=null && creator.groundItem)
			createGroundItem(plugin, creator.getPlayer(),displayStack,displayLocation);
	}

	private static void createGroundItem(AdminSignShop plugin, Player player, ItemStack displayStack, Location displayLocation)
	{
		// Test si il n'y a pas le même item à proximité
		boolean add = true;
		GroundItem toRemove = null;

		// récupère la liste des items du plugin itemstay
		List<GroundItem> groundItems = GroundItemApi.getGroundItemList();
		
		// Parcours toutes les entitées 
        for (int x = 0; x < groundItems.size(); x++)
        {
			GroundItem groundItem = groundItems.get(x);
            
            if(LocationUtil.equals(displayLocation,groundItem.getLocation())
                    && groundItem.getItemStack().getType()==displayStack.getType())
            {
				toRemove = groundItem;
                if(LocationUtil.equals(groundItem.getLocation(), displayLocation))
                	add = false;
                continue;
            }
        }

        if(toRemove!=null)
			GroundItemApi.removeGroundItem(toRemove);

        if(add)
			GroundItemApi.createGroundItem(displayStack,displayLocation);
	}
}
