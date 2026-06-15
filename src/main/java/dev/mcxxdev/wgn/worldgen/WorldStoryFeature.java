package dev.mcxxdev.wgn.worldgen;

import dev.mcxxdev.wgn.core.registry.Identifiable;

/**
 * Environmental storytelling features that make exploration meaningful.
 */
public enum WorldStoryFeature implements Identifiable {
	ABANDONED_VILLAGE("abandoned_village"),
	BATTLEFIELD("battlefield"),
	ANCIENT_RUIN("ancient_ruin"),
	LOST_TEMPLE("lost_temple"),
	DESTROYED_CASTLE("destroyed_castle");

	private final String id;

	WorldStoryFeature(String id) {
		this.id = id;
	}

	@Override
	public String id() {
		return id;
	}
}
