package dev.mcxxdev.wgn.core.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Generic identifier registry used across all WGN subsystems.
 */
public final class WgnRegistry<T extends Identifiable> {
	private final String name;
	private final Map<String, T> entries = new LinkedHashMap<>();

	public WgnRegistry(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public void register(T entry) {
		entries.put(entry.id(), entry);
	}

	public void registerAll(Collection<T> values) {
		for (T value : values) {
			register(value);
		}
	}

	public Optional<T> get(String id) {
		return Optional.ofNullable(entries.get(id));
	}

	public T getOrThrow(String id) {
		T entry = entries.get(id);
		if (entry == null) {
			throw new IllegalArgumentException("Unknown " + name + " entry: " + id);
		}
		return entry;
	}

	public Collection<T> values() {
		return Collections.unmodifiableCollection(entries.values());
	}

	public int size() {
		return entries.size();
	}
}
