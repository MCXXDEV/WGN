package dev.mcxxdev.wgn.build;

import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Locale;

public record BuildSpec(
		String rawRequest,
		BuildStructureType structureType,
		BuildSize size,
		List<Block> colors
) {
	public static BuildSpec parse(String request) {
		String lower = request.toLowerCase(Locale.ROOT);
		return new BuildSpec(
				request.trim(),
				BuildStructureType.fromText(lower),
				BuildSize.fromText(lower),
				BuildColorMapper.extractColors(lower)
		);
	}

	public Block primary() {
		return BuildColorMapper.primary(colors);
	}

	public Block secondary() {
		return BuildColorMapper.secondary(colors);
	}

	public Block accent() {
		return BuildColorMapper.accent(colors);
	}

	public BuildSpec withSize(BuildSize size) {
		return new BuildSpec(rawRequest, structureType, size, colors);
	}
}
