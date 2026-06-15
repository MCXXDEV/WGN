package dev.mcxxdev.wgn.kingdoms;

import dev.mcxxdev.wgn.core.registry.Identifiable;
import dev.mcxxdev.wgn.structures.MaterialPalette;

/**
 * Civilization archetypes that drive kingdom layout, palette, and NPC roles.
 */
public enum CivilizationType implements Identifiable {
	MEDIEVAL_KINGDOM("medieval_kingdom", MaterialPalette.MEDIEVAL),
	NORTHERN_CLAN("northern_clan", MaterialPalette.NORTHERN),
	FOREST_REALM("forest_realm", MaterialPalette.FOREST),
	DESERT_EMPIRE("desert_empire", MaterialPalette.DESERT),
	MOUNTAIN_HOLD("mountain_hold", MaterialPalette.MOUNTAIN),
	MERCHANT_REPUBLIC("merchant_republic", MaterialPalette.MEDIEVAL),
	ANCIENT_KINGDOM("ancient_kingdom", MaterialPalette.ANCIENT);

	private final String id;
	private final MaterialPalette defaultPalette;

	CivilizationType(String id, MaterialPalette defaultPalette) {
		this.id = id;
		this.defaultPalette = defaultPalette;
	}

	@Override
	public String id() {
		return id;
	}

	public MaterialPalette defaultPalette() {
		return defaultPalette;
	}
}
