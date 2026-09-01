package com.loottable.broadcasts.detection;

import java.util.Optional;

import com.loottable.broadcasts.model.OverallXpMilestoneEvent;

public class OverallXpMilestoneDetector
{
	private static final long[] MILESTONE_XP = {1_000_000_000L, 2_000_000_000L, 3_000_000_000L, OverallXpMilestoneEvent.MAX_OVERALL_XP};

	public Optional<OverallXpMilestoneEvent> detect(String playerName, long previousTotalXp, long currentTotalXp)
	{
		if (previousTotalXp < 0)
		{
			throw new IllegalArgumentException("Previous total XP must not be negative.");
		}

		if (previousTotalXp > OverallXpMilestoneEvent.MAX_OVERALL_XP)
		{
			throw new IllegalArgumentException("Previous total XP must be at most 4,800,000,000.");
		}

		if (currentTotalXp < 0)
		{
			throw new IllegalArgumentException("Current total XP must not be negative.");
		}

		if (currentTotalXp > OverallXpMilestoneEvent.MAX_OVERALL_XP)
		{
			throw new IllegalArgumentException("Current total XP must be at most 4,800,000,000.");
		}

		if (playerName == null || playerName.isBlank())
		{
			throw new IllegalArgumentException("Player name must not be null or empty.");
		}

		for (int index = MILESTONE_XP.length - 1; index >= 0; index--)
		{
			long milestoneXp = MILESTONE_XP[index];
			if (previousTotalXp < milestoneXp && currentTotalXp >= milestoneXp)
			{
				return Optional.of(new OverallXpMilestoneEvent(playerName, milestoneXp));
			}
		}

		return Optional.empty();
	}
}
