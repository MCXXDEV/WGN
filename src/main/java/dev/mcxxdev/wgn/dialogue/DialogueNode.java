package dev.mcxxdev.wgn.dialogue;

import java.util.List;

public record DialogueNode(
		String id,
		String speaker,
		String text,
		List<DialogueChoice> choices
) {}
