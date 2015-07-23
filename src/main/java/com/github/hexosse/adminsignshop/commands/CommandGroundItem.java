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
import com.github.hexosse.adminsignshop.configuration.Messages;
import com.github.hexosse.adminsignshop.configuration.Permissions;
import com.github.hexosse.adminsignshop.shop.Creator;
import com.github.hexosse.adminsignshop.shop.Shops;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This file is part of AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class CommandGroundItem
{
    private final static Messages messages = AdminSignShop.getMessages();
    private final static Shops shops = AdminSignShop.getShops();

    /**
     * @param sender sender
     * @param args args
     */
    public static void execute(CommandSender sender, String[] args)
    {
        if (!Permissions.has(sender, Permissions.ADMIN))
        {
            sender.sendMessage(messages.prefix(messages.AccesDenied));
            return;
        }

        Player player = (Player) sender;
        Creator creator = shops.creators.get(player);

        if (creator != null)
        {
            if (args[1].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("on") || args[1].equalsIgnoreCase("1"))
                creator.groundItem = true;
            else if (args[1].equalsIgnoreCase("disable") || args[1].equalsIgnoreCase("off") || args[1].equalsIgnoreCase("0"))
                creator.groundItem = false;

            if (args[1].equalsIgnoreCase("holographicDisplays") || args[1].equalsIgnoreCase("hd"))
            {
                creator.holographicDisplays = true;
                creator.itemStay = false;
            } else if (args[1].equalsIgnoreCase("itemStay") || args[1].equalsIgnoreCase("is"))
            {
                creator.holographicDisplays = false;
                creator.itemStay = true;
            }
        } else
            sender.sendMessage(messages.prefix(messages.not_enabled));
    }
}
