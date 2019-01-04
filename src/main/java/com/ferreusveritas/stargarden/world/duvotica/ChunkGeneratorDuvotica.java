package com.ferreusveritas.stargarden.world.duvotica;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockChorusFlower;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorSimplex;
import net.minecraft.world.gen.feature.WorldGenEndGateway;
import net.minecraft.world.gen.feature.WorldGenEndIsland;

public class ChunkGeneratorDuvotica implements IChunkGenerator {
	
	/** RNG. */
	private final Random rand;
	protected static final IBlockState END_STONE = Blocks.END_STONE.getDefaultState();
	protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
	private NoiseGeneratorOctaves lperlinNoise1;
	private NoiseGeneratorOctaves lperlinNoise2;
	private NoiseGeneratorOctaves perlinNoise1;
	/** A NoiseGeneratorOctaves used in generating terrain */
	public NoiseGeneratorOctaves noiseGen5;
	/** A NoiseGeneratorOctaves used in generating terrain */
	public NoiseGeneratorOctaves noiseGen6;
	/** Reference to the World object. */
	private final World world;
	/** are map structures going to be generated (e.g. strongholds) */
	private final boolean mapFeaturesEnabled;
	private final BlockPos spawnPoint;
	private NoiseGeneratorSimplex islandNoise;
	private double[] buffer;
	/** The biomes that are used to generate the chunk */
	private Biome[] biomesForGeneration;
	double[] pnr;
	double[] ar;
	double[] br;
	private final WorldGenEndIsland endIslands = new WorldGenEndIsland();
	// temporary variables used during event handling
	private int chunkX = 0;
	private int chunkZ = 0;
	
	public ChunkGeneratorDuvotica(World world, boolean mapFeaturesEnabled, long rand, BlockPos spawnPoint) {
		this.world = world;
		this.mapFeaturesEnabled = mapFeaturesEnabled;
		this.spawnPoint = spawnPoint;
		this.rand = new Random(rand);
		this.lperlinNoise1 = new NoiseGeneratorOctaves(this.rand, 16);
		this.lperlinNoise2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.perlinNoise1 = new NoiseGeneratorOctaves(this.rand, 8);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
		this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
		this.islandNoise = new NoiseGeneratorSimplex(this.rand);
		
		net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextEnd ctx =
				new net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextEnd(lperlinNoise1, lperlinNoise2, perlinNoise1, noiseGen5, noiseGen6, islandNoise);
		ctx = net.minecraftforge.event.terraingen.TerrainGen.getModdedNoiseGenerators(world, this.rand, ctx);
		this.lperlinNoise1 = ctx.getLPerlin1();
		this.lperlinNoise2 = ctx.getLPerlin2();
		this.perlinNoise1 = ctx.getPerlin();
		this.noiseGen5 = ctx.getDepth();
		this.noiseGen6 = ctx.getScale();
		this.islandNoise = ctx.getIsland();
	}
	
	/**
	 * Generates a bare-bones chunk of nothing but stone or ocean blocks, formed, but featureless.
	 */
	public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
		int i = 2;
		int xSamples = 3;
		int ySamples = 33;
		int zSamples = 3;
		this.buffer = this.getHeights(this.buffer, x * 2, 0, z * 2, xSamples, ySamples, zSamples);

		final double qtr = 0.25D;
		final double eighth = 0.125D;
		
