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

package com.github.hexosse.adminsignshop.commands;

import com.github.hexosse.adminsignshop.AdminSignShop;
import com.github.hexosse.baseplugin.command.BaseArgsCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.github.hexosse.adminsignshop.utils.plugins.HolographicDisplaysUtil.getHolographicDisplaysPlugin;
import static com.github.hexosse.adminsignshop.utils.plugins.ItemStayUtil.getItemStayPlugin;
import static com.github.hexosse.adminsignshop.utils.plugins.LangUtilsUtil.getLangUtilsPlugin;
import static com.github.hexosse.adminsignshop.utils.plugins.SignShopUtil.getSignShopPlugin;

/**
 * This file is part of AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class CommandReload extends BaseArgsCommand<AdminSignShop>
{
    private static Plugin signShop = getSignShopPlugin();
    private static Plugin holographicDisplays = getHolographicDisplaysPlugin();
    private static Plugin itemStay = getItemStayPlugin();
    private static Plugin langUtils = getLangUtilsPlugin();

    /**
     * @param plugin The plugin that this object belong to.
     */
    public CommandReload(AdminSignShop plugin) {
        super(plugin);
    }


    /**
     * @param sender sender
     * @param args args
     */
    public void execute(final CommandSender sender, String[] args)
    {
        final Player player = (sender instanceof Player) ? (Player)sender : null;

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                /*
                 * Si il faut faire quelque chose lors d'un reload alors mettre le code ici
                 * car le fait de faire un reload de SignShop appel les méthodes onDisable et onEnable de AdminSignShop
                 * */
                plugin.config.reloadConfig();
                plugin.messages.reloadConfig();

                if(signShop!=null)
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "SignShop reload");

                if(holographicDisplays!=null)
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "hd reload");

                if(itemStay!=null)
                    Bukkit.getServer().dispatchCommand(player, "ItemStay reload");

                if(langUtils!=null) {
                    Bukkit.getPluginManager().disablePlugin(langUtils);
                    Bukkit.getPluginManager().enablePlugin(langUtils);
                }

                /*Bukkit.getPluginManager().disablePlugin(plugin);
                Bukkit.getPluginManager().enablePlugin(plugin);*/

                pluginLogger.info(plugin.messages.reloaded);
                pluginLogger.help(plugin.messages.prefix() + ChatColor.RED + plugin.messages.reloaded, player);

            }

        }.runTask(plugin);

    }

}
