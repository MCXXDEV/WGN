package dev.mcxxdev.wgn;

import dev.mcxxdev.wgn.core.ModuleRegistry;
import dev.mcxxdev.wgn.core.WGNConstants;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * World Generation Nexus — main mod entry point.
 * Bootstraps all WGN modules through the registry-driven core.
 */
public class WGN implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(WGNConstants.MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing {} v{}", WGNConstants.MOD_NAME, WGNConstants.VERSION);
		ModuleRegistry.initializeAll();
		LOGGER.info("{} modules loaded", ModuleRegistry.loadedCount());
	}
}
