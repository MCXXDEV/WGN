package dev.mcxxdev.wgn.kingdoms;

import dev.mcxxdev.wgn.core.registry.Identifiable;

/**
 * Road network types that connect settlements logically.
 */
public enum RoadType implements Identifiable {
	VILLAGE_ROAD("village_road"),
	TRADE_ROUTE("trade_route"),
	KINGDOM_HIGHWAY("kingdom_highway"),
	MOUNTAIN_PATH("mountain_path"),
	BRIDGE("bridge");

	private final String id;

	RoadType(String id) {
		this.id = id;
	}

	@Override
	public String id() {
		return id;
	}
}
