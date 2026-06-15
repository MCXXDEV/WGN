package dev.mcxxdev.wgn.core;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.command.WgnCommands;
import dev.mcxxdev.wgn.data.WgnDataLoader;

/**
 * WGN-Core — structure generation bootstrap.
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
		WgnDataLoader.register();
		WgnCommands.register();
		WGN.LOGGER.info("WGN structure generation ready — use /wgn build <description>");
	}
}
