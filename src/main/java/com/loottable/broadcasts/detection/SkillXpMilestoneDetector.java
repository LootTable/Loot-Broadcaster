package com.loottable.broadcasts.detection;

import java.util.Optional;

import com.loottable.broadcasts.model.SkillXpMilestoneEvent;

import net.runelite.api.Experience;
import net.runelite.api.Skill;

public class SkillXpMilestoneDetector
{
	private static final int[] MILESTONE_XP = {100_000_000, Experience.MAX_SKILL_XP};

	public Optional<SkillXpMilestoneEvent> detect(String playerName, Skill skill, int previousXp, int currentXp)
	{
		if (previousXp < 0)
		{
			throw new IllegalArgumentException("Previous XP must not be negative.");
		}

		if (previousXp > Experience.MAX_SKILL_XP)
		{
			throw new IllegalArgumentException("Previous XP must be at most 200,000,000.");
		}

		if (currentXp < 0)
		{
			throw new IllegalArgumentException("Current XP must not be negative.");
		}

		if (currentXp > Experience.MAX_SKILL_XP)
		{
			throw new IllegalArgumentException("Current XP must be at most 200,000,000.");
		}

		if (playerName == null || playerName.isBlank())
		{
			throw new IllegalArgumentException("Player name must not be null or empty.");
		}

		if (skill == null)
		{
			throw new IllegalArgumentException("Skill must not be null.");
		}

		for (int index = MILESTONE_XP.length - 1; index >= 0; index--)
		{
			int milestoneXp = MILESTONE_XP[index];
			if (previousXp < milestoneXp && currentXp >= milestoneXp)
			{
				return Optional.of(new SkillXpMilestoneEvent(playerName, skill, milestoneXp));
			}
		}

		return Optional.empty();
	}
}
