package com.loottable.broadcasts.detection;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.loottable.broadcasts.model.BroadcastEventType;
import com.loottable.broadcasts.model.SkillXpMilestoneEvent;

import net.runelite.api.Skill;
import org.junit.Test;

public class SkillXpMilestoneDetectorTest
{
	@Test
	public void detects100mSkillXpMilestone()
	{
		assertDetectsMilestone(99_999_999, 100_000_000, 100_000_000);
	}

	@Test
	public void detects200mSkillXpMilestone()
	{
		assertDetectsMilestone(199_999_999, 200_000_000, 200_000_000);
	}

	@Test
	public void detectsHighestMilestoneWhenXpJumpsAcrossMultipleMilestones()
	{
		assertDetectsMilestone(99_999_999, 200_000_000, 200_000_000);
	}

	@Test
	public void doesNotDetectBelow100mSkillXpMilestone()
	{
		SkillXpMilestoneDetector detector = new SkillXpMilestoneDetector();

		Optional<SkillXpMilestoneEvent> result = detector.detect(
			"Nicholas",
			Skill.WOODCUTTING,
			50_000_000,
			99_999_999
		);

		assertFalse(result.isPresent());
	}

	@Test
	public void doesNotDuplicate100mSkillXpMilestone()
	{
		SkillXpMilestoneDetector detector = new SkillXpMilestoneDetector();

		Optional<SkillXpMilestoneEvent> result = detector.detect(
			"Nicholas",
			Skill.WOODCUTTING,
			100_000_000,
			100_000_001
		);

		assertFalse(result.isPresent());
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNegativePreviousXp()
	{
		new SkillXpMilestoneDetector().detect("Nicholas", Skill.WOODCUTTING, -1, 100_000_000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsPreviousXpAbove200m()
	{
		new SkillXpMilestoneDetector().detect("Nicholas", Skill.WOODCUTTING, 200_000_001, 200_000_000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNegativeCurrentXp()
	{
		new SkillXpMilestoneDetector().detect("Nicholas", Skill.WOODCUTTING, 100_000_000, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsCurrentXpAbove200m()
	{
		new SkillXpMilestoneDetector().detect("Nicholas", Skill.WOODCUTTING, 100_000_000, 200_000_001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBlankPlayerName()
	{
		new SkillXpMilestoneDetector().detect("", Skill.WOODCUTTING, 99_999_999, 100_000_000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullPlayerName()
	{
		new SkillXpMilestoneDetector().detect(null, Skill.WOODCUTTING, 99_999_999, 100_000_000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullSkill()
	{
		new SkillXpMilestoneDetector().detect("Nicholas", null, 99_999_999, 100_000_000);
	}

	private void assertDetectsMilestone(int previousXp, int currentXp, int expectedXp)
	{
		SkillXpMilestoneDetector detector = new SkillXpMilestoneDetector();

		Optional<SkillXpMilestoneEvent> result = detector.detect(
			"Nicholas",
			Skill.WOODCUTTING,
			previousXp,
			currentXp
		);

		assertTrue(result.isPresent());
		SkillXpMilestoneEvent event = result.get();

		assertEquals("Nicholas", event.getPlayerName());
		assertEquals(Skill.WOODCUTTING, event.getSkill());
		assertEquals(expectedXp, event.getXp());
		assertEquals(BroadcastEventType.SKILL_XP_MILESTONE, event.getEventType());
	}
}
