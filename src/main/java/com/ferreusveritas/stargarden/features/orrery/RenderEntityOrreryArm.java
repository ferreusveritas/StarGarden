package com.ferreusveritas.stargarden.features.orrery;

import com.ferreusveritas.stargarden.ModConstants;
import com.ferreusveritas.stargarden.model.ModelOrreryArm;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Rotations;

public class RenderEntityOrreryArm extends Render<EntityOrreryArm>{
	
    private static final ResourceLocation TEXTURE_ORRERY_ARM = new ResourceLocation(ModConstants.MODID, "textures/entity/orreryarm.png");
    
    private final ModelOrreryArm modelOrreryArm = new ModelOrreryArm();

    public RenderEntityOrreryArm(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityOrreryArm entity) {
		return TEXTURE_ORRERY_ARM;
	}
	
	@Override
	public void doRender(EntityOrreryArm entity, double x, double y, double z, float entityYaw, float partialTicks) {
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.enableRescaleNormal();
		
		Rotations rot = entity.getRotation();
		
		if (rot.getY() != 0.0F) {
			GlStateManager.rotate(rot.getY(), 0.0F, 1.0F, 0.0F);
		}
		
		if (rot.getX() != 0.0F) {
			GlStateManager.rotate(rot.getX(), 1.0F, 0.0F, 0.0F);
		}
		
		if (rot.getZ() != 0.0F) {
			GlStateManager.rotate(rot.getZ(), 0.0F, 0.0F, 1.0F);
		}
		
		float scale = entity.getScale();
		
		scale *= 1 / 16f;
		
		if (scale != 1.0F) {
			GlStateManager.scale(scale, scale, scale);
		}
		
        bindTexture(TEXTURE_ORRERY_ARM);
		
        GlStateManager.enableCull();
        
        float radius = entity.getRadius();
        float height = entity.getHeight();
        float planetAngle = entity.getAngle();
        
		modelOrreryArm.render(entity, radius, height, planetAngle, 1.0f);
		
        GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

}
