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

package com.github.hexosse.adminsignshop.events;

import com.github.hexosse.adminsignshop.AdminSignShop;
import com.github.hexosse.adminsignshop.Utils.BlockUtil;
import com.github.hexosse.adminsignshop.Utils.LocationUtil;
import com.github.hexosse.adminsignshop.shop.Creator;
import com.github.hexosse.adminsignshop.grounditem.GroundItem;
import com.github.hexosse.adminsignshop.shop.ShopCreators;
import com.github.hexosse.adminsignshop.shop.Shops;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;


/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class PlayerListener implements Listener
{
	private final static AdminSignShop plugin = AdminSignShop.getPlugin();
	private final static Shops shops = AdminSignShop.getShops();
	private final static ShopCreators creators = shops.creators;

	
	@EventHandler(priority=EventPriority.HIGH)
    public void onQuitEvent(PlayerQuitEvent e)
	{
		if(creators.exist(e.getPlayer()))
			creators.remove(e.getPlayer());
    }

	
	@EventHandler(priority=EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent  e)
	{
		final PlayerInteractEvent event = e;
		final Player player = event.getPlayer();
		final Creator creator = shops.creators.get(player);
		
		// Test si le joueur est en cours de création de shop
		if(creator==null || (creator!=null && creator.enable==false)) return;
		
		//
		if(event.getAction().equals(Action.LEFT_CLICK_BLOCK))
		{
			// L'utilisateur clique sur un sign
			// --> Création du shop
			if(BlockUtil.isSign(event.getClickedBlock()) && player.getItemInHand()!=null && player.getItemInHand().getAmount()>0)
			{
		        // Create the task anonymously and schedule to run it once, after 20 ticks
		        new BukkitRunnable()
		        {
		            @Override
		            public void run()
		            {
		            	shops.CreateSignShop(event.getClickedBlock(), player);
		            }
		 
		        }.runTask(PlayerListener.plugin);
			}
		 
			// On test si le block du dessus est de l'air
			// --> on utilise ItemStay ou holographicDisplay
			else if(BlockUtil.isAir(LocationUtil.top(event.getClickedBlock().getLocation()).getBlock()))
			{
				if(creator.groundItem)
				{
			        // Create the task anonymously and schedule to run it once, after 20 ticks
			        new BukkitRunnable()
			        {
			            @Override
			            public void run()
			            {
			            	GroundItem.create(creator, player.getItemInHand(), LocationUtil.top(event.getClickedBlock().getLocation()));
			            }
			 
			        }.runTask(PlayerListener.plugin);
				}
			}
		}
    }
	



}
