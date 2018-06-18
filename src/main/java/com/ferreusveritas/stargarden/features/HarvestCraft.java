package com.ferreusveritas.stargarden.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import com.ferreusveritas.mcf.ModConstants;
import com.ferreusveritas.mcf.features.IFeature;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class HarvestCraft implements IFeature {
	
	public static final String HARVESTCRAFT = "harvestcraft";
	
	public static Item nonpareils;
	
	public static Item getHarvestCraftItem(String name) {
		return Item.REGISTRY.getObject(new ResourceLocation(HARVESTCRAFT, name));
	}
	
	public static ArrayList<ItemStack> getRemoveItemList() {
		ArrayList<ItemStack> list = new ArrayList<>();
		
		//Misc stupid items
		Arrays.asList(
			"chaoscookieitem",//Chaos cookie?  Purple dye? What?!
			"cottoncandyitem",//The dye recipe is screwed up and I don't feel like fixing it.  Cya!
			"creepercookieitem",//How cute
			"epicbaconitem",//Bacon dyed with rainbow colors.. Yeah, whatever.
			"epicbltitem",//I'm wretching right now
			"minerstewitem",//Sorry we don't eat ingots, diamonds and flint
			"netherstartoastitem",//Are you fucking serious?
			"rainbowcurryitem"//Rainbow curry is not just curry with rainbow food coloring in it
		).forEach( name -> list.add(new ItemStack(getHarvestCraftItem(name))) );

		//All tofu related religious garbage
		//TODO
		
		//Ugly candles.  Quark candles are way better.
		IntStream.rangeClosed(1, 16).forEach(i -> list.add(new ItemStack(getHarvestCraftItem("candledeco" + i)) ));
		
		return list;
	}
	
	public static ArrayList<ResourceLocation> getRemoveRecipeList() {
		ArrayList<ResourceLocation> list = new ArrayList<>();
		//Harvestcraft names the recipes the same as the item
		getRemoveItemList().forEach(i -> list.add(i.getItem().getRegistryName()));
		
		//Recipes to be redone
		Arrays.asList(
			"fairybreaditem",
			"gummybearsitem",
			"frosteddonutitem",
			"chocolatesprinklecakeitem"
		).
		forEach(name -> list.add(new ResourceLocation(HARVESTCRAFT, name)));
		
		return list;
	}
	
	@Override
	public void preInit() { }
	
	@Override
	public void createBlocks() { }
	
	@Override
	public void createItems() {
		String name = "nonpareils";
		nonpareils = new Item().setRegistryName(new ResourceLocation(ModConstants.MODID, name)).setUnlocalizedName(name);
	}
	
	@Override
	public void registerEvents() { }
	
	@Override
	public void init() { }
	
	@Override
	public void postInit() {
		//Remove items from creative tabs(this is usually enough to remove from JEI as well)
		getRemoveItemList().forEach(i -> i.getItem().setCreativeTab(null));
		
		//Remove Recipes
		getRemoveRecipeList().forEach(Vanilla::removeRecipe);
	}
	
	@Override
	public void onLoadComplete() { }
	
	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) { }
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		registry.register(nonpareils);
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {

		//Nonpareils
		registry.register(
			new ShapelessOreRecipe(
				null,
				new ItemStack(nonpareils),
				new Object[] {
					"toolSaucepan",
					"listAllsugar",
					"dyeRed",
					"dyeGreen",
					"dyeYellow",
					"dyeBlue",
					"dyePink"
				}
			).setRegistryName(new ResourceLocation(ModConstants.MODID, "nonpareils"))
		);
		
		//Fairy Bread
		registry.register(
			new ShapelessOreRecipe(
				null,
				new ItemStack(getHarvestCraftItem("fairybreaditem")),
				new Object[] {
					"toolBakeware",
					"bread",
					"foodButter",
					new ItemStack(nonpareils)
				}
			).setRegistryName(new ResourceLocation(ModConstants.MODID, "fairybreaditem"))
		);
		
		//Gummy Bears
		registry.register(
				new ShapelessOreRecipe(
					null,
					new ItemStack(getHarvestCraftItem("gummybearsitem")),
					new Object[] {
						"toolSaucepan",
						"listAllsugar",
						"dyeRed",
						"dyeGreen"
					}
				).setRegistryName(new ResourceLocation(ModConstants.MODID, "gummybearsitem"))
			);
		
		//Frosted Donut
		registry.register(
				new ShapelessOreRecipe(
					null,
					new ItemStack(getHarvestCraftItem("frosteddonutitem")),
					new Object[] {
						"foodDonut",
						"listAllsugar",
						new ItemStack(nonpareils)
					}
				).setRegistryName(new ResourceLocation(ModConstants.MODID, "frosteddonutitem"))
			);
		
		//Chocolate Sprinkle Cake
		registry.register(
				new ShapelessOreRecipe(
					null,
					new ItemStack(getHarvestCraftItem("chocolatesprinklecakeitem")),
					new Object[] {
						"toolBakeware",
						"foodCocoapowder",
						"listAllsugar",
						"foodDough",
						new ItemStack(nonpareils)
					}
				).setRegistryName(new ResourceLocation(ModConstants.MODID, "chocolatesprinklecakeitem"))
			);
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() { }
	
}
