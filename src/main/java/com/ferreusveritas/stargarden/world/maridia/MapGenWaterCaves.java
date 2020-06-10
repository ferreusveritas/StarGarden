package com.ferreusveritas.stargarden.world.maridia;

import java.util.Random;

import com.google.common.base.MoreObjects;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

public class MapGenWaterCaves extends MapGenBase {
	protected static final IBlockState BLK_AIR = Blocks.AIR.getDefaultState();
	protected static final IBlockState BLK_WATER = Blocks.WATER.getDefaultState();


	protected void addRoom(long seed, int originalX, int originalZ, ChunkPrimer primer, double xPoke, double yPoke, double zPoke) {
		this.addTunnel(seed, originalX, originalZ, primer, xPoke, yPoke, zPoke, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
	}

	protected void addTunnel(long seed, int originalX, int originalZ, ChunkPrimer primer, double xPoke, double yPoke, double zPoke, float rand_0_to_3, float angle, float rand_nq_to_pq, int param_i_1, int param_i_2, double param_d_3) {
		
		double xTaco = (double)(originalX * 16 + 8);
		double zTaco = (double)(originalZ * 16 + 8);
		float f = 0.0F;
		float f1 = 0.0F;
		Random random = new Random(seed);

		if (param_i_2 <= 0) {
			int i = this.range * 16 - 16;
			param_i_2 = i - random.nextInt(i / 4);
		}

		boolean flag2 = false;

		if (param_i_1 == -1) {
			param_i_1 = param_i_2 / 2;
			flag2 = true;
		}

		int j = random.nextInt(param_i_2 / 2) + param_i_2 / 4;

		for (boolean flag = random.nextInt(6) == 0; param_i_1 < param_i_2; ++param_i_1) {
			double d2 = 1.5D + (double)(MathHelper.sin((float)param_i_1 * (float)Math.PI / (float)param_i_2) * rand_0_to_3);
			double d3 = d2 * param_d_3;
			float f2 = MathHelper.cos(rand_nq_to_pq);
			float f3 = MathHelper.sin(rand_nq_to_pq);
			xPoke += (double)(MathHelper.cos(angle) * f2);
			yPoke += (double)f3;
			zPoke += (double)(MathHelper.sin(angle) * f2);

			if (flag) {
				rand_nq_to_pq = rand_nq_to_pq * 0.92F;
			}
			else {
				rand_nq_to_pq = rand_nq_to_pq * 0.7F;
			}

			rand_nq_to_pq = rand_nq_to_pq + f1 * 0.1F;
			angle += f * 0.1F;
			f1 = f1 * 0.9F;
			f = f * 0.75F;
			f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

			if (!flag2 && param_i_1 == j && rand_0_to_3 > 1.0F && param_i_2 > 0) {
				this.addTunnel(random.nextLong(), originalX, originalZ, primer, xPoke, yPoke, zPoke, random.nextFloat() * 0.5F + 0.5F, angle - ((float)Math.PI / 2F), rand_nq_to_pq / 3.0F, param_i_1, param_i_2, 1.0D);
				this.addTunnel(random.nextLong(), originalX, originalZ, primer, xPoke, yPoke, zPoke, random.nextFloat() * 0.5F + 0.5F, angle + ((float)Math.PI / 2F), rand_nq_to_pq / 3.0F, param_i_1, param_i_2, 1.0D);
				return;
			}

			if (flag2 || random.nextInt(4) != 0) {
				double xDog = xPoke - xTaco;
				double zDog = zPoke - zTaco;
				double d6 = (double)(param_i_2 - param_i_1);
				double d7 = (double)(rand_0_to_3 + 2.0F + 16.0F);

				if (xDog * xDog + zDog * zDog - d6 * d6 > d7 * d7) {
					return;
				}

				if (xPoke >= xTaco - 16.0D - d2 * 2.0D && zPoke >= zTaco - 16.0D - d2 * 2.0D && xPoke <= xTaco + 16.0D + d2 * 2.0D && zPoke <= zTaco + 16.0D + d2 * 2.0D) {
					int minX = MathHelper.floor(xPoke - d2) - originalX * 16 - 1;
					int maxX = MathHelper.floor(xPoke + d2) - originalX * 16 + 1;
					int yMin = MathHelper.floor(yPoke - d3) - 1;
					int yMax = MathHelper.floor(yPoke + d3) + 1;
					int zMin = MathHelper.floor(zPoke - d2) - originalZ * 16 - 1;
					int zMax = MathHelper.floor(zPoke + d2) - originalZ * 16 + 1;

					if (minX < 0) {
						minX = 0;
					}

					if (maxX > 16) {
						maxX = 16;
					}

					if (yMin < 1) {
						yMin = 1;
					}

					if (yMax > 248) {
						yMax = 248;
					}

					if (zMin < 0) {
						zMin = 0;
					}

					if (zMax > 16) {
						zMax = 16;
					}

					boolean flag3 = false;

					for (int xi = minX; !flag3 && xi < maxX; ++xi) {
						for (int zi = zMin; !flag3 && zi < zMax; ++zi) {
							for (int yi = yMax + 1; !flag3 && yi >= yMin - 1; --yi) {
								if (yi >= 0 && yi < 256) {
									/*if (isOceanBlock(primer, xi, yi, zi, originalX, originalZ)) {
										flag3 = true;
									}*/

									if (yi != yMin - 1 && xi != minX && xi != maxX - 1 && zi != zMin && zi != zMax - 1) {
										yi = yMin;
									}
								}
							}
						}
					}

					if (!flag3) {
						//BlockPos.MutableBlockPos mutableBlockpos = new BlockPos.MutableBlockPos();

						for (int xi = minX; xi < maxX; ++xi) {
							double xMys = ((double)(xi + originalX * 16) + 0.5D - xPoke) / d2;

							for (int zi = zMin; zi < zMax; ++zi) {
								double zMys = ((double)(zi + originalZ * 16) + 0.5D - zPoke) / d2;

								if (xMys * xMys + zMys * zMys < 2.0D) {
									for (int yi = yMax; yi > yMin; --yi) {
										double yMys = ((double)(yi - 1) + 0.5D - yPoke) / d3;

										if (yMys > -0.7D && xMys * xMys + yMys * yMys + zMys * zMys < 2.0D) {
											IBlockState currentState = primer.getBlockState(xi, yi, zi);
											IBlockState aboveState = (IBlockState)MoreObjects.firstNonNull(primer.getBlockState(xi, yi + 1, zi), BLK_AIR);

											digBlock(primer, xi, yi, zi, originalX, originalZ, false, currentState, aboveState);
										}
									}
								}
							}
						}

						if (flag2) {
							break;
						}
					}
				}
			}
		}
	}

	protected boolean canReplaceBlock(IBlockState currentState, IBlockState aboveState) {
		return currentState.getBlock() == Blocks.STONE;// || currentState.getMaterial() == Material.SAND;
	}

	/**
	 * Recursively called by generate()
	 */
	 protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int originalX, int originalZ, ChunkPrimer chunkPrimerIn) {
		int numIterations = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(15) + 1) + 1); //Range 0 to 14 with heavy curve
		
		if (this.rand.nextInt(7) != 0) {
			numIterations = 0;
		}
		
		for (int iter = 0; iter < numIterations; ++iter) {
			double xPoke = (double)(chunkX * 16 + this.rand.nextInt(16));
			double yPoke = (double)this.rand.nextInt(this.rand.nextInt(120) + 8);
			double zPoke = (double)(chunkZ * 16 + this.rand.nextInt(16));
			int k = 1;
			
			if (this.rand.nextInt(4) == 0) {
				this.addRoom(this.rand.nextLong(), originalX, originalZ, chunkPrimerIn, xPoke, yPoke, zPoke);
				k += this.rand.nextInt(4);
			}
			
			for (int l = 0; l < k; ++l) {
				float angle = this.rand.nextFloat() * ((float)Math.PI * 2F);
				float rand_nq_to_pq = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F; //Range -0.125 to +0.125
				float rand_0_to_3 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat(); //Range 0.0 to +3.0
				
				if (this.rand.nextInt(10) == 0) {
					rand_0_to_3 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F; // Range +1.0 to +10.0 with heavy curve  
				}
				
				this.addTunnel(this.rand.nextLong(), originalX, originalZ, chunkPrimerIn, xPoke, yPoke, zPoke, rand_0_to_3, angle, rand_nq_to_pq, 0, 0, 1.0D);
			}
		}
	}

	protected boolean isOceanBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ) {
		net.minecraft.block.Block block = data.getBlockState(x, y, z).getBlock();
		return block== Blocks.FLOWING_WATER || block == Blocks.WATER;
	}

	/**
	 * Digs out the current block, default implementation removes stone, filler, and top block
	 * Sets the block to lava if y is less then 10, and air otherwise.
	 * If setting to air, it also checks to see if we've broken the surface and if so
	 * tries to make the floor the biome's top block
	 *
	 * @param data Block data array
	 * @param index Pre-calculated index into block data
	 * @param x local X position
	 * @param y local Y position
	 * @param z local Z position
	 * @param chunkX Chunk X position
	 * @param chunkZ Chunk Y position
	 * @param foundTop True if we've encountered the biome's top block. Ideally if we've broken the surface.
	 */
	protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop, IBlockState state, IBlockState up) {

		if (this.canReplaceBlock(state, up)) {
			data.setBlockState(x, y, z, BLK_WATER);
		}
	}
	
}