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

package com.github.hexosse.adminsignshop.utils.plugin;


import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.wargamer2010.signshop.SignShop;

/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class SignShopUtil
{
    private static SignShop signShop = null;

    /**
     * @return HolographicDisplays plugin instance
     */
    public static SignShop getSignShopPlugin()
    {
        if(SignShopUtil.getPlugin()!=null)
            return SignShopUtil.getPlugin();

        PluginManager pm = Bukkit.getServer().getPluginManager();
        SignShop signShop = (SignShop)pm.getPlugin("SignShop");
        if(signShop != null && pm.isPluginEnabled(signShop))
        {
            SignShopUtil.setPlugin(signShop);
            return signShop;
        }
        else return null;
    }

    /**
     * @param plugin The plugin that this object belong to.
     */
    public static void setPlugin(SignShop plugin)
    {
        signShop = plugin;
    }

    /**
     * @return The plugin that this object belong to.
     */
    public static SignShop getPlugin()
    {
        return signShop;
    }

    /**
     * Test if SignShop is installed
     *
     * @return true if SignShop is installed
     */
    private static boolean hasSignShop()
    {
        return SignShopUtil.getSignShopPlugin()!=null;
    }
}
