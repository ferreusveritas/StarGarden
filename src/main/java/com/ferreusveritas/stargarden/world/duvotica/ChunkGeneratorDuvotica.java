package com.ferreusveritas.stargarden.world.duvotica;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import biomesoplenty.api.block.BOPBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChorusFlower;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.NoiseGeneratorSimplex;
import net.minecraft.world.gen.feature.WorldGenEndGateway;
import net.minecraft.world.gen.feature.WorldGenEndIsland;

public class ChunkGeneratorDuvotica implements IChunkGenerator {
	
	/** RNG. */
	private final Random rand;
	protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
	protected static final IBlockState WATER = Blocks.WATER.getDefaultState();
	protected final IBlockState STONE = getStone();
	protected final IBlockState SAND = getSand();
	
	private NoiseGeneratorOctaves lPerlinNoise1;
	private NoiseGeneratorOctaves lPerlinNoise2;
	private NoiseGeneratorOctaves sPerlinNoise1;
	private NoiseGeneratorPerlin surfaceNoise;
	/** A NoiseGeneratorOctaves used in generating terrain */
	public NoiseGeneratorOctaves noiseGen5;
	/** A NoiseGeneratorOctaves used in generating terrain */
	public NoiseGeneratorOctaves noiseGen6;
	/** Reference to the World object. */
	private final World world;
	/** are map structures going to be generated (e.g. strongholds) */
	//private final boolean mapFeaturesEnabled;
	private final BlockPos spawnPoint;
	private NoiseGeneratorSimplex islandNoise;
	private double[] buffer;
	/** The biomes that are used to generate the chunk */
	private Biome[] biomesForGeneration;
	double[] sPOct1;
	double[] lPOct1;
	double[] lPOct2;
	private final WorldGenEndIsland endIslands = new WorldGenEndIsland();
	
	private final int seaLevel = 63;
	
	public ChunkGeneratorDuvotica(World world, boolean mapFeaturesEnabled, long rand, BlockPos spawnPoint) {
		this.world = world;
		//this.mapFeaturesEnabled = mapFeaturesEnabled;
		this.spawnPoint = spawnPoint;
		this.rand = new Random(rand);
		this.lPerlinNoise1 = new NoiseGeneratorOctaves(this.rand, 16);
		this.lPerlinNoise2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.sPerlinNoise1 = new NoiseGeneratorOctaves(this.rand, 8);
		this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
		this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
		this.islandNoise = new NoiseGeneratorSimplex(this.rand);
		
		/*net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextEnd ctx =
				new net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextEnd(lPerlinNoise1, lPerlinNoise2, sPerlinNoise1, noiseGen5, noiseGen6, islandNoise);
		ctx = net.minecraftforge.event.terraingen.TerrainGen.getModdedNoiseGenerators(world, this.rand, ctx);
		this.lPerlinNoise1 = ctx.getLPerlin1();
		this.lPerlinNoise2 = ctx.getLPerlin2();
		this.sPerlinNoise1 = ctx.getPerlin();
			this.surfaceNoise = ctx.getHeight();
		this.noiseGen5 = ctx.getDepth();
		this.noiseGen6 = ctx.getScale();
		this.islandNoise = ctx.getIsland();*/
		
		world.setSeaLevel(seaLevel);
	}
	
