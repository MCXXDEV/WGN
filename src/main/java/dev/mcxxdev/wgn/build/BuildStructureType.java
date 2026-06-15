package dev.mcxxdev.wgn.build;

public enum BuildStructureType {
	HOUSE("house", "home", "cottage", "cabin", "hut", "mansion", "building", "shelter"),
	CASTLE("castle", "fortress", "citadel", "keep", "stronghold"),
	TOWER("tower", "turret", "spire", "lighthouse"),
	BRIDGE("bridge", "overpass", "walkway"),
	WALL("wall", "rampart", "barrier", "fence"),
	FARM("farm", "barn", "stable"),
	MARKET("market", "shop", "store", "stall", "bazaar"),
	PALACE("palace", "manor", "estate", "villa"),
	TEMPLE("temple", "church", "chapel", "shrine", "cathedral"),
	DUNGEON("dungeon", "crypt", "tomb"),
	RUIN("ruin", "ruins", "abandoned"),
	ARENA("arena", "colosseum", "stadium"),
	WELL("well", "fountain");

	private final String id;
	private final String[] keywords;

	BuildStructureType(String id, String... keywords) {
		this.id = id;
		this.keywords = keywords;
	}

	public String id() {
		return id;
	}

	public static BuildStructureType fromText(String text) {
		String lower = text.toLowerCase();
		for (BuildStructureType type : values()) {
			for (String keyword : type.keywords) {
				if (lower.contains(keyword)) {
					return type;
				}
			}
		}
		return HOUSE;
	}
}
