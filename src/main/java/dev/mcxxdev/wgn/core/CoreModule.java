package dev.mcxxdev.wgn.core;

import dev.mcxxdev.wgn.WGN;

/**
 * WGN-Core — foundation module providing registry infrastructure,
 * shared types, and bootstrap coordination.
 */
public final class CoreModule implements WgnModule {
	@Override
	public String id() {
		return "wgn-core";
	}

	@Override
	public String displayName() {
		return "WGN-Core";
	}

	@Override
	public void initialize() {
		WGN.LOGGER.info("Core registry and shared infrastructure ready");
	}
}
