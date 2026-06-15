package dev.mcxxdev.wgn.structures;

import java.util.List;

/**
 * Material intelligence — block palettes keyed by civilization and biome style.
 */
public enum MaterialPalette {
	MEDIEVAL(
			"medieval",
			List.of("stone_bricks", "cobblestone", "spruce_planks", "dark_oak_planks", "lantern", "barrel")
	),
	DESERT(
			"desert",
			List.of("sandstone", "terracotta", "smooth_sandstone", "cut_sandstone")
	),
	NORTHERN(
			"northern",
			List.of("spruce_log", "stone", "campfire", "dark_oak_log", "spruce_planks")
	);

	private final String id;
	private final List<String> blocks;

	MaterialPalette(String id, List<String> blocks) {
		this.id = id;
		this.blocks = List.copyOf(blocks);
	}

	public String id() {
		return id;
	}

	public List<String> blocks() {
		return blocks;
	}
}
