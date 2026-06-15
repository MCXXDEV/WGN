package dev.mcxxdev.wgn.worldgen;

import dev.mcxxdev.wgn.core.registry.Identifiable;

/**
 * Infinite exploration targets — every chunk can surface something new.
 */
public enum ExplorationFeature implements Identifiable {
	INFINITE_TERRAIN("infinite_terrain"),
	INFINITE_STRUCTURES("infinite_structures"),
	INFINITE_KINGDOM_EXPANSION("infinite_kingdom_expansion"),
	INFINITE_ROAD_NETWORKS("infinite_road_networks"),
	INFINITE_DUNGEONS("infinite_dungeons");

	private final String id;

	ExplorationFeature(String id) {
		this.id = id;
	}

	@Override
	public String id() {
		return id;
	}
}
