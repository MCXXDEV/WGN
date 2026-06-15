package dev.mcxxdev.wgn.npcs;

import dev.mcxxdev.wgn.core.WgnIdentifiers;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class WgnEntities {
	public static final EntityType<WgnNpcEntity> WGN_NPC = EntityType.Builder
			.<WgnNpcEntity>of(WgnNpcEntity::new, MobCategory.CREATURE)
			.sized(0.6F, 1.95F)
			.clientTrackingRange(10)
			.build(WgnIdentifiers.id("wgn_npc").toString());

	private WgnEntities() {}

	public static void register() {
		Registry.register(BuiltInRegistries.ENTITY_TYPE, WgnIdentifiers.id("wgn_npc"), WGN_NPC);
		FabricDefaultAttributeRegistry.register(WGN_NPC, WgnNpcEntity.createAttributes());
	}
}
