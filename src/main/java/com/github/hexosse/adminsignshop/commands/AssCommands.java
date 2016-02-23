package com.github.hexosse.adminsignshop.commands;

/*
 * Copyright 2016 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.github.hexosse.adminsignshop.AdminSignShop;
import com.github.hexosse.adminsignshop.shop.Creator;
import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import com.github.hexosse.pluginframework.pluginapi.message.Message;
import com.github.hexosse.pluginframework.pluginapi.message.MessageColor;
import com.github.hexosse.pluginframework.pluginapi.message.MessagePart;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AssCommands extends PluginCommand<AdminSignShop>
{
	private AssCommandHelp cmdHelp = null;
	private AssCommandEnable cmdEnable = null;
	private AssCommandDisable cmdDisable = null;
	private AssCommandBuy cmdBuy = null;
	private AssCommandSell cmdSell = null;
	private AssCommandWorth cmdWorth = null;
	private AssCommandGroundItem cmdGrountItem = null;
	private AssCommandReload cmdReload = null;


	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public AssCommands(AdminSignShop plugin)
	{
		super("AdminSignShop", plugin);
		this.setAliases(Lists.newArrayList("ass"));
		this.setDescription("(Help)");

		this.addSubCommand(new AssCommandHelp(plugin));
		this.addSubCommand(new AssCommandEnable(plugin));
		this.addSubCommand(new AssCommandDisable(plugin));
		this.addSubCommand(new AssCommandBuy(plugin));
		this.addSubCommand(new AssCommandSell(plugin));
		this.addSubCommand(new AssCommandWorth(plugin));
		this.addSubCommand(new AssCommandGroundItem(plugin));
		this.addSubCommand(new AssCommandReload(plugin));
	}

	/**
	 * Executes the given command, returning its success
	 *
	 * @param commandInfo Info about the command
	 *
	 * @return true if a valid command, otherwise false
	 */
	@Override
	public boolean onCommand(CommandInfo commandInfo)
	{
		//plugin.getServer().dispatchCommand(commandInfo.getSender(), "AdminSignShop help");

		final Player player = commandInfo.getPlayer();
		Creator creator = plugin.shops.creators.get(player);

		MessagePart dash = new MessagePart(ChatColor.STRIKETHROUGH + StringUtils.leftPad("", 51, "-")).color(MessageColor.DESCRIPTION);

		if(creator==null)
		{
			Message message = new Message();
			message.add(dash);
			message.add("Plugin : " + ChatColor.AQUA + "off");
			message.add("Shop : " + ChatColor.AQUA + "disable");
			message.add("Worth : " + ChatColor.AQUA + "disable");
			message.add("GroundItem : " + ChatColor.AQUA + "disable");
			message.add(dash);
			messageManager.send(commandInfo.getSender(), message);
		}
		else
		{
			Message message = new Message();
			message.add(dash);
			message.add("Plugin : " + ChatColor.AQUA + (creator.enable ? "on" : "off"));
			message.add("Shop : " + ChatColor.AQUA + (creator.buy ? "Buy" : "Sell"));
			message.add("Worth : " + ChatColor.AQUA + Double.toString(creator.defWorth));
			message.add("GroundItem : " + ChatColor.AQUA + (creator.groundItem ? "on" : "off"));
			message.add(dash);
			messageManager.send(commandInfo.getSender(), message);
		}

		return true;
	}
}
