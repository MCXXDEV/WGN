package dev.mcxxdev.wgn.economy;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnModule;

/**
 * WGN-Economy — WGN coins, trade, and market rewards.
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
		return new String[] { "wgn-core", "wgn-npcs" };
	}

	@Override
	public void initialize() {
		WGN.LOGGER.info("Economy active — WGN coins, emerald trade (buy {}, sell {})",
				EconomyManager.EMERALD_BUY_PRICE, EconomyManager.EMERALD_SELL_PRICE);
	}
}
