package com.loottable.broadcasts.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ItemDropEventTest
{
	@Test
	public void storesValidItemDropValues()
	{
		ItemDropEvent event = new ItemDropEvent("Nicholas", "Bandos Godsword", 10);
		assertEquals("Nicholas", event.getPlayerName());
		assertEquals("Bandos Godsword", event.getItemName());
		assertEquals(10, event.getSourceCount());
		assertEquals(BroadcastEventType.ITEM_DROP, event.getEventType());
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBlankPlayerName()
	{
		new ItemDropEvent("", "Bandos Godsword", 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullPlayerName()
	{
		new ItemDropEvent(null, "Bandos Godsword", 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBlankItemName()
	{
		new ItemDropEvent("Nicholas", "", 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullItemName()
	{
		new ItemDropEvent("Nicholas", null, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBelowOneSourceCount()
	{
		new ItemDropEvent("Nicholas", "Bandos Godsword", 0);
	}
}
