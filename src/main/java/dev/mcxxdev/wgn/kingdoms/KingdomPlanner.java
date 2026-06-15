package dev.mcxxdev.wgn.kingdoms;

import dev.mcxxdev.wgn.structures.MaterialPalette;

import java.util.ArrayList;
import java.util.List;

public final class KingdomPlanner {
	public static final int REGION_BLOCKS = 1024;
	public static final int REGION_CHUNKS = REGION_BLOCKS / 16;

	private KingdomPlanner() {}

	public static int regionCoord(int blockCoord) {
		return Math.floorDiv(blockCoord, REGION_BLOCKS);
	}

	public static long regionSeed(long worldSeed, int regionX, int regionZ) {
		return worldSeed ^ (regionX * 3418727121L) ^ (regionZ * 1328979871L);
	}

	public static KingdomRegion region(long worldSeed, int regionX, int regionZ) {
		long seed = regionSeed(worldSeed, regionX, regionZ);
		CivilizationType[] types = CivilizationType.values();
		CivilizationType civilization = types[Math.floorMod(seed, types.length)];
		int centerX = regionX * REGION_BLOCKS + REGION_BLOCKS / 2;
		int centerZ = regionZ * REGION_BLOCKS + REGION_BLOCKS / 2;
		return new KingdomRegion(regionX, regionZ, seed, civilization, centerX, centerZ, settlements(centerX, centerZ, seed));
	}

	public static KingdomRegion regionForBlock(long worldSeed, int blockX, int blockZ) {
		return region(worldSeed, regionCoord(blockX), regionCoord(blockZ));
	}

	private static List<SettlementSite> settlements(int centerX, int centerZ, long seed) {
		List<SettlementSite> sites = new ArrayList<>();
		sites.add(new SettlementSite(centerX, centerZ, SettlementType.CAPITAL));
		int[] offsets = { 220, -220 };
		int index = 0;
		for (int dx : offsets) {
			for (int dz : offsets) {
				if (dx == 0 && dz == 0) {
					continue;
				}
				SettlementType type = switch (Math.floorMod(seed + index, 4)) {
					case 0 -> SettlementType.CITY;
					case 1 -> SettlementType.VILLAGE;
					case 2 -> SettlementType.MARKET;
					default -> SettlementType.OUTPOST;
				};
				sites.add(new SettlementSite(centerX + dx, centerZ + dz, type));
				index++;
			}
		}
		sites.add(new SettlementSite(centerX + 140, centerZ - 60, SettlementType.FARM));
		sites.add(new SettlementSite(centerX - 120, centerZ + 80, SettlementType.TOWER));
		return sites;
	}

	public record KingdomRegion(
			int regionX,
			int regionZ,
			long seed,
			CivilizationType civilization,
			int centerX,
			int centerZ,
			List<SettlementSite> settlements
	) {
		public MaterialPalette palette() {
			return civilization.defaultPalette();
		}
	}

	public record SettlementSite(int blockX, int blockZ, SettlementType type) {
		public boolean containsChunk(int chunkX, int chunkZ) {
			int siteChunkX = Math.floorDiv(blockX, 16);
			int siteChunkZ = Math.floorDiv(blockZ, 16);
			return siteChunkX == chunkX && siteChunkZ == chunkZ;
		}

		public boolean nearChunk(int chunkX, int chunkZ, int radiusChunks) {
			int siteChunkX = Math.floorDiv(blockX, 16);
			int siteChunkZ = Math.floorDiv(blockZ, 16);
			return Math.abs(siteChunkX - chunkX) <= radiusChunks && Math.abs(siteChunkZ - chunkZ) <= radiusChunks;
		}
	}
}
