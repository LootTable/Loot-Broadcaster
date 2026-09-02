package com.loottable.broadcasts.detection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;
import org.junit.Test;

public class PetDropDetectorTest
{
	@Test
	public void detectsPetNameFromUntradeableDropAfterPetMessage()
	{
		PetDropDetector detector = new PetDropDetector();

		assertFalse(detector.detect("You have a funny feeling like you're being followed.").isPresent());
		Optional<String> petName = detector.detect("Untradeable drop: Veti'ion jr. (73,200 coins)");

		assertTrue(petName.isPresent());
		assertEquals("Veti'ion jr.", petName.get());
	}

	@Test
	public void detectsPetNameFromCollectionLogAfterPetMessage()
	{
		PetDropDetector detector = new PetDropDetector();

		assertFalse(detector.detect("You feel something weird sneaking into your backpack.").isPresent());
		Optional<String> petName = detector.detect("New item added to your collection log: Scorpia's offspring");

		assertTrue(petName.isPresent());
		assertEquals("Scorpia's offspring", petName.get());
	}

	@Test
	public void ignoresUntradeableDropWithoutPetMessage()
	{
		PetDropDetector detector = new PetDropDetector();

		Optional<String> petName = detector.detect("Untradeable drop: Looting bag (1 coins)");

		assertFalse(petName.isPresent());
	}

	@Test
	public void ignoresCollectionLogWithoutPetMessage()
	{
		PetDropDetector detector = new PetDropDetector();

		Optional<String> petName = detector.detect("New item added to your collection log: Bronze defender");

		assertFalse(petName.isPresent());
	}

	@Test
	public void expiresPendingPetDropAfterMessageWindow()
	{
		PetDropDetector detector = new PetDropDetector();

		detector.detect("You have a funny feeling like you're being followed.");
		for (int count = 0; count < 5; count++)
		{
			assertFalse(detector.detect("Some unrelated game message").isPresent());
		}

		Optional<String> petName = detector.detect("Untradeable drop: Rocky (1 coins)");

		assertFalse(petName.isPresent());
	}

	@Test
	public void stripsRuneLiteTagsBeforeDetectingPetMessages()
	{
		PetDropDetector detector = new PetDropDetector();

		assertFalse(detector.detect("<col=ff0000>You have a funny feeling like you're being followed.</col>").isPresent());
		Optional<String> petName = detector.detect("<col=ff0000>Untradeable drop: Rocky (1 coins)</col>");

		assertTrue(petName.isPresent());
		assertEquals("Rocky", petName.get());
	}

	@Test
	public void resetClearsPendingPetDrop()
	{
		PetDropDetector detector = new PetDropDetector();

		detector.detect("You have a funny feeling like you're being followed.");
		detector.reset();

		Optional<String> petName = detector.detect("Untradeable drop: Rocky (1 coins)");

		assertFalse(petName.isPresent());
	}
}
