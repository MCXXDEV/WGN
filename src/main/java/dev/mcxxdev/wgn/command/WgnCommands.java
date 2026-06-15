package dev.mcxxdev.wgn.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.mcxxdev.wgn.economy.EconomyManager;
import dev.mcxxdev.wgn.factions.ReputationAction;
import dev.mcxxdev.wgn.factions.ReputationManager;
import dev.mcxxdev.wgn.network.WgnNetworking;
import dev.mcxxdev.wgn.quests.QuestManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class WgnCommands {
	private WgnCommands() {}

	public static void register() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> register(dispatcher));
	}

	private static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("wgn")
				.then(Commands.literal("reputation")
						.then(Commands.literal("add")
								.then(Commands.argument("faction", StringArgumentType.string())
										.then(Commands.argument("amount", IntegerArgumentType.integer())
												.executes(ctx -> {
													ServerPlayer player = ctx.getSource().getPlayerOrException();
													String faction = StringArgumentType.getString(ctx, "faction");
													int amount = IntegerArgumentType.getInteger(ctx, "amount");
													dev.mcxxdev.wgn.data.WgnAttachments.get(player).addReputation(faction, amount);
													WgnNetworking.syncPlayerData(player);
													ctx.getSource().sendSuccess(() -> Component.literal("Reputation updated."), false);
													return 1;
												})))))
				.then(Commands.literal("quest")
						.then(Commands.literal("start")
								.then(Commands.argument("id", StringArgumentType.string())
										.executes(ctx -> {
											ServerPlayer player = ctx.getSource().getPlayerOrException();
											String id = StringArgumentType.getString(ctx, "id");
											boolean started = QuestManager.start(player, id);
											ctx.getSource().sendSuccess(() -> Component.literal(started ? "Quest started." : "Quest unavailable."), false);
											return started ? 1 : 0;
										}))))
				.then(Commands.literal("coins")
						.then(Commands.literal("give")
								.then(Commands.argument("amount", IntegerArgumentType.integer(1))
										.executes(ctx -> {
											ServerPlayer player = ctx.getSource().getPlayerOrException();
											EconomyManager.deposit(player, IntegerArgumentType.getInteger(ctx, "amount"));
											ctx.getSource().sendSuccess(() -> Component.literal("Coins granted."), false);
											return 1;
										}))))
				.then(Commands.literal("sync")
						.executes(ctx -> {
							ServerPlayer player = ctx.getSource().getPlayerOrException();
							WgnNetworking.syncPlayerData(player);
							return 1;
						})));
	}
}
