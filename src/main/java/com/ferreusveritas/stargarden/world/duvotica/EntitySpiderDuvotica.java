package com.ferreusveritas.stargarden.world.duvotica;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntitySpiderDuvotica extends EntitySpider {
	
	public EntitySpiderDuvotica(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 0.5F);
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(12.0);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.32);
	}
	
	public boolean attackEntityAsMob(Entity entityIn)
	{
		if (super.attackEntityAsMob(entityIn)) {
			if (entityIn instanceof EntityLivingBase) {
				int i = 0;
				
				if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
					i = 7;
				}
				else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
					i = 15;
				}
				
				if (i > 0) {
					((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, i * 20, 1));
				}
			}
			
			return true;
		}
		else {
			return false;
		}
	}
	
}
