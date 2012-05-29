package com.mitsugaru.KarmicQuest;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.entity.Player;

import com.mitsugaru.KarmicQuest.config.RootConfig;
import com.mitsugaru.KarmicQuest.conversations.TestPrompt;
import com.mitsugaru.KarmicQuest.permissions.PermCheck;
import com.mitsugaru.KarmicQuest.permissions.PermissionNode;

public class Commander implements CommandExecutor
{
	// Class variables
	private final KarmicQuest plugin;
	private final RootConfig config;
	private final static String bar = "======================";
	private long time = 0;
	private ConversationFactory factory;

	public Commander(KarmicQuest plugin)
	{
		this.plugin = plugin;
		this.config = plugin.getPluginConfig();
		factory = new ConversationFactory(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args)
	{
		if (config.debugTime)
		{
			time = System.nanoTime();
		}
		// See if any arguments were given
		if (args.length == 0)
		{
			// Check if they have "karma" permission
			this.displayHelp(sender);
		}
		else
		{
			final String com = args[0].toLowerCase();
			if (com.equals("version") || com.equals("ver"))
			{
				// Version and author
				showVersion(sender, args);
			}
			else if (com.equals("?") || com.equals("help"))
			{
				displayHelp(sender);
			}
			else if (com.equals("reload"))
			{
				if (PermCheck.checkPermission(sender, PermissionNode.ADMIN))
				{
					config.reloadConfig();
				}
			}
			else if (com.equals("test"))
			{
				if (sender instanceof Player)
				{
					final Map<Object, Object> map = new HashMap<Object, Object>();
					map.put("data", "first");
					Conversation conv = factory
							.withFirstPrompt(new TestPrompt())
							.withPrefix(new ConversationPrefix() {

								@Override
								public String getPrefix(ConversationContext arg0)
								{
									return ChatColor.GREEN + KarmicQuest.TAG
											+ ChatColor.WHITE + " ";
								}

							}).withInitialSessionData(map).withLocalEcho(false)
							.buildConversation((Player) sender);
					conv.addConversationAbandonedListener(new ConversationAbandonedListener() {

						@Override
						public void conversationAbandoned(
								ConversationAbandonedEvent event)
						{
							if (event.gracefulExit())
							{
								plugin.getLogger().info("graceful exit");
							}
							try
							{
								plugin.getLogger().info(
										"Canceller"
												+ event.getCanceller()
														.toString());
							}
							catch (NullPointerException n)
							{
								// Was null
								plugin.getLogger().info(
										"null Canceller");
							}
						}
					});
					conv.begin();
				}

			}
			else
			{
				sender.sendMessage(ChatColor.RED + KarmicQuest.TAG
						+ " Unknown command '" + ChatColor.AQUA + com
						+ ChatColor.RED + "'");
			}
		}
		if (config.debugTime)
		{
			debugTime(sender, time);
		}
		return true;
	}

	private void showVersion(CommandSender sender, String[] args)
	{
		sender.sendMessage(ChatColor.BLUE + bar + "=====");
		sender.sendMessage(ChatColor.GREEN + "KarmicMarket v"
				+ plugin.getDescription().getVersion());
		sender.sendMessage(ChatColor.GREEN + "Coded by Mitsugaru");
		sender.sendMessage(ChatColor.BLUE + "===========" + ChatColor.GRAY
				+ "Config" + ChatColor.BLUE + "===========");
		if (config.debugTime)
			sender.sendMessage(ChatColor.GRAY + "Debug time: "
					+ config.debugTime);
	}

	/**
	 * Show the help menu, with commands and description
	 * 
	 * @param sender
	 *            to display to
	 */
	private void displayHelp(CommandSender sender)
	{
		sender.sendMessage(ChatColor.WHITE + "==========" + ChatColor.GOLD
				+ "KarmicQuest" + ChatColor.WHITE + "==========");
	}

	private void debugTime(CommandSender sender, long time)
	{
		time = System.nanoTime() - time;
		sender.sendMessage("[Debug]" + KarmicQuest.TAG + "Process time: "
				+ time);
	}

}
