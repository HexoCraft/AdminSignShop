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
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import com.github.hexosse.pluginframework.pluginapi.command.predifined.CommandReload;
import com.github.hexosse.pluginframework.pluginapi.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.github.hexosse.adminsignshop.utils.plugin.GroundItemUtil.getGroundItem;
import static com.github.hexosse.adminsignshop.utils.plugin.LangUtilsUtil.getLangUtilsPlugin;
import static com.github.hexosse.adminsignshop.utils.plugin.SignShopUtil.getSignShopPlugin;

/**
 * This file is part of AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class AssCommandReload extends CommandReload<AdminSignShop>
{
    private static Plugin signShop = getSignShopPlugin();
    private static Plugin groundItem = getGroundItem();
    private static Plugin langUtils = getLangUtilsPlugin();

    /**
     * @param plugin The plugin that this object belong to.
     */
    public AssCommandReload(AdminSignShop plugin)
    {
        super(plugin, Permissions.ADMIN.toString());
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

		super.onCommand(commandInfo);

		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				if(groundItem!=null){
					Bukkit.getPluginManager().disablePlugin(groundItem);
					Bukkit.getPluginManager().enablePlugin(groundItem);
				}

				if(langUtils!=null) {
					Bukkit.getPluginManager().disablePlugin(langUtils);
					Bukkit.getPluginManager().enablePlugin(langUtils);
				}

				// Log
				pluginLogger.info(plugin.messages.reloaded);

				// Message
				Message message = new Message();
				message.setPrefix(plugin.messages.chatPrefix);
				message.add(plugin.messages.reloaded);
				messageManager.send(player, message);
			}

		}.runTask(plugin);

		return true;
	}
}
