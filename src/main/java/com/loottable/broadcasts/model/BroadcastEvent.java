package com.loottable.broadcasts.model;

public interface BroadcastEvent
{
	BroadcastEventType getEventType();
	
	String getPlayerName();
}
