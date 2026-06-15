package dev.mcxxdev.wgn.build;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class BuildColorMapper {
	private static final Map<String, Block> COLORS = new LinkedHashMap<>();

	static {
		register("red", Blocks.RED_CONCRETE);
		register("blue", Blocks.BLUE_CONCRETE);
		register("green", Blocks.GREEN_CONCRETE);
		register("yellow", Blocks.YELLOW_CONCRETE);
		register("white", Blocks.WHITE_CONCRETE);
		register("black", Blocks.BLACK_CONCRETE);
		register("gray", Blocks.GRAY_CONCRETE);
		register("grey", Blocks.GRAY_CONCRETE);
		register("orange", Blocks.ORANGE_CONCRETE);
		register("purple", Blocks.PURPLE_CONCRETE);
		register("pink", Blocks.PINK_CONCRETE);
		register("brown", Blocks.BROWN_CONCRETE);
		register("cyan", Blocks.CYAN_CONCRETE);
		register("lime", Blocks.LIME_CONCRETE);
		register("magenta", Blocks.MAGENTA_CONCRETE);
		register("light blue", Blocks.LIGHT_BLUE_CONCRETE);
		register("light gray", Blocks.LIGHT_GRAY_CONCRETE);
		register("light grey", Blocks.LIGHT_GRAY_CONCRETE);
		register("gold", Blocks.GOLD_BLOCK);
		register("golden", Blocks.GOLD_BLOCK);
		register("silver", Blocks.IRON_BLOCK);
		register("stone", Blocks.STONE_BRICKS);
		register("cobble", Blocks.COBBLESTONE);
		register("cobblestone", Blocks.COBBLESTONE);
		register("brick", Blocks.BRICKS);
		register("bricks", Blocks.BRICKS);
		register("wood", Blocks.OAK_PLANKS);
		register("oak", Blocks.OAK_PLANKS);
		register("spruce", Blocks.SPRUCE_PLANKS);
		register("dark oak", Blocks.DARK_OAK_PLANKS);
		register("sand", Blocks.SANDSTONE);
		register("sandstone", Blocks.SANDSTONE);
		register("ice", Blocks.PACKED_ICE);
		register("snow", Blocks.SNOW_BLOCK);
		register("glass", Blocks.GLASS);
		register("obsidian", Blocks.OBSIDIAN);
		register("emerald", Blocks.EMERALD_BLOCK);
		register("diamond", Blocks.DIAMOND_BLOCK);
		register("lapis", Blocks.LAPIS_BLOCK);
		register("copper", Blocks.COPPER_BLOCK);
		register("terracotta", Blocks.TERRACOTTA);
		register("crimson", Blocks.CRIMSON_PLANKS);
		register("warped", Blocks.WARPED_PLANKS);
	}

	private BuildColorMapper() {}

	private static void register(String name, Block block) {
		COLORS.put(name, block);
	}

	public static List<Block> extractColors(String text) {
		String lower = text.toLowerCase(Locale.ROOT);
		List<Block> found = new ArrayList<>();
		List<String> keys = COLORS.keySet().stream().sorted((a, b) -> Integer.compare(b.length(), a.length())).toList();
		String remaining = " " + lower + " ";
		for (String key : keys) {
			String token = " " + key + " ";
			if (remaining.contains(token)) {
				found.add(COLORS.get(key));
				remaining = remaining.replace(token, " ");
			}
		}
		return found;
	}

	public static Block primary(List<Block> colors) {
		return colors.isEmpty() ? Blocks.STONE_BRICKS : colors.getFirst();
	}

	public static Block secondary(List<Block> colors) {
		if (colors.size() >= 2) {
			return colors.get(1);
		}
		return colors.isEmpty() ? Blocks.OAK_PLANKS : colors.getFirst();
	}

	public static Block accent(List<Block> colors) {
		if (colors.size() >= 3) {
			return colors.get(2);
		}
		return secondary(colors);
	}
}
