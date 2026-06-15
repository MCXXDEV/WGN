package dev.mcxxdev.wgn.quests;

import dev.mcxxdev.wgn.WGN;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class QuestDatabase {
	private static final Map<String, QuestDefinition> QUESTS = new ConcurrentHashMap<>();

	private QuestDatabase() {}

	public static void applyLoaded(List<QuestDefinition> quests) {
		QUESTS.clear();
		for (QuestDefinition quest : quests) {
			QUESTS.put(quest.id(), quest);
		}
		WGN.LOGGER.info("Loaded {} quests", QUESTS.size());
	}

	public static Optional<QuestDefinition> get(String id) {
		return Optional.ofNullable(QUESTS.get(id));
	}

	public static List<QuestDefinition> all() {
		return List.copyOf(QUESTS.values());
	}
}
