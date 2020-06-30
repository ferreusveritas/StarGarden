package com.ferreusveritas.stargarden.features.skyhook;

import java.util.Random;

import com.ferreusveritas.stargarden.ModConstants;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class BlockSkyHook extends Block {

	public final String NAME = "skyhook";
	
	public BlockSkyHook() {
		super(Material.GLASS);
		setRegistryName(new ResourceLocation(ModConstants.MODID, NAME));
		setUnlocalizedName(NAME);
		setHardness(2.0f);
		setResistance(1.0f);
		setSoundType(SoundType.GLASS);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.AIR;
	}
	
}
