package com.ferreusveritas.stargarden.features.skyhook;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockSkyHook extends ItemBlock {

	public ItemBlockSkyHook(Block block) {
		super(block);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return EnumActionResult.FAIL;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		if(!world.isRemote) {
			BlockPos pos = player.getPosition().down();
			IBlockState existingBlockState = world.getBlockState(pos);
			ItemStack itemstack = player.getHeldItem(hand);

			if (!itemstack.isEmpty() && world.isBlockModifiable(player, pos) && existingBlockState.getBlock().isReplaceable(world, pos)) {
				int meta = getMetadata(itemstack.getMetadata());
				IBlockState newBlockState = block.getStateFromMeta(meta);

				if (placeBlockAt(itemstack, player, world, pos, EnumFacing.UP, 0.0f, 0.0f, 0.0f, newBlockState)) {
					newBlockState = world.getBlockState(pos);
					SoundType soundtype = newBlockState.getBlock().getSoundType(newBlockState, world, pos, player);
					world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					itemstack.shrink(1);
				}

				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
			}	
			else {
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(hand));
			}

		}

		return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(hand));
	}

}
