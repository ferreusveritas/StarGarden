package com.ferreusveritas.stargarden.world.maridia;

import java.util.Random;

import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.common.block.BlockBOPDirt;
import biomesoplenty.common.block.BlockBOPDirt.BOPDirtType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeMaridia extends BiomeOcean {

	protected IBlockState stone;
	protected IBlockState sand;
	protected IBlockState dirt;
	
	public BiomeMaridia() {
		super(getBiomeProperties());
		
		decorator.treesPerChunk = -999;
		decorator.grassPerChunk = 20;
		decorator.flowersPerChunk = 0;
		
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		
		//This is so the biomeDictionary doesn't crash
		topBlock = Blocks.GRASS.getDefaultState();
		fillerBlock = Blocks.DIRT.getDefaultState();
	}

	public void assignMaterials() {
		stone = ChunkGeneratorMaridia.getStone();
		sand = ChunkGeneratorMaridia.getSand();
		dirt = BOPBlocks.dirt.getDefaultState().withProperty(BlockBOPDirt.VARIANT, BOPDirtType.LOAMY).withProperty(BlockBOPDirt.COARSE, true);
		
		topBlock = dirt;
		fillerBlock = stone;
	}
	
	private static BiomeProperties getBiomeProperties() {
		return new BiomeProperties("Maridia").setBaseHeight(-1.8F).setHeightVariation(0.1F);
	}
	
	@Override
	public BiomeDecorator createBiomeDecorator() {
		return getModdedBiomeDecorator(new BiomeDecoratorMaridia());
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
	
	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
		
		int seaLevel = worldIn.getSeaLevel();
		IBlockState top = topBlock;
		IBlockState fill = fillerBlock;
		int clipX = x & 15;
		int clipZ = z & 15;
		
		int thickCount = 0;
		
		for (int yi = seaLevel - 1; yi > 15; --yi) {
			IBlockState sample = chunkPrimerIn.getBlockState(clipX, yi, clipZ);

			if(sample == WATER) {
				thickCount = 0;
			} else if (sample == stone) {
				thickCount++;
				if(thickCount >= 1 && thickCount <= 3) {
					chunkPrimerIn.setBlockState(clipX, yi, clipZ, yi > 17 ? WATER : sand);
				}
				else if(thickCount == 4) {
					chunkPrimerIn.setBlockState(clipX, yi + 2, clipZ, yi > 16 ? top : sand);
					chunkPrimerIn.setBlockState(clipX, yi + 1, clipZ, rand.nextInt(3) == 0 ? (yi > 16 ? top : fill) : fill);
					chunkPrimerIn.setBlockState(clipX, yi, clipZ, fill);
				}
				else {
					chunkPrimerIn.setBlockState(clipX, yi, clipZ, fill);
				}
			}
		}
		
		int bedrockLevel = rand.nextInt(4);
		
		for (int yi = 15; yi >= 0; --yi) {
			if (yi <= bedrockLevel) {
				chunkPrimerIn.setBlockState(clipX, yi, clipZ, BEDROCK);
			}
			else {
				IBlockState sample = chunkPrimerIn.getBlockState(clipX, yi, clipZ);

				if(sample == stone) {
					chunkPrimerIn.setBlockState(clipX, yi, clipZ, fill);
				}
			}
		}
		
	}
	
}
