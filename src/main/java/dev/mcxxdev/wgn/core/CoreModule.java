package dev.mcxxdev.wgn.core;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.registry.WgnRegistries;

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
		WgnRegistries.bootstrap();
		WGN.LOGGER.info(
				"Core registries ready — {} civilizations, {} structure categories, {} NPC roles",
				WgnRegistries.CIVILIZATIONS.size(),
				WgnRegistries.STRUCTURE_CATEGORIES.size(),
				WgnRegistries.NPC_ROLES.size()
		);
	}
}
