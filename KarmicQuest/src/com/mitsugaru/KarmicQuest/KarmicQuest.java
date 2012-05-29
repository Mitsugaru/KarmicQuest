package com.mitsugaru.KarmicQuest;

import org.bukkit.plugin.java.JavaPlugin;

import com.mitsugaru.KarmicQuest.config.RootConfig;
import com.mitsugaru.KarmicQuest.permissions.PermCheck;

public class KarmicQuest extends JavaPlugin
{
	public static final String TAG = "[KarmicQuest]";
	private RootConfig config;
	
	public void onEnable()
	{
		//TODO get config
		config = new RootConfig(this);
		//Initialize permission handler
		PermCheck.init(this);
		//TODO grab economy?
		//Setup commander
		getCommand("kq").setExecutor(new Commander(this));
		//TODO register listeners
	}

	public RootConfig getPluginConfig()
	{
		// TODO Auto-generated method stub
		return config;
	}
}
