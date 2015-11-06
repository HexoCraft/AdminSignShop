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

package com.github.hexosse.adminsignshop;

import com.github.hexosse.adminsignshop.commands.Commands;
import com.github.hexosse.adminsignshop.configuration.Config;
import com.github.hexosse.adminsignshop.configuration.Messages;
import com.github.hexosse.adminsignshop.configuration.Permissions;
import com.github.hexosse.adminsignshop.events.BlockListener;
import com.github.hexosse.adminsignshop.events.PlayerListener;
import com.github.hexosse.adminsignshop.shop.Shops;
import com.github.hexosse.baseplugin.BasePlugin;
import com.github.hexosse.githubupdater.GitHubUpdater;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;



/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class AdminSignShop extends BasePlugin
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
			permissions = new Permissions(this);
			shops = new Shops(this);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }


		/* Enregistrement des listeners */
		Bukkit.getPluginManager().registerEvents(new BlockListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
			
		/* Enregistrement du gestionnaire de commandes */
		this.getCommand("ass").setExecutor(new Commands(this));
		this.getCommand("ass").setTabCompleter(new Commands(this));

        /* Updater */
		if(config.useUpdater)
			RunUpdater(config.downloadUpdate);

        /* Metrics */
		if(config.useMetrics)
			RunMetrics();
	}

	/**
	 * Désactivation du plugin
	 */
	@Override
	public void onDisable()
	{
        super.onDisable();
	}

	public void RunUpdater(final boolean download)
	{
		GitHubUpdater updater = new GitHubUpdater(this, this.repository, this.getFile(), download?GitHubUpdater.UpdateType.DEFAULT:GitHubUpdater.UpdateType.NO_DOWNLOAD, true);
	}

	private void RunMetrics()
	{
		try
		{
			com.github.hexosse.baseplugin.metric.MetricsLite metrics = new com.github.hexosse.baseplugin.metric.MetricsLite(this);
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
