package dev.mcxxdev.wgn.quests;

import java.util.List;

public record QuestDefinition(
		String id,
		String displayName,
		String description,
		int requiredReputation,
		String factionId,
		String storyFeature,
		int coinReward,
		int reputationReward,
		List<String> objectives
) {}
