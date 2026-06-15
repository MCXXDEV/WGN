package dev.mcxxdev.wgn.core;

import net.minecraft.resources.ResourceLocation;

public final class WgnIdentifiers {
	private WgnIdentifiers() {}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(WGNConstants.MOD_ID, path);
	}
}
