package com.loottable.broadcasts.detection;

import java.util.List;
import java.util.Optional;

import net.runelite.client.util.Text;

public class PetDropDetector
{
	private static final List<String> PET_MESSAGES = List.of(
		"You have a funny feeling like you're being followed.",
		"You feel something weird sneaking into your backpack.",
		"You have a funny feeling like you would have been followed..."
	);

	private static final String UNTRADEABLE_DROP_PREFIX = "Untradeable drop: ";
	private static final String COLLECTION_LOG_PREFIX = "New item added to your collection log: ";
	private static final int PET_DROP_MESSAGE_WINDOW = 5;

	private boolean pendingPetDrop;
	private int pendingPetDropMessagesRemaining;

	public Optional<String> detect(String message)
	{
		if (message == null || message.isBlank())
		{
			return Optional.empty();
		}

		String cleanMessage = Text.removeTags(message).trim();
		if (PET_MESSAGES.contains(cleanMessage))
		{
			// The pet trigger confirms a pet happened, but the following chat lines carry the pet name.
			pendingPetDrop = true;
			pendingPetDropMessagesRemaining = PET_DROP_MESSAGE_WINDOW;
			return Optional.empty();
		}

		if (!pendingPetDrop)
		{
			return Optional.empty();
		}

		Optional<String> petName = getPetNameFromPetDropMessage(cleanMessage);
		if (petName.isPresent())
		{
			// Once a name is found, clear the pending window so later untradeable drops do not get misread.
			reset();
			return petName;
		}

		pendingPetDropMessagesRemaining--;
		if (pendingPetDropMessagesRemaining <= 0)
		{
			reset();
		}

		return Optional.empty();
	}

	public void reset()
	{
		pendingPetDrop = false;
		pendingPetDropMessagesRemaining = 0;
	}

	private Optional<String> getPetNameFromPetDropMessage(String message)
	{
		if (message.startsWith(UNTRADEABLE_DROP_PREFIX))
		{
			return nonBlank(removePriceSuffix(message.substring(UNTRADEABLE_DROP_PREFIX.length())));
		}

		if (message.startsWith(COLLECTION_LOG_PREFIX))
		{
			return nonBlank(message.substring(COLLECTION_LOG_PREFIX.length()));
		}

		return Optional.empty();
	}

	private String removePriceSuffix(String value)
	{
		int priceStart = value.indexOf(" (");
		if (priceStart == -1)
		{
			return value.trim();
		}

		return value.substring(0, priceStart).trim();
	}

	private Optional<String> nonBlank(String value)
	{
		String trimmedValue = value.trim();
		if (trimmedValue.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(trimmedValue);
	}
}
