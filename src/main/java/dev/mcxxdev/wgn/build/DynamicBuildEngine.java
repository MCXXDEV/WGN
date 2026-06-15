package dev.mcxxdev.wgn.build;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

/**
 * Builds structures from parsed {@link BuildSpec} requests with material intelligence.
 * Clears all blocks in the structure footprint before placing.
 */
public final class DynamicBuildEngine {
	private DynamicBuildEngine() {}

	public static BlockPos build(ServerLevel level, BlockPos origin, BuildSpec spec) {
		clearStructureVolume(level, origin, spec);
		return switch (spec.structureType()) {
			case HOUSE -> buildHouse(level, origin, spec);
			case CASTLE -> buildCastle(level, origin, spec);
			case TOWER -> buildTower(level, origin, spec);
			case BRIDGE -> buildBridge(level, origin, spec);
			case WALL -> buildWall(level, origin, spec);
			case FARM -> buildFarm(level, origin, spec);
			case MARKET -> buildMarket(level, origin, spec);
			case PALACE -> buildPalace(level, origin, spec);
			case TEMPLE -> buildTemple(level, origin, spec);
			case DUNGEON -> buildDungeon(level, origin, spec);
			case RUIN -> buildRuin(level, origin, spec);
			case ARENA -> buildArena(level, origin, spec);
			case WELL -> buildWell(level, origin, spec);
		};
	}

