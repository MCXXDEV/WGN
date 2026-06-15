package dev.mcxxdev.wgn.core;

/**
 * Contract for all WGN subsystems. Each module registers itself with
 * {@link ModuleRegistry} and initializes in dependency order.
 */
public interface WgnModule {
	/** Unique module identifier, e.g. {@code "wgn-worldgen"}. */
	String id();

	/** Human-readable module name. */
	String displayName();

	/** Modules that must initialize before this one. */
	default String[] dependencies() {
		return new String[0];
	}

	void initialize();
}
