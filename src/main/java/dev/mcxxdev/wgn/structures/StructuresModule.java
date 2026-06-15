package dev.mcxxdev.wgn.structures;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnModule;
import dev.mcxxdev.wgn.core.registry.WgnRegistries;

/**
 * WGN-Structures — structure blueprint database, templates, and material palettes.
 * Target: 100,000+ procedural structure blueprints.
 */
public final class StructuresModule implements WgnModule {
	@Override
	public String id() {
		return "wgn-structures";
	}

	@Override
	public String displayName() {
		return "WGN-Structures";
	}

	@Override
	public String[] dependencies() {
		return new String[] { "wgn-core", "wgn-worldgen" };
	}

	@Override
	public void initialize() {
		WGN.LOGGER.info(
				"Structure database registered — {} categories, {} material palettes",
				WgnRegistries.STRUCTURE_CATEGORIES.size(),
				WgnRegistries.MATERIAL_PALETTES.size()
		);
	}
}
