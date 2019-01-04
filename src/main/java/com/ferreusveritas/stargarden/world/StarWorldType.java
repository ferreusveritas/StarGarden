package com.ferreusveritas.stargarden.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.IChunkGenerator;

public class StarWorldType extends WorldType {

	public StarWorldType(String name) {
		super(name);
	}
	
	public boolean hasCustomWorldProvider() {
		return false;
	}
	
	public WorldProvider createCustomWorldProvider() {
		return DimensionType.OVERWORLD.createDimension();
	}
	
	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		return new ChunkGeneratorEnd(world, false, 0, new BlockPos(0, 70, 0));
	}
}
