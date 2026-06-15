package dev.mcxxdev.wgn.dungeons;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnModule;

/**
 * WGN-Dungeons — crypts, ruins, catacombs, strongholds, temples, and boss arenas.
 */
public final class DungeonsModule implements WgnModule {
	@Override
	public String id() {
		return "wgn-dungeons";
	}

	@Override
	public String displayName() {
		return "WGN-Dungeons";
	}

	@Override
	public String[] dependencies() {
		return new String[] { "wgn-core", "wgn-worldgen", "wgn-structures" };
	}

	@Override
	public void initialize() {
		WGN.LOGGER.info("Dungeon generation system registered (loot, bosses, puzzles, traps)");
	}
}
