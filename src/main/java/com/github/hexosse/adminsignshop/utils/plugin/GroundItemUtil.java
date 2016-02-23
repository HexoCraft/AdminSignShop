package com.github.hexosse.adminsignshop.utils.plugin;

/*
 * Copyright 2016 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.github.hexosse.grounditem.GroundItemPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class GroundItemUtil
{
    private static GroundItemPlugin groundItem = null;

    /**
     * @return GroundItem plugin instance
     */
    public static GroundItemPlugin getGroundItem()
    {
        if(GroundItemUtil.getPlugin()!=null)
            return GroundItemUtil.getPlugin();

        PluginManager pm = Bukkit.getServer().getPluginManager();
        GroundItemPlugin groundItem = (GroundItemPlugin)pm.getPlugin("GroundItem");
        if(groundItem != null && pm.isPluginEnabled(groundItem))
        {
            GroundItemUtil.setPlugin(groundItem);
            return groundItem;
        }
        else return null;
    }

    /**
     * @param plugin The plugin that this object belong to.
     */
    public static void setPlugin(GroundItemPlugin plugin)
    {
        groundItem = plugin;
    }

    /**
     * @return The plugin that this object belong to.
     */
    public static GroundItemPlugin getPlugin()
    {
        return groundItem;
    }

    /**
     * Test if GroundItem is installed
     *
     * @return true if GroundItem is installed
     */
    public static boolean hasGroundItem()
    {
        return GroundItemUtil.getGroundItem()!=null;
    }
}
