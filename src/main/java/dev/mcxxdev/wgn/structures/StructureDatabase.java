package dev.mcxxdev.wgn.structures;

import dev.mcxxdev.wgn.WGN;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public final class StructureDatabase {
	private static final Map<StructureCategory, List<StructureBlueprint>> BY_CATEGORY = new ConcurrentHashMap<>();
	private static List<StructureBlueprint> all = List.of();

	private StructureDatabase() {}

	public static void applyLoaded(List<StructureBlueprint> loaded) {
		all = List.copyOf(loaded);
		BY_CATEGORY.clear();
		for (StructureBlueprint blueprint : all) {
			BY_CATEGORY.computeIfAbsent(blueprint.category(), key -> new ArrayList<>()).add(blueprint);
		}
		WGN.LOGGER.info("Structure database loaded {} blueprints", all.size());
	}

	public static List<StructureBlueprint> all() {
		return all;
	}

	public static Optional<StructureBlueprint> get(String id) {
		return all.stream().filter(blueprint -> blueprint.id().equals(id)).findFirst();
	}

	public static Optional<StructureBlueprint> pick(StructureCategory category) {
		List<StructureBlueprint> pool = BY_CATEGORY.getOrDefault(category, List.of());
		if (pool.isEmpty()) {
			return Optional.empty();
		}
		int total = pool.stream().mapToInt(StructureBlueprint::weight).sum();
		int roll = ThreadLocalRandom.current().nextInt(Math.max(total, 1));
		int cursor = 0;
		for (StructureBlueprint blueprint : pool) {
			cursor += blueprint.weight();
			if (roll < cursor) {
				return Optional.of(blueprint);
			}
		}
		return Optional.of(pool.getFirst());
	}

	public static Block resolveBlock(String id) {
		ResourceLocation location = ResourceLocation.tryParse(id);
		if (location == null) {
			return Blocks.STONE;
		}
		return BuiltInRegistries.BLOCK.getOptional(location).orElse(Blocks.STONE);
	}

	public static BlockState paletteState(MaterialPalette palette, int index) {
		List<String> blocks = palette.blocks();
		if (blocks.isEmpty()) {
			return Blocks.STONE_BRICKS.defaultBlockState();
		}
		return resolveBlock(blocks.get(Math.floorMod(index, blocks.size()))).defaultBlockState();
	}
}
