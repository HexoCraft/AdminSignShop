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
import com.github.hexosse.adminsignshop.configuration.Config;
import com.github.hexosse.adminsignshop.shop.Creator;
import com.github.hexosse.adminsignshop.shop.Shops;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This file is part of AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class CommandHelp
{
    private final static AdminSignShop plugin = AdminSignShop.getPlugin();
    private final static Config config = AdminSignShop.getConfiguration();
    private final static Shops shops = AdminSignShop.getShops();

    /**
     * @param sender sender
     * @param args args
     */
    public static void execute(CommandSender sender, String[] args)
    {
        Creator creator = shops.creators.get((Player)sender);

        sender.sendMessage("-----------------------------------------------------");
        sender.sendMessage(ChatColor.RED + plugin.getDescription().getName() + " help");
        sender.sendMessage(ChatColor.AQUA + "/ass help :" + ChatColor.WHITE + "AdminSignShop help");
        sender.sendMessage(ChatColor.AQUA + "/ass [enable|on] :" + ChatColor.WHITE + " Enable AdminShop creation");
        sender.sendMessage(ChatColor.AQUA + "/ass [disable|off] :" + ChatColor.WHITE + " Disable AdminSignShop creation");
        sender.sendMessage(ChatColor.AQUA + "/ass buy :" + ChatColor.WHITE + " Enable user to create a [Buy] shop");
        sender.sendMessage(ChatColor.AQUA + "/ass sell :" + ChatColor.WHITE + " Enable user to create a [Sell] shop");
        sender.sendMessage(ChatColor.AQUA + "/ass groundItem [enable|on] :" + ChatColor.WHITE + " Enable use ground item display");
        sender.sendMessage(ChatColor.AQUA + "/ass groundItem [disable|off] :" + ChatColor.WHITE + " Disable use ground item display");
        sender.sendMessage(ChatColor.AQUA + "/ass groundItem holographicDisplays :" + ChatColor.WHITE + " use holographicDisplay for ground item display");
        sender.sendMessage(ChatColor.AQUA + "/ass groundItem itemStay :" + ChatColor.WHITE + " use itemStay for ground item display");
        sender.sendMessage(ChatColor.AQUA + "/ass reload :" + ChatColor.WHITE + " Reload AdminSignShop");
        sender.sendMessage("");

        if(creator!=null)
        {
            sender.sendMessage(ChatColor.GOLD + "plugin : " + ChatColor.AQUA + "on");
            sender.sendMessage(ChatColor.GOLD + "buy : " + ChatColor.AQUA + (creator.buy?"enable":"disable"));
            sender.sendMessage(ChatColor.GOLD + "sell : " + ChatColor.AQUA + (creator.sell?"enable":"disable"));
            sender.sendMessage(ChatColor.GOLD + "groundItem : " + ChatColor.AQUA + (creator.groundItem?"enable":"disable"));
            if(creator.holographicDisplays)
                sender.sendMessage(ChatColor.GOLD + "holographicDisplays : " + ChatColor.AQUA + "active");
            if(creator.itemStay)
                sender.sendMessage(ChatColor.GOLD + "itemStay : " + ChatColor.AQUA + "active");
        }
        else
        {
            sender.sendMessage(ChatColor.GOLD + "plugin : " + ChatColor.AQUA + "off");
            sender.sendMessage(ChatColor.GOLD + "buy : " + ChatColor.AQUA + (config.buy?"enable":"disable"));
            sender.sendMessage(ChatColor.GOLD + "sell : " + ChatColor.AQUA + (config.sell?"enable":"disable"));
            sender.sendMessage(ChatColor.GOLD + "groundItem : " + ChatColor.AQUA + (config.groundItem?"enable":"disable"));
            if(config.holographicDisplays)
                sender.sendMessage(ChatColor.GOLD + "holographicDisplays : " + ChatColor.AQUA + "active");
            if(config.itemStay)
                sender.sendMessage(ChatColor.GOLD + "itemStay : " + ChatColor.AQUA + "active");
        }
        sender.sendMessage("-----------------------------------------------------");
    }
}
