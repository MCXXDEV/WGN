package dev.mcxxdev.wgn.factions;

import dev.mcxxdev.wgn.data.WgnAttachments;
import dev.mcxxdev.wgn.data.WgnPlayerData;
import dev.mcxxdev.wgn.network.WgnNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class ReputationManager {
	private ReputationManager() {}

	public static void apply(ServerPlayer player, String factionId, ReputationAction action) {
		WgnPlayerData data = WgnAttachments.get(player);
		data.addReputation(factionId, action.reputationDelta());
		player.displayClientMessage(Component.literal(
				"Reputation with " + factionId + ": " + (action.reputationDelta() >= 0 ? "+" : "") + action.reputationDelta()
		), true);
		WgnNetworking.syncPlayerData(player);
	}

	public static boolean canUnlock(WgnPlayerData data, String factionId, ReputationUnlock unlock) {
		int reputation = data.reputation(factionId);
		return switch (unlock) {
			case QUESTS -> reputation >= 0;
			case TRADES -> reputation >= 25;
			case REWARDS -> reputation >= 75;
			case TITLES -> reputation >= 100;
		};
	}

	public static int reputation(ServerPlayer player, String factionId) {
		return WgnAttachments.get(player).reputation(factionId);
	}
}
