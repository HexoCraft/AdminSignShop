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

package com.github.hexosse.adminsignshop.configuration;

import com.github.hexosse.adminsignshop.AdminSignShop;
import com.github.hexosse.adminsignshop.Utils.Essentials.EssentialsConf;
import org.bukkit.ChatColor;

import java.io.File;


/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class Messages
{
    private final static Config config = AdminSignShop.getConfiguration();

	private final transient EssentialsConf essConfig;
	private final File dataFolder;


    /* Chat */
	public String prefix;

    /* Errors */
    public String AccesDenied;

	/* Messages */
	public String enable;
	public String disable;
	public String not_enabled;
	public String reloaded;
	public String worth_syntax;


	/**
	 * @param dataFolder
	 */
	public Messages(File dataFolder)
	{
		this.essConfig = new EssentialsConf(new File(dataFolder, config.message));
		this.essConfig.setTemplateName("/messages.yml");
		this.dataFolder = dataFolder;

		reloadConfig();
	}

	/**
	 *
	 */
	public void reloadConfig()
	{
		essConfig.load();

		prefix = essConfig.getString("chat.prefix", ChatColor.GREEN + "[AdminSignShop] " + ChatColor.WHITE);

		AccesDenied = essConfig.getString("errors.AccesDenied", "You don't have permission to do that!");

		enable = essConfig.getString("messages.enable", "AdminSignShop is enable");
		disable = essConfig.getString("messages.disable", "AdminSignShop is disable");
		not_enabled = essConfig.getString("messages.not_enabled", "AdminSignShop must be enable");
		reloaded = essConfig.getString("messages.reloaded", "AdminSignShop has been reloaded");
		worth_syntax = essConfig.getString("messages.worth_syntax", "Use AdminSignShop worth <number>");
	}

	/**
	 * @param message
	 * @return
	 */
    public String prefix(String message) {
        return ChatColor.translateAlternateColorCodes('&', prefix + message);
    }
}
