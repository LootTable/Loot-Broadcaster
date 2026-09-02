package com.loottable.broadcasts.model;

public class PetDropEvent implements BroadcastEvent
{
	private final String playerName;
	private final String petName;

	public PetDropEvent(String playerName, String petName)
	{
		if (playerName == null || playerName.isBlank())
		{
			throw new IllegalArgumentException("playerName must not be null or blank");
		}

		if (petName == null || petName.isBlank())
		{
			throw new IllegalArgumentException("pet must not be null or blank");
		}

		this.playerName = playerName;
		this.petName = petName;
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
}
