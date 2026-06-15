package dev.mcxxdev.wgn.client.ui;

import dev.mcxxdev.wgn.client.data.ClientPlayerCache;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Map;

public class ReputationScreen extends Screen {
	public ReputationScreen() {
		super(Component.literal("WGN Reputation"));
	}

	@Override
	protected void init() {
		addRenderableWidget(Button.builder(Component.literal("Close"), button -> onClose())
				.bounds(width / 2 - 60, height - 30, 120, 20).build());
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		renderBackground(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredString(font, "Faction Reputation", width / 2, 20, 0xFFFFFF);
		graphics.drawString(font, "Coins: " + ClientPlayerCache.coins(), 30, 40, 0xFFD700);
		int y = 60;
		for (Map.Entry<String, Integer> entry : ClientPlayerCache.reputation().entrySet()) {
			graphics.drawString(font, entry.getKey() + ": " + entry.getValue(), 30, y, 0xA0E8A0);
			y += 14;
		}
		if (ClientPlayerCache.reputation().isEmpty()) {
			graphics.drawString(font, "No faction reputation yet. Help kingdoms or talk to NPCs.", 30, y, 0xAAAAAA);
		}
		super.render(graphics, mouseX, mouseY, delta);
	}
}
