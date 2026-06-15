package dev.mcxxdev.wgn.dungeons;

import dev.mcxxdev.wgn.core.registry.Identifiable;

/**
 * Dungeon archetypes with distinct layout and encounter profiles.
 */
public enum DungeonType implements Identifiable {
	CRYPT("crypt"),
	RUIN("ruin"),
	CATACOMB("catacomb"),
	STRONGHOLD("stronghold"),
	TEMPLE("temple"),
	BOSS_ARENA("boss_arena");

	private final String id;

	DungeonType(String id) {
		this.id = id;
	}

	@Override
	public String id() {
		return id;
	}
}
