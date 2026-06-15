package dev.mcxxdev.wgn.network;

import dev.mcxxdev.wgn.core.WgnIdentifiers;
import dev.mcxxdev.wgn.data.WgnAttachments;
import dev.mcxxdev.wgn.data.WgnPlayerData;
import dev.mcxxdev.wgn.dialogue.DialogueManager;
import dev.mcxxdev.wgn.dialogue.DialogueNode;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;

public final class WgnNetworking {
	public record PlayerDataPayload(
			int coins,
			Map<String, Integer> reputation,
			Map<String, Integer> questProgress
	) implements CustomPacketPayload {
		public static final CustomPacketPayload.Type<PlayerDataPayload> TYPE = new CustomPacketPayload.Type<>(WgnIdentifiers.id("player_data"));
		public static final StreamCodec<RegistryFriendlyByteBuf, PlayerDataPayload> CODEC = StreamCodec.composite(
				ByteBufCodecs.VAR_INT, PlayerDataPayload::coins,
				ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.VAR_INT), PlayerDataPayload::reputation,
				ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.VAR_INT), PlayerDataPayload::questProgress,
				PlayerDataPayload::new
		);

		@Override
		public Type<? extends CustomPacketPayload> type() {
			return TYPE;
		}
	}

	public record OpenDialoguePayload(
			String nodeId,
			String speaker,
			String text,
			String factionId,
			java.util.List<String> choices
	) implements CustomPacketPayload {
		public static final CustomPacketPayload.Type<OpenDialoguePayload> TYPE = new CustomPacketPayload.Type<>(WgnIdentifiers.id("open_dialogue"));
		public static final StreamCodec<RegistryFriendlyByteBuf, OpenDialoguePayload> CODEC = StreamCodec.composite(
				ByteBufCodecs.STRING_UTF8, OpenDialoguePayload::nodeId,
				ByteBufCodecs.STRING_UTF8, OpenDialoguePayload::speaker,
				ByteBufCodecs.STRING_UTF8, OpenDialoguePayload::text,
				ByteBufCodecs.STRING_UTF8, OpenDialoguePayload::factionId,
				ByteBufCodecs.collection(java.util.ArrayList::new, ByteBufCodecs.STRING_UTF8), OpenDialoguePayload::choices,
				OpenDialoguePayload::new
		);

		@Override
		public Type<? extends CustomPacketPayload> type() {
			return TYPE;
		}
	}

	public record DialogueChoicePayload(String factionId, String nodeId, int choiceIndex) implements CustomPacketPayload {
		public static final CustomPacketPayload.Type<DialogueChoicePayload> TYPE = new CustomPacketPayload.Type<>(WgnIdentifiers.id("dialogue_choice"));
		public static final StreamCodec<RegistryFriendlyByteBuf, DialogueChoicePayload> CODEC = StreamCodec.composite(
				ByteBufCodecs.STRING_UTF8, DialogueChoicePayload::factionId,
				ByteBufCodecs.STRING_UTF8, DialogueChoicePayload::nodeId,
				ByteBufCodecs.VAR_INT, DialogueChoicePayload::choiceIndex,
				DialogueChoicePayload::new
		);

		@Override
		public Type<? extends CustomPacketPayload> type() {
			return TYPE;
		}
	}

	private WgnNetworking() {}

	private static boolean payloadTypesRegistered;

	/** Register packet types once per JVM (integrated client runs common + client entrypoints). */
	public static void registerPayloadTypes() {
		if (payloadTypesRegistered) {
			return;
		}
		payloadTypesRegistered = true;
		PayloadTypeRegistry.playS2C().register(PlayerDataPayload.TYPE, PlayerDataPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(OpenDialoguePayload.TYPE, OpenDialoguePayload.CODEC);
		PayloadTypeRegistry.playC2S().register(DialogueChoicePayload.TYPE, DialogueChoicePayload.CODEC);
	}

	public static void registerServer() {
		ServerPlayNetworking.registerGlobalReceiver(DialogueChoicePayload.TYPE, (payload, context) ->
				context.server().execute(() -> DialogueManager.choose(
						context.player(),
						payload.factionId(),
						payload.nodeId(),
						payload.choiceIndex()
				)));
	}

	public static void syncPlayerData(ServerPlayer player) {
		WgnPlayerData data = WgnAttachments.get(player);
		ServerPlayNetworking.send(player, new PlayerDataPayload(
				data.coins(),
				data.reputationView(),
				data.questProgressView()
		));
	}

	public static void openDialogue(ServerPlayer player, DialogueNode node, String factionId) {
		ServerPlayNetworking.send(player, new OpenDialoguePayload(
				node.id(),
				node.speaker(),
				node.text(),
				factionId,
				node.choices().stream().map(choice -> choice.text()).toList()
		));
	}
}
