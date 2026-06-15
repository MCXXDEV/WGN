package dev.mcxxdev.wgn.dungeons;

import dev.mcxxdev.wgn.core.registry.Identifiable;

/**
 * Room and encounter features inside generated dungeons.
 */
public enum DungeonFeature implements Identifiable {
	LOOT_ROOM("loot_room"),
	BOSS_ROOM("boss_room"),
	SECRET("secret"),
	PUZZLE("puzzle"),
	TRAP("trap");

	private final String id;

	DungeonFeature(String id) {
		this.id = id;
	}

	@Override
	public String id() {
		return id;
	}
}
