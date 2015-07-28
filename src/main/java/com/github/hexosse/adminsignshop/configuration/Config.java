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
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;

/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class Config
{
    private final transient EssentialsConf config;
    private final File dataFolder;
    private final YamlConfiguration worth;


    /* Plugin */
    public boolean useMetrics = (boolean) true;
    public boolean useUpdater = (boolean) true;

    /* Shop */
    public String worthFile = "Essentials/worth.yml";
    public double sellFactor = (double) 0.75;
    public double enchantmentFactor = (double) 0.12;
    public boolean buy = (boolean) true;
    public boolean sell = (boolean) false;
    public double defWorth = (double) 0;
    public String currencySymbol = "$";
    public String buySign = "iBuy";
    public String sellSign = "iSell";
    public String line1 = "";
    public String line2 = "";
    public String line12 = "\"%quantity% %book% %enchanted_item% %name% %enchanted_name%\"";

    /* Ground Item */
    public boolean groundItem = (boolean) true;
    public boolean holographicDisplays = (boolean) true;
    public boolean itemStay = (boolean) false;

    /* Message */
    public String message = "messages.yml";

    /* Localization */
    public String locale = "en_US";


    /**
     * @param dataFolder Plugin data folder
     */
    public Config(File dataFolder)
    {
        this.config = new EssentialsConf(new File(dataFolder, "config.yml"));
        this.config.setTemplateName("/config.yml");
        this.dataFolder = dataFolder;
        this.worth = new YamlConfiguration();

        reloadConfig();
    }

    public void reloadConfig()
    {
        config.load();

        useMetrics = config.getBoolean("plugin.useMetrics", true);
        useUpdater = config.getBoolean("plugin.useUpdater", true);

        worthFile = config.getString("shop.worthFile", "Essentials/worth.yml");
        sellFactor = config.getDouble("shop.sellFactor", 0.75);
        buy = config.getBoolean("shop.buy", true);
        sell = config.getBoolean("shop.sell", false);
        defWorth = (double) 0;
        currencySymbol = config.getString("shop.signs.currency-symbol", "$");
        buySign = config.getString("shop.signs.buy", "iBuy");
        sellSign = config.getString("shop.signs.sell","iSell");
        line1 = config.getString("shop.signs.line1","");
        line2 = config.getString("shop.signs.line2","");
        line12 = config.getString("shop.signs.line12","%quantity% %book% %enchanted_item% %name% %enchanted_name%");

        groundItem = config.getBoolean("groundItem.groundItem", true);
        holographicDisplays = config.getBoolean("groundItem.holographicDisplays", true);
        itemStay = config.getBoolean("groundItem.itemStay", false);

        message = config.getString("message", "messages.yml");

        locale = config.getString("locale", Locale.getDefault().toString());
    }


    /**
     * Test la validité du fichier worth.yml
     *
     * @throws InvalidConfigurationException Exception
     */
    public void CheckWorthFile() throws InvalidConfigurationException
    {
        String worthFileName = (dataFolder.getParent() + File.separator + worthFile);

		/* Check worth file */
        File wFile = new File(worthFileName);
        if(!wFile.exists())
        {
            AdminSignShop.getPlugin().getLogger().severe("Worth file not found.");
            Bukkit.getPluginManager().disablePlugin(AdminSignShop.getPlugin());
            throw new InvalidConfigurationException("Worth file not found.");
        }

		/* */
        try
        {
            worth.load(worthFileName);
        }
        catch (InvalidConfigurationException ex)
        {
            AdminSignShop.getPlugin().getLogger().log(Level.SEVERE,"An error occurs reading worth file: {0}", ex.getMessage());
            Bukkit.getPluginManager().disablePlugin(AdminSignShop.getPlugin());
            throw new InvalidConfigurationException(ex.getMessage());
        }
        catch (IOException ex)
        {
            AdminSignShop.getPlugin().getLogger().log(Level.SEVERE,"An error occurs reading worth file: {0}", ex.getMessage());
            Bukkit.getPluginManager().disablePlugin(AdminSignShop.getPlugin());
            throw new InvalidConfigurationException(ex.getMessage());
        }
    }

    /**
     * @return Worth
     */
    public YamlConfiguration getWorth() {
        return worth;
    }
}
