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
public class CommandEnable
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

        if(creator==null)
            shops.creators.add(player);
        else
            creator.enable = true;

        if(shops.creators.exist(player))
            sender.sendMessage(messages.prefix(messages.enable));
    }
}
