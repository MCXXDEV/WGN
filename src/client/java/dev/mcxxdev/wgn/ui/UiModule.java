package dev.mcxxdev.wgn.ui;

import dev.mcxxdev.wgn.client.WGNClient;

/**
 * WGN-UI — client-side interfaces for reputation, quests, maps, and kingdom info.
 */
public final class UiModule {
	private UiModule() {}

	public static void initializeClient() {
		WGNClient.LOGGER.info("UI module registered (reputation, quests, exploration)");
	}
}
