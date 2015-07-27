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
import com.github.hexosse.adminsignshop.events.BlockListener;
import com.github.hexosse.adminsignshop.events.PlayerListener;
import com.github.hexosse.adminsignshop.metrics.MetricsLite;
import com.github.hexosse.adminsignshop.shop.Shops;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;


/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class AdminSignShop extends JavaPlugin
{
	private static AdminSignShop plugin;
    private static Server server;
    private static PluginDescriptionFile description;

	private static Config config;
	private static Messages messages;
    private static Shops shop;

	
	
    // Constructeur
	public AdminSignShop()
	{
		description = getDescription();
		server = getServer();
		plugin = this;
	}

	// Activation du plugin
	@Override
	public void onEnable()
	{
		try
		{
			/* Configuration du plugin */
			config = new Config(getDataFolder());
			config.CheckWorthFile();

			/* Messages du plugin */
			messages = new Messages(getDataFolder());

			/* Gestionnaire des shops */
			shop = new Shops();			
			
			/* Enregistrement des listeners */
			Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
			Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
			
			/* Enregistrement du gestionnaire de commandes */
			this.getCommand("ass").setExecutor(new Commands());

			/* Metrics */
            if(config.useMetrics)
            {
                try
                {
                    MetricsLite metrics = new MetricsLite(this);
                    if(metrics.start())
                        getLogger().info("Succesfully started Metrics, see http://mcstats.org for more information.");
                    else
                        getLogger().info("Could not start Metrics, see http://mcstats.org for more information.");
                } catch (IOException e)
                {
                    // Failed to submit the stats :-(
                }
            }

			/* Updater */
            if(config.useUpdater)
            {
            }
        }
		catch (InvalidConfigurationException ex)
		{
			ex.printStackTrace();
			getLogger().warning("Error while enabling the plugin... Check the stacktrace above.");
		}
        catch (Exception ex)
		{
			ex.printStackTrace();
			getLogger().warning("Error while enabling the plugin... Check the stacktrace above.");
		}
	}

	// Désactivation du plugin
	@Override
	public void onDisable()
	{
        super.onDisable();
	}

    
	
	
	// Instance du plugin
	public static AdminSignShop getPlugin()
	{
		return plugin;
	}

	// Version du plugin
    public static String getVersion()
    {
        return description.getVersion();
    }

    // Nom du plugin
    public static String getPluginName()
    {
        return description.getName();
    }

    // Liste des dépendances du plugin
    public static List<String> getDependencies()
    {
        return description.getSoftDepend();
    }

    //
	public static Shops getShops()
	{
		return shop;
	}

	//
	public static Config getConfiguration()
	{
		return config;
	}

	//
	public static Messages getMessages()
	{
		return messages;
	}
}
