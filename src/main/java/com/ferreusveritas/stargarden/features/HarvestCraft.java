package com.ferreusveritas.stargarden.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
	
	public static List<ItemStack> toItemStackList(List<String> names) {
		return names.stream().map(name -> new ItemStack(getHarvestCraftItem(name))).collect(Collectors.toList());
	}

	public static List<ResourceLocation> toResourceLocationList(List<String> names, String ModId) {
		return names.stream().map(name -> new ResourceLocation(HARVESTCRAFT, name)).collect(Collectors.toList());
	}
	
	public static List<ResourceLocation> toResourceLocationList(List<String> names) {
		return toResourceLocationList(names, HARVESTCRAFT);
	}
	
	public static List<ItemStack> getRawTofuList() {
		//Abominations all!
		return toItemStackList(Arrays.asList(
			"rawtofuttonitem",
			"rawtofurkeyitem",
			"rawtofuduckitem",
			"rawtofishitem",
			"rawtofickenitem",
			"rawtofenisonitem",
			"rawtofeegitem",
			"rawtofeakitem",
			"rawtofaconitem",
			"rawtofabbititem"
		));
	}
	
	public static ArrayList<ItemStack> getRemoveItemList() {
		ArrayList<ItemStack> list = new ArrayList<>();
		
		//Misc stupid items
		list.addAll( toItemStackList( Arrays.asList(
			"chaoscookieitem",//Chaos cookie?  Purple dye? What?!
			"cottoncandyitem",//The dye recipe is screwed up and I don't feel like fixing it.  Cya!
			"creepercookieitem",//How cute
			"epicbaconitem",//Bacon dyed with rainbow colors.. Yeah, whatever.
			"epicbltitem",//I'm wretching right now
			"minerstewitem",//Sorry we don't eat ingots, diamonds and flint
			"netherstartoastitem",//Are you fucking serious?
			"rainbowcurryitem"//Rainbow curry is not just curry with rainbow food coloring in it
		)));

		//All tofu related religious garbage
		list.addAll( getRawTofuList() );
		
		//Ugly candles.  Quark candles are way better.
		IntStream.rangeClosed(1, 16).forEach(i -> list.add(new ItemStack(getHarvestCraftItem("candledeco" + i)) ));
		
		return list;
	}
	
	public static ArrayList<ResourceLocation> getRemoveRecipeList() {
		ArrayList<ResourceLocation> list = new ArrayList<>();
		//Harvestcraft names the recipes the same as the item
		getRemoveItemList().forEach(i -> list.add(i.getItem().getRegistryName()));
		
		//Tofu recipes that have salt variations
		list.addAll(toResourceLocationList( Arrays.asList( "rawtofaconitem_itemsalt", "rawtofaconitem_foodsalt", "rawtofaconitem_dustsalt" )));
		
		//Recipes to be redone
		list.addAll(toResourceLocationList( Arrays.asList( "fairybreaditem", "gummybearsitem", "frosteddonutitem", "chocolatesprinklecakeitem" )));
		
		//Remove tofu from the Ore Dictionary as a valid type of meat
		Arrays.asList("meatraw", "beefraw", "porkraw", "egg", "muttonraw", "chickenraw", "rabbitraw", "venisonraw", "duckraw")
			.stream().map(meat -> "listAll" + meat).forEach( meat -> getRawTofuList().forEach(item -> Vanilla.removeOre(item, meat)) );
		
		//Remove tofu as a source of dairy
		Vanilla.removeOre(new ItemStack(getHarvestCraftItem("silkentofuitem")), "listAllicecream");
		Vanilla.removeOre(new ItemStack(getHarvestCraftItem("silkentofuitem")), "listAllheavycream");
		Vanilla.removeOre(new ItemStack(getHarvestCraftItem("soymilkitem")), "listAllmilk");

		
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
					new ItemStack(getHarvestCraftItem("chocolatesprinklecake")),
					new Object[] {
						"toolBakeware",
						"foodCocoapowder",
						"listAllsugar",
						"foodDough",
						new ItemStack(nonpareils)
					}
				).setRegistryName(new ResourceLocation(ModConstants.MODID, "chocolatesprinklecake"))
			);
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() { }
	
}
