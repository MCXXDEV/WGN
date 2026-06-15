package dev.mcxxdev.wgn.ui;

import dev.mcxxdev.wgn.client.WGNClient;
import dev.mcxxdev.wgn.client.render.WgnNpcEntityRenderer;
import dev.mcxxdev.wgn.npcs.WgnEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

/**
 * WGN client — entity rendering only (no menus or extra keybinds).
 */
public final class UiModule {
	private UiModule() {}

	public static void initializeClient() {
		EntityRendererRegistry.register(WgnEntities.WGN_NPC, WgnNpcEntityRenderer::new);
		WGNClient.LOGGER.info("WGN client rendering registered");
	}
}
