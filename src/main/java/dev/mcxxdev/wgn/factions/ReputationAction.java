package dev.mcxxdev.wgn.factions;

import dev.mcxxdev.wgn.core.registry.Identifiable;

/**
 * Player actions that factions remember and react to.
 */
public enum ReputationAction implements Identifiable {
	HELP_KINGDOM("help_kingdom", 10),
	ATTACK_VILLAGERS("attack_villagers", -25);

	private final String id;
	private final int reputationDelta;

	ReputationAction(String id, int reputationDelta) {
		this.id = id;
		this.reputationDelta = reputationDelta;
	}

	@Override
	public String id() {
		return id;
	}

	public int reputationDelta() {
		return reputationDelta;
	}
}
