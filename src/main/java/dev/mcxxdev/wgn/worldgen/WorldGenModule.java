package dev.mcxxdev.wgn.worldgen;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnModule;
import dev.mcxxdev.wgn.npcs.WgnEntities;

/**
 * WGN-WorldGen — automatic kingdoms, cities, roads, and dungeons in new chunks.
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
		return new String[] { "wgn-core", "wgn-structures" };
	}

	@Override
	public void initialize() {
		WgnEntities.register();
		KingdomChunkGenerator.register();
		WGN.LOGGER.info("Automatic structure generation active in new chunks");
	}
}
