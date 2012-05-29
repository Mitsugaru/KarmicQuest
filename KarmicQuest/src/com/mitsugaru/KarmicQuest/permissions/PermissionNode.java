package com.mitsugaru.KarmicQuest.permissions;

public enum PermissionNode
{
	ADMIN(".admin"), SIGN(".sign");
	private static final String prefix = "KarmicQuest";
	private String node;

	private PermissionNode(String node)
	{
		this.node = prefix + node;
	}
	
	public String getNode()
	{
		return node;
	}

}
