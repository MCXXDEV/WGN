package dev.mcxxdev.wgn.structures;

import dev.mcxxdev.wgn.core.registry.Identifiable;

import java.util.List;

/**
 * Material intelligence — block palettes keyed by civilization and biome style.
 */
public enum MaterialPalette implements Identifiable {
	MEDIEVAL(
			"medieval",
			List.of("minecraft:stone_bricks", "minecraft:cobblestone", "minecraft:spruce_planks", "minecraft:dark_oak_planks", "minecraft:lantern", "minecraft:barrel")
	),
	DESERT(
			"desert",
			List.of("minecraft:sandstone", "minecraft:terracotta", "minecraft:smooth_sandstone", "minecraft:cut_sandstone")
	),
	NORTHERN(
			"northern",
			List.of("minecraft:spruce_log", "minecraft:stone", "minecraft:campfire", "minecraft:dark_oak_log", "minecraft:spruce_planks")
	),
	FOREST(
			"forest",
			List.of("minecraft:oak_log", "minecraft:mossy_cobblestone", "minecraft:vine", "minecraft:oak_leaves", "minecraft:lantern")
	),
	MOUNTAIN(
			"mountain",
			List.of("minecraft:stone", "minecraft:stone_bricks", "minecraft:andesite", "minecraft:polished_andesite", "minecraft:torch")
	),
	ANCIENT(
			"ancient",
			List.of("minecraft:deepslate_bricks", "minecraft:cracked_deepslate_bricks", "minecraft:chiseled_deepslate", "minecraft:soul_lantern")
	);

	private final String id;
	private final List<String> blocks;

	MaterialPalette(String id, List<String> blocks) {
		this.id = id;
		this.blocks = List.copyOf(blocks);
	}

	@Override
	public String id() {
		return id;
	}

	public List<String> blocks() {
		return blocks;
	}
}
