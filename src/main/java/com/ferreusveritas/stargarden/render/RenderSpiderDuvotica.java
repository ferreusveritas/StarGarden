package com.ferreusveritas.stargarden.render;

import com.ferreusveritas.stargarden.ModConstants;
import com.ferreusveritas.stargarden.world.duvotica.EntitySpiderDuvotica;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderSpiderDuvotica extends RenderSpider<EntitySpiderDuvotica> {
	
	private static final ResourceLocation DUVOTICA_SPIDER_TEXTURES = new ResourceLocation(ModConstants.MODID, "textures/entity/spider/duvotica_spider.png");
	
	public RenderSpiderDuvotica(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize *= 0.5F;
	}
	
	@Override
	public <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean addLayer(U layer) {
		return true;
	}
	
	/**
	 * Allows the render to do state modifications necessary before the model is rendered.
	 */
	protected void preRenderCallback(EntitySpiderDuvotica entitylivingbaseIn, float partialTickTime) {
		GlStateManager.scale(0.5F, 0.5F, 0.5F);
	}
	
	@Override
	public void doRender(EntitySpiderDuvotica entity, double x, double y, double z, float entityYaw, float partialTicks) {
		//System.out.println("How about this?");
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntitySpiderDuvotica entity) {
		return DUVOTICA_SPIDER_TEXTURES;
	}
	
}
