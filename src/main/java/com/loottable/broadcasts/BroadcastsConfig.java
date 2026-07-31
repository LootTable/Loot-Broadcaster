package com.loottable.broadcasts;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(BroadcastsConfig.GROUP)
public interface BroadcastsConfig extends Config
{
	String GROUP = "osrsbroadcasts";

	@ConfigItem(
		keyName = "enableNetworking",
		name = "Enable networking",
		description = "Allows the plugin to send and receive achievement broadcasts"
	)
	default boolean enableNetworking()
	{
		return false;
	}

	@ConfigItem(
		keyName = "sendXpMilestones",
		name = "Send XP milestones",
		description = "Allows the plugin to send your XP milestone broadcasts"
	)
	default boolean sendXpMilestones()
	{
		return false;
	}

	@ConfigItem(
		keyName = "showXpMilestones",
		name = "Show XP milestones",
		description = "Shows XP milestone broadcasts from other players"
	)	
	default boolean showXpMilestones()
	{
		return false;
	}

}
