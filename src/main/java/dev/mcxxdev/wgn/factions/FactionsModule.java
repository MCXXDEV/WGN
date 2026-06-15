package dev.mcxxdev.wgn.factions;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnModule;

/**
 * WGN-Factions — reputation system where factions remember player actions.
 */
public final class FactionsModule implements WgnModule {
	@Override
	public String id() {
		return "wgn-factions";
	}

	@Override
	public String displayName() {
		return "WGN-Factions";
	}

	@Override
	public String[] dependencies() {
		return new String[] { "wgn-core", "wgn-kingdoms", "wgn-npcs" };
	}

	@Override
	public void initialize() {
		WGN.LOGGER.info("Faction reputation system registered");
	}
}