	/**
	 * Generates a bare-bones chunk of nothing but stone or ocean blocks, formed, but featureless.
	 */
	public void setBlocksInChunk(int chunkPosX, int chunkPosZ, ChunkPrimer primer) {
		//int i = 2;
		int xSamples = 3;
		int ySamples = 33;
		int zSamples = 3;
		this.buffer = this.getDensities(this.buffer, chunkPosX * 2, 0, chunkPosZ * 2, xSamples, ySamples, zSamples);
		
		final double qtr = 0.25D;
		final double eighth = 0.125D;
		
		for (int xHalf = 0; xHalf < 2; ++xHalf) {
			for (int zHalf = 0; zHalf < 2; ++zHalf) {
				for (int yTall = 0; yTall < 32; ++yTall) {
					
					//Here we sample the 8 corners of a cube in the noise space.  This will later be used as interpolation points
					double start_MinX_MinZ = this.buffer[((xHalf + 0) * 3 + zHalf + 0) * 33 + yTall + 0];
					double start_MinX_MaxZ = this.buffer[((xHalf + 0) * 3 + zHalf + 1) * 33 + yTall + 0];
					double start_MaxX_MinZ = this.buffer[((xHalf + 1) * 3 + zHalf + 0) * 33 + yTall + 0];
					double start_MaxX_MaxZ = this.buffer[((xHalf + 1) * 3 + zHalf + 1) * 33 + yTall + 0];
					double delta_MinX_MinZ = (this.buffer[((xHalf + 0) * 3 + zHalf + 0) * 33 + yTall + 1] - start_MinX_MinZ) * qtr;
					double delta_MinX_MaxZ = (this.buffer[((xHalf + 0) * 3 + zHalf + 1) * 33 + yTall + 1] - start_MinX_MaxZ) * qtr;
					double delta_MaxX_MinZ = (this.buffer[((xHalf + 1) * 3 + zHalf + 0) * 33 + yTall + 1] - start_MaxX_MinZ) * qtr;
					double delta_MaxX_MaxZ = (this.buffer[((xHalf + 1) * 3 + zHalf + 1) * 33 + yTall + 1] - start_MaxX_MaxZ) * qtr;
					
					for (int ySub = 0; ySub < 4; ++ySub) {
						double minZ = start_MinX_MinZ;
						double maxZ = start_MinX_MaxZ;
						double delta_X_minZ = (start_MaxX_MinZ - start_MinX_MinZ) * eighth;
						double delta_X_maxZ = (start_MaxX_MaxZ - start_MinX_MaxZ) * eighth;
						
						for (int xSub = 0; xSub < 8; ++xSub) {
							double density = minZ;
							double delZ = (maxZ - minZ) * eighth;
							
							for (int zSub = 0; zSub < 8; ++zSub) {
								int setX = xSub + xHalf * 8;
								int setY = ySub + yTall * 4;
								int setZ = zSub + zHalf * 8;
								
								setY += 48;
								
								boolean isAir = density < 0.0;
								IBlockState toSet = isAir ? AIR : STONE;
								
								if(toSet == AIR) {
									if(setY <= seaLevel - 4) {
										toSet = STONE;
									} else
									if(setY <= seaLevel - 2) {
										toSet = SAND;
									} else
									if(setY <= seaLevel) {
										toSet = WATER;
									}
								}
								
								primer.setBlockState(setX, setY, setZ, toSet);
								density += delZ;
							}
							
							minZ += delta_X_minZ;
							maxZ += delta_X_maxZ;
						}
						
						start_MinX_MinZ += delta_MinX_MinZ;
						start_MinX_MaxZ += delta_MinX_MaxZ;
						start_MaxX_MinZ += delta_MaxX_MinZ;
						start_MaxX_MaxZ += delta_MaxX_MaxZ;
					}
				}
			}
		}
		
		for(int iy = 0; iy < 48; iy++) {
			for(int ix = 0; ix < 16; ix++) {
				for(int iz = 0; iz < 16; iz++) {
					primer.setBlockState(ix, iy, iz, STONE);
				}
			}
		}
		
	}
	
	public static IBlockState getStone() {
		Block marble = Block.REGISTRY.getObject(new ResourceLocation("chisel", "marble2"));
		IProperty<Integer> property = (IProperty<Integer>) marble.getBlockState().getProperty("variation");
		return marble.getDefaultState().withProperty(property, 7);
	}
	
	public static IBlockState getSand() {
		return BOPBlocks.white_sand.getDefaultState();
	}
	
	
	
	/**
	 * Generates the chunk at the specified position, from scratch
	 */
	public Chunk generateChunk(int x, int z) {
		this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
		ChunkPrimer chunkprimer = new ChunkPrimer();
		this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
		this.setBlocksInChunk(x, z, chunkprimer);
		this.replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);
		
		Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
		byte[] abyte = chunk.getBiomeArray();
		
		for (int i = 0; i < abyte.length; ++i) {
			abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
		}
		
