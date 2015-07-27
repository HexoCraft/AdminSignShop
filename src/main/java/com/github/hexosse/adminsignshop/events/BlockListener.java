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
import com.github.hexosse.adminsignshop.shop.ShopCreators;
import com.github.hexosse.adminsignshop.shop.Shops;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>)
 */
public class BlockListener implements Listener
{
    private final static AdminSignShop plugin = AdminSignShop.getPlugin();
    private final static Shops shops = AdminSignShop.getShops();
    private final static ShopCreators creators = shops.creators;

    @EventHandler(priority= EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event)
    {
        if(creators.exist(event.getPlayer()))
            event.setCancelled(true);
    }
}
