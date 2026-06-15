package dev.mcxxdev.wgn.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class WgnPlayerData {
	public static final Codec<WgnPlayerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("reputation").forGetter(data -> data.reputation),
			Codec.INT.fieldOf("coins").forGetter(WgnPlayerData::coins),
			Codec.STRING.listOf().fieldOf("completed_quests").forGetter(data -> data.completedQuests.stream().toList()),
			Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("quest_progress").forGetter(data -> data.questProgress),
			Codec.STRING.listOf().fieldOf("dialogue_flags").forGetter(data -> data.dialogueFlags.stream().toList())
	).apply(instance, (reputation, coins, completedQuests, questProgress, dialogueFlags) ->
			new WgnPlayerData(reputation, coins, new HashSet<>(completedQuests), questProgress, new HashSet<>(dialogueFlags))));

	private final Map<String, Integer> reputation;
	private int coins;
	private final Set<String> completedQuests;
	private final Map<String, Integer> questProgress;
	private final Set<String> dialogueFlags;

	public WgnPlayerData() {
		this(new HashMap<>(), 25, new HashSet<>(), new HashMap<>(), new HashSet<>());
	}

	private WgnPlayerData(
			Map<String, Integer> reputation,
			int coins,
			Set<String> completedQuests,
			Map<String, Integer> questProgress,
			Set<String> dialogueFlags
	) {
		this.reputation = new HashMap<>(reputation);
		this.coins = coins;
		this.completedQuests = new HashSet<>(completedQuests);
		this.questProgress = new HashMap<>(questProgress);
		this.dialogueFlags = new HashSet<>(dialogueFlags);
	}

	public int reputation(String factionId) {
		return reputation.getOrDefault(factionId, 0);
	}

	public void addReputation(String factionId, int amount) {
		reputation.put(factionId, reputation.getOrDefault(factionId, 0) + amount);
	}

	public int coins() {
		return coins;
	}

	public void addCoins(int amount) {
		coins = Math.max(0, coins + amount);
	}

	public boolean spendCoins(int amount) {
		if (coins < amount) {
			return false;
		}
		coins -= amount;
		return true;
	}

	public boolean isQuestCompleted(String questId) {
		return completedQuests.contains(questId);
	}

	public void completeQuest(String questId) {
		completedQuests.add(questId);
		questProgress.remove(questId);
	}

	public int questProgress(String questId) {
		return questProgress.getOrDefault(questId, 0);
	}

	public void setQuestProgress(String questId, int progress) {
		questProgress.put(questId, progress);
	}

	public boolean hasDialogueFlag(String flag) {
		return dialogueFlags.contains(flag);
	}

	public void setDialogueFlag(String flag) {
		dialogueFlags.add(flag);
	}

	public Map<String, Integer> reputationView() {
		return Map.copyOf(reputation);
	}

	public Set<String> completedQuests() {
		return Set.copyOf(completedQuests);
	}

	public Map<String, Integer> questProgressView() {
		return Map.copyOf(questProgress);
	}
}
