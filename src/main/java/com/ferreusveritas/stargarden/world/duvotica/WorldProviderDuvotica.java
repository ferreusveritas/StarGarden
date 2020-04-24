package com.ferreusveritas.stargarden.world.duvotica;

import com.ferreusveritas.stargarden.world.StarWorldProvider;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;

public class WorldProviderDuvotica extends StarWorldProvider {
	
	private final Vec3d fogColor = convColor(0xB1DCBE);
	
	@Override
	public DimensionType getDimensionType() {
		return DimensionType.OVERWORLD;
	}
	
	@Override
	public Vec3d getFogColor(float celestialAngle, float partialTicks) {
		return fogColor;
	}

}
