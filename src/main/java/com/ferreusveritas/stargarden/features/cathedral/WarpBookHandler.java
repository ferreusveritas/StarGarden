package com.ferreusveritas.stargarden.features.cathedral;

import com.ferreusveritas.cathedral.features.lectern.IBookReadHandler;
import com.ferreusveritas.stargarden.StarGarden;
import com.ferreusveritas.stargarden.features.Cathedral;
import com.ferreusveritas.warpbook.WarpBook;
import com.ferreusveritas.warpbook.item.WarpBookItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class WarpBookHandler implements IBookReadHandler {

	@Override
	public boolean isReadable(ItemStack itemStack) {
		return itemStack.getItem() instanceof WarpBookItem;
	}

	@Override
	public void read(EntityPlayer player, ItemStack bookStack, BlockPos lecternPos) {
		WarpBook.lastHeldBooks.put(player, bookStack);
		player.openGui(StarGarden.instance, Cathedral.WarpBookLecternGuiIndex, player.world, (int)lecternPos.getX(), (int)lecternPos.getY(), (int)lecternPos.getZ());
	}

}
