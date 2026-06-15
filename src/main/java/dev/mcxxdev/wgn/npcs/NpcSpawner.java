package dev.mcxxdev.wgn.npcs;

import dev.mcxxdev.wgn.kingdoms.CivilizationType;
import dev.mcxxdev.wgn.kingdoms.KingdomPlanner;
import dev.mcxxdev.wgn.kingdoms.SettlementType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;

import java.util.EnumSet;
import java.util.List;

public final class NpcSpawner {
	private NpcSpawner() {}

	public static void spawnForSettlement(
			ServerLevel level,
			BlockPos origin,
			SettlementType type,
			KingdomPlanner.KingdomRegion region,
			RandomSource random
	) {
		List<NpcRole> roles = rolesFor(type);
		int count = Math.min(roles.size(), switch (type) {
			case CAPITAL -> 8;
			case CITY -> 6;
			case VILLAGE -> 4;
			default -> 2;
		});
		for (int i = 0; i < count; i++) {
			NpcRole role = roles.get(i % roles.size());
			BlockPos spawnPos = origin.offset(random.nextInt(9) - 4, 1, random.nextInt(9) - 4);
			WgnNpcEntity npc = WgnEntities.WGN_NPC.create(level);
			if (npc == null) {
				continue;
			}
			npc.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, random.nextFloat() * 360.0F, 0.0F);
			npc.configure(role, region.civilization(), type);
			level.addFreshEntity(npc);
		}
	}

	private static List<NpcRole> rolesFor(SettlementType type) {
		return switch (type) {
			case CAPITAL, CITY -> List.of(NpcRole.KING, NpcRole.GUARD, NpcRole.MERCHANT, NpcRole.SCHOLAR, NpcRole.BLACKSMITH, NpcRole.BUILDER);
			case VILLAGE -> List.of(NpcRole.FARMER, NpcRole.INNKEEPER, NpcRole.HUNTER, NpcRole.GUARD);
			case MARKET -> List.of(NpcRole.MERCHANT, NpcRole.GUARD);
			case FARM -> List.of(NpcRole.FARMER, NpcRole.HUNTER);
			case CASTLE -> List.of(NpcRole.KNIGHT, NpcRole.GUARD, NpcRole.KING);
			default -> List.of(NpcRole.GUARD, NpcRole.BUILDER);
		};
	}
}
