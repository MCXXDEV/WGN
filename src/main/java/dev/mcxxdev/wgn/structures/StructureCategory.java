package dev.mcxxdev.wgn.structures;

import dev.mcxxdev.wgn.core.registry.Identifiable;

/**
 * Structure blueprint categories for the advanced structure database.
 * Target scale: 100,000+ blueprints combined into millions of layouts.
 */
public enum StructureCategory implements Identifiable {
	HOUSE("house"),
	CASTLE("castle"),
	MARKET("market"),
	BRIDGE("bridge"),
	WALL("wall"),
	ROAD("road"),
	TOWER("tower"),
	DUNGEON("dungeon"),
	RUIN("ruin"),
	FARM("farm"),
	PALACE("palace");

	private final String id;

	StructureCategory(String id) {
		this.id = id;
	}

	@Override
	public String id() {
		return id;
	}

	public static StructureCategory fromId(String id) {
		for (StructureCategory category : values()) {
			if (category.id.equals(id)) {
				return category;
			}
		}
		return HOUSE;
	}
}
