package com.loottable.broadcasts.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PetDropEventTest
{
	@Test
	public void storesValidPetDropValues()
	{
		PetDropEvent event = new PetDropEvent("Nicholas", "Rocky", 1000000);
		assertEquals("Nicholas", event.getPlayerName());
		assertEquals("Rocky", event.getPetName());
		assertEquals(1000000, event.getXpAtDrop());
		assertEquals(BroadcastEventType.PET_DROP, event.getEventType());
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBlankPlayerName()
	{
		new PetDropEvent("", "Rocky", 1000000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullPlayerName()
	{
		new PetDropEvent(null, "Rocky", 1000000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBlankPetName()
	{
		new PetDropEvent("Nicholas", "", 1000000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullPetName()
	{
		new PetDropEvent("Nicholas", null, 1000000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBelowOneXpAtDrop()
	{
		new PetDropEvent("Nicholas", "Rocky", 0);
	}
}
