package com.loottable.broadcasts.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PetDropEventTest
{
	@Test
	public void storesValidPetDropValues()
	{
		PetDropEvent event = new PetDropEvent("Nicholas", "Rocky");
		assertEquals("Nicholas", event.getPlayerName());
		assertEquals("Rocky", event.getPetName());
		assertEquals(BroadcastEventType.PET_DROP, event.getEventType());
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBlankPlayerName()
	{
		new PetDropEvent("", "Rocky");
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullPlayerName()
	{
		new PetDropEvent(null, "Rocky");
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsBlankPetName()
	{
		new PetDropEvent("Nicholas", "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullPetName()
	{
		new PetDropEvent("Nicholas", null);
	}
}
