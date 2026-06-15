package dev.mcxxdev.wgn.build;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public final class BuildCommandHandler {
	private BuildCommandHandler() {}

	public static Result execute(ServerPlayer player, String description) {
		BuildContentFilter.FilterResult filter = BuildContentFilter.validate(description);
		if (!filter.allowed()) {
			return Result.fail(filter.reason());
		}

		BuildSpec spec = BuildSpec.parse(description);
		ServerLevel level = player.serverLevel();
		BlockPos placement = DynamicBuildEngine.findPlacement(level, findBuildPosition(player));
		DynamicBuildEngine.build(level, placement, spec);

		return Result.success(spec, placement);
	}

	private static BlockPos findBuildPosition(ServerPlayer player) {
		Vec3 look = player.getLookAngle();
		return player.blockPosition().offset(
				(int) Math.round(look.x * 4),
				0,
				(int) Math.round(look.z * 4)
		);
	}

	public record Result(boolean success, String message, BuildSpec spec, BlockPos position) {
		public static Result success(BuildSpec spec, BlockPos position) {
			String colors = spec.colors().isEmpty() ? "default stone" : spec.colors().size() + " color(s)";
			return new Result(true,
					"Built a " + spec.size().name().toLowerCase() + " " + spec.structureType().id()
							+ " using " + colors + " at " + position.toShortString(),
					spec, position);
		}

		public static Result fail(String message) {
			return new Result(false, message, null, null);
		}

		public Component toChat() {
			return Component.literal(message);
		}
	}
}
