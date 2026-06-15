package dev.mcxxdev.wgn.kingdoms;

import dev.mcxxdev.wgn.core.registry.Identifiable;

/**
 * Settlement and infrastructure elements within a civilization.
 */
public enum SettlementType implements Identifiable {
	CAPITAL("capital"),
	CITY("city"),
	VILLAGE("village"),
	ROAD("road"),
	FARM("farm"),
	MARKET("market"),
	CASTLE("castle"),
	TOWER("tower"),
	OUTPOST("outpost");

	private final String id;

	SettlementType(String id) {
		this.id = id;
	}

	@Override
	public String id() {
		return id;
	}
}
