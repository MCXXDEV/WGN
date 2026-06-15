package dev.mcxxdev.wgn.worldgen;

import dev.mcxxdev.wgn.dungeons.DungeonGenerator;
import dev.mcxxdev.wgn.dungeons.DungeonType;
import dev.mcxxdev.wgn.kingdoms.KingdomPlanner;
import dev.mcxxdev.wgn.kingdoms.RoadGenerator;
import dev.mcxxdev.wgn.npcs.NpcSpawner;
import dev.mcxxdev.wgn.structures.StructurePlacer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;

final class KingdomChunkGeneratorLogic {
	private KingdomChunkGeneratorLogic() {}

	static void generate(ServerLevel level, LevelChunk chunk) {
		int chunkX = chunk.getPos().x;
		int chunkZ = chunk.getPos().z;
		int blockX = chunkX * 16 + 8;
		int blockZ = chunkZ * 16 + 8;
		long worldSeed = level.getSeed();
		KingdomPlanner.KingdomRegion region = KingdomPlanner.regionForBlock(worldSeed, blockX, blockZ);
		RandomSource random = RandomSource.create(region.seed() ^ (chunkX * 31L) ^ (chunkZ * 17L));

		RoadGenerator.generateRoadsForChunk(level, chunkX, chunkZ, region);

		for (KingdomPlanner.SettlementSite site : region.settlements()) {
			if (!site.containsChunk(chunkX, chunkZ)) {
				continue;
			}
			int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, site.blockX(), site.blockZ());
			BlockPos origin = new BlockPos(site.blockX(), surfaceY, site.blockZ());
			StructurePlacer.placeSettlement(level, origin, site.type(), region, random);
			NpcSpawner.spawnForSettlement(level, origin, site.type(), region, random);
		}

		if (DungeonGenerator.shouldPlaceDungeon(worldSeed, chunkX, chunkZ)) {
			int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, blockX, blockZ);
			DungeonType type = DungeonGenerator.pickType(random);
			DungeonGenerator.placeDungeon(level, new BlockPos(blockX, surfaceY, blockZ), type, random);
		}
	}
}
