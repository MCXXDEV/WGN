package dev.mcxxdev.wgn.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.mcxxdev.wgn.client.network.WgnClientNetworking;
import dev.mcxxdev.wgn.client.render.WgnNpcEntityRenderer;
import dev.mcxxdev.wgn.client.ui.WgnHudScreen;
import dev.mcxxdev.wgn.core.WgnIdentifiers;
import dev.mcxxdev.wgn.npcs.WgnEntities;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public final class WgnKeybinds {
	public static KeyMapping OPEN_WGN_MENU;

	private WgnKeybinds() {}

	public static void register() {
		OPEN_WGN_MENU = KeyBindingHelper.registerKeyBinding(new KeyMapping(
				"key.wgn.menu",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_J,
				"key.categories.wgn"
		));
		WgnClientNetworking.register();
		EntityRendererRegistry.register(WgnEntities.WGN_NPC, WgnNpcEntityRenderer::new);
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (OPEN_WGN_MENU.consumeClick()) {
				if (client.player != null) {
					client.setScreen(new WgnHudScreen());
				}
			}
		});
	}
}
