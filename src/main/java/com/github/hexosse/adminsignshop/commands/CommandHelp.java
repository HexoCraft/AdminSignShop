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
import com.github.hexosse.adminsignshop.shop.Creator;
import com.github.hexosse.baseplugin.command.BaseArgsCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.github.hexosse.adminsignshop.utils2.HolographicDisplaysUtil.hasHolographicDisplays;
import static com.github.hexosse.adminsignshop.utils2.ItemStayUtil.hasItemStay;

/**
 * This file is part of AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class CommandHelp extends BaseArgsCommand<AdminSignShop>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
    public CommandHelp(AdminSignShop plugin) {
        super(plugin);
    }

    /**
     * @param sender sender
     * @param args args
     */
    public void execute(CommandSender sender, String[] args)
    {
        final Player player = (sender instanceof Player) ? (Player)sender : null;
        Creator creator = plugin.shops.creators.get(player);

        if(args.length>0) {
            pluginLogger.help(ChatColor.YELLOW + "-----------------------------------------------", player);
            pluginLogger.help(ChatColor.YELLOW + plugin.getDescription().getName() + " help", player);
            pluginLogger.help(ChatColor.AQUA + "/ass " + ChatColor.GREEN + "[enable|on] :" + ChatColor.WHITE + " Enable AdminShop creation", player);
            pluginLogger.help(ChatColor.AQUA + "/ass " + ChatColor.GREEN + "[disable|off] :" + ChatColor.WHITE + " Disable AdminSignShop creation", player);
            pluginLogger.help(ChatColor.AQUA + "/ass " + ChatColor.GREEN + "[buy] :" + ChatColor.WHITE + " Enable user to create a [iBuy] shop", player);
            pluginLogger.help(ChatColor.AQUA + "/ass " + ChatColor.GREEN + "[sell] :" + ChatColor.WHITE + " Enable user to create a [iSell] shop", player);
            if (hasHolographicDisplays() || hasItemStay()) {
                pluginLogger.help(ChatColor.AQUA + "/ass groundItem " + ChatColor.GREEN + "[enable|on] :" + ChatColor.WHITE + " Enable use ground item display", player);
                pluginLogger.help(ChatColor.AQUA + "/ass groundItem " + ChatColor.GREEN + "[disable|off] :" + ChatColor.WHITE + " Disable use ground item display", player);
            }
            if (hasHolographicDisplays())
                pluginLogger.help(ChatColor.AQUA + "/ass groundItem " + ChatColor.GREEN + "[holographicDisplays] :" + ChatColor.WHITE + " use holographicDisplay for ground item display", player);
            if (hasItemStay())
                pluginLogger.help(ChatColor.AQUA + "/ass groundItem " + ChatColor.GREEN + "[itemStay] :" + ChatColor.WHITE + " use itemStay for ground item display", player);
            pluginLogger.help(ChatColor.AQUA + "/ass " + ChatColor.GREEN + "[reload] :" + ChatColor.WHITE + " Reload AdminSignShop", player);
            pluginLogger.help(ChatColor.YELLOW + "-----------------------------------------------", player);
        }
        else
        {
            pluginLogger.help(ChatColor.YELLOW + "-----------------------------------------------", player);
            if(creator!=null)
            {
                pluginLogger.help(ChatColor.YELLOW + "plugin : " + (creator.enable ? ChatColor.GREEN : ChatColor.RED) + (creator.enable ? "enable" : "disable"), player);
                pluginLogger.help(ChatColor.YELLOW + "buy : " + (creator.buy ? ChatColor.GREEN : ChatColor.RED) + (creator.buy ? "enable" : "disable"), player);
                pluginLogger.help(ChatColor.YELLOW + "sell : " + (creator.sell ? ChatColor.GREEN : ChatColor.RED) + (creator.sell ? "enable" : "disable"), player);
                if (hasHolographicDisplays() || hasItemStay())
                    pluginLogger.help(ChatColor.YELLOW + "groundItem : " + (creator.groundItem ? ChatColor.GREEN : ChatColor.RED) + (creator.groundItem ? "enable" : "disable"), player);
                if (hasHolographicDisplays())
                    pluginLogger.help(ChatColor.YELLOW + "holographicDisplays : " + (creator.holographicDisplays ? ChatColor.GREEN : ChatColor.RED) + (creator.holographicDisplays ? "active" : "inactive"), player);
                if (hasItemStay())
                    pluginLogger.help(ChatColor.YELLOW + "itemStay : " + (creator.itemStay ? ChatColor.GREEN : ChatColor.RED) + (creator.itemStay ? "active" : "inactive"), player);
            }
            else
            {
                pluginLogger.help(ChatColor.YELLOW + "plugin : " + ChatColor.RED + "off", player);
                pluginLogger.help(ChatColor.YELLOW + "buy : " + (plugin.config.buy ? ChatColor.GREEN : ChatColor.RED) + (plugin.config.buy ? "enable" : "disable"), player);
                pluginLogger.help(ChatColor.YELLOW + "sell : " + (plugin.config.sell ? ChatColor.GREEN : ChatColor.RED) + (plugin.config.sell ? "enable" : "disable"), player);
                if (hasHolographicDisplays() || hasItemStay())
                    pluginLogger.help(ChatColor.YELLOW + "groundItem : " + (plugin.config.groundItem ? ChatColor.GREEN : ChatColor.RED) + (plugin.config.groundItem ? "enable" : "disable"), player);
                if (hasHolographicDisplays())
                    pluginLogger.help(ChatColor.YELLOW + "holographicDisplays : " + (plugin.config.holographicDisplays ? ChatColor.GREEN : ChatColor.RED) + (plugin.config.holographicDisplays ? "active" : "inactive"), player);
                if (hasItemStay())
                    pluginLogger.help(ChatColor.YELLOW + "itemStay : " + (plugin.config.itemStay ? ChatColor.GREEN : ChatColor.RED) + (plugin.config.itemStay ? "active" : "inactive"), player);
            }
            pluginLogger.help(ChatColor.YELLOW + "-----------------------------------------------", player);
        }
    }
}
