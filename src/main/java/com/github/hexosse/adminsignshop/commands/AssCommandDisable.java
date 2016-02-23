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
import com.github.hexosse.adminsignshop.shop.Creator;
import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import com.github.hexosse.pluginframework.pluginapi.message.Message;
import com.google.common.collect.Lists;
import org.bukkit.entity.Player;

/**
 * This file is part of AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class AssCommandDisable extends PluginCommand<AdminSignShop>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
	public AssCommandDisable(AdminSignShop plugin)
	{
		super("disable", plugin);
		this.setAliases(Lists.newArrayList("off"));
		this.setDescription(plugin.messages.helpDisable);
		this.setPermission(Permissions.ADMIN.toString());
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
        final Player player = commandInfo.getPlayer();
        Creator creator = plugin.shops.creators.get(player);

        if(creator!=null)
            creator.enable = false;

        if(!plugin.shops.creators.exist(player))
		{
			// Message
			Message message = new Message();
			message.setPrefix(plugin.messages.chatPrefix);
			message.add(plugin.messages.disable);
			messageManager.send(commandInfo.getSender(), message);
		}

		return true;
	}
}
