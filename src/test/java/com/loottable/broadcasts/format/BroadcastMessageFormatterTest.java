package com.loottable.broadcasts.format;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.loottable.broadcasts.model.ItemDropEvent;
import com.loottable.broadcasts.model.PetDropEvent;
import com.loottable.broadcasts.model.Skill;
import com.loottable.broadcasts.model.XpMilestoneEvent;

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
	public void itemDropBroadcastMessage()
	{
		ItemDropEvent event = new ItemDropEvent("Nicholas", "Bandos Godsword", 10);
		BroadcastMessageFormatter formatter = new BroadcastMessageFormatter();
		assertEquals("News: Nicholas has gotten a Bandos Godsword drop at 10kc!", formatter.format(event));
	}
}
