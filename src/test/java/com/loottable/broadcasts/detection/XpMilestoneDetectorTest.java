package com.loottable.broadcasts.detection;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.loottable.broadcasts.model.BroadcastEventType;
import com.loottable.broadcasts.model.XpMilestoneEvent;

import net.runelite.api.Skill;

public class XpMilestoneDetectorTest
{
	@Test
	public void detectsLevel99Milestone()
	{
		assertDetectsMilestone(98, 99, 99);
	}

	@Test
	public void doesNotDetectBelowLevel99Milestone()
	{
		XpMilestoneDetector detector = new XpMilestoneDetector();

		Optional<XpMilestoneEvent> result = detector.detect(
			"Nicholas",
			Skill.WOODCUTTING,
			90,
			91
		);
	
		assertFalse(result.isPresent());
	}

	@Test
	public void doesNotDuplicateLevelMilestones()
	{
		XpMilestoneDetector detector = new XpMilestoneDetector();

		Optional<XpMilestoneEvent> result = detector.detect(
			"Nicholas",
			Skill.WOODCUTTING,
			99,
			100
		);
	
		assertFalse(result.isPresent());
	}

	@Test
	public void detectsMilestoneWhenLevelJumpsPast99()
	{
		assertDetectsMilestone(97, 100, 99);
	}

	@Test
	public void doesNotDetectMaxVirtualLevelMilestone()
	{
		XpMilestoneDetector detector = new XpMilestoneDetector();

		Optional<XpMilestoneEvent> result = detector.detect(
			"Nicholas",
			Skill.WOODCUTTING,
			125,
			126
		);
	
		assertFalse(result.isPresent());
	}

	@Test
	public void doesNotDetectNonMilestonePost99Level()
	{
		XpMilestoneDetector detector = new XpMilestoneDetector();

		Optional<XpMilestoneEvent> result = detector.detect
		(
			"Nicholas",
			Skill.WOODCUTTING,
			120,
			121
		);
	
		assertFalse(result.isPresent());
	}

	@Test
	public void detectsHighestMilestoneWhenLevelJumpsAcrossMultipleMilestones()
	{
		assertDetectsMilestone(98, 111, 110);
	}

	@Test
	public void doesNotDetectLevel105Milestone()
	{
		XpMilestoneDetector detector = new XpMilestoneDetector();

		Optional<XpMilestoneEvent> result = detector.detect(
			"Nicholas",
			Skill.WOODCUTTING,
			104,
			105
		);
	
		assertFalse(result.isPresent());
	}

	@Test
	public void detectsLevel110Milestone()
	{
		assertDetectsMilestone(109, 110, 110);
	}

	@Test
	public void doesNotDetectLevel115Milestone()
	{
		XpMilestoneDetector detector = new XpMilestoneDetector();

		Optional<XpMilestoneEvent> result = detector.detect(
			"Nicholas",
			Skill.WOODCUTTING,
			114,
			115
		);
	
		assertFalse(result.isPresent());
	}

	@Test
	public void detectsLevel120Milestone()
	{
		assertDetectsMilestone(119, 120, 120);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsPreviousLevelBelowOne()
	{
		new XpMilestoneDetector().detect("Nicholas", Skill.WOODCUTTING, 0, 99);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsCurrentLevelBelowOne()
	{
		new XpMilestoneDetector().detect("Nicholas", Skill.WOODCUTTING, 99, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsCurrentLevelAbove126()
	{
		new XpMilestoneDetector().detect("Nicholas", Skill.WOODCUTTING, 126, 127);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsPreviousLevelAbove126()
	{
		new XpMilestoneDetector().detect("Nicholas", Skill.WOODCUTTING, 127, 126);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBlankPlayerName()
	{
		new XpMilestoneDetector().detect("", Skill.WOODCUTTING, 90, 91);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullPlayerName()
	{
		new XpMilestoneDetector().detect(null, Skill.WOODCUTTING, 90, 91);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullSkill()
	{
		new XpMilestoneDetector().detect("Nicholas", null, 90, 91);
	}

	private void assertDetectsMilestone(int previousLevel, int currentLevel, int expectedLevel)
	{
		XpMilestoneDetector detector = new XpMilestoneDetector();
		
		Optional<XpMilestoneEvent> results = detector.detect
		(
			"Nicholas", 
			Skill.WOODCUTTING, 
			previousLevel, 
			currentLevel
		);
	
		assertTrue(results.isPresent());
		XpMilestoneEvent event = results.get();

		assertEquals("Nicholas", event.getPlayerName());
		assertEquals(Skill.WOODCUTTING, event.getSkill());
		assertEquals(expectedLevel, event.getLevel());
		assertEquals(BroadcastEventType.XP_MILESTONE, event.getEventType());
	}
}
