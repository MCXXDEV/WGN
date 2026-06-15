package dev.mcxxdev.wgn.dialogue;

import dev.mcxxdev.wgn.WGN;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class DialogueDatabase {
	private static final Map<String, DialogueNode> NODES = new ConcurrentHashMap<>();

	private DialogueDatabase() {}

	public static void applyLoaded(List<DialogueNode> nodes) {
		NODES.clear();
		for (DialogueNode node : nodes) {
			NODES.put(node.id(), node);
		}
		WGN.LOGGER.info("Loaded {} dialogue nodes", NODES.size());
	}

	public static Optional<DialogueNode> get(String id) {
		return Optional.ofNullable(NODES.get(id));
	}
}
