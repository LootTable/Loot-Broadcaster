package com.loottable.broadcasts.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ItemDropEventTest
{
	@Test
	public void storesValidItemDropValues()
	{
		ItemDropEvent event = new ItemDropEvent("Nicholas", "Bandos Godsword", "General Graardor", 431, 1);
		assertEquals("Nicholas", event.getPlayerName());
		assertEquals("Bandos Godsword", event.getItemName());
		assertEquals("General Graardor", event.getSourceName());
		assertEquals(431, event.getSourceCount());
		assertEquals(1, event.getQuantity());
		assertEquals(BroadcastEventType.ITEM_DROP, event.getEventType());
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBlankPlayerName()
	{
		new ItemDropEvent("", "Bandos Godsword", "General Graardor", 431, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullPlayerName()
	{
		new ItemDropEvent(null, "Bandos Godsword", "General Graardor", 431, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBlankItemName()
	{
		new ItemDropEvent("Nicholas", "", "General Graardor", 431, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullItemName()
	{
		new ItemDropEvent("Nicholas", null, "General Graardor", 431, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBlankSourceName()
	{
		new ItemDropEvent("Nicholas", "Bandos Godsword", "", 431, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullSourceName()
	{
		new ItemDropEvent("Nicholas", "Bandos Godsword", null, 431, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBelowOneSourceCount()
	{
		new ItemDropEvent("Nicholas", "Bandos Godsword", "General Graardor", 0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBelowOneQuantity()
	{
		new ItemDropEvent("Nicholas", "Bandos Godsword", "General Graardor", 431, 0);
	}
}
