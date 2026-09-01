package com.loottable.broadcasts.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import net.runelite.api.Skill;


public class XpMilestoneEventTest
{
    @Test
    public void storesValidXpMilestoneValues()
    {
        XpMilestoneEvent event = new XpMilestoneEvent("Nicholas", Skill.ATTACK, 99);
        assertEquals("Nicholas", event.getPlayerName());
        assertEquals(Skill.ATTACK, event.getSkill());
        assertEquals(99, event.getLevel());
        assertEquals(BroadcastEventType.XP_MILESTONE, event.getEventType());
    }

    @Test
    public void allowsLevelOne()
    {
        XpMilestoneEvent event = new XpMilestoneEvent("Nicholas", Skill.ATTACK, 1);
        assertEquals(1, event.getLevel());
    }

    @Test
    public void allowsLevel126()
    {
        XpMilestoneEvent event = new XpMilestoneEvent("Nicholas", Skill.ATTACK, 126);
        assertEquals(126, event.getLevel());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsBlankPlayerName()
    {
        new XpMilestoneEvent("", Skill.ATTACK, 99);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullPlayerName()
    {
        new XpMilestoneEvent(null, Skill.ATTACK, 99);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullSkill()
    {
        new XpMilestoneEvent("Nicholas", null, 99);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsLevelBelowOne()
    {
        new XpMilestoneEvent("Nicholas", Skill.ATTACK, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsLevelAbove126()
    {
        new XpMilestoneEvent("Nicholas", Skill.ATTACK, 127);
    }
}
