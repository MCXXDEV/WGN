package dev.mcxxdev.wgn.client.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ClientPlayerCache {
	private static int coins;
	private static Map<String, Integer> reputation = Map.of();
	private static Map<String, Integer> questProgress = Map.of();

	private ClientPlayerCache() {}

	public static void update(int coinBalance, Map<String, Integer> rep, Map<String, Integer> quests) {
		coins = coinBalance;
		reputation = Collections.unmodifiableMap(new HashMap<>(rep));
		questProgress = Collections.unmodifiableMap(new HashMap<>(quests));
	}

	public static int coins() {
		return coins;
	}

	public static Map<String, Integer> reputation() {
		return reputation;
	}

	public static Map<String, Integer> questProgress() {
		return questProgress;
	}
}
