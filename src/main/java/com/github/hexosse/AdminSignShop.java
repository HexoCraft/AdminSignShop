package com.github.hexosse;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by hexosse.
 */
public class AdminSignShop extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        getLogger().info("On passe dans onEnable");
    }

    @Override
    public void onDisable()
    {
        getLogger().info("On passe dans onDisable");
    }
}
