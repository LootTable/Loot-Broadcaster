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

	@ConfigItem(
		keyName = "sendItemDrops",
		name = "Send item drops",
		description = "Allows the plugin to send your item drops to others"
	)
	default boolean sendItemDrops()
	{
		return false;
	}

	@ConfigItem(
		keyName = "showItemDrops",
		name = "Show item drops",
		description = "Shows item drop broadcasts from other players"
	)
	default boolean showItemDrops()
	{
		return false;
	}

	@ConfigItem(
		keyName = "sendPetDrops",
		name = "Send pet drops",
		description = "(TURN ON UNTRADEABLE LOOT NOTIFICATIONS) Allows the plugin to send your pet drop broadcasts to others."
	)
	default boolean sendPetDrops()
	{
		return false;
	}

	@ConfigItem(
		keyName = "showPetDrops",
		name = "Show pet drops",
		description = "Show pet drop broadcasts from other players"
	)
	default boolean showPetDrops()
	{
		return false;
	}

	@ConfigItem(
		keyName = "sendSkillXpMilestones",
		name = "Send skill XP milestones",
		description = "Allows the plugin to send your skill XP milestone broadcasts to others"
	)
	default boolean sendSkillXpMilestones()
	{
		return false;
	}

	@ConfigItem(
		keyName = "showSkillXpMilestones",
		name = "Show skill XP milestones",
		description = "Shows skill XP milestone broadcasts from other players"
	)
	default boolean showSkillXpMilestones()
	{
		return false;
	}

	@ConfigItem(
		keyName = "sendOverallXpMilestones",
		name = "Send overall XP milestones",
		description = "Allows the plugin to send your overall XP milestone broadcasts to others"
	)
	default boolean sendOverallXpMilestones()
	{
		return false;
	}

	@ConfigItem(
		keyName = "showOverallXpMilestones",
		name = "Show overall XP milestones",
		description = "Shows overall XP milestone broadcasts from other players"
	)
	default boolean showOverallXpMilestones()
	{
		return false;
	}

}
