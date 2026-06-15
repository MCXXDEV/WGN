package dev.mcxxdev.wgn.client;

import dev.mcxxdev.wgn.core.WGNConstants;
import dev.mcxxdev.wgn.ui.UiModule;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WGNClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(WGNConstants.MOD_ID + "-client");

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initializing {} client modules", WGNConstants.MOD_NAME);
		UiModule.initializeClient();
	}
}
