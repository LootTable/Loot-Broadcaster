package com.loottable.broadcasts;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public final class BroadcastsPluginTest
{
	private BroadcastsPluginTest()
	{
	}

	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BroadcastsPlugin.class);
		RuneLite.main(args);
	}
}