	public static BlockPos findPlacement(ServerLevel level, BlockPos target) {
		int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, target.getX(), target.getZ());
		return new BlockPos(target.getX(), y, target.getZ());
	}

	/** Removes every block in the structure's bounding volume (trees, stone, water, etc.). */
	private static void clearStructureVolume(ServerLevel level, BlockPos origin, BuildSpec spec) {
		BuildFootprint footprint = BuildFootprint.forSpec(spec);
		for (int x = footprint.minX(); x <= footprint.maxX(); x++) {
			for (int y = footprint.minY(); y <= footprint.maxY(); y++) {
				for (int z = footprint.minZ(); z <= footprint.maxZ(); z++) {
					clearBlock(level, origin.offset(x, y, z));
				}
			}
		}
	}

	private static void clearBlock(ServerLevel level, BlockPos pos) {
		if (level.isOutsideBuildHeight(pos)) {
			return;
		}
		if (level.getBlockState(pos).isAir()) {
			return;
		}
		level.removeBlock(pos, false);
		level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
	}

	private static BlockPos buildHouse(ServerLevel level, BlockPos origin, BuildSpec spec) {
		int w = spec.size().width();
		int d = spec.size().depth();
		int h = spec.size().height();
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				for (int z = 0; z < d; z++) {
					boolean wall = x == 0 || z == 0 || x == w - 1 || z == d - 1;
					boolean floor = y == 0;
					boolean roof = y == h - 1;
					BlockPos pos = origin.offset(x, y, z);
					if (floor) {
						set(level, pos, spec.secondary());
					} else if (roof) {
						set(level, pos, spec.accent());
					} else if (wall) {
						set(level, pos, wallBlock(spec, x, z));
					}
				}
			}
		}
		setAir(level, origin.offset(w / 2, 1, 0));
		setAir(level, origin.offset(w / 2, 2, 0));
		if (w > 7) {
			set(level, origin.offset(0, 2, d / 2), Blocks.GLASS.defaultBlockState());
			set(level, origin.offset(w - 1, 2, d / 2), Blocks.GLASS.defaultBlockState());
		}
		return origin;
	}

	private static BlockPos buildCastle(ServerLevel level, BlockPos origin, BuildSpec spec) {
		int radius = Math.max(4, spec.size().width() / 2);
		int height = spec.size().height() + 4;
		for (int y = 0; y < height; y++) {
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					boolean wall = Math.abs(x) == radius || Math.abs(z) == radius;
					if (!wall && y > 0 && y < height - 1) {
						continue;
					}
					set(level, origin.offset(x, y, z), y >= height - 2 ? spec.accent().defaultBlockState() : wallBlock(spec, x, z));
				}
			}
		}
		for (int corner : new int[] { -radius, radius }) {
			buildTower(level, origin.offset(corner, 0, corner), spec.withSize(BuildSize.SMALL));
		}
		setAir(level, origin.offset(0, 1, -radius));
		setAir(level, origin.offset(0, 2, -radius));
		return origin;
	}

	private static BlockPos buildTower(ServerLevel level, BlockPos origin, BuildSpec spec) {
		int h = spec.size().height() + 6;
		int r = Math.max(2, spec.size().width() / 4);
		for (int y = 0; y < h; y++) {
			for (int x = -r; x <= r; x++) {
				for (int z = -r; z <= r; z++) {
					if (Math.abs(x) != r && Math.abs(z) != r && y > 0) {
						continue;
					}
					set(level, origin.offset(x, y, z), wallBlock(spec, x + y, z));
				}
			}
		}
		set(level, origin.above(h), Blocks.TORCH.defaultBlockState());
		return origin;
	}

	private static BlockPos buildBridge(ServerLevel level, BlockPos origin, BuildSpec spec) {
		int length = spec.size().width() + 10;
		int width = 3 + spec.size().stories();
		for (int i = 0; i < length; i++) {
			for (int w = 0; w < width; w++) {
				BlockPos pos = origin.offset(i, 0, w);
				set(level, pos, spec.primary());
				BlockPos below = pos.below();
				if (!level.getFluidState(below).isEmpty()) {
					set(level, below, Blocks.OAK_FENCE.defaultBlockState());
				}
			}
		}
		return origin;
	}

	private static BlockPos buildWall(ServerLevel level, BlockPos origin, BuildSpec spec) {
		int length = spec.size().width() + 8;
		int height = spec.size().height();
		for (int i = 0; i < length; i++) {
			for (int y = 0; y < height; y++) {
				set(level, origin.offset(i, y, 0), wallBlock(spec, i, y));
			}
		}
		return origin;
	}

	private static BlockPos buildFarm(ServerLevel level, BlockPos origin, BuildSpec spec) {
		int size = spec.size().width();
		for (int x = 0; x < size; x++) {
			for (int z = 0; z < size; z++) {
				set(level, origin.offset(x, 0, z), Blocks.FARMLAND.defaultBlockState());
				if ((x + z) % 2 == 0) {
					set(level, origin.offset(x, 1, z), Blocks.WHEAT.defaultBlockState());
				}
			}
		}
		buildHouse(level, origin.offset(size + 2, 0, 0), spec);
		return origin;
	}

	private static BlockPos buildMarket(ServerLevel level, BlockPos origin, BuildSpec spec) {
		int stalls = Math.max(3, spec.size().width() / 3);
		for (int i = 0; i < stalls; i++) {
			BlockPos stall = origin.offset(i * 3, 0, 0);
			set(level, stall, spec.primary());
			set(level, stall.above(), Blocks.OAK_FENCE.defaultBlockState());
			set(level, stall.above(2), spec.accent());
		}
		return origin;
	}

	private static BlockPos buildPalace(ServerLevel level, BlockPos origin, BuildSpec spec) {
		buildCastle(level, origin, spec.withSize(BuildSize.HUGE));
		buildHouse(level, origin.offset(spec.size().width(), 0, 0), spec.withSize(BuildSize.LARGE));
		return origin;
	}

	private static BlockPos buildTemple(ServerLevel level, BlockPos origin, BuildSpec spec) {
		int w = spec.size().width() + 4;
		int d = spec.size().depth() + 4;
		for (int x = 0; x < w; x++) {
			for (int z = 0; z < d; z++) {
				boolean pillar = x % 4 == 0 && z % 4 == 0;
				set(level, origin.offset(x, 0, z), spec.secondary());
				if (pillar) {
					for (int y = 1; y <= spec.size().height(); y++) {
						set(level, origin.offset(x, y, z), spec.primary());
					}
				}
			}
		}
		return origin;
	}

	private static BlockPos buildDungeon(ServerLevel level, BlockPos origin, BuildSpec spec) {
		int r = Math.max(3, spec.size().width() / 3);
		for (int x = -r; x <= r; x++) {
			for (int z = -r; z <= r; z++) {
				for (int y = 0; y < 4; y++) {
					boolean shell = Math.abs(x) == r || Math.abs(z) == r || y == 0;
					BlockPos pos = origin.offset(x, y, z);
					if (shell) {
						set(level, pos, spec.primary());
					}
				}
			}
		}
		set(level, origin.above(), Blocks.CHEST.defaultBlockState());
		set(level, origin.offset(0, 1, 0), Blocks.CHEST.defaultBlockState());
		return origin;
	}

	private static BlockPos buildRuin(ServerLevel level, BlockPos origin, BuildSpec spec) {
		buildHouse(level, origin, spec);
		int w = spec.size().width();
		int d = spec.size().depth();
		for (int x = 0; x < w; x++) {
			for (int z = 0; z < d; z++) {
				if ((x + z) % 3 == 0) {
					setAir(level, origin.offset(x, 1, z));
					setAir(level, origin.offset(x, 2, z));
				}
			}
		}
		return origin;
	}

	private static BlockPos buildArena(ServerLevel level, BlockPos origin, BuildSpec spec) {
		int r = spec.size().width() / 2;
		for (int x = -r; x <= r; x++) {
			for (int z = -r; z <= r; z++) {
				if (Math.abs(x) == r || Math.abs(z) == r) {
					for (int y = 0; y < 3; y++) {
						set(level, origin.offset(x, y, z), wallBlock(spec, x, z));
					}
				} else {
					set(level, origin.offset(x, 0, z), Blocks.SAND.defaultBlockState());
				}
			}
		}
		return origin;
	}

	private static BlockPos buildWell(ServerLevel level, BlockPos origin, BuildSpec spec) {
		set(level, origin, spec.primary());
		set(level, origin.above(), Blocks.WATER.defaultBlockState());
		set(level, origin.above(2), Blocks.OAK_FENCE.defaultBlockState());
		return origin;
	}

	private static BlockState wallBlock(BuildSpec spec, int x, int z) {
		if (spec.colors().size() >= 2 && (x + z) % 2 == 0) {
			return spec.primary().defaultBlockState();
		}
		if (spec.colors().size() >= 2) {
			return spec.secondary().defaultBlockState();
		}
		return spec.primary().defaultBlockState();
	}

	private static void set(ServerLevel level, BlockPos pos, Block block) {
		level.setBlock(pos, block.defaultBlockState(), 3);
	}

	private static void set(ServerLevel level, BlockPos pos, BlockState state) {
		level.setBlock(pos, state, 3);
	}

	private static void setAir(ServerLevel level, BlockPos pos) {
		clearBlock(level, pos);
	}
}
