package com.loottable.broadcasts;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import com.google.inject.Provides;
import com.loottable.broadcasts.detection.NotableDropRules;
import com.loottable.broadcasts.detection.OverallXpMilestoneDetector;
import com.loottable.broadcasts.detection.PetDropDetector;
import com.loottable.broadcasts.detection.SkillXpMilestoneDetector;
import com.loottable.broadcasts.detection.XpMilestoneDetector;
import com.loottable.broadcasts.format.BroadcastMessageFormatter;
import com.loottable.broadcasts.model.BroadcastEvent;
import com.loottable.broadcasts.model.ItemDropEvent;
import com.loottable.broadcasts.model.OverallXpMilestoneEvent;
import com.loottable.broadcasts.model.PetDropEvent;
import com.loottable.broadcasts.model.SkillXpMilestoneEvent;
import com.loottable.broadcasts.model.XpMilestoneEvent;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.Experience;
import net.runelite.api.GameState;
import net.runelite.api.ItemComposition;
import net.runelite.api.Player;
import net.runelite.api.Skill;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.StatChanged;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ServerNpcLoot;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "OSRS Broadcasts",
	description = "Broadcasts notable achievements to participating plugin users in an RS3 style",
	tags = {"broadcast", "achievement", "milestone", "loot", "drop", "pet", "xp", "notification"}
)
public class BroadcastsPlugin extends Plugin
{
	private final XpMilestoneDetector xpMilestoneDetector = new XpMilestoneDetector();
	private final SkillXpMilestoneDetector skillXpMilestoneDetector = new SkillXpMilestoneDetector();
	private final OverallXpMilestoneDetector overallXpMilestoneDetector = new OverallXpMilestoneDetector();
	private final PetDropDetector petDropDetector = new PetDropDetector();
	private final BroadcastMessageFormatter formatter = new BroadcastMessageFormatter();
	private final Map<Skill, Integer> lastKnownVirtualLevels = new EnumMap<>(Skill.class);
	private final Map<Skill, Integer> lastKnownXp = new EnumMap<>(Skill.class);
	private final NotableDropRules notableDropRules = new NotableDropRules();
	private Long lastKnownOverallXp;

	@Inject
	private BroadcastsConfig config;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Inject
	private Client client;

	@Inject
	private ItemManager itemManager;

	@Inject
	private ConfigManager configManager;

	@Override
	protected void startUp()
	{
	}

	@Override
	protected void shutDown()
	{
	}

