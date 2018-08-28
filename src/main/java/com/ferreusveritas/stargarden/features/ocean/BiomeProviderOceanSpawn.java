package com.ferreusveritas.stargarden.features.ocean;

import java.util.List;

import com.ferreusveritas.stargarden.util.BiomeProviderWrapper;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;

public class BiomeProviderOceanSpawn extends BiomeProviderWrapper {
	
	private Biome ocean;
	private Biome deepOcean;
	private Biome coralReef;
	private int radius = 512;
	
	public BiomeProviderOceanSpawn(BiomeProvider wrapped) {
		super(wrapped);
		ocean = Biome.REGISTRY.getObject(new ResourceLocation("ocean"));
		deepOcean = Biome.REGISTRY.getObject(new ResourceLocation("deep_ocean"));
		coralReef = Biome.REGISTRY.getObject(new ResourceLocation("biomesoplenty", "coral_reef"));
		if(coralReef == null) {
			coralReef = ocean;
		}
	}
	
	/**
	 * Returns the biome generator
	 */
	@Override
	public Biome getBiome(BlockPos pos) {
		return getBiome(pos.getX(), pos.getZ(), super.getBiome(pos));
	}
	
	@Override
	public Biome getBiome(BlockPos pos, Biome defaultBiome) {
		return getBiome(pos.getX(), pos.getZ(), super.getBiome(pos, defaultBiome));
	}
	
	@Override
	public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height) {
		
		biomes = super.getBiomesForGeneration(biomes, x, z, width, height);
		
		int i = 0;
		
		int blockZ = z << 2;
		for (int iz = 0; iz < height; iz++) {
			int blockX = x << 2;
			for (int ix = 0; ix < width; ix++) {
				biomes[i] = getBiome(blockX, blockZ, biomes[i]);
				blockX += 4;
				i++;
			}
			blockZ += 4;
		}
				
		return biomes;
	}
	
	boolean isDistanceLessThan(int blockX, int blockZ, int distance) {
		return blockX * blockX + blockZ * blockZ < distance * distance;
	}
	
	Biome getBiome(int blockX, int blockZ, Biome original) {
		if(isDistanceLessThan(blockX, blockZ, radius)) {
			if(original.equals(deepOcean) || isDistanceLessThan(blockX, blockZ, radius - 48)) {
				return deepOcean;
			}
			
			if(isDistanceLessThan(blockX, blockZ, radius - 32)) {
				return coralReef;
			}
			
			return ocean;
		}

		return original;
	}
	
	@Override
	public Biome[] getBiomes(Biome[] listToReuse, int x, int z, int width, int length ) {
		
		listToReuse = super.getBiomes(listToReuse, x, z, width, length);
		
		int i = 0;
		int blockZ = z;
		for (int iz = 0; iz < length; iz++) {
			int blockX = x;
			for (int ix = 0; ix < width; ix++) {
				listToReuse[i] = getBiome(blockX, blockZ, listToReuse[i]);
				blockX += 1;
				i++;
			}
			blockZ += 1;
		}
		
		return listToReuse;
	}

	
	@Override
	public Biome[] getBiomes(Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag ) {
		
		listToReuse = super.getBiomes(listToReuse, x, z, width, length, cacheFlag);
		
		int i = 0;
		int blockZ = z;
		for (int iz = 0; iz < length; iz++) {
			int blockX = x;
			for (int ix = 0; ix < width; ix++) {
				listToReuse[i] = getBiome(blockX, blockZ, listToReuse[i]);
				blockX += 1;
				i++;
			}
			blockZ += 1;
		}
				
		return listToReuse;
	}
	
	@Override
	public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed) {
		return isDistanceLessThan(x, z, this.radius) ? false : super.areBiomesViable(x, z, radius, allowed);
	}
}
