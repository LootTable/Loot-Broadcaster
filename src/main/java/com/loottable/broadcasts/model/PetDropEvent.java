package com.loottable.broadcasts.model;

public class PetDropEvent implements BroadcastEvent
{
	private final String playerName;
	private final String petName;
	private final int xpAtDrop;

	public PetDropEvent(String playerName, String petName, int xpAtDrop)
	{
		if (playerName == null || playerName.isBlank())
		{
			throw new IllegalArgumentException("playerName must not be null or blank");
		}

		if (petName == null || petName.isBlank())
		{
			throw new IllegalArgumentException("pet must not be null or blank");
		}

		if (xpAtDrop < 1)
		{
			throw new IllegalArgumentException("xp can't be lower than 1");
		}

		this.playerName = playerName;
		this.petName = petName;
		this.xpAtDrop = xpAtDrop;
	}

	@Override
	public BroadcastEventType getEventType()
	{
		return BroadcastEventType.PET_DROP;
	}

	@Override
	public String getPlayerName()
	{
		return playerName;
	}

	public String getPetName()
	{
		return petName;
	}

	public int getXpAtDrop()
	{
		return xpAtDrop;
	}
}
