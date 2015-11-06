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

import com.github.hexosse.adminsignshop.AdminSignShop;
import com.github.hexosse.baseplugin.BaseObject;
import org.bukkit.entity.Player;


/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class Creator extends BaseObject<AdminSignShop>
{
	/* Player */
	private Player player = null;

	/* Enable */
	public boolean enable;
	
	/* Shop */
	public double sellFactor = plugin.config.sellFactor;
	public double enchantmentFactor = plugin.config.enchantmentFactor;
	public boolean buy = plugin.config.buy;
	public boolean sell = plugin.config.sell;
	public double defWorth = plugin.config.defWorth;

	/* 3D */
	public boolean groundItem = plugin.config.groundItem;
	public boolean holographicDisplays = plugin.config.holographicDisplays;
	public boolean itemStay = plugin.config.itemStay;

	
	
	// Constructeur
    public Creator(AdminSignShop plugin, Player creator)
    {
		super(plugin);
    	player = creator;
		enable = true;
    }

	public Player getPlayer() { return player; }
}
