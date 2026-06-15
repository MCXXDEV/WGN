package dev.mcxxdev.wgn.structures;

import dev.mcxxdev.wgn.kingdoms.KingdomPlanner;
import dev.mcxxdev.wgn.kingdoms.SettlementType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;

public final class StructurePlacer {
	private StructurePlacer() {}

	public static void placeSettlement(
			WorldGenLevel level,
			BlockPos origin,
			SettlementType type,
			KingdomPlanner.KingdomRegion region,
			RandomSource random
	) {
		StructureDatabase.pick(mapCategory(type)).ifPresentOrElse(
				blueprint -> ProceduralStructures.placeFromBlueprint(level, origin, blueprint, random),
				() -> ProceduralStructures.placeSettlement(level, origin, type, region.palette(), random)
		);
	}

	private static StructureCategory mapCategory(SettlementType type) {
		return switch (type) {
			case CASTLE, CAPITAL -> StructureCategory.CASTLE;
			case CITY -> StructureCategory.PALACE;
			case MARKET -> StructureCategory.MARKET;
			case FARM -> StructureCategory.FARM;
			case TOWER -> StructureCategory.TOWER;
			case VILLAGE -> StructureCategory.HOUSE;
			default -> StructureCategory.HOUSE;
		};
	}
}
