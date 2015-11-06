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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This file is part of AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class CommandWorth extends BaseArgsCommand<AdminSignShop>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
    public CommandWorth(AdminSignShop plugin) {
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

        if(creator!=null && creator.enable)
        {
            if(args.length != 2)
            {
                pluginLogger.help(plugin.messages.prefix() + plugin.messages.worth_syntax, player);
                return;
            }

            creator.defWorth = Double.parseDouble(args[1]);
        }
        else
            pluginLogger.help(plugin.messages.prefix() + plugin.messages.not_enabled, player);
    }
}
