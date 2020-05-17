package com.ferreusveritas.stargarden.features.cathedral;

import com.ferreusveritas.cathedral.features.lectern.IBookReadHandler;
import com.ferreusveritas.stargarden.StarGarden;
import com.ferreusveritas.stargarden.features.Cathedral;

import dan200.computercraft.shared.media.items.ItemPrintout;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class CCBookHandler implements IBookReadHandler {

	@Override
	public boolean isReadable(ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemPrintout;// && ItemPrintout.getType(itemStack) == Type.Book;
	}

	@Override
	public void read(EntityPlayer player, ItemStack bookStack, BlockPos lecternPos) {
		player.openGui(StarGarden.instance, Cathedral.CCBookLecternGuiIndex, player.world, (int)lecternPos.getX(), (int)lecternPos.getY(), (int)lecternPos.getZ());
	}

}
