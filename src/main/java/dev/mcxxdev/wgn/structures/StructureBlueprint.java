package dev.mcxxdev.wgn.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.mcxxdev.wgn.kingdoms.CivilizationType;

import java.util.List;

public record StructureBlueprint(
		String id,
		StructureCategory category,
		String paletteId,
		List<Integer> size,
		int weight,
		String placement
) {
	public static final Codec<StructureBlueprint> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.STRING.fieldOf("id").forGetter(StructureBlueprint::id),
			Codec.STRING.xmap(StructureCategory::fromId, StructureCategory::id).fieldOf("category").forGetter(StructureBlueprint::category),
			Codec.STRING.fieldOf("palette").forGetter(StructureBlueprint::paletteId),
			Codec.INT.listOf().optionalFieldOf("size", List.of(7, 5, 9)).forGetter(StructureBlueprint::size),
			Codec.INT.optionalFieldOf("weight", 100).forGetter(StructureBlueprint::weight),
			Codec.STRING.optionalFieldOf("placement", "surface").forGetter(StructureBlueprint::placement)
	).apply(instance, StructureBlueprint::new));

	public int width() {
		return size.size() > 0 ? size.get(0) : 7;
	}

	public int height() {
		return size.size() > 1 ? size.get(1) : 5;
	}

	public int depth() {
		return size.size() > 2 ? size.get(2) : 9;
	}

	public MaterialPalette palette() {
		String key = paletteId.contains(":") ? paletteId.substring(paletteId.indexOf(':') + 1) : paletteId;
		for (MaterialPalette palette : MaterialPalette.values()) {
			if (palette.id().equals(key)) {
				return palette;
			}
		}
		return MaterialPalette.MEDIEVAL;
	}

	public CivilizationType civilization() {
		return switch (palette()) {
			case DESERT -> CivilizationType.DESERT_EMPIRE;
			case NORTHERN -> CivilizationType.NORTHERN_CLAN;
			case FOREST -> CivilizationType.FOREST_REALM;
			case MOUNTAIN -> CivilizationType.MOUNTAIN_HOLD;
			case ANCIENT -> CivilizationType.ANCIENT_KINGDOM;
			default -> CivilizationType.MEDIEVAL_KINGDOM;
		};
	}
}
