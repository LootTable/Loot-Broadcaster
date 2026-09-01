package com.loottable.broadcasts.model;

import static org.junit.Assert.assertEquals;

import net.runelite.api.Skill;
import org.junit.Test;

public class SkillXpMilestoneEventTest
{
	@Test
	public void storesValidSkillXpMilestoneValues()
	{
		SkillXpMilestoneEvent event = new SkillXpMilestoneEvent("Nicholas", Skill.WOODCUTTING, 100_000_000);

		assertEquals("Nicholas", event.getPlayerName());
		assertEquals(Skill.WOODCUTTING, event.getSkill());
		assertEquals(100_000_000, event.getXp());
		assertEquals(BroadcastEventType.SKILL_XP_MILESTONE, event.getEventType());
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBlankPlayerName()
	{
		new SkillXpMilestoneEvent("", Skill.WOODCUTTING, 100_000_000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullPlayerName()
	{
		new SkillXpMilestoneEvent(null, Skill.WOODCUTTING, 100_000_000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullSkill()
	{
		new SkillXpMilestoneEvent("Nicholas", null, 100_000_000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNegativeXp()
	{
		new SkillXpMilestoneEvent("Nicholas", Skill.WOODCUTTING, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsXpAbove200m()
	{
		new SkillXpMilestoneEvent("Nicholas", Skill.WOODCUTTING, 200_000_001);
	}
}
