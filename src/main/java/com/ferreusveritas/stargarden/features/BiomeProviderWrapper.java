package com.ferreusveritas.stargarden.features;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;

public class BiomeProviderWrapper extends BiomeProvider {
	
	public BiomeProvider wrapped;
	
	public BiomeProviderWrapper(BiomeProvider wrapped) {
		this.wrapped = wrapped;
	}
	
	@Override
	public List<Biome> getBiomesToSpawnIn() {
		return wrapped.getBiomesToSpawnIn();
	}
	
	@Override
	public Biome getBiome(BlockPos pos) {
		return wrapped.getBiome(pos);
	}
	
	@Override
	public Biome getBiome(BlockPos pos, Biome defaultBiome) {
		return wrapped.getBiome(pos, defaultBiome);
	}
	
	@Override
	public float getTemperatureAtHeight(float p_76939_1_, int p_76939_2_) {
		return wrapped.getTemperatureAtHeight(p_76939_1_, p_76939_2_);
	}
	
	@Override
	public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height) {
		return wrapped.getBiomesForGeneration(biomes, x, z, width, height);
	}
	
	@Override
	public Biome[] getBiomes(@Nullable Biome[] oldBiomeList, int x, int z, int width, int depth) {
		return wrapped.getBiomes(oldBiomeList, x, z, width, depth);
	}
	
	@Override
	public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
		return wrapped.getBiomes(listToReuse, x, z, width, length, cacheFlag);
	}
	
	@Override
	public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed) {
		return wrapped.areBiomesViable(x, z, radius, allowed);
	}
	
	@Override
	@Nullable
	public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random) {
		return wrapped.findBiomePosition(x, z, range, biomes, random);
	}
	
	@Override
	public void cleanupCache() {
		wrapped.cleanupCache();
	}
	
	@Override
	public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
		return wrapped.getModdedBiomeGenerators(worldType, seed, original);
	}
	
	@Override
	public boolean isFixedBiome() {
		return wrapped.isFixedBiome();
	}
	
	@Override
	public Biome getFixedBiome() {
		return wrapped.getFixedBiome();
	}
	
}
