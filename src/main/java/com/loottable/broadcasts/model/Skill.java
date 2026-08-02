package com.loottable.broadcasts.model;

public enum Skill
{
	ATTACK("Attack"),
	STRENGTH("Strength"),
	DEFENCE("Defence"),
	RANGED("Ranged"),
	PRAYER("Prayer"),
	MAGIC("Magic"),
	RUNECRAFT("Runecraft"),
	HITPOINTS("Hitpoints"),
	CRAFTING("Crafting"),
	MINING("Mining"),
	SMITHING("Smithing"),
	FISHING("Fishing"),
	COOKING("Cooking"),
	FIREMAKING("Firemaking"),
	WOODCUTTING("Woodcutting"),
	AGILITY("Agility"),
	HERBLORE("Herblore"),
	THIEVING("Thieving"),
	FLETCHING("Fletching"),
	SLAYER("Slayer"),
	FARMING("Farming"),
	CONSTRUCTION("Construction"),
	HUNTER("Hunter"),
	SAILING("Sailing");

	private final String displayName;

	Skill(String displayName)
	{
		this.displayName = displayName;
	}

	public String getDisplayName()
	{
		return displayName;
	}
}
