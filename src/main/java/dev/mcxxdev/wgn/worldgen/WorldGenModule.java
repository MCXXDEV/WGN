package dev.mcxxdev.wgn.worldgen;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnModule;

/**
 * WGN-WorldGen — infinite terrain, biomes, and chunk-level discovery hooks.
 */
public final class WorldGenModule implements WgnModule {
	@Override
	public String id() {
		return "wgn-worldgen";
	}

	@Override
	public String displayName() {
		return "WGN-WorldGen";
	}

	@Override
	public String[] dependencies() {
		return new String[] { "wgn-core" };
	}

	@Override
	public void initialize() {
		WGN.LOGGER.info("World generation framework registered (terrain, biomes, exploration)");
	}
}
