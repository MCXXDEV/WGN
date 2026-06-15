package dev.mcxxdev.wgn.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.mcxxdev.wgn.build.BuildCommandHandler;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public final class WgnCommands {
	private WgnCommands() {}

	public static void register() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> register(dispatcher));
	}

	private static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("wgn")
				.then(Commands.literal("build")
						.then(Commands.argument("description", StringArgumentType.greedyString())
								.executes(ctx -> {
									ServerPlayer player = ctx.getSource().getPlayerOrException();
									String description = StringArgumentType.getString(ctx, "description");
									BuildCommandHandler.Result result = BuildCommandHandler.execute(player, description);
									ctx.getSource().sendSuccess(() -> result.toChat(), result.success());
									return result.success() ? 1 : 0;
								}))));
	}
}
