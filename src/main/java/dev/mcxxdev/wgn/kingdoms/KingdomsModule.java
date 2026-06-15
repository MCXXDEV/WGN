package dev.mcxxdev.wgn.kingdoms;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnModule;
import dev.mcxxdev.wgn.core.registry.WgnRegistries;

/**
 * WGN-Kingdoms — procedural kingdom generation, cities, villages, roads.
 */
public final class KingdomsModule implements WgnModule {
	@Override
	public String id() {
		return "wgn-kingdoms";
	}

	@Override
	public String displayName() {
		return "WGN-Kingdoms";
	}

	@Override
	public String[] dependencies() {
		return new String[] { "wgn-core", "wgn-structures" };
	}

	@Override
	public void initialize() {
		WGN.LOGGER.info(
				"Kingdom engine ready — {} civilizations, {} settlements, {} road types",
				WgnRegistries.CIVILIZATIONS.size(),
				WgnRegistries.SETTLEMENTS.size(),
				WgnRegistries.ROADS.size()
		);
	}
}
