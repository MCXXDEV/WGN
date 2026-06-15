package dev.mcxxdev.wgn.structures;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnModule;
import dev.mcxxdev.wgn.core.registry.WgnRegistries;

/**
 * WGN-Structures — structure blueprint database, templates, and material palettes.
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
		return new String[] { "wgn-core" };
	}

	@Override
	public void initialize() {
		WGN.LOGGER.info(
				"Structure system ready — {} categories, {} palettes, procedural builder active",
				WgnRegistries.STRUCTURE_CATEGORIES.size(),
				WgnRegistries.MATERIAL_PALETTES.size()
		);
	}
}
