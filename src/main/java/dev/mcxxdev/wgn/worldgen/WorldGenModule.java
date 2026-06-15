package dev.mcxxdev.wgn.worldgen;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnModule;
import dev.mcxxdev.wgn.core.registry.WgnRegistries;

/**
 * WGN-WorldGen — kingdom chunk generation, roads, settlements, dungeons.
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
		return new String[] { "wgn-core", "wgn-structures", "wgn-kingdoms", "wgn-dungeons", "wgn-npcs" };
	}

	@Override
	public void initialize() {
		KingdomChunkGenerator.register();
		WGN.LOGGER.info(
				"World generation active — chunk kingdoms, {} exploration features",
				WgnRegistries.EXPLORATION_FEATURES.size()
		);
	}
}
