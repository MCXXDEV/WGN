package dev.mcxxdev.wgn.npcs;

import dev.mcxxdev.wgn.kingdoms.CivilizationType;
import dev.mcxxdev.wgn.kingdoms.SettlementType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;

public class WgnNpcEntity extends PathfinderMob {
	private static final EntityDataAccessor<String> ROLE = SynchedEntityData.defineId(WgnNpcEntity.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<String> FACTION = SynchedEntityData.defineId(WgnNpcEntity.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<String> DIALOGUE = SynchedEntityData.defineId(WgnNpcEntity.class, EntityDataSerializers.STRING);

	public WgnNpcEntity(EntityType<? extends PathfinderMob> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 20.0)
				.add(Attributes.MOVEMENT_SPEED, 0.28)
				.add(Attributes.FOLLOW_RANGE, 24.0);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ROLE, NpcRole.FARMER.id());
		builder.define(FACTION, "default");
		builder.define(DIALOGUE, "king_greeting");
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
		goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(7, new RandomLookAroundGoal(this));
	}

	public void configure(NpcRole role, CivilizationType civilization, SettlementType settlement) {
		setRole(role);
		setFactionId(civilization.id());
		setDialogueId(dialogueFor(role, settlement));
		setCustomName(net.minecraft.network.chat.Component.literal(formatName(role, civilization)));
		setCustomNameVisible(true);
	}

	private static String dialogueFor(NpcRole role, SettlementType settlement) {
		return switch (role) {
			case KING, QUEEN -> "king_greeting";
			case MERCHANT -> "merchant_greeting";
			case GUARD, KNIGHT -> "guard_greeting";
			default -> "villager_greeting";
		};
	}

	private static String formatName(NpcRole role, CivilizationType civilization) {
		String name = role.id();
		return Character.toUpperCase(name.charAt(0)) + name.substring(1) + " (" + civilization.id() + ")";
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		return InteractionResult.PASS;
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
	}

	public NpcRole getRole() {
		return NpcRole.fromId(getRoleId());
	}

	public String getRoleId() {
		return entityData.get(ROLE);
	}

	public void setRole(NpcRole role) {
		entityData.set(ROLE, role.id());
	}

	public String getFactionId() {
		return entityData.get(FACTION);
	}

	public void setFactionId(String factionId) {
		entityData.set(FACTION, factionId);
	}

	public String getDialogueId() {
		return entityData.get(DIALOGUE);
	}

	public void setDialogueId(String dialogueId) {
		entityData.set(DIALOGUE, dialogueId);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putString("Role", getRoleId());
		tag.putString("Faction", getFactionId());
		tag.putString("Dialogue", getDialogueId());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains("Role")) {
			entityData.set(ROLE, tag.getString("Role"));
		}
		if (tag.contains("Faction")) {
			setFactionId(tag.getString("Faction"));
		}
		if (tag.contains("Dialogue")) {
			setDialogueId(tag.getString("Dialogue"));
		}
	}
}
