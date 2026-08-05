package com.loottable.broadcasts.format;

import com.loottable.broadcasts.model.BroadcastEvent;
import com.loottable.broadcasts.model.BroadcastEventType;
import com.loottable.broadcasts.model.ItemDropEvent;
import com.loottable.broadcasts.model.PetDropEvent;
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
			return formatXpMilestone(xpEvent);
		}

		if (event.getEventType() == BroadcastEventType.PET_DROP)
		{
			PetDropEvent petEvent = (PetDropEvent) event;
			return formatPetDrop(petEvent);
		}

		if (event.getEventType() == BroadcastEventType.ITEM_DROP)
		{
			ItemDropEvent itemEvent = (ItemDropEvent) event;
			return formatItemDrop(itemEvent);
		}

		throw new UnsupportedOperationException("Broadcast message formatting is not implemented yet");
	}

	private String formatXpMilestone(XpMilestoneEvent xpEvent)
	{
		return "News: " + xpEvent.getPlayerName() + " has achieved level " + xpEvent.getLevel() + " in " + xpEvent.getSkill().getDisplayName() + ".";
	}

	private String formatPetDrop(PetDropEvent petEvent)
	{
		String formattedXp = String.format("%,d", petEvent.getXpAtDrop());
		return "News: " + petEvent.getPlayerName() + " has gotten " + petEvent.getPetName() + " at " + formattedXp + " xp!";
	}

	private String formatItemDrop(ItemDropEvent dropEvent)
	{
		String formattedSourceCount = String.format("%,d", dropEvent.getSourceCount());
		return "News: " + dropEvent.getPlayerName() + " has gotten a " + dropEvent.getItemName() + " drop at " + formattedSourceCount + "kc!";
	}
}
