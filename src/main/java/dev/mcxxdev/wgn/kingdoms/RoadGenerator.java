package dev.mcxxdev.wgn.kingdoms;

import dev.mcxxdev.wgn.structures.ProceduralStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

import java.util.List;

public final class RoadGenerator {
	private RoadGenerator() {}

	public static void generateRoadsForChunk(
			WorldGenLevel level,
			int chunkX,
			int chunkZ,
			KingdomPlanner.KingdomRegion region
	) {
		List<KingdomPlanner.SettlementSite> sites = region.settlements();
		if (sites.size() < 2) {
			return;
		}
		KingdomPlanner.SettlementSite hub = sites.getFirst();
		for (int i = 1; i < sites.size(); i++) {
			KingdomPlanner.SettlementSite target = sites.get(i);
			traceRoad(level, chunkX, chunkZ, hub.blockX(), hub.blockZ(), target.blockX(), target.blockZ(), region);
		}
	}

	private static void traceRoad(
			WorldGenLevel level,
			int chunkX,
			int chunkZ,
			int x0,
			int z0,
			int x1,
			int z1,
			KingdomPlanner.KingdomRegion region
	) {
		int dx = Math.abs(x1 - x0);
		int dz = Math.abs(z1 - z0);
		int sx = x0 < x1 ? 1 : -1;
		int sz = z0 < z1 ? 1 : -1;
		int err = dx - dz;
		int x = x0;
		int z = z0;
		while (true) {
			placeIfInChunk(level, chunkX, chunkZ, x, z, region);
			if (x == x1 && z == z1) {
				break;
			}
			int e2 = err * 2;
			if (e2 > -dz) {
				err -= dz;
				x += sx;
			}
			if (e2 < dx) {
				err += dx;
				z += sz;
			}
		}
	}

	private static void placeIfInChunk(WorldGenLevel level, int chunkX, int chunkZ, int blockX, int blockZ, KingdomPlanner.KingdomRegion region) {
		if (Math.floorDiv(blockX, 16) != chunkX || Math.floorDiv(blockZ, 16) != chunkZ) {
			return;
		}
		int y = level.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE_WG, blockX, blockZ);
		BlockPos pos = new BlockPos(blockX, y, blockZ);
		boolean bridge = !level.getFluidState(pos).isEmpty();
		ProceduralStructures.placeRoad(level, pos, region.palette(), bridge);
	}
}
