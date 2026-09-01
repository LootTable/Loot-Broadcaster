package com.loottable.broadcasts.model;

public class ItemDropEvent implements BroadcastEvent
{
	private final String playerName;
	private final String itemName;
	private final String sourceName;
	private final int sourceCount;
	private final int quantity;

	public ItemDropEvent(String playerName, String itemName, String sourceName, int sourceCount, int quantity)
	{
		if (playerName == null || playerName.isBlank())
		{
			throw new IllegalArgumentException("playerName must not be null or blank");
		}

		if (itemName == null || itemName.isBlank())
		{
			throw new IllegalArgumentException("itemName must not be null or blank");
		}

		if (sourceName == null || sourceName.isBlank())
		{
			throw new IllegalArgumentException("sourceName must not be null or blank");
		}

		if (sourceCount <= 0)
		{
			throw new IllegalArgumentException("sourceCount must be at least 1");
		}

		if (quantity <= 0)
		{
			throw new IllegalArgumentException("quantity must be at least 1");
		}

		this.playerName = playerName;
		this.itemName = itemName;
		this.sourceName = sourceName;
		this.sourceCount = sourceCount;
		this.quantity = quantity;
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

	public String getSourceName()
	{
		return sourceName;
	}

	public int getSourceCount()
	{
		return sourceCount;
	}

	public int getQuantity()
	{
		return quantity;
	}
}
