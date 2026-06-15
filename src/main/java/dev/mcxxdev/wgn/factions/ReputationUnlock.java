package dev.mcxxdev.wgn.factions;

import dev.mcxxdev.wgn.core.registry.Identifiable;

/**
 * Rewards unlocked through positive faction reputation.
 */
public enum ReputationUnlock implements Identifiable {
	QUESTS("quests"),
	TRADES("trades"),
	REWARDS("rewards"),
	TITLES("titles");

	private final String id;

	ReputationUnlock(String id) {
		this.id = id;
	}

	@Override
	public String id() {
		return id;
	}
}
