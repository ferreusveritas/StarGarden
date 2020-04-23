package com.ferreusveritas.stargarden.world.maridia;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeMaridia extends BiomeOcean {

	public BiomeMaridia(BiomeProperties properties) {
		super(properties);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		return getModdedBiomeFoliageColor(0X3BAFB2);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return getModdedBiomeGrassColor(0X3BAFB2);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return 0XFFF478;
	}
	
	@Override
	public float getSpawningChance() {
		return 0.07f;
	}
	
}
