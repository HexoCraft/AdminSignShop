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

import com.github.hexosse.adminsignshop.configuration.Config;
import org.bukkit.entity.Player;

import com.github.hexosse.adminsignshop.AdminSignShop;


/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class Creator
{
	private final static Config config = AdminSignShop.getConfiguration();

	/* Player */
	private Player player = null;

	/* Enable */
	public boolean enable;
	
	/* Shop */
	public double sellFactor = config.sellFactor;
	public double enchantmentFactor = config.enchantmentFactor;
	public boolean buy = config.buy;
	public boolean sell = config.sell;
	public double defWorth = config.defWorth;

	/* 3D */
	public boolean groundItem = config.groundItem;
	public boolean holographicDisplays = config.holographicDisplays;
	public boolean itemStay = config.itemStay;

	
	
	// Constructeur
    public Creator(Player creator)
    {
    	player = creator;
		enable = true;
    }

	public Player getPlayer() { return player; }
}
