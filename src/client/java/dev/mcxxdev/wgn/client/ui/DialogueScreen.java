package dev.mcxxdev.wgn.client.ui;

import dev.mcxxdev.wgn.client.data.ClientPlayerCache;
import dev.mcxxdev.wgn.network.WgnNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class DialogueScreen extends Screen {
	private final String nodeId;
	private final String speaker;
	private final String text;
	private final String factionId;
	private final List<String> choices;

	public DialogueScreen(String nodeId, String speaker, String text, String factionId, List<String> choices) {
		super(Component.literal("Dialogue"));
		this.nodeId = nodeId;
		this.speaker = speaker;
		this.text = text;
		this.factionId = factionId;
		this.choices = choices;
	}

	@Override
	protected void init() {
		int y = 120;
		for (int i = 0; i < choices.size(); i++) {
			int index = i;
			addRenderableWidget(Button.builder(Component.literal(choices.get(i)), button -> {
				ClientPlayNetworking.send(new WgnNetworking.DialogueChoicePayload(factionId, nodeId, index));
				onClose();
			}).bounds(width / 2 - 120, y, 240, 20).build());
			y += 24;
		}
		addRenderableWidget(Button.builder(Component.literal("Close"), button -> onClose())
				.bounds(width / 2 - 60, height - 40, 120, 20).build());
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		renderBackground(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredString(font, speaker, width / 2, 30, 0xFFD700);
		graphics.drawWordWrap(font, Component.literal(text), 40, 55, width - 80, 0xFFFFFF);
		super.render(graphics, mouseX, mouseY, delta);
	}
}
