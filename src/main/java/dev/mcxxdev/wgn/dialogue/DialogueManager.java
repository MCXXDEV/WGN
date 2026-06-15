package dev.mcxxdev.wgn.dialogue;

import dev.mcxxdev.wgn.data.WgnAttachments;
import dev.mcxxdev.wgn.network.WgnNetworking;
import dev.mcxxdev.wgn.quests.QuestManager;
import net.minecraft.server.level.ServerPlayer;

public final class DialogueManager {
	private DialogueManager() {}

	public static void open(ServerPlayer player, String nodeId, String factionId) {
		DialogueDatabase.get(nodeId).ifPresent(node -> WgnNetworking.openDialogue(player, node, factionId));
	}

	public static void choose(ServerPlayer player, String factionId, String currentNodeId, int choiceIndex) {
		DialogueDatabase.get(currentNodeId).ifPresent(node -> {
			if (choiceIndex < 0 || choiceIndex >= node.choices().size()) {
				return;
			}
			DialogueChoice choice = node.choices().get(choiceIndex);
			if (choice.reputationDelta() != null && choice.reputationDelta() != 0) {
				WgnAttachments.get(player).addReputation(factionId, choice.reputationDelta());
			}
			if (choice.startsQuest() != null) {
				QuestManager.start(player, choice.startsQuest());
			}
			if (choice.next() == null || choice.next().isBlank()) {
				WgnNetworking.syncPlayerData(player);
				return;
			}
			open(player, choice.next(), factionId);
		});
	}
}
