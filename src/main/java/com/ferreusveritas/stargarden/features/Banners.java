package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.mcf.features.IFeature;
import com.ferreusveritas.stargarden.ModConstants;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class Banners implements IFeature {

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

	@Override
	public void createBlocks() { }

	@Override
	public void createItems() { }

	@Override
	public void registerEvents() { }
	
	@Override
	public void init() { }

	@Override
	public void postInit() { }

	public BannerPattern addCraftingPattern (String name, ItemStack craftingStack) {
		final Class<?>[] paramTypes = { String.class, String.class, ItemStack.class };
		final Object[] paramValues = { ModConstants.MODID + "_" + name, ModConstants.MODID + "." + name, craftingStack };
		return EnumHelper.addEnum(BannerPattern.class, name.toUpperCase(), paramTypes, paramValues);
	}
	
	@Override
	public void onLoadComplete() { }
	
	@Override
	public void registerBlocks(IForgeRegistry<Block> event) { }
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) { }
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) { }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() { }
	
}