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

import com.github.hexosse.adminsignshop.configuration.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.hexosse.adminsignshop.AdminSignShop;
import com.github.hexosse.adminsignshop.configuration.Messages;
import com.github.hexosse.adminsignshop.configuration.Permissions;
import com.github.hexosse.adminsignshop.shop.Shops;

public class Commands implements CommandExecutor
{
	private final static AdminSignShop plugin = AdminSignShop.getPlugin();
	private final static Config config = AdminSignShop.getConfiguration();
	private final static Messages messages = AdminSignShop.getMessages();
	private final static Shops shops = AdminSignShop.getShops();

	
	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args)
	{
        if (!Permissions.has(sender, Permissions.ADMIN))
        {
            sender.sendMessage(messages.prefix(messages.AccesDenied));
            return true;
        }


		if(cmd.getName().equalsIgnoreCase("ass"))
		{
			if (!(sender instanceof Player))
		        sender.sendMessage(ChatColor.GRAY + AdminSignShop.getPluginName() + "'s version is: " + ChatColor.GREEN + AdminSignShop.getVersion());

			else if(args.length == 0 || args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("help"))
				CommandHelp.execute(sender,args);

			else if(args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("on"))
				CommandEnable.execute(sender,args);

			else if(args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("off"))
				CommandDisable.execute(sender,args);

			else if(args[0].equalsIgnoreCase("buy"))
				CommandBuy.execute(sender,args);

			else if(args[0].equalsIgnoreCase("sell"))
				CommandSell.execute(sender,args);

			else if(args[0].equalsIgnoreCase("groundItem"))
				CommandGroundItem.execute(sender,args);

			else if(args[0].equalsIgnoreCase("reload"))
				CommandReload.execute(sender,args);
		}

        return true;
	}
}
