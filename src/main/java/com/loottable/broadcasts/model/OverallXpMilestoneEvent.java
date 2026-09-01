package com.loottable.broadcasts.model;

public class OverallXpMilestoneEvent implements BroadcastEvent
{
	public static final long MAX_OVERALL_XP = 4_800_000_000L;

	private final String playerName;
	private final long totalXp;

	public OverallXpMilestoneEvent(String playerName, long totalXp)
	{
		if (playerName == null || playerName.isBlank())
		{
			throw new IllegalArgumentException("playerName must not be null or blank");
		}

		if (totalXp < 0 || totalXp > MAX_OVERALL_XP)
		{
			throw new IllegalArgumentException("totalXp must be between 0 and 4,800,000,000");
		}

		this.playerName = playerName;
		this.totalXp = totalXp;
	}

	@Override
	public BroadcastEventType getEventType()
	{
		return BroadcastEventType.OVERALL_XP_MILESTONE;
	}

	@Override
	public String getPlayerName()
	{
		return playerName;
	}

	public long getTotalXp()
	{
		return totalXp;
	}
}
