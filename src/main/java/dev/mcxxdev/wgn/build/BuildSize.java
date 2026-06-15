package dev.mcxxdev.wgn.build;

public enum BuildSize {
	TINY(5, 3, 5, 1),
	SMALL(7, 4, 7, 1),
	MEDIUM(11, 5, 11, 1),
	LARGE(15, 6, 15, 2),
	HUGE(21, 8, 21, 2),
	MASSIVE(29, 10, 29, 3);

	private final int width;
	private final int height;
	private final int depth;
	private final int stories;

	BuildSize(int width, int height, int depth, int stories) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.stories = stories;
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public int depth() {
		return depth;
	}

	public int stories() {
		return stories;
	}

	public static BuildSize fromText(String text) {
		if (containsAny(text, "massive", "gigantic", "enormous", "colossal")) {
			return MASSIVE;
		}
		if (containsAny(text, "huge", "giant", "gigantic")) {
			return HUGE;
		}
		if (containsAny(text, "big", "large", "grand")) {
			return LARGE;
		}
		if (containsAny(text, "small", "little", "compact", "tiny")) {
			return text.contains("tiny") ? TINY : SMALL;
		}
		if (containsAny(text, "medium", "moderate", "average")) {
			return MEDIUM;
		}
		return MEDIUM;
	}

	private static boolean containsAny(String text, String... words) {
		for (String word : words) {
			if (text.contains(word)) {
				return true;
			}
		}
		return false;
	}
}
