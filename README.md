# WGN — World Generation Nexus

**WGN (World Generation Nexus)** is a next-generation Minecraft Fabric mod focused on creating the most advanced procedural world generation system ever built for Minecraft.

WGN is **not** a dimension mod.  
WGN is **not** a structure mod.  
WGN is a **complete world-generation framework** capable of generating living civilizations, kingdoms, cities, villages, dungeons, roads, biomes, ruins, factions, NPCs, and dynamic adventures.

Every world should feel handcrafted.  
No two worlds should ever feel the same.

## Platform

| Requirement | Version |
|-------------|---------|
| Minecraft | 1.21.1 |
| Loader | Fabric |
| API | Fabric API |
| Java | 21 |

## Vision

WGN should feel like Minecraft evolved into a **living fantasy world simulator** — where players discover kingdoms, cities, roads, dungeons, factions, NPCs, bosses, and adventures generated naturally across an effectively endless world.

## Core Goals

### Infinite Exploration

- Infinite terrain
- Infinite structures
- Infinite kingdom expansion
- Infinite road networks
- Infinite dungeons

Every chunk should have the possibility of discovering something new.

### Civilization Engine

Generate civilizations that naturally appear in the world:

- Medieval Kingdoms
- Northern Clans
- Forest Realms
- Desert Empires
- Mountain Holds
- Merchant Republics
- Ancient Kingdoms

Each civilization contains capitals, cities, villages, roads, farms, markets, castles, towers, and outposts.

### Human NPC System

Realistic human NPCs with roles including King, Queen, Knight, Guard, Merchant, Farmer, Hunter, Miner, Blacksmith, Builder, Scholar, and Innkeeper.

NPCs walk, sleep, eat, work, trade, travel, and defend cities.

### Procedural Kingdom Generation

Kingdoms are built from jigsaw structures, district systems, road systems, and building templates — expanding naturally over time.

### Advanced Structure Database

Target: **100,000+** structure blueprints across houses, castles, markets, bridges, walls, roads, towers, dungeons, ruins, farms, and palaces — combined procedurally into millions of unique layouts.

### Material Intelligence

WGN understands block palettes per civilization style (Medieval, Desert, Northern, and more).

### Dungeon System

Crypts, ruins, catacombs, strongholds, temples, and boss arenas — with loot rooms, boss rooms, secrets, puzzles, and traps.

### Road Network System

Village roads, trade routes, kingdom highways, mountain paths, and bridges — connecting settlements logically.

### Reputation System

Factions remember player actions. Help a kingdom to gain reputation; attack villagers to lose it. Reputation unlocks quests, trades, rewards, and titles.

### World Storytelling

Abandoned villages, battlefields, ancient ruins, lost temples, and destroyed castles make exploration meaningful.

## Architecture

WGN is **modular**, **scalable**, **registry-driven**, and **data-driven**.

| Module | Package | Responsibility |
|--------|---------|----------------|
| WGN-Core | `dev.mcxxdev.wgn.core` | Registry, bootstrap, shared infrastructure |
| WGN-WorldGen | `dev.mcxxdev.wgn.worldgen` | Terrain, biomes, chunk discovery |
| WGN-Structures | `dev.mcxxdev.wgn.structures` | Blueprint database, palettes, templates |
| WGN-Kingdoms | `dev.mcxxdev.wgn.kingdoms` | Civilizations, settlements, roads |
| WGN-NPCs | `dev.mcxxdev.wgn.npcs` | Human NPCs, roles, routines |
| WGN-Dungeons | `dev.mcxxdev.wgn.dungeons` | Dungeons, bosses, loot |
| WGN-Factions | `dev.mcxxdev.wgn.factions` | Reputation, faction memory |
| WGN-Economy | `dev.mcxxdev.wgn.economy` | Trade, markets |
| WGN-Quests | `dev.mcxxdev.wgn.quests` | Dynamic adventures |
| WGN-UI | `dev.mcxxdev.wgn.ui` | Client interfaces |

See [docs/VISION.md](docs/VISION.md) for the complete product vision and [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) for technical design details.

## Technical Requirements

| Requirement | Value |
|-------------|-------|
| Minecraft | 1.21.1 |
| Loader | Fabric |
| API | Fabric API |
| Java | 21 |

**Architecture:** Modular · Scalable · Registry Driven · Data Driven · Enterprise Quality

## End Goal

WGN should feel like Minecraft evolved into a **living fantasy world simulator** — where players discover kingdoms, cities, roads, dungeons, factions, NPCs, bosses, and adventures generated naturally across an effectively endless world.

## Development

### Prerequisites

- JDK 21
- Git

### Build

```bash
./gradlew build
```

### Run client (dev)

```bash
./gradlew runClient
```

## Project Status

**v0.3.0-alpha** — Runtime systems + **/wgn build** command for natural-language structure generation.

| System | Status |
|--------|--------|
| World generation | Kingdoms generate per 1024-block region on chunk creation |
| Cities / Villages / Roads | Capitals, cities, villages, farms, markets, castles, trade roads |
| Dungeons | Underground crypts with loot, traps, boss rooms |
| Human NPCs | `wgn_npc` entity — walk, interact, defend settlements |
| Structure database | JSON blueprints + procedural builder with material palettes |
| Reputation | Faction memory — help kingdoms / attack NPCs |
| Economy | WGN coins (start with 25), quest rewards |
| Quests | Data-driven quests unlocked via dialogue |
| Dialogue | Right-click NPCs for branching conversation |
| Custom UI | Press **J** — reputation, quest journal, coin balance |

### In-game usage

- Explore new chunks to discover kingdoms (every ~1024 blocks)
- **Right-click** any WGN NPC to open dialogue
- Press **J** to open the WGN menu
- `/wgn quest start explore_abandoned_village` — start a quest manually
- `/wgn coins give 50` — grant coins (testing)
- `/wgn reputation add medieval_kingdom 10` — adjust reputation

## License

MIT — see [LICENSE](LICENSE).
