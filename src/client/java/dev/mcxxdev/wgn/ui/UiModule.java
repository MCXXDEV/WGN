package dev.mcxxdev.wgn.ui;

import dev.mcxxdev.wgn.client.WGNClient;
import dev.mcxxdev.wgn.client.WgnKeybinds;

/**
 * WGN-UI — custom screens for reputation, quests, dialogue, and HUD.
 */
public final class UiModule {
	private UiModule() {}

	public static void initializeClient() {
		WgnKeybinds.register();
		WGNClient.LOGGER.info("WGN UI registered — press J for menu, right-click NPCs for dialogue");
	}
}
