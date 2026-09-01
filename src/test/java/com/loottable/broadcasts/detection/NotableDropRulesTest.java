package com.loottable.broadcasts.detection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import net.runelite.api.gameval.ItemID;
import org.junit.Test;

public class NotableDropRulesTest
{
	@Test
	public void detectsCuratedItemAsNotable()
	{
		NotableDropRules rules = new NotableDropRules();

		assertTrue(rules.isNotable(ItemID.TWISTED_BOW));
	}

	@Test
	public void doesNotDetectCommonItemAsNotable()
	{
		NotableDropRules rules = new NotableDropRules();

		assertFalse(rules.isNotable(ItemID.COINS));
	}

	@Test
	public void doesNotTreatZeroItemIdAsNotable()
	{
		NotableDropRules rules = new NotableDropRules();

		assertFalse(rules.isNotable(0));
	}

	@Test
	public void doesNotTreatNegativeItemIdAsNotable()
	{
		NotableDropRules rules = new NotableDropRules();

		assertFalse(rules.isNotable(-1));
	}
}
