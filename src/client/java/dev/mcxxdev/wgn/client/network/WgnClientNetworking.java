package dev.mcxxdev.wgn.client.network;

import dev.mcxxdev.wgn.client.data.ClientPlayerCache;
import dev.mcxxdev.wgn.client.ui.DialogueScreen;
import dev.mcxxdev.wgn.network.WgnNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.Minecraft;

public final class WgnClientNetworking {
	private WgnClientNetworking() {}

	public static void register() {
		PayloadTypeRegistry.playS2C().register(WgnNetworking.PlayerDataPayload.TYPE, WgnNetworking.PlayerDataPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(WgnNetworking.OpenDialoguePayload.TYPE, WgnNetworking.OpenDialoguePayload.CODEC);
		PayloadTypeRegistry.playC2S().register(WgnNetworking.DialogueChoicePayload.TYPE, WgnNetworking.DialogueChoicePayload.CODEC);

		ClientPlayNetworking.registerGlobalReceiver(WgnNetworking.PlayerDataPayload.TYPE, (payload, context) ->
				context.client().execute(() -> ClientPlayerCache.update(payload.coins(), payload.reputation(), payload.questProgress())));

		ClientPlayNetworking.registerGlobalReceiver(WgnNetworking.OpenDialoguePayload.TYPE, (payload, context) ->
				context.client().execute(() -> Minecraft.getInstance().setScreen(new DialogueScreen(
						payload.nodeId(),
						payload.speaker(),
						payload.text(),
						payload.factionId(),
						payload.choices()
				))));
	}
}
