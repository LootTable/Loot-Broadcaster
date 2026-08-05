package com.loottable.broadcasts.model;

public class ItemDropEvent implements BroadcastEvent
{
	private final String playerName;
	private final String itemName;
	private final int sourceCount;

	public ItemDropEvent(String playerName, String itemName, int sourceCount)
	{
		if (playerName == null || playerName.isBlank())
		{
			throw new IllegalArgumentException("playerName must not be null or blank");
		}

		if (itemName == null || itemName.isBlank())
		{
			throw new IllegalArgumentException("itemName must not be null or blank");
		}

		if (sourceCount <= 0)
		{
			throw new IllegalArgumentException("sourceCount must be at least 1");
		}

		this.playerName = playerName;
		this.itemName = itemName;
		this.sourceCount = sourceCount;
	}

	@Override
	public BroadcastEventType getEventType()
	{
		return BroadcastEventType.ITEM_DROP;
	}

	@Override
	public String getPlayerName()
	{
		return playerName;
	}

	public String getItemName()
	{
		return itemName;
	}

	public int getSourceCount()
	{
		return sourceCount;
	}
}
