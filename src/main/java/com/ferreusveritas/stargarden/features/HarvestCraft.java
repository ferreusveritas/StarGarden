package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.mcf.features.IFeature;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class HarvestCraft implements IFeature {

	public static final String HARVESTCRAFT = "harvestcraft";
	
	@Override
	public void preInit() {
		
		//REMOVE ITEMS:
		//cottoncandyitem
		//candledeco1 - candledeco2
		//rainbowcurryitem
		//epicbaconitem
		//epicbltitemrrrr
		//chaoscookieitem
		//creepercookieitem
		//minerstewitem
		//netherstartoastitem
		//all tofu related garbage

		//ADD ITEM:
		//nonpareils(colored sprinkles)
		
		//REDO RECIPES:
		//fairybreaditem
		//gummybearsitem
		//frosteddonutitem
		//chocolatesprinklecakeitem
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

	@Override
	public void onLoadComplete() { }

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) { }

	@Override
	public void registerItems(IForgeRegistry<Item> registry) { }

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) { }

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() { }

}