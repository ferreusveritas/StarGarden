package com.ferreusveritas.stargarden.world.maridia;

import com.ferreusveritas.stargarden.world.StarWorldProvider;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;

public class WorldProviderMaridia extends StarWorldProvider {

	private final Vec3d fogColor = convColor(0xE7E4D8);
	
	@Override
	public DimensionType getDimensionType() {
		return DimensionType.OVERWORLD;
	}
	
	@Override
	public Vec3d getFogColor(float celestialAngle, float partialTicks) {
		return fogColor;
	}
	
}
