package dev.mcxxdev.wgn.core;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.command.WgnCommands;
import dev.mcxxdev.wgn.core.registry.WgnRegistries;
import dev.mcxxdev.wgn.data.WgnAttachments;
import dev.mcxxdev.wgn.data.WgnDataLoader;
import dev.mcxxdev.wgn.dialogue.DialogueDatabase;
import dev.mcxxdev.wgn.network.WgnNetworking;
import dev.mcxxdev.wgn.quests.QuestDatabase;
import dev.mcxxdev.wgn.structures.StructureDatabase;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

/**
 * WGN-Core — foundation module providing registry infrastructure,
 * shared types, and bootstrap coordination.
 */
public final class CoreModule implements WgnModule {
	@Override
	public String id() {
		return "wgn-core";
	}

	@Override
	public String displayName() {
		return "WGN-Core";
	}

	@Override
	public void initialize() {
		WgnRegistries.bootstrap();
		WgnNetworking.registerPayloadTypes();
		WgnDataLoader.register();
		WgnNetworking.registerServer();
		WgnCommands.register();

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
				server.execute(() -> WgnNetworking.syncPlayerData(handler.player)));
		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) ->
				newPlayer.setAttached(WgnAttachments.PLAYER_DATA, oldPlayer.getAttachedOrCreate(WgnAttachments.PLAYER_DATA)));

		WGN.LOGGER.info(
				"Core ready — {} civilizations, structure DB, quests, dialogue, networking",
				WgnRegistries.CIVILIZATIONS.size()
		);
	}
}
