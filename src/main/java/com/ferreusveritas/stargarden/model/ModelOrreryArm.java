package com.ferreusveritas.stargarden.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelOrreryArm extends ModelBase {
	
	public ModelRenderer mainBox = (new ModelRenderer(this)).setTextureOffset(0, 14).addBox(-8.0F, -1.0F, -8.0F, 16, 2, 16);
	public ModelRenderer verBox = (new ModelRenderer(this)).setTextureOffset(8, 5).addBox(-2.5F, -1.5F, -2.5F, 5, 3, 5);
	
	public ModelRenderer[] hBarSections = new ModelRenderer[4];
	public ModelRenderer[] vBarSections = new ModelRenderer[8];
	
	public ModelOrreryArm() {
		for(int i = 0; i < 4; i++) {
			hBarSections[i] = (new ModelRenderer(this)).setTextureOffset(0, 0).addBox(0.0F, -1.0F, -1.5F, (i + 1) * 4, 2, 3);
		}
		
		for(int i = 0; i < 8; i++) {
			vBarSections[i] = (new ModelRenderer(this)).setTextureOffset(0, 5).addBox(-1.0F, 0.0F, -1.0F, 2, (i + 1) * 2, 2);
		}
	}
	
	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	public void render(Entity entityIn, float radius, float height, float planetAngle, float scale) {
		
		mainBox.render(scale);

		verBox.offsetX = radius * 16.0f;
		verBox.render(scale);

		//Process horizontal bar
		
		float hBarLen = radius - 0.5f;
		int hSections = (int)Math.floor(hBarLen);
		int hRemain = (int)Math.floor((hBarLen % 1.0f) * 16.0f);
		
		for(int i = 0; i < hSections; i++) {
			hBarSections[3].offsetX = 8.0f + (i * 16.0f);
			hBarSections[3].render(scale);
		}
		
		if(hRemain >= 2) {
			int section = (hRemain - 2) / 4;
			ModelRenderer bar = hBarSections[section];
			bar.offsetX = 8.0f + (hSections * 16.0f);
			bar.render(scale);
		}
				
		//Process vertical bar
		
		float vBarRot = (float)Math.toRadians(planetAngle);
		float vBarLen = height;
		int vSections = (int)Math.floor(vBarLen);
		int vRemain = (int)Math.floor((vBarLen % 1.0f) * 16.0f);
		
		for(int i = 0; i < vSections; i++) {
			vBarSections[7].offsetX = radius * 16.0f;
			vBarSections[7].offsetY = i * 16.0f;
			vBarSections[7].rotateAngleY = vBarRot;
			vBarSections[7].render(scale);
		}

		if(vRemain >= 1) {
			int section = (vRemain - 1) / 2;
			ModelRenderer bar = vBarSections[section];
			bar.offsetX = radius * 16.0f;
			bar.rotateAngleY = vBarRot;
			bar.offsetY = vSections * 16.0f;
			bar.render(scale);
		}
		
	}
	
}
