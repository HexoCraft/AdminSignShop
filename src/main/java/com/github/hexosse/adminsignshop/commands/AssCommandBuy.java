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

/**
 * This file is part of AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class AssCommandBuy extends PluginCommand<AdminSignShop>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
    public AssCommandBuy(AdminSignShop plugin)
    {
        super("buy", plugin);
		this.setDescription(plugin.messages.helpBuy);
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
        Creator creator = plugin.shops.creators.get(commandInfo.getPlayer());

        if(creator!=null && creator.enable)
        {
            creator.buy = true;
            creator.sell = false;
        }

		return true;
	}
}
