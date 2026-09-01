package com.loottable.broadcasts.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OverallXpMilestoneEventTest
{
	@Test
	public void storesValidOverallXpMilestoneValues()
	{
		OverallXpMilestoneEvent event = new OverallXpMilestoneEvent("Nicholas", 4_800_000_000L);

		assertEquals("Nicholas", event.getPlayerName());
		assertEquals(4_800_000_000L, event.getTotalXp());
		assertEquals(BroadcastEventType.OVERALL_XP_MILESTONE, event.getEventType());
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBlankPlayerName()
	{
		new OverallXpMilestoneEvent("", 1_000_000_000L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullPlayerName()
	{
		new OverallXpMilestoneEvent(null, 1_000_000_000L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNegativeTotalXp()
	{
		new OverallXpMilestoneEvent("Nicholas", -1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsTotalXpAboveMaximum()
	{
		new OverallXpMilestoneEvent("Nicholas", 4_800_000_001L);
	}
}
