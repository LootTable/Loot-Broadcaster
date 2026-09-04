# OSRS Broadcasts

OSRS Broadcasts is a RuneLite plugin that detects notable player achievements and
formats them as RuneScape-style broadcast messages.

The plugin is built around typed event models, detector classes, formatter logic,
and RuneLite event subscriptions so each achievement category can be validated,
tested, and extended independently.

## Current Features

- Detects level milestones for 99, 110, and 120 using RuneLite's virtual-level
  experience calculations.
- Detects skill XP milestones at 100 million and 200 million XP.
- Detects overall XP milestones at 1 billion, 2 billion, 3 billion, and maximum
  total XP.
- Detects curated notable NPC item drops and formats item broadcasts with source
  name, kill count, and duplicate-drop quantity handling.
- Detects pet drops from RuneLite chat messages by pairing pet trigger messages
  with nearby untradeable drop or collection log messages.
- Provides configurable send/show toggles for XP milestones, item drops, and pet
  drops.
- Includes unit tests for event models, detectors, notable-drop rules, and message
  formatting.

## Requirements

- Java 11
- Gradle
- RuneLite development environment

## Development

Open the project as a Gradle project in IntelliJ IDEA or VS Code.

Run the test suite:

```sh
./gradlew test
```

Launch RuneLite in developer mode with the plugin loaded:

```sh
./gradlew run
```

## Manual Smoke Test Checklist

Some behavior depends on RuneLite and OSRS client events, so it should be checked
manually in developer mode before release.

- Enable networking and the relevant send toggles in the plugin config.
- Confirm a level milestone queues a local broadcast at level 99, 110, or 120.
- Confirm a skill XP milestone queues a local broadcast at 100 million or 200
  million XP.
- Confirm an overall XP milestone queues a local broadcast at 1 billion, 2
  billion, 3 billion, or maximum total XP.
- Confirm a notable NPC item drop queues a local broadcast with item name, source
  name, kill count, and quantity handling.
- Confirm pet drops queue a local broadcast only after the pet trigger message is
  followed by an untradeable drop or collection log message.
- Confirm pet-drop detection requires OSRS untradeable loot notifications or
  collection log messages to expose the pet name in chat.

## Architecture

- `model` contains immutable broadcast event types and validation guards.
- `detection` contains milestone and notable-drop rules.
- `format` converts broadcast events into player-facing chat messages.
- `BroadcastsPlugin` connects RuneLite events to the local detection and formatting
  pipeline.

## Roadmap

- Add backend delivery for opt-in cross-player broadcasts.
- Add one-time cosmetic broadcast rules for jars, dusts, kits, cosmetics, and boat
  paint.
- Add delayed broadcasts for wilderness-sensitive drops such as amulet of eternal
  glory and pets.
- Improve source-specific drop handling for cases where the same item can come from
  multiple activities.
- Limit broadcast visibility to friends only on the server side.
