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

## Architecture

- `model` contains immutable broadcast event types and validation guards.
- `detection` contains milestone and notable-drop rules.
- `format` converts broadcast events into player-facing chat messages.
- `BroadcastsPlugin` connects RuneLite events to the local detection and formatting
  pipeline.

## Roadmap

- Add real pet-drop detection.
- Add backend delivery for opt-in cross-player broadcasts.
- Add one-time cosmetic broadcast rules for jars, dusts, kits, cosmetics, and boat
  paint.
- Add delayed broadcasts for wilderness-sensitive drops such as amulet of eternal
  glory & pets.
- Improve source-specific drop handling for cases where the same item can come from
  multiple activities.
