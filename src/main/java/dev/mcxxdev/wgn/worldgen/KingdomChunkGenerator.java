package dev.mcxxdev.wgn.worldgen;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public final class KingdomChunkGenerator {
	private KingdomChunkGenerator() {}

	public static void register() {
		ServerChunkEvents.CHUNK_GENERATE.register(KingdomChunkGenerator::generate);
	}

	public static void generate(ServerLevel level, LevelChunk chunk) {
		if (level.dimension() != Level.OVERWORLD) {
			return;
		}
		KingdomChunkGeneratorLogic.generate(level, chunk);
	}
}
