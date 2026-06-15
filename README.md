# WGN — World Generation Nexus

**WGN** is a Minecraft Fabric mod focused on **big procedural structure generation**.

Describe what you want in chat — WGN builds it with the right blocks and colors. New world chunks also generate kingdoms, cities, roads, and dungeons automatically.

## Platform

| Requirement | Version |
|-------------|---------|
| Minecraft | 1.21.1 |
| Loader | Fabric |
| Java | 21 |

## The only command

```
/wgn build <description>
```

**Examples:**
```
/wgn build a big blue and red house
/wgn build huge stone castle
/wgn build massive gold palace
/wgn build large glass tower
/wgn build long stone bridge
```

### What it understands

| You say | WGN does |
|---------|----------|
| **Size** — tiny, small, big, huge, massive | Scales the structure |
| **Colors** — blue, red, gold, stone, oak, glass… | Picks the right blocks |
| **Type** — house, castle, tower, bridge, palace… | Builds that layout |

### Structure types

house, castle, tower, bridge, wall, farm, market, palace, temple, dungeon, ruin, arena, well

### Blocked requests

Adult content, slurs, trademarked IP (Disney, Marvel, Pokémon, etc.), and inappropriate descriptions are rejected.

### Tips

- Face where you want the build — it places ~4 blocks in front of you
- Combine **size + colors + type** for best results: `/wgn build huge blue and red castle`

## Automatic world generation

Exploring **new chunks** spawns kingdoms (~every 1024 blocks) with cities, villages, roads, dungeons, and NPCs — no commands needed.

## Build the mod

```bash
./gradlew build
```

```bash
./gradlew runClient
```

## License

MIT — see [LICENSE](LICENSE).
