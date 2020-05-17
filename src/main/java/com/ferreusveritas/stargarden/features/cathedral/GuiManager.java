package com.ferreusveritas.stargarden.features.cathedral;

import javax.annotation.Nonnull;

import com.ferreusveritas.cathedral.features.lectern.TileEntityLectern;
import com.ferreusveritas.stargarden.features.Cathedral;
import com.ferreusveritas.warpbook.gui.GuiBook;

import dan200.computercraft.client.gui.GuiPrintout;
import dan200.computercraft.shared.media.inventory.ContainerHeldItem;
import dan200.computercraft.shared.media.items.ItemPrintout;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiManager implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if(ID == Cathedral.WarpBookLecternGuiIndex) {
			BlockPos pos = new BlockPos(x, y, z);
			return new GuiBook(player, () -> getBook(world, pos));
		}
		else if(ID == Cathedral.CCBookLecternGuiIndex) {
			BlockPos pos = new BlockPos(x, y, z);
			ContainerLectern container = new ContainerLectern(player, world, pos);
			if( container.getStack().getItem() instanceof ItemPrintout ) {
				return new GuiPrintout( container );
			}
		}


		return null;
	}

	private ItemStack getBook(World world, BlockPos pos) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof TileEntityLectern) {
			TileEntityLectern lecternEntity = (TileEntityLectern) tileEntity;
			return lecternEntity.getBook();
		}

		return null;
	}


	public class ContainerLectern extends ContainerHeldItem {
		private final ItemStack bookStack;

		public ContainerLectern( EntityPlayer player, World world, BlockPos lecternPos ) {
			super(player, EnumHand.MAIN_HAND);
			this.bookStack = getBook(world, lecternPos);
		}

		@Override
		@Nonnull
		public ItemStack getStack() {
			return bookStack;
		}

		@Override
		public boolean canInteractWith( @Nonnull EntityPlayer player ) {
			return true;
		}
		
	}

}