		for (int xHalf = 0; xHalf < 2; ++xHalf) {
			for (int zHalf = 0; zHalf < 2; ++zHalf) {
				for (int yTall = 0; yTall < 32; ++yTall) {
					
					//Here we sample the 8 corners of a cube in the noise space.  This will later be used as interpolation points
					double sample000 = this.buffer[((xHalf + 0) * 3 + zHalf + 0) * 33 + yTall + 0];
					double sample001 = this.buffer[((xHalf + 0) * 3 + zHalf + 1) * 33 + yTall + 0];
					double sample100 = this.buffer[((xHalf + 1) * 3 + zHalf + 0) * 33 + yTall + 0];
					double sample101 = this.buffer[((xHalf + 1) * 3 + zHalf + 1) * 33 + yTall + 0];
					double sample010 = (this.buffer[((xHalf + 0) * 3 + zHalf + 0) * 33 + yTall + 1] - sample000) * qtr;
					double sample011 = (this.buffer[((xHalf + 0) * 3 + zHalf + 1) * 33 + yTall + 1] - sample001) * qtr;
					double sample110 = (this.buffer[((xHalf + 1) * 3 + zHalf + 0) * 33 + yTall + 1] - sample100) * qtr;
					double sample111 = (this.buffer[((xHalf + 1) * 3 + zHalf + 1) * 33 + yTall + 1] - sample101) * qtr;
					
					for (int ySub = 0; ySub < 4; ++ySub) {
						double minZ = sample000;
						double maxZ = sample001;
						double delXminZ = (sample100 - sample000) * eighth;
						double delXmaxZ = (sample101 - sample001) * eighth;
						
						for (int xSub = 0; xSub < 8; ++xSub) {
							double density = minZ;
							double delZ = (maxZ - minZ) * eighth;
							
							for (int zSub = 0; zSub < 8; ++zSub) {
								int setX = xSub + xHalf * 8;
								int setY = ySub + yTall * 4;
								int setZ = zSub + zHalf * 8;
								primer.setBlockState(setX, setY, setZ, density > 0.0D ? END_STONE : AIR);
								density += delZ;
							}
							
							minZ += delXminZ;
							maxZ += delXmaxZ;
						}
						
						sample000 += sample010;
						sample001 += sample011;
						sample100 += sample110;
						sample101 += sample111;
					}
				}
			}
		}
	}
	
	public void buildSurfaces(ChunkPrimer primer) {
		if (!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, this.chunkX, this.chunkZ, primer, this.world)) return;
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				int k = 1;
				int l = -1;
				IBlockState iblockstate = END_STONE;
				IBlockState iblockstate1 = END_STONE;
				
				for (int i1 = 127; i1 >= 0; --i1) {
					IBlockState iblockstate2 = primer.getBlockState(i, i1, j);
					
					if (iblockstate2.getMaterial() == Material.AIR) {
						l = -1;
					}
					else if (iblockstate2.getBlock() == Blocks.STONE) {
						if (l == -1) {
							l = 1;
							
							if (i1 >= 0) {
								primer.setBlockState(i, i1, j, iblockstate);
							}
							else {
								primer.setBlockState(i, i1, j, iblockstate1);
							}
						}
						else if (l > 0) {
							--l;
							primer.setBlockState(i, i1, j, iblockstate1);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Generates the chunk at the specified position, from scratch
	 */
	public Chunk generateChunk(int x, int z) {
		this.chunkX = x; this.chunkZ = z;
		this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
		ChunkPrimer chunkprimer = new ChunkPrimer();
		this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
		this.setBlocksInChunk(x, z, chunkprimer);
		this.buildSurfaces(chunkprimer);
		
		Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
		byte[] abyte = chunk.getBiomeArray();
		
		for (int i = 0; i < abyte.length; ++i) {
			abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
		}
		
		chunk.generateSkylightMap();
		return chunk;
	}
	
	private float getIslandHeightValue(int p_185960_1_, int p_185960_2_, int p_185960_3_, int p_185960_4_) {
		float f = (float)(p_185960_1_ * 2 + p_185960_3_);
		float f1 = (float)(p_185960_2_ * 2 + p_185960_4_);
		float f2 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * 8.0F;
		
		if (f2 > 80.0F)
		{
			f2 = 80.0F;
		}
		
		if (f2 < -100.0F)
		{
			f2 = -100.0F;
		}
		
		for (int i = -12; i <= 12; ++i) {
			for (int j = -12; j <= 12; ++j) {
				long k = (long)(p_185960_1_ + i);
				long l = (long)(p_185960_2_ + j);
				
				if (k * k + l * l > 4096L && this.islandNoise.getValue((double)k, (double)l) < -0.8999999761581421D) {
					float f3 = (MathHelper.abs((float)k) * 3439.0F + MathHelper.abs((float)l) * 147.0F) % 13.0F + 9.0F;
					f = (float)(p_185960_3_ - i * 2);
					f1 = (float)(p_185960_4_ - j * 2);
					float f4 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * f3;
					
					if (f4 > 80.0F) {
						f4 = 80.0F;
					}
					
					if (f4 < -100.0F) {
						f4 = -100.0F;
					}
					
					if (f4 > f2) {
						f2 = f4;
					}
				}
			}
		}
		
		return f2;
	}
	
	public boolean isIslandChunk(int p_185961_1_, int p_185961_2_) {
		return (long)p_185961_1_ * (long)p_185961_1_ + (long)p_185961_2_ * (long)p_185961_2_ > 4096L && this.getIslandHeightValue(p_185961_1_, p_185961_2_, 1, 1) >= 0.0F;
	}
	
	private double[] getHeights(double[] p_185963_1_, int p_185963_2_, int p_185963_3_, int p_185963_4_, int p_185963_5_, int p_185963_6_, int p_185963_7_) {
		net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField event = new net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField(this, p_185963_1_, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_);
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) return event.getNoisefield();
		
		if (p_185963_1_ == null) {
			p_185963_1_ = new double[p_185963_5_ * p_185963_6_ * p_185963_7_];
		}
		
		double d0 = 684.412D;
		double d1 = 684.412D;
		d0 = d0 * 2.0D;
		this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0 / 80.0D, 4.277575000000001D, d0 / 80.0D);
		this.ar = this.lperlinNoise1.generateNoiseOctaves(this.ar, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, 684.412D, d0);
		this.br = this.lperlinNoise2.generateNoiseOctaves(this.br, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, 684.412D, d0);
		int i = p_185963_2_ / 2;
		int j = p_185963_4_ / 2;
		int k = 0;
		
		for (int l = 0; l < p_185963_5_; ++l) {
			for (int i1 = 0; i1 < p_185963_7_; ++i1) {
				float f = this.getIslandHeightValue(i, j, l, i1);
				
				for (int j1 = 0; j1 < p_185963_6_; ++j1) {
					double d2 = this.ar[k] / 512.0D;
					double d3 = this.br[k] / 512.0D;
					double d5 = (this.pnr[k] / 10.0D + 1.0D) / 2.0D;
					double d4;
					
					if (d5 < 0.0D) {
						d4 = d2;
					}
					else if (d5 > 1.0D) {
						d4 = d3;
					}
					else {
						d4 = d2 + (d3 - d2) * d5;
					}
					
					d4 = d4 - 8.0D;
					d4 = d4 + (double)f;
					int k1 = 2;
					
					if (j1 > p_185963_6_ / 2 - k1) {
						double d6 = (double)((float)(j1 - (p_185963_6_ / 2 - k1)) / 64.0F);
						d6 = MathHelper.clamp(d6, 0.0D, 1.0D);
						d4 = d4 * (1.0D - d6) + -3000.0D * d6;
					}
					
					k1 = 8;
					
					if (j1 < k1) {
						double d7 = (double)((float)(k1 - j1) / ((float)k1 - 1.0F));
						d4 = d4 * (1.0D - d7) + -30.0D * d7;
					}
					
					p_185963_1_[k] = d4;
					++k;
				}
			}
		}
		
		return p_185963_1_;
	}
	
	/**
	 * Generate initial structures in this chunk, e.g. mineshafts, temples, lakes, and dungeons
	 */
	public void populate(int x, int z) {
		BlockFalling.fallInstantly = true;
		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);
		BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);
		
		this.world.getBiome(blockpos.add(16, 0, 16)).decorate(this.world, this.world.rand, blockpos);
		long i = (long)x * (long)x + (long)z * (long)z;
		
		if (i > 4096L) {
			float f = this.getIslandHeightValue(x, z, 1, 1);
			
			if (f < -20.0F && this.rand.nextInt(14) == 0) {
				this.endIslands.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, 55 + this.rand.nextInt(16), this.rand.nextInt(16) + 8));
				
				if (this.rand.nextInt(4) == 0) {
					this.endIslands.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, 55 + this.rand.nextInt(16), this.rand.nextInt(16) + 8));
				}
			}
			
			if (this.getIslandHeightValue(x, z, 1, 1) > 40.0F) {
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
				
				if (this.rand.nextInt(700) == 0) {
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
		}
		
		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
		BlockFalling.fallInstantly = false;
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
