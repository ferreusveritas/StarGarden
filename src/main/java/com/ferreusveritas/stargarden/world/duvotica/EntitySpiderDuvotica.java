package com.ferreusveritas.stargarden.world.duvotica;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.DifficultyInstance;
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
	
	/**
	 * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
	 * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
	 */
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		
		if (livingdata == null) {
			livingdata = new EntitySpider.GroupData();
			
			if (this.world.getDifficulty() == EnumDifficulty.HARD && this.world.rand.nextFloat() < 0.1F * difficulty.getClampedAdditionalDifficulty()) {
				((EntitySpider.GroupData)livingdata).setRandomEffect(this.world.rand);
			}
		}
		
		if (livingdata instanceof EntitySpider.GroupData) {
			Potion potion = ((EntitySpider.GroupData)livingdata).effect;
			
			if (potion != null) {
				this.addPotionEffect(new PotionEffect(potion, Integer.MAX_VALUE));
			}
		}
		
		return livingdata;
	}
	
}
