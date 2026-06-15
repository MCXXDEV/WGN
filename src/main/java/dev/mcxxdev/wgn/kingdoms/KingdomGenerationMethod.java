package dev.mcxxdev.wgn.kingdoms;

import dev.mcxxdev.wgn.core.registry.Identifiable;

/**
 * Techniques used to procedurally generate and expand kingdoms.
 */
public enum KingdomGenerationMethod implements Identifiable {
	JIGSAW_STRUCTURE("jigsaw_structure"),
	DISTRICT_SYSTEM("district_system"),
	ROAD_SYSTEM("road_system"),
	BUILDING_TEMPLATE("building_template");

	private final String id;

	KingdomGenerationMethod(String id) {
		this.id = id;
	}

	@Override
	public String id() {
		return id;
	}
}
