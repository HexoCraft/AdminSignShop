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

package com.github.hexosse.adminsignshop.utils.plugins;

import me.nighteyes604.ItemStay.ItemStay;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ItemStayUtil
{
    private static ItemStay itemStay = null;

    /**
     * @return HolographicDisplays plugin instance
     */
    public static ItemStay getItemStayPlugin()
    {
        if(ItemStayUtil.getPlugin()!=null)
            return ItemStayUtil.getPlugin();

        PluginManager pm = Bukkit.getServer().getPluginManager();
        ItemStay itemStay = (ItemStay)pm.getPlugin("ItemStay");
        if(itemStay != null && pm.isPluginEnabled(itemStay))
        {
            ItemStayUtil.setPlugin(itemStay);
            return itemStay;
        }
        else return null;
    }

    /**
     *
     */
    public static void setPlugin(ItemStay plugin)
    {
        itemStay = plugin;
    }

    /**
     *
     */
    public static ItemStay getPlugin()
    {
        return itemStay;
    }

    /**
     * Test if ItemStay is installed
     *
     * @return true if ItemStay is installed
     */
    public static boolean hasItemStay()
    {
        return ItemStayUtil.getItemStayPlugin()!=null;
    }
}