	@Provides
	BroadcastsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BroadcastsConfig.class);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if( event.getGameState() == GameState.LOGIN_SCREEN)
		{
			lastKnownVirtualLevels.clear();
			lastKnownXp.clear();
			lastKnownOverallXp = null;
			petDropDetector.reset();
			return;
		}
		
		if ( event.getGameState() != GameState.LOGGED_IN)
		{
			return;
		}

		if ( !config.enableNetworking())
		{
			return;
		}

		if (lastKnownVirtualLevels.isEmpty() || lastKnownXp.isEmpty() || lastKnownOverallXp == null)
		{
			initializeLastKnownXp();
			return;
		}
	}

	@Subscribe
	public void onStatChanged(StatChanged event)
	{
		if ( !config.enableNetworking())
		{
			return;
		}
		
		Player player = client.getLocalPlayer();
		if (player == null)
		{
			return;
		}
		
		String playerName = player.getName();
		Skill skill = event.getSkill();
		int currentXp = event.getXp();
		int currentVirtualLevel = Experience.getLevelForXp(currentXp);
		long currentOverallXp = client.getOverallExperience();
		Integer lastVirtualLevel = lastKnownVirtualLevels.get(skill);
		Integer previousXp = lastKnownXp.get(skill);
		Long previousOverallXp = lastKnownOverallXp;

		if (playerName == null || playerName.isBlank())
		{
			return;
		}

		if (lastVirtualLevel == null)
		{
			lastKnownVirtualLevels.put(skill, currentVirtualLevel);
			lastKnownXp.put(skill, currentXp);
			lastKnownOverallXp = currentOverallXp;
			return;
		}

		if (previousXp == null || previousOverallXp == null)
		{
			lastKnownVirtualLevels.put(skill, currentVirtualLevel);
			lastKnownXp.put(skill, currentXp);
			lastKnownOverallXp = currentOverallXp;
			return;
		}

		if (currentVirtualLevel > lastVirtualLevel && config.sendXpMilestones())
		{
			Optional<XpMilestoneEvent> detected = xpMilestoneDetector.detect(playerName, skill, lastVirtualLevel, currentVirtualLevel);
			if (detected.isPresent())
			{
				queueBroadcastMessage(detected.get());
			}
		}

		if (currentXp > previousXp && config.sendSkillXpMilestones())
		{
			Optional<SkillXpMilestoneEvent> detected = skillXpMilestoneDetector.detect(playerName, skill, previousXp, currentXp);
			if (detected.isPresent())
			{
				queueBroadcastMessage(detected.get());
			}
		}

		if (currentOverallXp > previousOverallXp && config.sendOverallXpMilestones())
		{
			Optional<OverallXpMilestoneEvent> detected = overallXpMilestoneDetector.detect(playerName, previousOverallXp, currentOverallXp);
			if (detected.isPresent())
			{
				queueBroadcastMessage(detected.get());
			}
		}

		lastKnownVirtualLevels.put(skill, currentVirtualLevel);
		lastKnownXp.put(skill, currentXp);
		lastKnownOverallXp = currentOverallXp;
	}

	@Subscribe
	public void onServerNpcLoot(ServerNpcLoot event)
	{
		if (!config.enableNetworking())
		{
			return;
		}

		if (!config.sendItemDrops())
		{
			return;
		}
	
		Optional<String> playerName = getLocalPlayerName();
		if (playerName.isEmpty())
		{
			return;
		}

		Optional<String> sourceName = getSourceName(event);
		if (sourceName.isEmpty())
		{
			return;
		}

		int sourceCount = getKillCount(sourceName.get());
		if (sourceCount <= 0)
		{
			return;
		}

		for (ItemStack droppedItem : event.getItems())
		{
			if (!notableDropRules.isNotable(droppedItem.getId()))
			{
				continue;
			}

			Optional<String> itemName = getItemName(droppedItem.getId());
			if (itemName.isEmpty())
			{
				continue;
			}

			ItemDropEvent notableDrop = new ItemDropEvent(playerName.get(), itemName.get(), sourceName.get(), sourceCount, droppedItem.getQuantity());
			queueBroadcastMessage(notableDrop);
		}
	}
	
	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (!config.enableNetworking())
		{
			return;
		}

		if (!config.sendPetDrops())
		{
			return;
		}

		if (event.getType() != ChatMessageType.GAMEMESSAGE)
		{
			return;
		}

		String message = event.getMessage();
		if (message == null || message.isBlank())
		{
			return;
		}

		Optional<String> petName = petDropDetector.detect(message);
		if (petName.isEmpty())
		{
			return;
		}

		Optional<String> playerName = getLocalPlayerName();
		if (playerName.isEmpty())
		{
			petDropDetector.reset();
			return;
		}

		PetDropEvent petDropEvent = new PetDropEvent(playerName.get(), petName.get());
		queueBroadcastMessage(petDropEvent);
	}

	private void queueBroadcastMessage(BroadcastEvent eventType)
	{
		String formattedMessage = formatter.format(eventType);
		QueuedMessage message = QueuedMessage.builder()
			.type(ChatMessageType.GAMEMESSAGE)
			.value(formattedMessage)
			.build();

		chatMessageManager.queue(message);
	}

	private void initializeLastKnownXp()
	{
		for (Skill skill : Skill.values())
		{
			int xp = client.getSkillExperience(skill);
			int virtualLevel = Experience.getLevelForXp(xp);
			lastKnownVirtualLevels.put(skill, virtualLevel);
			lastKnownXp.put(skill, xp);
		}

		lastKnownOverallXp = client.getOverallExperience();
	}

	private Optional<String> getLocalPlayerName()
	{
		Player player = client.getLocalPlayer();
		if (player == null)
		{
			return Optional.empty();
		}

		return nonBlank(player.getName());
	}

	private Optional<String> getItemName(int itemId)
	{
		ItemComposition itemComposition = itemManager.getItemComposition(itemId);
		if (itemComposition == null)
		{
			return Optional.empty();
		}

		return nonBlank(itemComposition.getName());
	}

	private Optional<String> getSourceName(ServerNpcLoot event)
	{
		if (event.getComposition() == null)
		{
			return Optional.empty();
		}

		return nonBlank(event.getComposition().getName());
	}

	private int getKillCount(String sourceName)
	{
		String killCountKey = sourceName.replace(":", "").toLowerCase(Locale.ENGLISH);
		Integer killCount = configManager.getRSProfileConfiguration("killcount", killCountKey, int.class);
		return killCount == null ? 0 : killCount;
	}

	private Optional<String> nonBlank(String value)
	{
		if (value == null || value.isBlank())
		{
			return Optional.empty();
		}

		return Optional.of(value);
	}
}
