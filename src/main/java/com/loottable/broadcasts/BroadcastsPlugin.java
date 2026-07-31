package com.loottable.broadcasts;

import javax.inject.Inject;

import com.google.inject.Provides;

import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;


@PluginDescriptor(
	name = "OSRS Broadcasts",
	description = "Broadcasts notable achievements to participating plugin users in an RS3 style",
	tags = {"broadcast", "achievement", "milestone", "loot", "drop", "pet", "xp", "notification"}
)
public class BroadcastsPlugin extends Plugin
{

	@Inject
	private BroadcastsConfig config;

	@Override
	protected void startUp()
	{
		if (!config.enableNetworking())
		{
			return;
		}
	}

	@Override
	protected void shutDown()
	{
	}

	@Provides
	BroadcastsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BroadcastsConfig.class);
	}
}

