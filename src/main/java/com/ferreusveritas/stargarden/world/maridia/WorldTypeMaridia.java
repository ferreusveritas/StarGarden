package com.ferreusveritas.stargarden.world.maridia;

import com.ferreusveritas.stargarden.world.StarWorldType;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldTypeMaridia extends StarWorldType {
	
	public static final String name = "maridia";
	
	public WorldTypeMaridia() {
		this(name);
	}
	
	public WorldTypeMaridia(String name) {
		super(name);
	}
	
	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		String genOptions = "{\"coordinateScale\":684.412,\"heightScale\":684.412,\"lowerLimitScale\":512.0,\"upperLimitScale\":512.0,\"depthNoiseScaleX\":200.0,\"depthNoiseScaleZ\":200.0,\"depthNoiseScaleExponent\":0.5,\"mainNoiseScaleX\":80.0,\"mainNoiseScaleY\":160.0,\"mainNoiseScaleZ\":80.0,\"baseSize\":8.5,\"stretchY\":12.0,\"biomeDepthWeight\":1.0,\"biomeDepthOffset\":0.0,\"biomeScaleWeight\":1.0,\"biomeScaleOffset\":0.0,\"seaLevel\":128,\"useCaves\":false,\"useDungeons\":false,\"dungeonChance\":1,\"useStrongholds\":false,\"useVillages\":false,\"useMineShafts\":false,\"useTemples\":false,\"useMonuments\":false,\"useMansions\":false,\"useRavines\":false,\"useWaterLakes\":false,\"waterLakeChance\":1,\"useLavaLakes\":false,\"lavaLakeChance\":80,\"useLavaOceans\":false,\"fixedBiome\":22,\"biomeSize\":4,\"riverSize\":4,\"dirtSize\":33,\"dirtCount\":0,\"dirtMinHeight\":0,\"dirtMaxHeight\":256,\"gravelSize\":33,\"gravelCount\":8,\"gravelMinHeight\":0,\"gravelMaxHeight\":256,\"graniteSize\":33,\"graniteCount\":10,\"graniteMinHeight\":0,\"graniteMaxHeight\":80,\"dioriteSize\":33,\"dioriteCount\":10,\"dioriteMinHeight\":0,\"dioriteMaxHeight\":0,\"andesiteSize\":33,\"andesiteCount\":10,\"andesiteMinHeight\":0,\"andesiteMaxHeight\":0,\"coalSize\":17,\"coalCount\":0,\"coalMinHeight\":0,\"coalMaxHeight\":128,\"ironSize\":9,\"ironCount\":0,\"ironMinHeight\":0,\"ironMaxHeight\":64,\"goldSize\":9,\"goldCount\":0,\"goldMinHeight\":0,\"goldMaxHeight\":32,\"redstoneSize\":8,\"redstoneCount\":0,\"redstoneMinHeight\":0,\"redstoneMaxHeight\":16,\"diamondSize\":8,\"diamondCount\":0,\"diamondMinHeight\":0,\"diamondMaxHeight\":16,\"lapisSize\":12,\"lapisCount\":25,\"lapisCenterHeight\":16,\"lapisSpread\":32}";
		return new ChunkGeneratorOverworld(world, world.getSeed(), false, genOptions);
	}
	
	@Override
	public BiomeProvider getBiomeProvider(World world) {
		return new BiomeProviderMaridia();
	}
	
	@Override
	public boolean hasCustomWorldProvider() {
		return true;
	}
	
	@Override
	public WorldProvider createCustomWorldProvider(WorldProvider worldProvider) {
		return new WorldProvider() {
			
			private final Vec3d fogColor = new Vec3d(0xe7 / 255.0, 0xe4 / 255.0, 0xd8 / 255.0);
			
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
