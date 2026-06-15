package dev.mcxxdev.wgn.npcs;

import dev.mcxxdev.wgn.core.registry.Identifiable;

/**
 * NPC role definitions for civilization populations.
 */
public enum NpcRole implements Identifiable {
	KING("king"),
	QUEEN("queen"),
	KNIGHT("knight"),
	GUARD("guard"),
	MERCHANT("merchant"),
	FARMER("farmer"),
	HUNTER("hunter"),
	MINER("miner"),
	BLACKSMITH("blacksmith"),
	BUILDER("builder"),
	SCHOLAR("scholar"),
	INNKEEPER("innkeeper");

	private final String id;

	NpcRole(String id) {
		this.id = id;
	}

	@Override
	public String id() {
		return id;
	}

	public static NpcRole fromId(String id) {
		for (NpcRole role : values()) {
			if (role.id.equals(id)) {
				return role;
			}
		}
		return FARMER;
	}
}
