package com.ferreusveritas.stargarden.world.maridia;

import java.util.Arrays;

import javax.annotation.Nullable;

import com.ferreusveritas.stargarden.features.Worlds;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;

public class BiomeProviderMaridia extends BiomeProvider {
	
	@Override
	public Biome getBiome(BlockPos pos) {
		return Worlds.duvoticaBiome;
	}
	
	/**
	 * Gets biomes to use for the blocks and loads the other data like temperature and humidity onto the
	 * WorldChunkManager.
	 */
	@Override
	public Biome[] getBiomes(@Nullable Biome[] oldBiomeList, int x, int z, int width, int depth) {
		if (oldBiomeList == null || oldBiomeList.length < width * depth) {
			oldBiomeList = new Biome[width * depth];
		}
		
		Arrays.fill(oldBiomeList, 0, width * depth, Worlds.duvoticaBiome);
		
		return oldBiomeList;
	}
	
	/**
 	* Gets a list of biomes for the specified blocks.
	 	*/
	@Override
	public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
		return this.getBiomes(listToReuse, x, z, width, length);
	}

}
