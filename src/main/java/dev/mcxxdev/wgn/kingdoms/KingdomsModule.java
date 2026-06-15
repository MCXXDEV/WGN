package dev.mcxxdev.wgn.kingdoms;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnModule;
import dev.mcxxdev.wgn.core.registry.WgnRegistries;

/**
 * WGN-Kingdoms — procedural kingdom generation via jigsaw structures,
 * district systems, road networks, and natural expansion.
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
		return new String[] { "wgn-core", "wgn-worldgen", "wgn-structures" };
	}

	@Override
	public void initialize() {
		WGN.LOGGER.info(
				"Civilization engine registered — {} types, {} settlements, {} road types",
				WgnRegistries.CIVILIZATIONS.size(),
				WgnRegistries.SETTLEMENTS.size(),
				WgnRegistries.ROADS.size()
		);
	}
}
