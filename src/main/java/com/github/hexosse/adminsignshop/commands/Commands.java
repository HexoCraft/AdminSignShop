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
import com.github.hexosse.adminsignshop.configuration.Permissions;
import com.github.hexosse.baseplugin.BaseObject;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.hexosse.adminsignshop.utils.plugins.HolographicDisplaysUtil.getHolographicDisplaysPlugin;
import static com.github.hexosse.adminsignshop.utils.plugins.HolographicDisplaysUtil.hasHolographicDisplays;
import static com.github.hexosse.adminsignshop.utils.plugins.ItemStayUtil.hasItemStay;

public class Commands extends BaseObject<AdminSignShop> implements CommandExecutor, TabCompleter
{
	private CommandHelp cmdHelp = null;
	private CommandEnable cmdEnable = null;
	private CommandDisable cmdDisable = null;
	private CommandBuy cmdBuy = null;
	private CommandSell cmdSell = null;
	private CommandWorth cmdWorth = null;
	private CommandGroundItem cmdGrountItem = null;
	private CommandReload cmdReload = null;


	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public Commands(AdminSignShop plugin)
	{
		super(plugin);
		cmdHelp = new CommandHelp(plugin);
		cmdEnable = new CommandEnable(plugin);
		cmdDisable = new CommandDisable(plugin);
		cmdBuy = new CommandBuy(plugin);
		cmdSell = new CommandSell(plugin);
		cmdWorth = new CommandWorth(plugin);
		cmdGrountItem = new CommandGroundItem(plugin);
		cmdReload = new CommandReload(plugin);
	}

	
	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player)) {
			pluginLogger.info(ChatColor.GRAY + plugin.getDescription().getName() + "'s version is: " + ChatColor.GREEN + plugin.getDescription().getVersion());
			return false;
		}

		if(!Permissions.has(sender, Permissions.ADMIN))
		{
			pluginLogger.help(ChatColor.RED + plugin.messages.accesDenied, (Player)sender);
			return false;
		}


		if(cmd.getName().equalsIgnoreCase("ass"))
		{
			if(args.length == 0 || args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?"))
				cmdHelp.execute(sender, args);

			else if(args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("on"))
				cmdEnable.execute(sender,args);

			else if(args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("off"))
				cmdDisable.execute(sender,args);

			else if(args[0].equalsIgnoreCase("buy"))
				cmdBuy.execute(sender,args);

			else if(args[0].equalsIgnoreCase("sell"))
				cmdSell.execute(sender,args);

			else if(args[0].equalsIgnoreCase("worth"))
				cmdWorth.execute(sender,args);

			else if( (args[0].equalsIgnoreCase("groundItem") || args[0].equalsIgnoreCase("gi")) && (hasHolographicDisplays() || hasItemStay()))
				cmdGrountItem.execute(sender,args);

			else if(args[0].equalsIgnoreCase("reload"))
				cmdReload.execute(sender,args);
		}

        return true;
	}

	/**
	 * Requests a list of possible completions for a command argument.
	 *
	 * @param sender  Source of the command
	 * @param command Command which was executed
	 * @param alias   The alias used
	 * @param args    The arguments passed to the command, including final
	 *                partial argument to be completed and command label
	 * @return A List of possible completions for the final argument, or null
	 * to default to the command executor
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        List<String> fList = Lists.newArrayList();

        List<String> list = new ArrayList<String>(Arrays.asList("help", "enable", "disable", "buy", "sell", "worth", "reload"));
        if(hasHolographicDisplays() || hasItemStay())
            list.add("groundItem");
        java.util.Collections.sort(list);

        List<String> groundItemList = new ArrayList<String>(Arrays.asList("enable", "disable"));
        if(hasHolographicDisplays())
            groundItemList.add("holographicDisplays");
        if(hasItemStay())
            groundItemList.add("itemStay");
        java.util.Collections.sort(groundItemList);

		// Commandes de base
		if(args.length==1)
		{
            if(args[0].isEmpty())
                return list;

			for(String s : list)
				if(s.toLowerCase().startsWith(args[0])) fList.add(s);
		}

        // GroundItem
		else if(args.length==2 && args[0].toLowerCase().compareToIgnoreCase("groundItem")==0)
		{
            if(args[1].isEmpty())
                return groundItemList;

            for(String s : groundItemList)
				if(s.toLowerCase().startsWith(args[1])) fList.add(s);
		}

		return fList;
	}
}
