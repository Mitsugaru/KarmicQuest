package com.mitsugaru.KarmicQuest.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;

import com.mitsugaru.KarmicQuest.KarmicQuest;

public class RootConfig
{
	private KarmicQuest plugin;
	public boolean debugTime;
	
	public RootConfig(KarmicQuest plugin)
	{
		this.plugin = plugin;
		final ConfigurationSection config = plugin.getConfig();
		// LinkedHashmap of defaults
		final Map<String, Object> defaults = new LinkedHashMap<String, Object>();
		// TODO defaults
		defaults.put("debug.time", false);
		defaults.put("version", plugin.getDescription().getVersion());
		// Insert defaults into config file if they're not present
		for (final Entry<String, Object> e : defaults.entrySet())
		{
			if (!config.contains(e.getKey()))
			{
				config.set(e.getKey(), e.getValue());
			}
		}
		// Save config
		plugin.saveConfig();
		// Load settings
		loadSettings(config);
	}
	
	public void reloadConfig()
	{
		// Initial relaod
		plugin.reloadConfig();
		// Grab config
		final ConfigurationSection config = plugin.getConfig();
		// Load settings
		loadSettings(config);
		plugin.getLogger().info("Config reloaded");
	}
	
	private void loadSettings(ConfigurationSection config)
	{
		debugTime = config.getBoolean("debug.time", false);
	}
}
