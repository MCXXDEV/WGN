package dev.mcxxdev.wgn.quests;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnModule;

/**
 * WGN-Quests — reputation-gated dynamic adventures.
 */
public final class QuestsModule implements WgnModule {
	@Override
	public String id() {
		return "wgn-quests";
	}

	@Override
	public String displayName() {
		return "WGN-Quests";
	}

	@Override
	public String[] dependencies() {
		return new String[] { "wgn-core", "wgn-factions", "wgn-economy" };
	}

	@Override
	public void initialize() {
		WGN.LOGGER.info("Quest system active — data-driven quests with coin and reputation rewards");
	}
}