		chunk.generateSkylightMap();
		return chunk;
	}
	
	private float getIslandDensityValue(int chunkX, int chunkZ, int offX, int offZ) {
		float x = (float)(chunkX * 2 + offX);
		float z = (float)(chunkZ * 2 + offZ);
		float height = 50.0f - MathHelper.sqrt(x * x + z * z) * 8.0f;
		
		if (height > 80.0F) {
			height = 80.0F;
		}
		
		if (height < -100.0F) {
			height = -100.0F;
		}
		
		for (int xi = -12; xi <= 12; ++xi) {
			for (int zi = -12; zi <= 12; ++zi) {
				long absX = (long)(chunkX + xi);
				long absZ = (long)(chunkZ + zi);
				
				if (absX * absX + absZ * absZ > 10 * 10 && this.islandNoise.getValue((double)absX, (double)absZ) < -0.9) {
					float psuedoRand = (MathHelper.abs((float)absX) * 3439.0F + MathHelper.abs((float)absZ) * 147.0F) % 13.0F + 9.0F;
					x = (float)(offX - xi * 2);
					z = (float)(offZ - zi * 2);
					float newHeight = 100.0F - MathHelper.sqrt(x * x + z * z) * psuedoRand;
					
					if (newHeight > 80.0F) {
						newHeight = 80.0F;
					}
					
					if (newHeight < -100.0F) {
						newHeight = -100.0F;
					}
					
					if (newHeight > height) {
						height = newHeight;
					}
				}
			}
		}
		
		return height;
	}
	
	private double[] depthBuffer = new double[256];
	
	public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
		if (!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.world)) return;
		double d0 = 0.03125D;
		this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (double)(x * 16), (double)(z * 16), 16, 16, d0, d0, 1.0D);
		
		for (int xi = 0; xi < 16; ++xi) {
			for (int zi = 0; zi < 16; ++zi) {
				Biome biome = biomesIn[zi + xi * 16];
				biome.genTerrainBlocks(this.world, this.rand, primer, x * 16 + xi, z * 16 + zi, this.depthBuffer[zi + xi * 16]);
			}
		}
	}
	
	private double[] getDensities(double[] buffer, int chunkPosX, int chunkPosY, int chunkPosZ, int xSamples, int ySamples, int zSamples) {
		net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField event = new net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField(this, buffer, chunkPosX, chunkPosY, chunkPosZ, xSamples, ySamples, zSamples);
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) return event.getNoisefield();
		
		if (buffer == null) {
			buffer = new double[xSamples * ySamples * zSamples];
		}
		
		double d0 = 684.412D;
		//double d1 = 684.412D;
		d0 = d0 * 2.0D;
		this.sPOct1 = this.sPerlinNoise1.generateNoiseOctaves(this.sPOct1, chunkPosX, chunkPosY, chunkPosZ, xSamples, ySamples, zSamples, d0 / 80.0D, 4.277575000000001D, d0 / 80.0D);
		this.lPOct1 = this.lPerlinNoise1.generateNoiseOctaves(this.lPOct1, chunkPosX, chunkPosY, chunkPosZ, xSamples, ySamples, zSamples, d0, 684.412D, d0);
		this.lPOct2 = this.lPerlinNoise2.generateNoiseOctaves(this.lPOct2, chunkPosX, chunkPosY, chunkPosZ, xSamples, ySamples, zSamples, d0, 684.412D, d0);
		int chunkX = chunkPosX / 2;
		int chunkZ = chunkPosZ / 2;
		int bufferPos = 0;
		
		for (int xi = 0; xi < xSamples; ++xi) {
			for (int zi = 0; zi < zSamples; ++zi) {
				float islandDensity = this.getIslandDensityValue(chunkX, chunkZ, xi, zi);
				
				for (int yi = 0; yi < ySamples; ++yi) {
					double lOct1 = this.lPOct1[bufferPos] / 512.0D;
					double lOct2 = this.lPOct2[bufferPos] / 512.0D;
					double sOct1 = (this.sPOct1[bufferPos] / 10.0D + 1.0D) / 2.0D;
					double result;
					
					if (sOct1 < 0.0D) {
						result = lOct1;
					}
					else if (sOct1 > 1.0D) {
						result = lOct2;
					}
					else {
						result = lOct1 + (lOct2 - lOct1) * sOct1;
					}
					
					result = result - 8.0D;
					result = result + (double)islandDensity;
					
					
					int limit = 2;
					
					//Limit for top side of islands
					if (yi > ySamples / 2 - limit) {
						double depthMixer = (double)((float)(yi - (ySamples / 2 - limit)) / 64.0F);
						depthMixer = MathHelper.clamp(depthMixer, 0.0D, 1.0D);
						result = mix(result, -3000.0, depthMixer);
					}
					
					//Limit for bottom side of islands
					limit = 24;
					
					if (yi < limit) {
						double depthMixer = (limit - yi) / (limit - 1.0);
						result = mix(result, result - 40.0, depthMixer);
					}
					
					//System.out.println(islandHeight + " -> " + result);
					
					buffer[bufferPos] = result;
					++bufferPos;
				}
			}
		}
		
		return buffer;
	}
	
	double mix(double atZero, double atOne, double mixer) {
		return (atZero * (1.0 - mixer)) + (atOne * mixer);
	}
	
	/**
	 * Generate initial structures in this chunk, e.g. mineshafts, temples, lakes, and dungeons
	 */
	public void populate(int x, int z) {
		BlockFalling.fallInstantly = true;
		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);
		BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);
		
		this.world.getBiome(blockpos.add(16, 0, 16)).decorate(this.world, this.world.rand, blockpos);
		
		long distSquared = (long)x * (long)x + (long)z * (long)z;
		
		if (distSquared > 64 * 64) { //Must be 64 chunks(1024 blocks) from the world origin(0, 0) to run this populator
			
			populateEndIslands(x, z);
			
			if (this.getIslandDensityValue(x, z, 1, 1) > 40.0F) {
				populateChorusFlowers(x, z);
				populateEndGateways(x, z);
			}
		}
		
		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
		BlockFalling.fallInstantly = false;
	}
	
	private void populateEndIslands(int chunkX, int chunkZ) {
		BlockPos blockpos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
		
		float islandHeight = this.getIslandDensityValue(chunkX, chunkZ, 1, 1);
		
		if (islandHeight < -20.0F && this.rand.nextInt(14) == 0) {
			this.endIslands.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, 55 + this.rand.nextInt(16), this.rand.nextInt(16) + 8));
			
			if (this.rand.nextInt(4) == 0) {
				this.endIslands.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, 55 + this.rand.nextInt(16), this.rand.nextInt(16) + 8));
			}
		}
	}
	
	private void populateChorusFlowers(int chunkX, int chunkZ) {
		BlockPos blockpos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
		
		int j = this.rand.nextInt(5);
		
		for (int k = 0; k < j; ++k) {
			int l = this.rand.nextInt(16) + 8;
			int i1 = this.rand.nextInt(16) + 8;
			int j1 = this.world.getHeight(blockpos.add(l, 0, i1)).getY();
			
			if (j1 > 0) {
				int k1 = j1 - 1;
				
				if (this.world.isAirBlock(blockpos.add(l, k1 + 1, i1)) && this.world.getBlockState(blockpos.add(l, k1, i1)).getBlock() == Blocks.END_STONE) {
					BlockChorusFlower.generatePlant(this.world, blockpos.add(l, k1 + 1, i1), this.rand, 8);
				}
			}
		}
	}
	
	private void populateEndGateways(int chunkX, int chunkZ) {
		
		if (this.rand.nextInt(700) == 0) {
			BlockPos blockpos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
			
			int l1 = this.rand.nextInt(16) + 8;
			int i2 = this.rand.nextInt(16) + 8;
			int j2 = this.world.getHeight(blockpos.add(l1, 0, i2)).getY();
			
			if (j2 > 0) {
				int k2 = j2 + 3 + this.rand.nextInt(7);
				BlockPos blockpos1 = blockpos.add(l1, k2, i2);
				(new WorldGenEndGateway()).generate(this.world, this.rand, blockpos1);
				TileEntity tileentity = this.world.getTileEntity(blockpos1);
				
				if (tileentity instanceof TileEntityEndGateway) {
					TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)tileentity;
					tileentityendgateway.setExactPosition(this.spawnPoint);
				}
			}
		}
	}
	
	/**
	 * Called to generate additional structures after initial worldgen, used by ocean monuments
	 */
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}
	
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return this.world.getBiome(pos).getSpawnableList(creatureType);
	}
	
	@Nullable
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
		return null;
	}
	
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		return false;
	}
	
	/**
	 * Recreates data about structures intersecting given chunk (used for example by getPossibleCreatures), without
	 * placing any blocks. When called for the first time before any chunk is generated - also initializes the internal
	 * state needed by getPossibleCreatures.
	 */
	public void recreateStructures(Chunk chunkIn, int x, int z) {
	}
	
}
