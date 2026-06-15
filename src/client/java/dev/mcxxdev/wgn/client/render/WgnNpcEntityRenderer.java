package dev.mcxxdev.wgn.client.render;

import dev.mcxxdev.wgn.npcs.WgnNpcEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WgnNpcEntityRenderer extends HumanoidMobRenderer<WgnNpcEntity, HumanoidModel<WgnNpcEntity>> {
	private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/player/wide/steve.png");

	public WgnNpcEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(WgnNpcEntity entity) {
		return TEXTURE;
	}
}
