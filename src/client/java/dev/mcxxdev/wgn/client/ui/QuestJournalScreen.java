package dev.mcxxdev.wgn.client.ui;

import dev.mcxxdev.wgn.client.data.ClientPlayerCache;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Map;

public class QuestJournalScreen extends Screen {
	public QuestJournalScreen() {
		super(Component.literal("WGN Quest Journal"));
	}

	@Override
	protected void init() {
		addRenderableWidget(Button.builder(Component.literal("Close"), button -> onClose())
				.bounds(width / 2 - 60, height - 30, 120, 20).build());
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		renderBackground(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredString(font, "Active Quest Progress", width / 2, 20, 0xFFFFFF);
		int y = 45;
		for (Map.Entry<String, Integer> entry : ClientPlayerCache.questProgress().entrySet()) {
			graphics.drawString(font, entry.getKey() + " — step " + entry.getValue(), 30, y, 0x88CCFF);
			y += 14;
		}
		if (ClientPlayerCache.questProgress().isEmpty()) {
			graphics.drawString(font, "No active quests. Talk to kings and merchants.", 30, y, 0xAAAAAA);
		}
		super.render(graphics, mouseX, mouseY, delta);
	}
}
