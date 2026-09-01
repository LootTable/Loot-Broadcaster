package com.loottable.broadcasts.format;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.loottable.broadcasts.model.ItemDropEvent;
import com.loottable.broadcasts.model.OverallXpMilestoneEvent;
import com.loottable.broadcasts.model.PetDropEvent;
import com.loottable.broadcasts.model.SkillXpMilestoneEvent;
import com.loottable.broadcasts.model.XpMilestoneEvent;

import net.runelite.api.Skill;

public class BroadcastMessageFormatterTest
{
	@Test
	public void formattedBroadcastMessage()
	{
		XpMilestoneEvent event = new XpMilestoneEvent("Nicholas", Skill.ATTACK, 99);
		BroadcastMessageFormatter formatter = new BroadcastMessageFormatter();
		assertEquals("News: Nicholas has achieved level 99 in Attack.", formatter.format(event));
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullEvent()
	{
		BroadcastMessageFormatter formatter = new BroadcastMessageFormatter();
		formatter.format(null);
	}

	@Test
	public void petDropBroadcastMessage()
	{
		PetDropEvent event = new PetDropEvent("Nicholas", "Rocky", 1000000);
		BroadcastMessageFormatter formatter = new BroadcastMessageFormatter();
		assertEquals("News: Nicholas has gotten Rocky at 1,000,000 xp!", formatter.format(event));
	}

	@Test
	public void skillXpMilestoneBroadcastMessage()
	{
		SkillXpMilestoneEvent event = new SkillXpMilestoneEvent("Nicholas", Skill.WOODCUTTING, 100_000_000);
		BroadcastMessageFormatter formatter = new BroadcastMessageFormatter();
		assertEquals("News: Nicholas has reached 100,000,000 Woodcutting XP.", formatter.format(event));
	}

	@Test
	public void overallXpMilestoneBroadcastMessage()
	{
		OverallXpMilestoneEvent event = new OverallXpMilestoneEvent("Nicholas", 4_800_000_000L);
		BroadcastMessageFormatter formatter = new BroadcastMessageFormatter();
		assertEquals("News: Nicholas has reached 4,800,000,000 total XP.", formatter.format(event));
	}

	@Test
	public void itemDropBroadcastMessage()
	{
		ItemDropEvent event = new ItemDropEvent("Nicholas", "Bandos hilt", "General Graardor", 431, 1);
		BroadcastMessageFormatter formatter = new BroadcastMessageFormatter();
		assertEquals("News: Nicholas received Bandos hilt from General Graardor at 431kc!", formatter.format(event));
	}

	@Test
	public void itemDropBroadcastMessageWithMultipleQuantity()
	{
		ItemDropEvent event = new ItemDropEvent("Nicholas", "Bandos hilt", "General Graardor", 431, 2);
		BroadcastMessageFormatter formatter = new BroadcastMessageFormatter();
		assertEquals("News: Nicholas received 2 Bandos hilt drops from General Graardor at 431kc!", formatter.format(event));
	}

	@Test
	public void itemDropBroadcastMessageWithBigKc()
	{
		ItemDropEvent event = new ItemDropEvent("Nicholas", "Bandos hilt", "General Graardor", 431_000, 1);
		BroadcastMessageFormatter formatter = new BroadcastMessageFormatter();
		assertEquals("News: Nicholas received Bandos hilt from General Graardor at 431,000kc!", formatter.format(event));
	}

	@Test
	public void itemDropBroadcastMessageWithMultipleQuantityAndBigKc()
	{
		ItemDropEvent event = new ItemDropEvent("Nicholas", "Bandos hilt", "General Graardor", 431_000, 2);
		BroadcastMessageFormatter formatter = new BroadcastMessageFormatter();
		assertEquals("News: Nicholas received 2 Bandos hilt drops from General Graardor at 431,000kc!", formatter.format(event));
	}

}
