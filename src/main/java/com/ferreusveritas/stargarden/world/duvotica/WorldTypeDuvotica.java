package com.ferreusveritas.stargarden.world.duvotica;

import com.ferreusveritas.stargarden.world.StarWorldType;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldTypeDuvotica extends StarWorldType {
	
	public static final String name = "duvotica";
	
	public WorldTypeDuvotica() {
		this(name);
	}
	
	public WorldTypeDuvotica(String name) {
		super(name);
	}
	
	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		return new ChunkGeneratorDuvotica(world, 0);
	}
	
	@Override
	public BiomeProvider getBiomeProvider(World world) {
		return new BiomeProviderDuvotica();
	}
	
	@Override
	public boolean hasCustomWorldProvider() {
		return true;
	}
	
	@Override
	public WorldProvider createCustomWorldProvider(WorldProvider worldProvider) {
		return new WorldProvider() {
			private final Vec3d fogColor = new Vec3d(177 / 255.0, 220 / 255.0, 190 / 255.0);
			
			@Override
			public DimensionType getDimensionType() {
				return DimensionType.OVERWORLD;
			}
			
			@Override
			public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
				return fogColor;
			}
			
			@Override
			public IChunkGenerator createChunkGenerator() {
				return super.createChunkGenerator();
			}
		};
	}
}
