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
import com.github.hexosse.adminsignshop.Utils.LocationUtil;
import com.github.hexosse.adminsignshop.configuration.Messages;
import com.github.hexosse.adminsignshop.configuration.Permissions;
import com.github.hexosse.adminsignshop.grounditem.GroundItem;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.wargamer2010.signshop.SignShop;

/**
 * This file is part of AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class CommandReload
{
    private final static AdminSignShop plugin = AdminSignShop.getPlugin();
    private final static Messages messages = AdminSignShop.getMessages();

    private static Plugin signShop = plugin.getServer().getPluginManager().getPlugin("SignShop");
    private static Plugin holographicDisplays = plugin.getServer().getPluginManager().getPlugin("HolographicDisplays");
    private static Plugin itemStay = plugin.getServer().getPluginManager().getPlugin("ItemStay");
    private static Plugin langUtils = plugin.getServer().getPluginManager().getPlugin("LangUtils");
    /**
     * @param sender sender
     * @param args args
     */
    public static void execute(final CommandSender sender, String[] args)
    {
        if (!Permissions.has(sender, Permissions.ADMIN))
        {
            sender.sendMessage(messages.prefix(messages.AccesDenied));
            return;
        }

        final Player player = (Player) sender;

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                /*
                 * Si il faut faire quelque chose lors d'un reload alors mettre le code ici
                 * car le fait de faire un reload de SignShop appel les méthodes onDisable et onEnable de AdminSignShop
                 * */

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

                player.sendMessage(messages.prefix(messages.reloaded));
            }

        }.runTask(plugin);

    }
}
