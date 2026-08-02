package com.loottable.broadcasts.format;

import com.loottable.broadcasts.model.BroadcastEvent;
import com.loottable.broadcasts.model.BroadcastEventType;
import com.loottable.broadcasts.model.XpMilestoneEvent;

public class BroadcastMessageFormatter
{
    public String format(BroadcastEvent event)
	{
		if (event == null)
		{
			throw new IllegalArgumentException("event must not be null");
		}

		if (event.getEventType() == BroadcastEventType.XP_MILESTONE)
		{
			XpMilestoneEvent xpEvent = (XpMilestoneEvent) event;
			return "News: " + xpEvent.getPlayerName() + " has achieved level " + xpEvent.getLevel() + " in " + xpEvent.getSkill().getDisplayName() + ".";
		}

		throw new UnsupportedOperationException("Broadcast message formatting is not implemented yet");
	}
}
