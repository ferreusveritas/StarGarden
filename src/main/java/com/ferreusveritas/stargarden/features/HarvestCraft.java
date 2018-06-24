package com.ferreusveritas.stargarden.features;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.ferreusveritas.mcf.features.IFeature;
import com.ferreusveritas.mcf.util.Util;
import com.ferreusveritas.stargarden.ModConstants;
import com.pam.harvestcraft.item.GrinderRecipes;

import cofh.thermalexpansion.util.managers.machine.CentrifugeManager;
import cofh.thermalexpansion.util.managers.machine.PulverizerManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
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
	
	public static Block getHarvestCraftBlock(String name) {
		return Block.REGISTRY.getObject(new ResourceLocation(HARVESTCRAFT, name));
	}
	
	public static List<ItemStack> toItemStackList(List<String> names) {
		return names.stream().map(name -> new ItemStack(getHarvestCraftItem(name))).collect(Collectors.toList());
	}

	public static List<ResourceLocation> toResourceLocationList(List<String> names, String ModId) {
		return names.stream().map(name -> new ResourceLocation(ModId, name)).collect(Collectors.toList());
	}
	
	public static List<ResourceLocation> toResourceLocationListFromItemStackList(List<ItemStack> items) {
		return items.stream().map(item -> item.getItem().getRegistryName()).collect(Collectors.toList());
	}
	
	public static List<ResourceLocation> toResourceLocationList(List<String> names) {
		return toResourceLocationList(names, HARVESTCRAFT);
	}

	private static Map<ItemStack, ItemStack[]> grindingList = null;
	
	public static void removeGrinderRecipe(ItemStack input) {
		if(grindingList == null) {
			grindingList = (Map<ItemStack, ItemStack[]>) Util.getRestrictedObject(GrinderRecipes.class, null, "grindingList");
		}
		
		//We are resorted to using iterators because the ItemStack::equals method compares addresses which is almost always not what we want
		Iterator<Entry<ItemStack, ItemStack[]>> iter = grindingList.entrySet().iterator();
		
		while(iter.hasNext()) {
			Entry<ItemStack, ItemStack[]> set = iter.next();
			if( input.getItem().equals(set.getKey().getItem()) ) {
				iter.remove();
			}
		}
		
	}
	
	public static List<ItemStack> getRawTofuList() {
		//Abominations all!  Tofu is tofu.
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
	
	public static List<ItemStack> getCookedTofuList() {
		//Abominations all!
		return toItemStackList(Arrays.asList(
			"cookedtofeakitem",
			"cookedtofaconitem",
			"cookedtofishitem",
			"cookedtofeegitem",
			"cookedtofuttonitem",
			"cookedtofickenitem",
			"cookedtofabbititem",
			"cookedtofurkeyitem",
			"cookedtofenisonitem",
			"cookedtofuduckitem"
		));
	}
	
	public static List<ItemStack> getCrazyItemList() {
		//Misc stupid items
		return toItemStackList( Arrays.asList(
			"chaoscookieitem",//Chaos cookie?  Purple dye? What?!
			"cottoncandyitem",//The dye recipe is screwed up and I don't feel like fixing it.  Cya!
			"creepercookieitem",//How pointlessly cute
			"epicbaconitem",//Bacon dyed with rainbow colors.. Yeah, whatever.
			"epicbltitem",//I'm retching right now
			"minerstewitem",//Sorry, we don't eat ingots, diamonds or flint
			"netherstartoastitem",//Are you fucking serious?
			"rainbowcurryitem",//Rainbow curry is not just curry with rainbow food coloring in it
			"mobsoupitem"//Meh,  I'm not buyin' it
		));
	}
	
	public static ArrayList<ItemStack> getRemoveItemList() {
		ArrayList<ItemStack> list = new ArrayList<>();
		
		//Misc stupid items
		list.addAll( getCrazyItemList() );
		
		//All tofu related religious garbage
		list.addAll( getRawTofuList() );
		list.addAll( getCookedTofuList() );
		
		//Ugly candles.  Quark candles are way better.
		IntStream.rangeClosed(1, 16).forEach(i -> list.add(new ItemStack(getHarvestCraftBlock("candledeco" + i)) ));
		
		return list;
	}
	
	public static ArrayList<ResourceLocation> getRemoveRecipeList() {
		ArrayList<ResourceLocation> list = new ArrayList<>();

		//Harvestcraft names the recipes the same as the item
		list.addAll(toResourceLocationListFromItemStackList(getRemoveItemList()));
		
		//Candles recipes
		IntStream.rangeClosed(1, 16).forEach(i -> list.add(new ResourceLocation(HARVESTCRAFT, "candledeco" + i + "_x4") ));
		
		//Tofu bacon recipes that have salt variations
		list.addAll(toResourceLocationList( Arrays.asList( "rawtofaconitem_itemsalt", "rawtofaconitem_foodsalt", "rawtofaconitem_dustsalt" )));
		
		//Recipes to be redone
		list.addAll(toResourceLocationList( Arrays.asList( "fairybreaditem", "gummybearsitem", "frosteddonutitem", "chocolatesprinklecakeitem" )));
		
		//Remove the ability to convert grass seeds into cooking oil
		list.add(new ResourceLocation(HARVESTCRAFT, "oliveoilitem_x2_listallseed_listallseed"));
		list.add(new ResourceLocation(HARVESTCRAFT, "oliveoilitem_croptea"));
		
		return list;
	}
	
	@Override
	public void preInit() { }
	
	@Override
	public void createBlocks() { }
	
	@Override
	public void createItems() {
		String name = "nonpareils";//(Colored Sprinkles)
		nonpareils = new Item().setRegistryName(new ResourceLocation(ModConstants.MODID, name)).setUnlocalizedName(name).setCreativeTab(Util.findCreativeTab(HARVESTCRAFT));
	}
	
	@Override
	public void registerEvents() { }
	
	@Override
	public void init() {
		//Remove Tofu Smelting
		getCookedTofuList().forEach(Vanilla::removeSmelterRecipe);
	}
	
	@Override
	public void postInit() {
		//Remove items from creative tabs
		getRemoveItemList().forEach(i -> i.getItem().setCreativeTab(null));
		
		//Remove Recipes
		getRemoveRecipeList().forEach(Vanilla::removeRecipe);
		
		//Remove Smelter Recipes
		toItemStackList(Arrays.asList( "tofeak", "tofacon", "tofish", "tofeeg", "tofutton", "toficken", "tofabbit", "tofurkey", "tofenison", "tofuduck")
			.stream().map( n -> "cooked" + n + "item" ).collect(Collectors.toList())).forEach(Vanilla::removeSmelterRecipe);

		//Remove Grinder Recipes
		getRawTofuList().forEach(PulverizerManager::removeRecipe);
		getRawTofuList().forEach(HarvestCraft::removeGrinderRecipe);
		
		//Remove Centrifuge recipes that make Cooking oil.  Cotton Seed Oil is toxic,  Tea leaf oil is not suitable for cooking.
		List<ItemStack> csRemoveList = toItemStackList(Arrays.asList("mustardseeditem", "cottonseeditem", "tealeafitem"));
		csRemoveList.addAll(Arrays.asList(new ItemStack(Blocks.PUMPKIN), new ItemStack(Items.PUMPKIN_SEEDS)));
		csRemoveList.forEach(CentrifugeManager::removeRecipe);
		
		//Fix sesame seed Centrifuge recipe
		ItemStack oilSesame = new ItemStack(getHarvestCraftItem("sesameoilitem"));
		ItemStack baitGrain = new ItemStack(getHarvestCraftItem("grainbaititem"));
		CentrifugeManager.addRecipe(4000, new ItemStack(getHarvestCraftItem("sesameseedsitem")), asList(oilSesame, baitGrain), null);
	}
	
	@Override
	public void onLoadComplete() {
		getRemoveItemList().forEach( Vanilla::removeItemStackFromJEI );
	}
	
	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) { }
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		registry.register(nonpareils);
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		
		//Remove tofu from the Ore Dictionary as a valid type of meat
		Arrays.asList("meatraw", "beefraw", "porkraw", "muttonraw", "chickenraw", "rabbitraw", "venisonraw", "duckraw", "turkeyraw", "fishraw", "eggraw", "egg")
			.stream().map(meat -> "listAll" + meat).forEach( meat -> getRawTofuList().forEach(item -> Vanilla.removeOre(item, meat)) );
		
		Arrays.asList("meatcooked", "beefcooked", "porkcooked", "muttoncooked", "chickencooked", "rabbitcooked", "venisoncooked", "duckcooked", "turkeycooked", "fishcooked", "eggcooked", "tofu")
			.stream().map(meat -> "listAll" + meat).forEach( meat -> getCookedTofuList().forEach(item -> Vanilla.removeOre(item, meat)) );
		
		//Remove tofu as a valid source of dairy
		Vanilla.removeOre(new ItemStack(getHarvestCraftItem("silkentofuitem")), "listAllicecream");
		Vanilla.removeOre(new ItemStack(getHarvestCraftItem("silkentofuitem")), "listAllheavycream");
		Vanilla.removeOre(new ItemStack(getHarvestCraftItem("soymilkitem")), "listAllmilk");
		
		//Remove cookies from ore dictionary
		Vanilla.removeOre(new ItemStack(getHarvestCraftItem("chaoscookieitem")), "listAllcookie");
		Vanilla.removeOre(new ItemStack(getHarvestCraftItem("creepercookieitem")), "listAllcookie");
		
		//Nonpareils(Colored Sprinkles)
		easyShapelessOreRecipe(registry, "nonpareils", nonpareils, 
			new Object[] { "toolSaucepan", "listAllsugar", "dyeRed", "dyeGreen", "dyeYellow", "dyeBlue", "dyePink" }
		);
		
		//Fairy Bread
		easyShapelessOreRecipe(registry, "fairybreaditem", getHarvestCraftItem("fairybreaditem"),
			new Object[] { "toolBakeware", "bread", "foodButter", nonpareils }
		);
		
		//Gummy Bears
		easyShapelessOreRecipe(registry, "gummybearsitem", getHarvestCraftItem("gummybearsitem"),
			new Object[] { "toolSaucepan", "listAllsugar", "dyeRed", "dyeGreen" }
		);
		
		//Frosted Donut
		easyShapelessOreRecipe(registry, "frosteddonutitem", getHarvestCraftItem("frosteddonutitem"), 
			new Object[] { "foodDonut", "listAllsugar", nonpareils }
		);
		
		//Chocolate Sprinkle Cake
		easyShapelessOreRecipe(registry, "chocolatesprinklecake", getHarvestCraftItem("chocolatesprinklecake"),
			new Object[] { "toolBakeware", "foodCocoapowder", "listAllsugar", "foodDough" }
		);
		
	}
	
	public void easyShapelessOreRecipe(IForgeRegistry<IRecipe> registry, String recipeName, Item output, Object[] ingredients) {
		registry.register(
			new ShapelessOreRecipe(null, new ItemStack(output), ingredients).setRegistryName(new ResourceLocation(ModConstants.MODID, recipeName))
		);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(nonpareils, 0, new ModelResourceLocation(nonpareils.getRegistryName(), "inventory"));
	}
	
}
