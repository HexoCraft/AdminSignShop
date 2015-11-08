package com.github.hexosse.adminsignshop.configuration;

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

import com.github.hexosse.adminsignshop.AdminSignShop;
import com.github.hexosse.baseplugin.config.BaseConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

@BaseConfig.ConfigHeader(comment = {
        "############################################################",
        "# | AdminSignShop by hexosse                             | #",
        "############################################################"
})
@BaseConfig.ConfigFooter(comment = {
        " ",
        " ",
        "############################################################"
})

public class Config extends BaseConfig<AdminSignShop>
{
    /* Plugin */
    @ConfigComment(path = "plugin")
    @ConfigOptions(path = "plugin.useMetrics")
    public boolean useMetrics = (boolean) true;
    @ConfigOptions(path = "plugin.useUpdater")
    public boolean useUpdater = (boolean) true;
    @ConfigOptions(path = "plugin.downloadUpdate")
    public boolean downloadUpdate = (boolean) true;

    /* Shop */
    @ConfigComment(path = "shop")
    @ConfigOptions(path = "shop.worthFile")
    public String worthFile = "Essentials/worth.yml";
    @ConfigOptions(path = "shop.sellFactor")
    public double sellFactor = (double) 0.75;
    @ConfigOptions(path = "shop.enchantmentFactor")
    public double enchantmentFactor = (double) 0.12;
    @ConfigOptions(path = "shop.buy")
    public boolean buy = (boolean) true;
    @ConfigOptions(path = "shop.sell")
    public boolean sell = (boolean) false;
    @ConfigOptions(path = "shop.defWorth")
    public double defWorth = (double) 0;

    @ConfigComment(path = "shop.signs")
    @ConfigOptions(path = "shop.signs.currencySymbol")
    public String currencySymbol = "$";
    @ConfigOptions(path = "shop.signs.buy")
    public String buySign = "iBuy";
    @ConfigOptions(path = "shop.signs.sell")
    public String sellSign = "iSell";
    @ConfigOptions(path = "shop.signs.line1")
    public String line1 = "";
    @ConfigOptions(path = "shop.signs.line2")
    public String line2 = "";
    @ConfigOptions(path = "shop.signs.line12")
    public String line12 = "\"%quantity% %book% %enchanted_item% %name% %enchanted_name%\"";

    /* Ground Item */
    @ConfigComment(path = "groundItem")
    @ConfigOptions(path = "groundItem.groundItem")
    public boolean groundItem = (boolean) true;
    @ConfigOptions(path = "groundItem.holographicDisplays")
    public boolean holographicDisplays = (boolean) true;
    @ConfigOptions(path = "groundItem.itemStay")
    public boolean itemStay = (boolean) false;

    /* Message */
    @ConfigOptions(path = "messages")
    public String messages = "messages.yml";

    /* Localization */
    @ConfigOptions(path = "locale")
    public String locale = "en_US";


    /* Variables locales */
    private final YamlConfiguration worth = new YamlConfiguration();


    /**
     * @param plugin The plugin that this object belong to.
     * @param dataFolder Folder that contains the config file
     * @param filename   Name of the config file
     */
    public Config(AdminSignShop plugin, File dataFolder, String filename)
    {
        super(plugin, new File(dataFolder, filename), filename);
    }

    public void reloadConfig() {
        load();
    }

    /**
     * Test la validité du fichier worth.yml
     *
     * @throws InvalidConfigurationException Exception
     */
    public void CheckWorthFile() throws InvalidConfigurationException
    {
        String worthFileName = (plugin.getDataFolder().getParent() + File.separator + worthFile);

		/* Check worth file */
        File wFile = new File(worthFileName);
        if(!wFile.exists()) {
            pluginLogger.fatal("Worth file not found.");
            Bukkit.getPluginManager().disablePlugin(plugin);
            throw new InvalidConfigurationException("Worth file not found.");
        }

		/* */
        try
        {
            worth.load(worthFileName);
        }
        catch (InvalidConfigurationException | IOException ex)
        {
            pluginLogger.fatal(MessageFormat.format("An error occurs reading worth file: {0}", ex.getMessage()));
            Bukkit.getPluginManager().disablePlugin(plugin);
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
