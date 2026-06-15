package dev.mcxxdev.wgn.quests;

import dev.mcxxdev.wgn.data.WgnAttachments;
import dev.mcxxdev.wgn.data.WgnPlayerData;
import dev.mcxxdev.wgn.economy.EconomyManager;
import dev.mcxxdev.wgn.factions.ReputationManager;
import dev.mcxxdev.wgn.network.WgnNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public final class QuestManager {
	private QuestManager() {}

	public static List<QuestDefinition> available(ServerPlayer player) {
		WgnPlayerData data = WgnAttachments.get(player);
		List<QuestDefinition> available = new ArrayList<>();
		for (QuestDefinition quest : QuestDatabase.all()) {
			if (data.isQuestCompleted(quest.id())) {
				continue;
			}
			if (data.reputation(quest.factionId()) >= quest.requiredReputation()) {
				available.add(quest);
			}
		}
		return available;
	}

	public static boolean start(ServerPlayer player, String questId) {
		return QuestDatabase.get(questId)
				.filter(quest -> WgnAttachments.get(player).reputation(quest.factionId()) >= quest.requiredReputation())
				.map(quest -> {
					WgnAttachments.get(player).setQuestProgress(quest.id(), 0);
					player.displayClientMessage(Component.literal("Quest started: " + quest.displayName()), false);
					WgnNetworking.syncPlayerData(player);
					return true;
				})
				.orElse(false);
	}

	public static void advance(ServerPlayer player, String questId, int amount) {
		QuestDatabase.get(questId).ifPresent(quest -> {
			WgnPlayerData data = WgnAttachments.get(player);
			int progress = data.questProgress(quest.id()) + amount;
			data.setQuestProgress(quest.id(), progress);
			int target = Math.max(quest.objectives().size(), 1);
			if (progress >= target) {
				complete(player, quest);
			} else {
				WgnNetworking.syncPlayerData(player);
			}
		});
	}

	public static void complete(ServerPlayer player, QuestDefinition quest) {
		WgnPlayerData data = WgnAttachments.get(player);
		data.completeQuest(quest.id());
		EconomyManager.rewardQuest(player, quest.coinReward());
		data.addReputation(quest.factionId(), quest.reputationReward());
		player.displayClientMessage(net.minecraft.network.chat.Component.literal("Quest completed: " + quest.displayName()), false);
		WgnNetworking.syncPlayerData(player);
	}
}
