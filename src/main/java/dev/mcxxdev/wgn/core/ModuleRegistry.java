package dev.mcxxdev.wgn.core;

import dev.mcxxdev.wgn.WGN;
import dev.mcxxdev.wgn.structures.StructuresModule;
import dev.mcxxdev.wgn.worldgen.WorldGenModule;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Registry-driven bootstrap for WGN structure generation modules.
 */
public final class ModuleRegistry {
	private static final Map<String, WgnModule> MODULES = new LinkedHashMap<>();
	private static boolean initialized;

	static {
		register(new CoreModule());
		register(new StructuresModule());
		register(new WorldGenModule());
	}

	private ModuleRegistry() {}

	public static void register(WgnModule module) {
		MODULES.put(module.id(), module);
	}

	public static void initializeAll() {
		if (initialized) {
			return;
		}

		for (WgnModule module : topologicalSort()) {
			WGN.LOGGER.info("Loading module: {} ({})", module.displayName(), module.id());
			module.initialize();
		}

		initialized = true;
	}

	public static int loadedCount() {
		return MODULES.size();
	}

	private static List<WgnModule> topologicalSort() {
		List<WgnModule> sorted = new ArrayList<>();
		Map<String, Boolean> visited = new LinkedHashMap<>();

		for (WgnModule module : MODULES.values()) {
			visit(module, visited, sorted);
		}

		return sorted;
	}

	private static void visit(WgnModule module, Map<String, Boolean> visited, List<WgnModule> sorted) {
		if (Boolean.TRUE.equals(visited.get(module.id()))) {
			return;
		}

		visited.put(module.id(), true);

		for (String dep : module.dependencies()) {
			WgnModule dependency = MODULES.get(dep);
			if (dependency != null) {
				visit(dependency, visited, sorted);
			}
		}

		sorted.add(module);
	}
}
