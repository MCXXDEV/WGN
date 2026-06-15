package dev.mcxxdev.wgn.dungeons;

import dev.mcxxdev.wgn.kingdoms.KingdomPlanner;
import dev.mcxxdev.wgn.structures.StructureDatabase;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;

public final class DungeonGenerator {
	private DungeonGenerator() {}

	public static boolean shouldPlaceDungeon(long worldSeed, int chunkX, int chunkZ) {
		long regionSeed = KingdomPlanner.regionSeed(worldSeed, KingdomPlanner.regionCoord(chunkX * 16), KingdomPlanner.regionCoord(chunkZ * 16));
		RandomSource random = RandomSource.create(regionSeed ^ (chunkX * 31L) ^ (chunkZ * 17L));
		return random.nextFloat() < 0.08F;
	}

	public static void placeDungeon(WorldGenLevel level, BlockPos surface, DungeonType type, RandomSource random) {
		int y = Math.max(level.getMinBuildHeight() + 12, surface.getY() - 24 - random.nextInt(16));
		BlockPos origin = new BlockPos(surface.getX(), y, surface.getZ());
		BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
		int radius = switch (type) {
			case BOSS_ARENA -> 8;
			case STRONGHOLD, TEMPLE -> 7;
			default -> 5;
		};
		for (int dx = -radius; dx <= radius; dx++) {
			for (int dz = -radius; dz <= radius; dz++) {
				for (int dy = 0; dy < 5; dy++) {
					cursor.set(origin).move(dx, dy, dz);
					boolean shell = Math.abs(dx) == radius || Math.abs(dz) == radius || dy == 0 || dy == 4;
					if (shell) {
						level.setBlock(cursor, StructureDatabase.paletteState(dev.mcxxdev.wgn.structures.MaterialPalette.ANCIENT, dy), 2);
					} else {
						level.setBlock(cursor, Blocks.AIR.defaultBlockState(), 2);
					}
				}
			}
		}
		placeFeatureRoom(level, origin, DungeonFeature.LOOT_ROOM, random);
		placeFeatureRoom(level, origin, DungeonFeature.TRAP, random);
		if (type == DungeonType.BOSS_ARENA || random.nextBoolean()) {
			placeFeatureRoom(level, origin, DungeonFeature.BOSS_ROOM, random);
		}
		if (random.nextBoolean()) {
			placeFeatureRoom(level, origin, DungeonFeature.SECRET, random);
		}
	}

	private static void placeFeatureRoom(WorldGenLevel level, BlockPos origin, DungeonFeature feature, RandomSource random) {
		BlockPos pos = origin.offset(random.nextInt(5) - 2, 1, random.nextInt(5) - 2);
		switch (feature) {
			case LOOT_ROOM -> {
				level.setBlock(pos, Blocks.CHEST.defaultBlockState(), 2);
				level.setBlock(pos.above(), Blocks.TORCH.defaultBlockState(), 2);
			}
			case BOSS_ROOM -> level.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 2);
			case TRAP -> level.setBlock(pos.below(), Blocks.STONE_PRESSURE_PLATE.defaultBlockState(), 2);
			case SECRET -> level.setBlock(pos, Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 2);
			case PUZZLE -> {
				level.setBlock(pos, Blocks.IRON_DOOR.defaultBlockState(), 2);
				level.setBlock(pos.east(), Blocks.STONE_BUTTON.defaultBlockState(), 2);
			}
		}
	}

	public static DungeonType pickType(RandomSource random) {
		DungeonType[] types = DungeonType.values();
		return types[random.nextInt(types.length)];
	}
}
