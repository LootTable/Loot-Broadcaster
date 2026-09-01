package com.loottable.broadcasts.detection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import net.runelite.api.gameval.ItemID;
import org.junit.Test;

public class NotableDropItemsTest
{
	@Test
	public void detectsExpensiveItemAsNotable()
	{
		NotableDropItems items = new NotableDropItems();

		assertTrue(items.isNotableItem(ItemID.TWISTED_BOW));
	}

	@Test
	public void detectsCuratedClueItemBelow100mAsNotable()
	{
		NotableDropItems items = new NotableDropItems();

		assertTrue(items.isNotableItem(ItemID.BRUT_DRAGON_FULL_HELM));
	}

	@Test
	public void doesNotDetectThirdAgeRingAsNotable()
	{
		NotableDropItems items = new NotableDropItems();

		assertFalse(items.isNotableItem(ItemID.RING_OF_3RD_AGE));
	}

	@Test
	public void doesNotDetectCommonItemAsNotable()
	{
		NotableDropItems items = new NotableDropItems();

		assertFalse(items.isNotableItem(ItemID.COINS));
	}
}
