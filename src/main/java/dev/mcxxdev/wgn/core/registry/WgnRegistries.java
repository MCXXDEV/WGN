package dev.mcxxdev.wgn.core.registry;

import dev.mcxxdev.wgn.dungeons.DungeonFeature;
import dev.mcxxdev.wgn.dungeons.DungeonType;
import dev.mcxxdev.wgn.factions.ReputationAction;
import dev.mcxxdev.wgn.factions.ReputationUnlock;
import dev.mcxxdev.wgn.kingdoms.CivilizationType;
import dev.mcxxdev.wgn.kingdoms.KingdomGenerationMethod;
import dev.mcxxdev.wgn.kingdoms.RoadType;
import dev.mcxxdev.wgn.kingdoms.SettlementType;
import dev.mcxxdev.wgn.npcs.NpcBehavior;
import dev.mcxxdev.wgn.npcs.NpcRole;
import dev.mcxxdev.wgn.structures.MaterialPalette;
import dev.mcxxdev.wgn.structures.StructureCategory;
import dev.mcxxdev.wgn.worldgen.ExplorationFeature;
import dev.mcxxdev.wgn.worldgen.WorldStoryFeature;

/**
 * Central access point for all WGN content registries.
 */
public final class WgnRegistries {
	public static final WgnRegistry<CivilizationType> CIVILIZATIONS = new WgnRegistry<>("civilization");
	public static final WgnRegistry<SettlementType> SETTLEMENTS = new WgnRegistry<>("settlement");
	public static final WgnRegistry<KingdomGenerationMethod> KINGDOM_METHODS = new WgnRegistry<>("kingdom_method");
	public static final WgnRegistry<RoadType> ROADS = new WgnRegistry<>("road");
	public static final WgnRegistry<StructureCategory> STRUCTURE_CATEGORIES = new WgnRegistry<>("structure_category");
	public static final WgnRegistry<MaterialPalette> MATERIAL_PALETTES = new WgnRegistry<>("material_palette");
	public static final WgnRegistry<NpcRole> NPC_ROLES = new WgnRegistry<>("npc_role");
	public static final WgnRegistry<NpcBehavior> NPC_BEHAVIORS = new WgnRegistry<>("npc_behavior");
	public static final WgnRegistry<DungeonType> DUNGEON_TYPES = new WgnRegistry<>("dungeon_type");
	public static final WgnRegistry<DungeonFeature> DUNGEON_FEATURES = new WgnRegistry<>("dungeon_feature");
	public static final WgnRegistry<ReputationAction> REPUTATION_ACTIONS = new WgnRegistry<>("reputation_action");
	public static final WgnRegistry<ReputationUnlock> REPUTATION_UNLOCKS = new WgnRegistry<>("reputation_unlock");
	public static final WgnRegistry<ExplorationFeature> EXPLORATION_FEATURES = new WgnRegistry<>("exploration_feature");
	public static final WgnRegistry<WorldStoryFeature> WORLD_STORY_FEATURES = new WgnRegistry<>("world_story_feature");

	private WgnRegistries() {}

	public static void bootstrap() {
		CIVILIZATIONS.registerAll(java.util.Arrays.asList(CivilizationType.values()));
		SETTLEMENTS.registerAll(java.util.Arrays.asList(SettlementType.values()));
		KINGDOM_METHODS.registerAll(java.util.Arrays.asList(KingdomGenerationMethod.values()));
		ROADS.registerAll(java.util.Arrays.asList(RoadType.values()));
		STRUCTURE_CATEGORIES.registerAll(java.util.Arrays.asList(StructureCategory.values()));
		MATERIAL_PALETTES.registerAll(java.util.Arrays.asList(MaterialPalette.values()));
		NPC_ROLES.registerAll(java.util.Arrays.asList(NpcRole.values()));
		NPC_BEHAVIORS.registerAll(java.util.Arrays.asList(NpcBehavior.values()));
		DUNGEON_TYPES.registerAll(java.util.Arrays.asList(DungeonType.values()));
		DUNGEON_FEATURES.registerAll(java.util.Arrays.asList(DungeonFeature.values()));
		REPUTATION_ACTIONS.registerAll(java.util.Arrays.asList(ReputationAction.values()));
		REPUTATION_UNLOCKS.registerAll(java.util.Arrays.asList(ReputationUnlock.values()));
		EXPLORATION_FEATURES.registerAll(java.util.Arrays.asList(ExplorationFeature.values()));
		WORLD_STORY_FEATURES.registerAll(java.util.Arrays.asList(WorldStoryFeature.values()));
	}
}
