package com.ferreusveritas.stargarden.world.duvotica;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import biomesoplenty.api.block.BOPBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
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
	//private final BlockPos spawnPoint;
	private NoiseGeneratorSimplex islandNoise;
	private double[] buffer;
	/** The biomes that are used to generate the chunk */
	private Biome[] biomesForGeneration;
	double[] sPOct1;
	double[] lPOct1;
	double[] lPOct2;
	//private final WorldGenEndIsland endIslands = new WorldGenEndIsland();
	
	private final int seaLevel = 63;
	private final int seaLevel2 = 31;
	
	public ChunkGeneratorDuvotica(World world, long rand) {
		this.world = world;
		//this.mapFeaturesEnabled = mapFeaturesEnabled;
		//this.spawnPoint = spawnPoint;
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
		
		for (int xHalf = 0; xHalf < xSamples - 1; ++xHalf) {
			for (int zHalf = 0; zHalf < zSamples - 1; ++zHalf) {
				for (int yTall = 0; yTall < ySamples - 1; ++yTall) {
					
					//Here we sample the 8 corners of a cube in the noise space.  This will later be used as interpolation points
					double start_MinX_MinZ = this.buffer[((xHalf + 0) * 3 + zHalf + 0) * ySamples + yTall + 0];
					double start_MinX_MaxZ = this.buffer[((xHalf + 0) * 3 + zHalf + 1) * ySamples + yTall + 0];
					double start_MaxX_MinZ = this.buffer[((xHalf + 1) * 3 + zHalf + 0) * ySamples + yTall + 0];
					double start_MaxX_MaxZ = this.buffer[((xHalf + 1) * 3 + zHalf + 1) * ySamples + yTall + 0];
					double delta_MinX_MinZ = (this.buffer[((xHalf + 0) * 3 + zHalf + 0) * ySamples + yTall + 1] - start_MinX_MinZ) * qtr;
					double delta_MinX_MaxZ = (this.buffer[((xHalf + 0) * 3 + zHalf + 1) * ySamples + yTall + 1] - start_MinX_MaxZ) * qtr;
					double delta_MaxX_MinZ = (this.buffer[((xHalf + 1) * 3 + zHalf + 0) * ySamples + yTall + 1] - start_MaxX_MinZ) * qtr;
					double delta_MaxX_MaxZ = (this.buffer[((xHalf + 1) * 3 + zHalf + 1) * ySamples + yTall + 1] - start_MaxX_MaxZ) * qtr;
					
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
		
		xSamples = 3;
		ySamples = 15;
		zSamples = 3;
		this.buffer = this.getSubSeaDensities(this.buffer, chunkPosX * 2, 0, chunkPosZ * 2, xSamples, ySamples, zSamples);
		
		for (int xHalf = 0; xHalf < xSamples - 1; ++xHalf) {
			for (int zHalf = 0; zHalf < zSamples - 1; ++zHalf) {
				for (int yTall = 0; yTall < ySamples - 1; ++yTall) {
					
					//Here we sample the 8 corners of a cube in the noise space.  This will later be used as interpolation points
					double start_MinX_MinZ = this.buffer[((xHalf + 0) * 3 + zHalf + 0) * ySamples + yTall + 0];
					double start_MinX_MaxZ = this.buffer[((xHalf + 0) * 3 + zHalf + 1) * ySamples + yTall + 0];
					double start_MaxX_MinZ = this.buffer[((xHalf + 1) * 3 + zHalf + 0) * ySamples + yTall + 0];
					double start_MaxX_MaxZ = this.buffer[((xHalf + 1) * 3 + zHalf + 1) * ySamples + yTall + 0];
					double delta_MinX_MinZ = (this.buffer[((xHalf + 0) * 3 + zHalf + 0) * ySamples + yTall + 1] - start_MinX_MinZ) * qtr;
					double delta_MinX_MaxZ = (this.buffer[((xHalf + 0) * 3 + zHalf + 1) * ySamples + yTall + 1] - start_MinX_MaxZ) * qtr;
					double delta_MaxX_MinZ = (this.buffer[((xHalf + 1) * 3 + zHalf + 0) * ySamples + yTall + 1] - start_MaxX_MinZ) * qtr;
					double delta_MaxX_MaxZ = (this.buffer[((xHalf + 1) * 3 + zHalf + 1) * ySamples + yTall + 1] - start_MaxX_MaxZ) * qtr;
					
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
																
								boolean isAir = density < 0.0;
								IBlockState toSet = isAir ? AIR : STONE;
								
								if(toSet == AIR) {
									if(setY <= seaLevel2 ) {
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
	@Override
	public Chunk generateChunk(int x, int z) {
		this.rand.setSeed(x * 341873128712L + z * 132897987541L);
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
		float x = chunkX * 2 + offX;
		float z = chunkZ * 2 + offZ;
		float height = 50.0f - MathHelper.sqrt(x * x + z * z) * 8.0f;
		
		if (height > 80.0F) {
			height = 80.0F;
		}
		
		if (height < -100.0F) {
			height = -100.0F;
		}
		
		for (int xi = -12; xi <= 12; ++xi) {
			for (int zi = -12; zi <= 12; ++zi) {
				long absX = chunkX + xi;
				long absZ = chunkZ + zi;
				
				if (absX * absX + absZ * absZ > 10 * 10 && this.islandNoise.getValue(absX, absZ) < -0.9) {
					float psuedoRand = (MathHelper.abs(absX) * 3439.0F + MathHelper.abs(absZ) * 147.0F) % 13.0F + 9.0F;
					x = offX - xi * 2;
					z = offZ - zi * 2;
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
		this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, x * 16, z * 16, 16, 16, d0, d0, 1.0D);
		
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
					result = result + islandDensity;
					
					
					int limit = 2;
					
					//Limit for top side of islands
					if (yi > ySamples / 2 - limit) {
						double depthMixer = (yi - (ySamples / 2 - limit)) / 64.0F;
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
	
	private double[] getSubSeaDensities(double[] buffer, int chunkPosX, int chunkPosY, int chunkPosZ, int xSamples, int ySamples, int zSamples) {
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
		this.lPOct1 = this.lPerlinNoise1.generateNoiseOctaves(this.lPOct1, chunkPosX, chunkPosY, chunkPosZ, xSamples, ySamples, zSamples, d0 * 8, 684.412D, d0 * 8);
		this.lPOct2 = this.lPerlinNoise2.generateNoiseOctaves(this.lPOct2, chunkPosX, chunkPosY, chunkPosZ, xSamples, ySamples, zSamples, d0 * 8, 684.412D, d0 * 8);
		int chunkX = chunkPosX / 2;
		int chunkZ = chunkPosZ / 2;
		int bufferPos = 0;
		
		for (int xi = 0; xi < xSamples; ++xi) {
			for (int zi = 0; zi < zSamples; ++zi) {
				float islandDensity = this.getIslandDensityValue(chunkX, chunkZ, xi, zi);
				
				for (int yi = 0; yi < ySamples; ++yi) {
					double lOct1 = this.lPOct1[bufferPos] / 128.0D;
					double lOct2 = this.lPOct2[bufferPos] / 128.0D;
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
					result = result + islandDensity;
					
					int limit = 4;
					
					//Limit for top side of islands
					if (yi > ySamples - limit) {
						double depthMixer = (yi - (ySamples - limit)) / 32.0F;
						depthMixer = MathHelper.clamp(depthMixer, 0.0D, 1.0D);
						result = mix(result, 3000.0, depthMixer);
					}
					
					//Limit for bottom side of islands
					limit = 11;
					
					if (yi < limit) {
						double depthMixer = (limit - yi) / (limit - 1.0);
						result = mix(result, result + 500.0, depthMixer);
					}
					
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
	@Override
	public void populate(int x, int z) {
		BlockFalling.fallInstantly = true;
		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);
		BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);
		
		this.world.getBiome(blockpos.add(16, 0, 16)).decorate(this.world, this.world.rand, blockpos);
		populateHoles(x, z);
		
		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
		BlockFalling.fallInstantly = false;
	}
	
	private void populateHoles(int chunkX, int chunkZ) {
		BlockPos blockpos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
		
		float islandHeight = this.getIslandDensityValue(chunkX, chunkZ, 1, 1);
		
		if (islandHeight < -90.0F && this.rand.nextInt(128) == 0) {
			BlockPos pos = blockpos.add(this.rand.nextInt(16) + 8, 42, this.rand.nextInt(16) + 8);
			
			int size = this.rand.nextInt(3) + 4;
			
			for( int z = -size - 1; z <= size + 1; z++ ) {
				for( int x = -size - 1; x <= size + 1; x++ ) {
					
					if(x * x + z * z < (size + 1) * (size + 1)) {
						this.world.setBlockState(pos.add(x, 19, z), Blocks.WATER.getDefaultState());
					}
					
					if(x * x + z * z < size * size) {
						for(int y = 0; y < 30; y++) {
							this.world.setBlockToAir(pos.add(x, y, z));
							
							IBlockState state = this.world.getBlockState(pos.add(x, -y, z));
							if(state.getMaterial() == Material.GROUND || state.getMaterial() == Material.GRASS) {
								this.world.setBlockState(pos.add(x, -y, z), Blocks.STONE.getDefaultState());
							}
						}
					}
					
				}
				
			}
			
			
		}

	}
	
	/**
	 * Called to generate additional structures after initial worldgen, used by ocean monuments
	 */
	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}
	
	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		
        Biome biome = this.world.getBiome(pos);
		
		if(pos.getY() > seaLevel) {
			return biome.getSpawnableList(creatureType);
		} else {
			if(creatureType == EnumCreatureType.WATER_CREATURE) {
		        return biome.getSpawnableList(creatureType);
			}
		}
		
		return new ArrayList<>();
	}
	
	@Override
	@Nullable
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
		return null;
	}
	
	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		return false;
	}
	
	/**
	 * Recreates data about structures intersecting given chunk (used for example by getPossibleCreatures), without
	 * placing any blocks. When called for the first time before any chunk is generated - also initializes the internal
	 * state needed by getPossibleCreatures.
	 */
	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
	}
	
}
