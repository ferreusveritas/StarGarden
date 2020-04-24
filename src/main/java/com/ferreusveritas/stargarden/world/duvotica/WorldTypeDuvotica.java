package com.ferreusveritas.stargarden.world.duvotica;

import com.ferreusveritas.stargarden.features.Worlds;
import com.ferreusveritas.stargarden.world.StarWorldType;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
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
		return new ChunkGeneratorDuvotica(world, world.getSeed());
	}
	
	@Override
	public BiomeProvider getBiomeProvider(World world) {
		return new BiomeProviderSingle(Worlds.duvoticaBiome);
	}

}
