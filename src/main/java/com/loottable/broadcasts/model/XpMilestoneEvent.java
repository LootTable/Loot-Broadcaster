package com.loottable.broadcasts.model;

public class XpMilestoneEvent implements BroadcastEvent
{
	private final String playerName;
	private final Skill skill;
	private final int level;

	public XpMilestoneEvent(String playerName, Skill skill, int level)
	{
		if (playerName == null || playerName.isBlank())
		{
			throw new IllegalArgumentException("playerName must not be null or blank");
		}
		
		if (skill == null)
		{
			throw new IllegalArgumentException("skill must not be null");
		}
		
		if (level < 1 || level > 126)
		{
			throw new IllegalArgumentException("level can't be below 1 or higher than 126");
		}
			
		this.playerName = playerName;
		this.skill = skill;
		this.level = level;
	}

	@Override
	public BroadcastEventType getEventType()
	{
		return BroadcastEventType.XP_MILESTONE;
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

	public int getLevel()
	{
		return level;
	}
}