package com.github.hexosse.adminsignshop.utils.plugins;

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

import com.gmail.filoghost.holographicdisplays.HolographicDisplays;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class HolographicDisplaysUtil
{
    private static HolographicDisplays holographicDisplays = null;

    /**
     * @return HolographicDisplays plugin instance
     */
    public static HolographicDisplays getHolographicDisplaysPlugin()
    {
        if(HolographicDisplaysUtil.getPlugin()!=null)
            return HolographicDisplaysUtil.getPlugin();

        PluginManager pm = Bukkit.getServer().getPluginManager();
        HolographicDisplays holographicDisplays = (HolographicDisplays)pm.getPlugin("HolographicDisplays");
        if(holographicDisplays != null && pm.isPluginEnabled(holographicDisplays))
        {
            HolographicDisplaysUtil.setPlugin(holographicDisplays);
            return holographicDisplays;
        }
        else return null;
    }

    /**
     *
     */
    public static void setPlugin(HolographicDisplays plugin)
    {
        holographicDisplays = plugin;
    }

    /**
     *
     */
    public static HolographicDisplays getPlugin()
    {
        return holographicDisplays;
    }

    /**
     * Test if HolographicDisplays is installed
     *
     * @return true if HolographicDisplays is installed
     */
    public static boolean hasHolographicDisplays()
    {
        return HolographicDisplaysUtil.getHolographicDisplaysPlugin()!=null;
    }
}
