package com.loottable.broadcasts.detection;

import java.util.Optional;

import com.loottable.broadcasts.model.XpMilestoneEvent;

import net.runelite.api.Experience;
import net.runelite.api.Skill;

public class XpMilestoneDetector
{
	private static final int MIN_LEVEL = 1;
	private static final int MAX_VIRTUAL_LEVEL = Experience.MAX_VIRT_LEVEL;
	private static final int[] MILESTONE_LEVELS = {Experience.MAX_REAL_LEVEL, 110, 120};

	public Optional<XpMilestoneEvent> detect(String playerName, Skill skill, int previousLevel, int currentLevel)
	{
		if (previousLevel < MIN_LEVEL)
		{
			throw new IllegalArgumentException("Previous level must be at least 1.");
		}

		if (previousLevel > MAX_VIRTUAL_LEVEL)
		{
			throw new IllegalArgumentException("Previous level must be at most 126.");
		}

		if (currentLevel < MIN_LEVEL)
		{
			throw new IllegalArgumentException("Current level must be at least 1.");
		}

		if (currentLevel > MAX_VIRTUAL_LEVEL)
		{
			throw new IllegalArgumentException("Current level must be at most 126.");
		}

		if (playerName == null || playerName.isBlank())
		{
			throw new IllegalArgumentException("Player name must not be null or empty.");
		}
		
		if (skill == null)
		{
			throw new IllegalArgumentException("Skill must not be null.");
		}
		
		for (int index = MILESTONE_LEVELS.length - 1; index >= 0; index--)
		{
			int milestoneLevel = MILESTONE_LEVELS[index];
			if (previousLevel < milestoneLevel && currentLevel >= milestoneLevel)
			{
				return Optional.of(new XpMilestoneEvent(playerName, skill, milestoneLevel));
			}
		}

		return Optional.empty();
	}
}
