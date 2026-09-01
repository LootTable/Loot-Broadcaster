package com.loottable.broadcasts.model;

import net.runelite.api.Experience;
import net.runelite.api.Skill;

public class SkillXpMilestoneEvent implements BroadcastEvent
{
	private final String playerName;
	private final Skill skill;
	private final int xp;

	public SkillXpMilestoneEvent(String playerName, Skill skill, int xp)
	{
		if (playerName == null || playerName.isBlank())
		{
			throw new IllegalArgumentException("playerName must not be null or blank");
		}

		if (skill == null)
		{
			throw new IllegalArgumentException("skill must not be null");
		}

		if (xp < 0 || xp > Experience.MAX_SKILL_XP)
		{
			throw new IllegalArgumentException("xp must be between 0 and 200,000,000");
		}

		this.playerName = playerName;
		this.skill = skill;
		this.xp = xp;
	}

	@Override
	public BroadcastEventType getEventType()
	{
		return BroadcastEventType.SKILL_XP_MILESTONE;
	}

	@Override
	public String getPlayerName()
	{
		return playerName;
	}

	public Skill getSkill()
	{
		return skill;
	}

	public int getXp()
	{
		return xp;
	}
}
