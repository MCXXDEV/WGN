package dev.mcxxdev.wgn.economy;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnModule;

/**
 * WGN-Economy — trade, markets, and economic simulation across settlements.
 */
public final class EconomyModule implements WgnModule {
	@Override
	public String id() {
		return "wgn-economy";
	}

	@Override
	public String displayName() {
		return "WGN-Economy";
	}

	@Override
	public String[] dependencies() {
		return new String[] { "wgn-core", "wgn-kingdoms", "wgn-npcs" };
	}

	@Override
	public void initialize() {
		WGN.LOGGER.info("Economy and trade system registered");
	}
}
