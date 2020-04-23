package com.ferreusveritas.stargarden.world.duvotica;

import java.util.Random;

import cofh.thermalfoundation.block.BlockOre.Type;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class BiomeDecoratorDuvotica extends BiomeDecorator {

	public WorldGenMinable copperGen;
	public WorldGenMinable tinGen;
	public WorldGenMinable silverGen;

	int copperSize = 9;
	int copperCount = 10;
	int tinSize = 9;
	int tinCount = 8;
	int silverSize = 9;
	int silverCount = 6;

	@Override
	public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {

		ChunkGeneratorSettings.Factory factory = new ChunkGeneratorSettings.Factory();

		//Disable all ugly cave rainbow stone
		factory.dirtCount = 0;
		factory.dirtSize = 0;
		factory.gravelCount = 0;
		factory.graniteSize = 0;
		factory.graniteCount = 0;
		factory.graniteSize = 0;
		factory.dioriteCount = 0;
		factory.dioriteSize = 0;
		factory.andesiteCount = 0;
		factory.andesiteSize = 0;

		this.chunkProviderSettings = factory.build();

		chunkProviderSettings = ChunkGeneratorSettings.Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
		chunkPos = pos;
		//This is just in case these members are accessed outside.  We don't use them here.
		dirtGen = gravelOreGen = graniteGen = dioriteGen = andesiteGen = new WorldGenMinable(Blocks.STONE.getDefaultState(), 0);

		coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), chunkProviderSettings.coalSize);
		ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), chunkProviderSettings.ironSize);
		goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), chunkProviderSettings.goldSize);
		redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), chunkProviderSettings.redstoneSize);
		diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), chunkProviderSettings.diamondSize);
		lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), chunkProviderSettings.lapisSize);

		IBlockState copperOre = cofh.thermalfoundation.init.TFBlocks.blockOre.getStateFromMeta(Type.COPPER.getMetadata());
		IBlockState silverOre = cofh.thermalfoundation.init.TFBlocks.blockOre.getStateFromMeta(Type.SILVER.getMetadata());
		IBlockState tinOre = cofh.thermalfoundation.init.TFBlocks.blockOre.getStateFromMeta(Type.TIN.getMetadata());

		copperGen = new WorldGenMinable(copperOre, copperSize);
		tinGen = new WorldGenMinable(tinOre, tinSize);
		silverGen = new WorldGenMinable(silverOre, silverSize);

		genDecorations(biome, worldIn, random);
	}

	/**
	 * Generates ores in the current chunk
	 */
	protected void generateOres(World worldIn, Random random) {
		net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Pre(worldIn, random, chunkPos));
		if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, coalGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.COAL)) {
			genStandardOre1(worldIn, random, chunkProviderSettings.coalCount, coalGen, chunkProviderSettings.coalMinHeight, chunkProviderSettings.coalMaxHeight);
		}
		if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, ironGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.IRON)) {
			genStandardOre1(worldIn, random, chunkProviderSettings.ironCount, ironGen, chunkProviderSettings.ironMinHeight, chunkProviderSettings.ironMaxHeight);
		}
		if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, goldGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD)) {
			genStandardOre1(worldIn, random, chunkProviderSettings.goldCount, goldGen, chunkProviderSettings.goldMinHeight, chunkProviderSettings.goldMaxHeight);
		}

		genStandardOre1(worldIn, random, copperCount, copperGen, chunkProviderSettings.ironMinHeight, chunkProviderSettings.ironMaxHeight);
		genStandardOre1(worldIn, random, tinCount, tinGen, chunkProviderSettings.ironMinHeight, chunkProviderSettings.ironMaxHeight);
		genStandardOre1(worldIn, random, silverCount, silverGen, 0, 32);

		if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, redstoneGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.REDSTONE)) {
			genStandardOre1(worldIn, random, chunkProviderSettings.redstoneCount, redstoneGen, chunkProviderSettings.redstoneMinHeight, chunkProviderSettings.redstoneMaxHeight);
		}
		if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, diamondGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIAMOND)) {
			genStandardOre1(worldIn, random, chunkProviderSettings.diamondCount, diamondGen, chunkProviderSettings.diamondMinHeight, chunkProviderSettings.diamondMaxHeight);
		}
		if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, lapisGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.LAPIS)) {
			genStandardOre2(worldIn, random, chunkProviderSettings.lapisCount, lapisGen, chunkProviderSettings.lapisCenterHeight, chunkProviderSettings.lapisSpread);
		}
		net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Post(worldIn, random, chunkPos));

	}

}
