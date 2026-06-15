package dev.mcxxdev.wgn.client.ui;

import dev.mcxxdev.wgn.client.data.ClientPlayerCache;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class WgnHudScreen extends Screen {
	public WgnHudScreen() {
		super(Component.literal("World Generation Nexus"));
	}

	@Override
	protected void init() {
		addRenderableWidget(Button.builder(Component.literal("Reputation"), button ->
				minecraft.setScreen(new ReputationScreen())).bounds(30, 40, 140, 20).build());
		addRenderableWidget(Button.builder(Component.literal("Quest Journal"), button ->
				minecraft.setScreen(new QuestJournalScreen())).bounds(30, 70, 140, 20).build());
		addRenderableWidget(Button.builder(Component.literal("Close"), button -> onClose())
				.bounds(width / 2 - 60, height - 30, 120, 20).build());
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		renderBackground(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredString(font, "World Generation Nexus", width / 2, 15, 0x55FF55);
		graphics.drawString(font, "Coins: " + ClientPlayerCache.coins(), 180, 45, 0xFFD700);
		super.render(graphics, mouseX, mouseY, delta);
	}
}
