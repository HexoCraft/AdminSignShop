package com.github.hexosse.adminsignshop;

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

import com.github.hexosse.adminsignshop.commands.AssCommands;
import com.github.hexosse.adminsignshop.configuration.Config;
import com.github.hexosse.adminsignshop.configuration.Messages;
import com.github.hexosse.adminsignshop.configuration.Permissions;
import com.github.hexosse.adminsignshop.listeners.BlockListener;
import com.github.hexosse.adminsignshop.listeners.PlayerListener;
import com.github.hexosse.adminsignshop.shop.Shops;
import com.github.hexosse.githubupdater.GitHubUpdater;
import com.github.hexosse.pluginframework.pluginapi.Plugin;
import com.github.hexosse.pluginframework.pluginapi.message.Message;
import com.github.hexosse.pluginframework.pluginapi.metric.MetricsLite;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;



/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class AdminSignShop extends Plugin
{
	public Config config = null;
	public Messages messages = null;
	public Permissions permissions = null;
	public Shops shops = null;
	private String repository = "hexosse/AdminSignShop";


	/**
	 * Activation du plugin
	 */
	@Override
	public void onEnable()
	{
        /* Chargement de la config */
        try {
			config = new Config(this, getDataFolder(), "config.yml");			config.load(); 		config.CheckWorthFile();
			messages = new Messages(this, getDataFolder(), "messages.yml");		messages.load();
			shops = new Shops(this);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

		/* Enregistrement des listeners */
		Bukkit.getPluginManager().registerEvents(new BlockListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
			
        /* Enregistrement du gestionnaire de commandes */
		registerCommands(new AssCommands(this));

        /* Updater */
		if(config.useUpdater)
			RunUpdater(config.downloadUpdate);

        /* Metrics */
		if(config.useMetrics)
			RunMetrics();

		/* Console message */
		Message message = new Message();
		message.setPrefix("§3[§b" + this.getDescription().getName() + " " + this.getDescription().getVersion() + "§3]§r");
		message.add(new Message(ChatColor.YELLOW, "Enable"));
		messageManager.send(Bukkit.getConsoleSender(), message);
	}

	/**
	 * Désactivation du plugin
	 */
	@Override
	public void onDisable()
	{
        super.onDisable();

		/* Console message */
		Message message = new Message();
		message.setPrefix("§3[§b" + this.getDescription().getName() + " " + this.getDescription().getVersion() + "§3]§r");
		message.add(new Message(ChatColor.YELLOW, "Disabled"));
		messageManager.send(Bukkit.getConsoleSender(), message);
	}

	public void RunUpdater(final boolean download)
	{
		GitHubUpdater updater = new GitHubUpdater(this, this.repository, this.getFile(), download?GitHubUpdater.UpdateType.DEFAULT:GitHubUpdater.UpdateType.NO_DOWNLOAD, true);
	}

	private void RunMetrics()
	{
		try
		{
			MetricsLite metrics = new MetricsLite(this);
			if(metrics.start())
				pluginLogger.info("Succesfully started Metrics, see http://mcstats.org for more information.");
			else
				pluginLogger.info("Could not start Metrics, see http://mcstats.org for more information.");
		} catch (IOException e)
		{
			// Failed to submit the stats :-(
		}
	}
}
