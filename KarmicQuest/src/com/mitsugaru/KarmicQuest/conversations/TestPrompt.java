package com.mitsugaru.KarmicQuest.conversations;

import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

public class TestPrompt extends ValidatingPrompt
{

	@Override
	public String getPromptText(ConversationContext context)
	{
		Bukkit.getLogger().info("hit get prompt text for TestPrompt");
		return "Data: " + context.getSessionData("data");
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String in)
	{
		Bukkit.getLogger().info("hit accept validated input for TestPrompt");
		if(in.equalsIgnoreCase("stop") || in.equalsIgnoreCase("end") || in.equalsIgnoreCase("quit"))
		{
			return END_OF_CONVERSATION;
		}
		else
		{
			context.setSessionData("data", in);
		}
		return this;
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String in)
	{
		Bukkit.getLogger().info("hit input valid for TestPrompt");
		return true;
	}

}
