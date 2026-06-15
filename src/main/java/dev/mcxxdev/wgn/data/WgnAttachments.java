package dev.mcxxdev.wgn.data;

import dev.mcxxdev.wgn.core.WgnIdentifiers;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.world.entity.player.Player;

public final class WgnAttachments {
	public static final AttachmentType<WgnPlayerData> PLAYER_DATA = AttachmentRegistry.create(
			WgnIdentifiers.id("player_data"),
			builder -> builder
					.initializer(WgnPlayerData::new)
					.persistent(WgnPlayerData.CODEC)
					.copyOnDeath()
	);

	private WgnAttachments() {}

	public static WgnPlayerData get(Player player) {
		return player.getAttachedOrCreate(PLAYER_DATA);
	}
}
