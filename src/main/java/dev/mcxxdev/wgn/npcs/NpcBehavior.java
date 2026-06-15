package dev.mcxxdev.wgn.npcs;

import dev.mcxxdev.wgn.core.registry.Identifiable;

/**
 * Daily routines and behaviors for human NPCs.
 */
public enum NpcBehavior implements Identifiable {
	WALK("walk"),
	SLEEP("sleep"),
	EAT("eat"),
	WORK("work"),
	TRADE("trade"),
	TRAVEL("travel"),
	DEFEND_CITY("defend_city");

	private final String id;

	NpcBehavior(String id) {
		this.id = id;
	}

	@Override
	public String id() {
		return id;
	}
}
