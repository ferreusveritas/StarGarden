package com.ferreusveritas.stargarden.world.duvotica;

import java.util.Random;

import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.api.enums.BOPPlants;
import biomesoplenty.common.block.BlockBOPDirt;
import biomesoplenty.common.block.BlockBOPDirt.BOPDirtType;
import biomesoplenty.common.block.BlockBOPGrass;
import biomesoplenty.common.block.BlockBOPGrass.BOPGrassType;
import biomesoplenty.common.block.BlockBOPPlant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeDuvotica extends Biome {
	
	protected IBlockState stone;
	protected IBlockState sand;
	
	protected static IBlockState grass;
	protected static IBlockState dirt;
	
	protected static WorldGenerator koru;
	private static WorldGenerator tallGrass;
	private static WorldGenDoublePlant dTallGrass;
	
	public BiomeDuvotica() {
		super(getBiomeProperties());
		
		this.decorator.treesPerChunk = -999;
		this.decorator.grassPerChunk = 20;
		this.decorator.flowersPerChunk = 0;
		
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityWitherSkeleton.class, 5, 1, 1));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpiderDuvotica.class, 15, 1, 1));
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityParrot.class, 10, 4, 4));
		
		//This is so the biomeDictionary doesn't crash
		topBlock = Blocks.GRASS.getDefaultState();
		fillerBlock = Blocks.DIRT.getDefaultState();
		
	}
	
	public void assignMaterials() {
		stone = ChunkGeneratorDuvotica.getStone();
		sand = ChunkGeneratorDuvotica.getSand();
		grass = BOPBlocks.grass.getDefaultState().withProperty(BlockBOPGrass.VARIANT, BOPGrassType.SILTY);
		dirt = BOPBlocks.dirt.getDefaultState().withProperty(BlockBOPDirt.VARIANT, BOPDirtType.SILTY);
		
		koru = new WorldGenBOPPlant(BOPPlants.KORU);
		tallGrass = new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
		dTallGrass = new WorldGenDoublePlant();
		dTallGrass.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);
		
		topBlock = grass;
		fillerBlock = dirt;
	}
	
	public static class WorldGenBOPPlant extends WorldGenerator {
		
		private final IBlockState plantState;
		
		public WorldGenBOPPlant(BOPPlants type) {
			this.plantState = BlockBOPPlant.paging.getVariantState(type);
		}
		
		public boolean generate(World worldIn, Random rand, BlockPos position) {
			for (IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getBlock().isAir(iblockstate, worldIn, position) || iblockstate.getBlock().isLeaves(iblockstate, worldIn, position)) && position.getY() > 0; iblockstate = worldIn.getBlockState(position)) {
				position = position.down();
			}
			
			Block block = plantState.getBlock();
			
			for (int i = 0; i < 128; ++i) {
				BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
				if(worldIn.isAirBlock(blockpos) && ((BlockBOPPlant)block).canBlockStay(worldIn, blockpos, plantState)) {
					worldIn.setBlockState(blockpos, plantState, 2);	
				}
			}
			
			return true;
		}
		
	}
	
	private static BiomeProperties getBiomeProperties() {
		return new BiomeProperties("Duvotica").setTemperature(0.75f).setRainfall(0.75f).setWaterColor(0x00FF90);
	}
	
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand) {
		return rand.nextInt(3) == 0 ? koru : (rand.nextInt(6) == 0 ? dTallGrass : tallGrass);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		return getModdedBiomeFoliageColor(0XBE415A);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return getModdedBiomeGrassColor(0XDB4B62);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return 0X5AC4BA;
	}
	
	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
		
		int seaLevel = worldIn.getSeaLevel();
		IBlockState top = topBlock;
		IBlockState fill = fillerBlock;
		int density = -1;
		int random = rand.nextInt(2) + 1;//(int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int clipX = x & 15;
		int clipZ = z & 15;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
		
		//System.out.println(random);
		
		for (int yi = 255; yi >= 0; --yi) {
			if (yi <= rand.nextInt(5)) {
				chunkPrimerIn.setBlockState(clipZ, yi, clipX, BEDROCK);
			}
			else {
				IBlockState sample = chunkPrimerIn.getBlockState(clipZ, yi, clipX);
				
				if (sample.getMaterial() == Material.AIR) {
					density = -1;
				}
				else if (sample == stone) {
					if (density == -1) {
						
						if (random <= 0) {
							top = AIR;
							fill = stone;
						}
						else if (yi >= seaLevel - 4 && yi <= seaLevel + 1) {
							top = this.topBlock;
							fill = this.fillerBlock;
						}
						
						if (yi < seaLevel && (top == null || top.getMaterial() == Material.AIR)) {
							if (this.getTemperature(blockpos$mutableblockpos.setPos(x, yi, z)) < 0.15F) {
								top = ICE;
							}
							else {
								top = WATER;
							}
						}
						
						density = random;
						
						if (yi >= seaLevel - 1) { //Set the top layer of grass
							chunkPrimerIn.setBlockState(clipZ, yi, clipX, top);
						}
						/*else if (yi < seaLevel - 7 - random) {
							top = AIR;
							fill = STONE;
							chunkPrimerIn.setBlockState(clipZ, yi, clipX, GRAVEL);
						}*/
						else {
							chunkPrimerIn.setBlockState(clipZ, yi, clipX, fill);
						}
					}
					else if (density > 0) {
						--density;
						chunkPrimerIn.setBlockState(clipZ, yi, clipX, fill);
					}
				}
			}
		}
		
	}
	
}
