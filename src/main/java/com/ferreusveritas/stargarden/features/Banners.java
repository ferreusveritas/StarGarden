package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.stargarden.ModConstants;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class Banners extends BaseFeature {
	
	@Override
	public void preInit() {
		addCraftingPattern("diamond", new ItemStack(Items.DIAMOND));
		addCraftingPattern("pinwheel", new ItemStack(Blocks.YELLOW_FLOWER));
		addCraftingPattern("gear", new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "material")), 1, 24));
		addCraftingPattern("redsox", new ItemStack(Items.LEATHER_BOOTS));
		addCraftingPattern("circle", new ItemStack(Items.SNOWBALL));
		addCraftingPattern("pickaxe", new ItemStack(Items.WOODEN_PICKAXE));
		addCraftingPattern("logo", new ItemStack(Logo.logo));
	}
	
	public BannerPattern addCraftingPattern (String name, ItemStack craftingStack) {
		final Class<?>[] paramTypes = { String.class, String.class, ItemStack.class };
		final Object[] paramValues = { ModConstants.MODID + "_" + name, ModConstants.MODID + "." + name, craftingStack };
		return EnumHelper.addEnum(BannerPattern.class, name.toUpperCase(), paramTypes, paramValues);
	}
	
}
