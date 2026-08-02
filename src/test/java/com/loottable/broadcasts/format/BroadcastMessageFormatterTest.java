package com.loottable.broadcasts.format;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

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
}
