package dev.mcxxdev.wgn.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.core.WgnIdentifiers;
import dev.mcxxdev.wgn.dialogue.DialogueNode;
import dev.mcxxdev.wgn.quests.QuestDefinition;
import dev.mcxxdev.wgn.structures.StructureBlueprint;
import dev.mcxxdev.wgn.structures.StructureCategory;
import dev.mcxxdev.wgn.structures.StructureDatabase;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class WgnDataLoader {
	private static final Gson GSON = new Gson();

	private WgnDataLoader() {}

	public static void register() {
		ServerLifecycleEvents.SERVER_STARTED.register(server -> load(server.getResourceManager()));
	}

	public static void load(ResourceManager manager) {
		loadStructures(manager);
		loadQuests(manager);
		loadDialogue(manager);
	}

	private static void loadStructures(ResourceManager manager) {
		Map<ResourceLocation, JsonElement> data = new HashMap<>();
		for (Map.Entry<ResourceLocation, List<Resource>> entry : manager.listResourceStacks("structures", location -> location.getNamespace().equals(WgnIdentifiers.id("").getNamespace())).entrySet()) {
			if (entry.getValue().isEmpty()) {
				continue;
			}
			try (var reader = new InputStreamReader(entry.getValue().getFirst().open(), StandardCharsets.UTF_8)) {
				data.put(entry.getKey(), GSON.fromJson(reader, JsonElement.class));
			} catch (Exception exception) {
				WGN.LOGGER.error("Failed to read structure {}", entry.getKey(), exception);
			}
		}
		List<StructureBlueprint> loaded = new ArrayList<>();
		for (Map.Entry<ResourceLocation, JsonElement> entry : data.entrySet()) {
			try {
				JsonObject object = entry.getValue().getAsJsonObject();
				loaded.add(new StructureBlueprint(
						object.get("id").getAsString(),
						StructureCategory.fromId(object.get("category").getAsString()),
						object.get("palette").getAsString(),
						object.has("size") ? object.get("size").getAsJsonArray().asList().stream().map(JsonElement::getAsInt).toList() : List.of(7, 5, 9),
						object.has("weight") ? object.get("weight").getAsInt() : 100,
						object.has("placement") ? object.get("placement").getAsString() : "surface"
				));
			} catch (Exception exception) {
				WGN.LOGGER.error("Failed to parse structure {}", entry.getKey(), exception);
			}
		}
		StructureDatabase.applyLoaded(loaded);
	}

	private static void loadQuests(ResourceManager manager) {
		List<QuestDefinition> quests = new ArrayList<>();
		for (Map.Entry<ResourceLocation, List<Resource>> entry : manager.listResourceStacks("quests", location -> location.getNamespace().equals(WgnIdentifiers.id("").getNamespace())).entrySet()) {
			if (entry.getValue().isEmpty()) {
				continue;
			}
			try (var reader = new InputStreamReader(entry.getValue().getFirst().open(), StandardCharsets.UTF_8)) {
				QuestDefinition quest = GSON.fromJson(reader, QuestDefinition.class);
				if (quest != null) {
					quests.add(quest);
				}
			} catch (Exception exception) {
				WGN.LOGGER.error("Failed to read quest {}", entry.getKey(), exception);
			}
		}
		dev.mcxxdev.wgn.quests.QuestDatabase.applyLoaded(quests);
	}

	private static void loadDialogue(ResourceManager manager) {
		List<DialogueNode> nodes = new ArrayList<>();
		for (Map.Entry<ResourceLocation, List<Resource>> entry : manager.listResourceStacks("dialogue", location -> location.getNamespace().equals(WgnIdentifiers.id("").getNamespace())).entrySet()) {
			if (entry.getValue().isEmpty()) {
				continue;
			}
			try (var reader = new InputStreamReader(entry.getValue().getFirst().open(), StandardCharsets.UTF_8)) {
				DialogueNode node = GSON.fromJson(reader, DialogueNode.class);
				if (node != null) {
					nodes.add(node);
				}
			} catch (Exception exception) {
				WGN.LOGGER.error("Failed to read dialogue {}", entry.getKey(), exception);
			}
		}
		dev.mcxxdev.wgn.dialogue.DialogueDatabase.applyLoaded(nodes);
	}
}
