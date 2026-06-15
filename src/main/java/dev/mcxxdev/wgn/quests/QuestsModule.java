package dev.mcxxdev.wgn.quests;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnModule;

/**
 * WGN-Quests — dynamic adventures unlocked through reputation and world events.
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
		return new String[] { "wgn-core", "wgn-factions", "wgn-economy", "wgn-dungeons" };
	}

	@Override
	public void initialize() {
		WGN.LOGGER.info("Quest and adventure system registered");
	}
}
