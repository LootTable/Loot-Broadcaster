package com.loottable.broadcasts.format;

import com.loottable.broadcasts.model.BroadcastEvent;
import com.loottable.broadcasts.model.BroadcastEventType;
import com.loottable.broadcasts.model.ItemDropEvent;
import com.loottable.broadcasts.model.OverallXpMilestoneEvent;
import com.loottable.broadcasts.model.PetDropEvent;
import com.loottable.broadcasts.model.SkillXpMilestoneEvent;
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

		if (event.getEventType() == BroadcastEventType.SKILL_XP_MILESTONE)
		{
			SkillXpMilestoneEvent skillXpEvent = (SkillXpMilestoneEvent) event;
			return formatSkillXpMilestone(skillXpEvent);
		}

		if (event.getEventType() == BroadcastEventType.OVERALL_XP_MILESTONE)
		{
			OverallXpMilestoneEvent overallXpEvent = (OverallXpMilestoneEvent) event;
			return formatOverallXpMilestone(overallXpEvent);
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
		return "News: " + xpEvent.getPlayerName() + " has achieved level " + xpEvent.getLevel() + " in " + xpEvent.getSkill().getName() + ".";
	}

	private String formatPetDrop(PetDropEvent petEvent)
	{
		String formattedXp = String.format("%,d", petEvent.getXpAtDrop());
		return "News: " + petEvent.getPlayerName() + " has gotten " + petEvent.getPetName() + " at " + formattedXp + " xp!";
	}

	private String formatSkillXpMilestone(SkillXpMilestoneEvent skillXpEvent)
	{
		String formattedXp = String.format("%,d", skillXpEvent.getXp());
		return "News: " + skillXpEvent.getPlayerName() + " has reached " + formattedXp + " " + skillXpEvent.getSkill().getName() + " XP.";
	}

	private String formatOverallXpMilestone(OverallXpMilestoneEvent overallXpEvent)
	{
		String formattedXp = String.format("%,d", overallXpEvent.getTotalXp());
		return "News: " + overallXpEvent.getPlayerName() + " has reached " + formattedXp + " total XP.";
	}

	private String formatItemDrop(ItemDropEvent dropEvent)
	{
		String formattedSourceCount = String.format("%,d", dropEvent.getSourceCount());
		if (dropEvent.getQuantity() > 1)
		{
			return "News: " + dropEvent.getPlayerName() + " received " + dropEvent.getQuantity() + " " + dropEvent.getItemName() + " drops from " + dropEvent.getSourceName() + " at " + formattedSourceCount + "kc!";
		}

		return "News: " + dropEvent.getPlayerName() + " received " + dropEvent.getItemName() + " from " + dropEvent.getSourceName() + " at " + formattedSourceCount + "kc!";
	}
}
