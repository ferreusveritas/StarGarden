package com.ferreusveritas.stargarden.features;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;

public class BiomeProviderOceanSpawn extends BiomeProviderWrapper {
	
	private Biome ocean;
	private Biome deepOcean;
	
	public BiomeProviderOceanSpawn(BiomeProvider wrapped, Biome ocean, Biome deepOcean) {
		super(wrapped);
		this.ocean = ocean;
		this.deepOcean = deepOcean;
	}
	
	/**
	 * Returns the biome generator
	 */
	@Override
	public Biome getBiome(BlockPos pos) {
		//System.out.println("getBiome: " + pos);
		return deepOcean;
	}
	
	@Override
	public Biome getBiome(BlockPos pos, Biome defaultBiome) {
		//System.out.println("getBiome: " + pos);
		return deepOcean;
	}
	
	@Override
	public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height) {
		
		if (biomes == null || biomes.length < width * height)
		{
			biomes = new Biome[width * height];
		}
		
		for (int i = 0; i < width * height; ++i) {
			biomes[i] = deepOcean;
		}
		
		//System.out.println("getBiomesForGeneration: " + x + "," + z + "," + width + "," + height);
		
		return biomes;
		//return super.getBiomesForGeneration(biomes, x, z, width, height);
	}

	@Override
	public Biome[] getBiomes(Biome[] listToReuse, int x, int z, int width, int length ) {
		
		if (listToReuse == null || listToReuse.length < width * length) {
			listToReuse = new Biome[width * length];
		}
		
		for (int i = 0; i < width * length; ++i) {
			listToReuse[i] = deepOcean;
		}
		
		return listToReuse;
	}

	
	@Override
	public Biome[] getBiomes(Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag ) {
		
		if (listToReuse == null || listToReuse.length < width * length) {
			listToReuse = new Biome[width * length];
		}
		
		for (int i = 0; i < width * length; ++i) {
			listToReuse[i] = deepOcean;
		}
		
		return listToReuse;
	}
	
}
