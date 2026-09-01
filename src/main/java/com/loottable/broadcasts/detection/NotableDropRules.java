package com.loottable.broadcasts.detection;

public class NotableDropRules
{
	private final NotableDropItems notableDropItems = new NotableDropItems();

	public boolean isNotable(int itemId)
	{
		if (itemId <= 0)
		{
			return false;
		}

		return notableDropItems.isNotableItem(itemId);
	}
}
