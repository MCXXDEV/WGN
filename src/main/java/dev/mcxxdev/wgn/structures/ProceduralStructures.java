package dev.mcxxdev.wgn.structures;

import dev.mcxxdev.wgn.kingdoms.SettlementType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class ProceduralStructures {
	private ProceduralStructures() {}

	public static void placeSettlement(
			WorldGenLevel level,
			BlockPos origin,
			SettlementType type,
			MaterialPalette palette,
			RandomSource random
	) {
		switch (type) {
			case CAPITAL, CITY -> placeCity(level, origin, palette, random, type == SettlementType.CAPITAL);
			case VILLAGE -> placeVillage(level, origin, palette, random);
			case CASTLE -> placeCastle(level, origin, palette, random);
			case MARKET -> placeMarket(level, origin, palette, random);
			case FARM -> placeFarm(level, origin, palette, random);
			case TOWER -> placeTower(level, origin, palette, random);
			case OUTPOST -> placeOutpost(level, origin, palette, random);
			default -> placeHouse(level, origin, palette, random, 9, 7);
		}
	}

	public static void placeFromBlueprint(WorldGenLevel level, BlockPos origin, StructureBlueprint blueprint, RandomSource random) {
		switch (blueprint.category()) {
			case CASTLE, PALACE -> placeCastle(level, origin, blueprint.palette(), random);
			case MARKET -> placeMarket(level, origin, blueprint.palette(), random);
			case FARM -> placeFarm(level, origin, blueprint.palette(), random);
			case TOWER -> placeTower(level, origin, blueprint.palette(), random);
			case HOUSE -> placeHouse(level, origin, blueprint.palette(), random, blueprint.depth(), blueprint.width());
			default -> placeHouse(level, origin, blueprint.palette(), random, blueprint.depth(), blueprint.width());
		}
	}

	public static void placeRoad(WorldGenLevel level, BlockPos pos, MaterialPalette palette, boolean bridge) {
		BlockState surface = StructureDatabase.paletteState(palette, 1);
		BlockState edge = StructureDatabase.paletteState(palette, 0);
		level.setBlock(pos, bridge && level.getFluidState(pos).isSource() ? Blocks.OAK_PLANKS.defaultBlockState() : surface, 2);
		level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 2);
		if (bridge) {
			for (int dx = -1; dx <= 1; dx++) {
				for (int dz = -1; dz <= 1; dz++) {
					if (dx == 0 && dz == 0) {
						continue;
					}
					BlockPos support = pos.offset(dx, -1, dz);
					if (!level.getFluidState(support).isEmpty()) {
						level.setBlock(support, Blocks.OAK_FENCE.defaultBlockState(), 2);
					}
				}
			}
		}
		level.setBlock(pos.north(), edge, 2);
		level.setBlock(pos.south(), edge, 2);
	}

	private static void placeCity(WorldGenLevel level, BlockPos origin, MaterialPalette palette, RandomSource random, boolean capital) {
		int radius = capital ? 6 : 4;
		flatten(level, origin, radius * 3 + 6);
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				if (Math.abs(x) == radius || Math.abs(z) == radius) {
					placeWall(level, origin.offset(x, 0, z), palette);
				}
			}
		}
		placeCastle(level, origin, palette, random);
		placeMarket(level, origin.offset(radius - 1, 0, 0), palette, random);
		for (int i = 0; i < (capital ? 6 : 4); i++) {
			int x = random.nextInt(radius * 2) - radius;
			int z = random.nextInt(radius * 2) - radius;
			placeHouse(level, origin.offset(x, 0, z), palette, random, 7 + random.nextInt(3), 5 + random.nextInt(2));
		}
	}

	private static void placeVillage(WorldGenLevel level, BlockPos origin, MaterialPalette palette, RandomSource random) {
		flatten(level, origin, 18);
		placeWell(level, origin, palette);
		for (int i = 0; i < 4; i++) {
			int x = random.nextInt(11) - 5;
			int z = random.nextInt(11) - 5;
			placeHouse(level, origin.offset(x, 0, z), palette, random, 7, 5);
		}
		placeFarm(level, origin.offset(6, 0, -4), palette, random);
	}

	private static void placeCastle(WorldGenLevel level, BlockPos origin, MaterialPalette palette, RandomSource random) {
		BlockState wall = StructureDatabase.paletteState(palette, 0);
		BlockState accent = StructureDatabase.paletteState(palette, 2);
		for (int y = 0; y < 8; y++) {
			for (int x = -4; x <= 4; x++) {
				for (int z = -4; z <= 4; z++) {
					boolean edge = Math.abs(x) == 4 || Math.abs(z) == 4 || y == 0;
					if (!edge && y < 7) {
						continue;
					}
					level.setBlock(origin.offset(x, y, z), y >= 6 ? accent : wall, 2);
				}
			}
		}
		level.setBlock(origin.offset(0, 1, -4), Blocks.AIR.defaultBlockState(), 2);
		level.setBlock(origin.offset(0, 2, -4), Blocks.AIR.defaultBlockState(), 2);
	}

	private static void placeMarket(WorldGenLevel level, BlockPos origin, MaterialPalette palette, RandomSource random) {
		BlockState stall = StructureDatabase.paletteState(palette, 5);
		BlockState roof = StructureDatabase.paletteState(palette, 2);
		for (int i = -2; i <= 2; i++) {
			level.setBlock(origin.offset(i, 0, 0), stall, 2);
			level.setBlock(origin.offset(i, 1, 0), Blocks.OAK_FENCE.defaultBlockState(), 2);
			level.setBlock(origin.offset(i, 2, 0), roof, 2);
			level.setBlock(origin.offset(0, 0, i), stall, 2);
		}
	}

	private static void placeFarm(WorldGenLevel level, BlockPos origin, MaterialPalette palette, RandomSource random) {
		for (int x = 0; x < 6; x++) {
			for (int z = 0; z < 6; z++) {
				level.setBlock(origin.offset(x, 0, z), Blocks.FARMLAND.defaultBlockState(), 2);
				if (random.nextBoolean()) {
					level.setBlock(origin.offset(x, 1, z), Blocks.WHEAT.defaultBlockState(), 2);
				}
			}
		}
		level.setBlock(origin.offset(-1, 0, 2), Blocks.COMPOSTER.defaultBlockState(), 2);
	}

	private static void placeTower(WorldGenLevel level, BlockPos origin, MaterialPalette palette, RandomSource random) {
		BlockState wall = StructureDatabase.paletteState(palette, 0);
		for (int y = 0; y < 12; y++) {
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					if (Math.abs(x) + Math.abs(z) > 1 && y > 0) {
						continue;
					}
					level.setBlock(origin.offset(x, y, z), wall, 2);
				}
			}
		}
		level.setBlock(origin.offset(0, 12, 0), Blocks.TORCH.defaultBlockState(), 2);
	}

	private static void placeOutpost(WorldGenLevel level, BlockPos origin, MaterialPalette palette, RandomSource random) {
		placeTower(level, origin, palette, random);
		placeHouse(level, origin.offset(3, 0, 0), palette, random, 5, 5);
	}

	private static void placeHouse(WorldGenLevel level, BlockPos origin, MaterialPalette palette, RandomSource random, int width, int depth) {
		BlockState wall = StructureDatabase.paletteState(palette, 0);
		BlockState roof = StructureDatabase.paletteState(palette, 2);
		BlockState floor = StructureDatabase.paletteState(palette, 1);
		for (int x = 0; x < width; x++) {
			for (int z = 0; z < depth; z++) {
				level.setBlock(origin.offset(x, 0, z), floor, 2);
				for (int y = 1; y <= 3; y++) {
					boolean edge = x == 0 || z == 0 || x == width - 1 || z == depth - 1;
					if (edge) {
						level.setBlock(origin.offset(x, y, z), wall, 2);
					} else {
						level.setBlock(origin.offset(x, y, z), Blocks.AIR.defaultBlockState(), 2);
					}
				}
				level.setBlock(origin.offset(x, 4, z), roof, 2);
			}
		}
		level.setBlock(origin.offset(width / 2, 1, 0), Blocks.AIR.defaultBlockState(), 2);
		level.setBlock(origin.offset(width / 2, 2, 0), Blocks.AIR.defaultBlockState(), 2);
	}

	private static void placeWell(WorldGenLevel level, BlockPos origin, MaterialPalette palette) {
		level.setBlock(origin, Blocks.COBBLESTONE.defaultBlockState(), 2);
		level.setBlock(origin.above(), Blocks.WATER.defaultBlockState(), 2);
		level.setBlock(origin.above(2), Blocks.OAK_FENCE.defaultBlockState(), 2);
	}

	private static void placeWall(WorldGenLevel level, BlockPos pos, MaterialPalette palette) {
		BlockState wall = StructureDatabase.paletteState(palette, 0);
		level.setBlock(pos, wall, 2);
		level.setBlock(pos.above(), wall, 2);
	}

	private static void flatten(WorldGenLevel level, BlockPos center, int size) {
		int half = size / 2;
		for (int x = -half; x <= half; x++) {
			for (int z = -half; z <= half; z++) {
				BlockPos surface = findSurface(level, center.offset(x, 0, z));
				for (int y = surface.getY() - 1; y <= surface.getY(); y++) {
					level.setBlock(new BlockPos(surface.getX(), y, surface.getZ()), Blocks.GRASS_BLOCK.defaultBlockState(), 2);
				}
			}
		}
	}

	private static BlockPos findSurface(WorldGenLevel level, BlockPos pos) {
		BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos(pos.getX(), level.getMaxBuildHeight() - 1, pos.getZ());
		while (cursor.getY() > level.getMinBuildHeight() && level.isEmptyBlock(cursor)) {
			cursor.move(0, -1, 0);
		}
		return cursor.above();
	}
}
