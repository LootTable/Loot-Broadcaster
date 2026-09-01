package com.loottable.broadcasts.detection;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.loottable.broadcasts.model.BroadcastEventType;
import com.loottable.broadcasts.model.OverallXpMilestoneEvent;

import org.junit.Test;

public class OverallXpMilestoneDetectorTest
{
	@Test
	public void detects1bOverallXpMilestone()
	{
		assertDetectsMilestone(999_999_999L, 1_000_000_000L, 1_000_000_000L);
	}

	@Test
	public void detects2bOverallXpMilestone()
	{
		assertDetectsMilestone(1_999_999_999L, 2_000_000_000L, 2_000_000_000L);
	}

	@Test
	public void detects3bOverallXpMilestone()
	{
		assertDetectsMilestone(2_999_999_999L, 3_000_000_000L, 3_000_000_000L);
	}

	@Test
	public void detectsMaximumOverallXpMilestone()
	{
		assertDetectsMilestone(4_799_999_999L, 4_800_000_000L, 4_800_000_000L);
	}

	@Test
	public void detectsHighestMilestoneWhenOverallXpJumpsAcrossMultipleMilestones()
	{
		assertDetectsMilestone(999_999_999L, 3_100_000_000L, 3_000_000_000L);
	}

	@Test
	public void doesNotDetectBelow1bOverallXpMilestone()
	{
		OverallXpMilestoneDetector detector = new OverallXpMilestoneDetector();

		Optional<OverallXpMilestoneEvent> result = detector.detect(
			"Nicholas",
			500_000_000L,
			999_999_999L
		);

		assertFalse(result.isPresent());
	}

	@Test
	public void doesNotDuplicate1bOverallXpMilestone()
	{
		OverallXpMilestoneDetector detector = new OverallXpMilestoneDetector();

		Optional<OverallXpMilestoneEvent> result = detector.detect(
			"Nicholas",
			1_000_000_000L,
			1_000_000_001L
		);

		assertFalse(result.isPresent());
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNegativePreviousTotalXp()
	{
		new OverallXpMilestoneDetector().detect("Nicholas", -1L, 1_000_000_000L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsPreviousTotalXpAboveMaximum()
	{
		new OverallXpMilestoneDetector().detect("Nicholas", 4_800_000_001L, 4_800_000_000L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNegativeCurrentTotalXp()
	{
		new OverallXpMilestoneDetector().detect("Nicholas", 1_000_000_000L, -1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsCurrentTotalXpAboveMaximum()
	{
		new OverallXpMilestoneDetector().detect("Nicholas", 1_000_000_000L, 4_800_000_001L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBlankPlayerName()
	{
		new OverallXpMilestoneDetector().detect("", 999_999_999L, 1_000_000_000L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullPlayerName()
	{
		new OverallXpMilestoneDetector().detect(null, 999_999_999L, 1_000_000_000L);
	}

	private void assertDetectsMilestone(long previousTotalXp, long currentTotalXp, long expectedTotalXp)
	{
		OverallXpMilestoneDetector detector = new OverallXpMilestoneDetector();

		Optional<OverallXpMilestoneEvent> result = detector.detect(
			"Nicholas",
			previousTotalXp,
			currentTotalXp
		);

		assertTrue(result.isPresent());
		OverallXpMilestoneEvent event = result.get();

		assertEquals("Nicholas", event.getPlayerName());
		assertEquals(expectedTotalXp, event.getTotalXp());
		assertEquals(BroadcastEventType.OVERALL_XP_MILESTONE, event.getEventType());
	}
}
