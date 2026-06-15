package dev.mcxxdev.wgn.npcs;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnModule;

/**
 * WGN-NPCs — human NPC system with daily routines, trade, travel, and defense.
 */
public final class NpcsModule implements WgnModule {
	@Override
	public String id() {
		return "wgn-npcs";
	}

	@Override
	public String displayName() {
		return "WGN-NPCs";
	}

	@Override
	public String[] dependencies() {
		return new String[] { "wgn-core", "wgn-kingdoms" };
	}

	@Override
	public void initialize() {
		WGN.LOGGER.info("Human NPC system registered (roles, routines, trade, defense)");
	}
}
