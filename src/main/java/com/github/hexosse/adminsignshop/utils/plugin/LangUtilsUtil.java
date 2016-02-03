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


import com.meowj.langutils.LangUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * This file is part AdminLangUtils
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class LangUtilsUtil
{
    private static LangUtils langUtils = null;

    /**
     * @return HolographicDisplays plugin instance
     */
    public static LangUtils getLangUtilsPlugin()
    {
        if(LangUtilsUtil.getPlugin()!=null)
            return LangUtilsUtil.getPlugin();

        PluginManager pm = Bukkit.getServer().getPluginManager();
        LangUtils langUtils = (LangUtils)pm.getPlugin("LangUtils");
        if(langUtils != null && pm.isPluginEnabled(langUtils))
        {
            LangUtilsUtil.setPlugin(langUtils);
            return langUtils;
        }
        else return null;
    }

    /**
     * @param plugin The plugin that this object belong to.
     */
    public static void setPlugin(LangUtils plugin)
    {
        langUtils = plugin;
    }

    /**
     * @return The plugin that this object belong to.
     */
    public static LangUtils getPlugin()
    {
        return langUtils;
    }

    /**
     * Test if LangUtils is installed
     *
     * @return true if LangUtils is installed
     */
    public static boolean hasLangUtils()
    {
        return LangUtilsUtil.getLangUtilsPlugin()!=null;
    }
}
