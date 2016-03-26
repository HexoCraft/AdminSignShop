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
import com.github.hexosse.adminsignshop.configuration.Permissions;
import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import com.google.common.collect.Lists;

/**
 * This file is part of AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class AssCommandGroundItem extends PluginCommand<AdminSignShop>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
    public AssCommandGroundItem(AdminSignShop plugin)
    {
        super("GroundItem", plugin);
        this.setAliases(Lists.newArrayList("groundItem", "gi"));
		this.setDescription("(Help)");
        this.setPermission(Permissions.ADMIN.toString());

		this.addSubCommand(new AssCommandGroundItemHelp(plugin));
		this.addSubCommand(new AssCommandGroundItemEnable(plugin));
		this.addSubCommand(new AssCommandGroundItemDisable(plugin));
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
		plugin.getServer().dispatchCommand(commandInfo.getSender(), "AdminSignShop GroundItem help");

		/*if(hasGroundItem()==false) return true;

		// Title line
		MessagePart help = new MessagePart(" " + MessageText.help_for_command + " \"" + commandInfo.getCommand().getParentCommand().getName() + "\" ").color(MessageColor.DESCRIPTION);
		MessagePart dash = new MessagePart(ChatColor.STRIKETHROUGH + StringUtils.leftPad("", (51-help.getText().length())/2, "-")).color(MessageColor.DESCRIPTION);
		Message m = new Message(new MessageLine().add(dash).add(help).add(dash));
		plugin.messageManager.send(commandInfo,new Message(""));
		plugin.messageManager.send(commandInfo,m);

		// Help lines


		onCommandHelp(null,commandInfo);

		com.github.hexosse.pluginframework.pluginapi.message.predifined.CommandHelp subHelp = new com.github.hexosse.pluginframework.pluginapi.message.predifined.CommandHelp(null, subCommandInfo);
		*/

		return true;
	}
}
