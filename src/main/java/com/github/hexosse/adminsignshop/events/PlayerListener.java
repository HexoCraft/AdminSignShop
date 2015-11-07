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
import com.github.hexosse.adminsignshop.grounditem.GroundItem;
import com.github.hexosse.adminsignshop.shop.Creator;
import com.github.hexosse.baseplugin.event.BaseListener;
import com.github.hexosse.baseplugin.utils.LocationUtil;
import com.github.hexosse.baseplugin.utils.block.BlockUtil;
import com.github.hexosse.baseplugin.utils.block.SignUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;


/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class PlayerListener extends BaseListener<AdminSignShop>
{
	/**
	 * @param plugin The plugin that this listener belongs to.
	 */
	public PlayerListener(AdminSignShop plugin) {
		super(plugin);
	}

	/**
	 * @param event PlayerQuitEvent
	 */
	@EventHandler(priority=EventPriority.HIGH)
    public void onQuitEvent(PlayerQuitEvent event)
	{
		if(plugin.shops.creators.exist(event.getPlayer()))
			plugin.shops.creators.remove(event.getPlayer());
    }

	public boolean isSign(Block block)
	{
		// Ne fonctionne pas ??
		return (block.getType()== Material.SIGN
				|| block.getType()== Material.WALL_SIGN);
	}

	/**
	 * @param event
	 */
	@EventHandler(priority=EventPriority.HIGH)
    public void onPlayerInteract(final PlayerInteractEvent event)
	{
		final Player player = event.getPlayer();
		final Creator creator = plugin.shops.creators.get(player);
		
		// Test si le joueur est en cours de création de shop
		if(creator==null || (creator!=null && creator.enable==false)) return;
		
		//
		if(event.getAction().equals(Action.LEFT_CLICK_BLOCK))
		{
			// L'utilisateur clique sur un sign
			// --> Création du shop
			if(SignUtil.isSign(event.getClickedBlock()) && player.getItemInHand()!=null && player.getItemInHand().getAmount()>0)
			{
		        // Create the task anonymously and schedule to run it once, after 20 ticks
		        new BukkitRunnable()
		        {
		            @Override
		            public void run()
		            {
						plugin.shops.CreateSignShop(event.getClickedBlock(), player);
		            }
		 
		        }.runTask(plugin);
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
			 
			        }.runTask(plugin);
				}
			}
		}
    }
}
