package com.ferreusveritas.stargarden;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class ProjectRed {
	
	public static ArrayList<ItemStack> remove = new ArrayList<>();
	public static ArrayList<String> removeOre = new ArrayList<>();
	public static Item resItem;
	public static Item resPlate;
	
	public static void preInit() {
		resItem = Item.REGISTRY.getObject(new ResourceLocation("projectred-core:resource_item"));
		resPlate = Item.REGISTRY.getObject(new ResourceLocation("projectred-core:drawplate"));

		resItem.setCreativeTab(null);
		resPlate.setCreativeTab(null);
		
		int resMetas[] = {
			100, //Copper Ingot
			101, //Tin Ingot
			102, //Silver Ingot
			104, //Elecrotine Alloy Ingot
			105, //Elecrotine
			200, //Ruby
			201, //Sapphire
			202, //Peridot
			250, //Sandy Coal Compound
			251, //Red Iron Compound
			252, //Elecrotine Iron Compound
			310, //Red Silicon Compound
			311, //Glowing Silicon Compound
			312, //Electro Silicon Compound
			342, //Electro Silicon
			400, //Copper Coil
			401, //Iron Coil
			402, //Gold Coil
			410, //Motor
			420, //Woven Cloth
			421, //Sail
			600  //Null Logic Routing Chip
		};
		
		for(int meta : resMetas) {
			remove.add(new ItemStack(resItem, 1, meta));
		}
		
		remove.add(new ItemStack(resPlate, 1));
		
		for(String oreName : new String[]{ "gemRuby", "gemSapphire", "gemPeridot", "ingotCopper", "ingotTin", "ingotSilver" }) {
			removeOre.add(oreName);	
		}
		
	}
	
	public static void init() {
		Thermal.removeFurnaceRecipe(new ItemStack(resItem, 1, 252)); //Electrotine Iron Compound
		Thermal.removeFurnaceRecipe(new ItemStack(resItem, 1, 312)); //Electrotine Silicon Compound
		Thermal.removeFurnaceRecipe(new ItemStack(resItem, 1, 310));//Red Silicon Compound
		Thermal.removeFurnaceRecipe(new ItemStack(resItem, 1, 311));//Glowing Silicon Compound
		Thermal.removeFurnaceRecipe(new ItemStack(resItem, 1, 250));//Sandy Coal Compound
		Thermal.removeFurnaceRecipe(new ItemStack(resItem, 1, 251));//Red Iron Compound
	}
	
	public static void postInit() {
		ModMolester.removeSmelterRecipe(new ItemStack(resItem, 1, 104));//Electrotine Alloy Ingot
		ModMolester.removeSmelterRecipe(new ItemStack(resItem, 1, 342));//Electro Silicon
		ModMolester.removeSmelterRecipe(new ItemStack(resItem, 1, 320));//Infused Silicon
		ModMolester.removeSmelterRecipe(new ItemStack(resItem, 1, 341));//Energized Silicon
		ModMolester.removeSmelterRecipe(new ItemStack(resItem, 1, 300));//Silicon Boule
		ModMolester.removeSmelterRecipe(new ItemStack(resItem, 1, 103));//Red Alloy Ingot
		
		//These materials no longer have any use
		ModMolester.removeRecipe("projectred-core:resource/electrotine_silicon_compound");
		ModMolester.removeRecipe("projectred-core:resource/electrotine_iron_compound");
		ModMolester.removeRecipe("projectred-core:misc/iron_coil");
		ModMolester.removeRecipe("projectred-core:misc/gold_coil");
		ModMolester.removeRecipe("projectred-core:misc/copper_coil");
		ModMolester.removeRecipe("projectred-core:misc/motor");
		ModMolester.removeRecipe("projectred-core:tools/draw_plate");
		ModMolester.removeRecipe("projectred-transmission:power_low_load");
		ModMolester.removeRecipe("projectred-transmission:framed/34_framed");
		ModMolester.removeRecipe("projectred-core:misc/sail");
		ModMolester.removeRecipe("projectred-core:misc/woven_cloth");
	
		//These will be replaced with Induction Smelter Recipes
		ModMolester.removeRecipe("projectred-core:resource/red_iron_compound");
		ModMolester.removeRecipe("projectred-core:resource/sandy_coal_compound");
		ModMolester.removeRecipe("projectred-core:resource/red_silicon_compound");
		ModMolester.removeRecipe("projectred-core:resource/glowing_silicon_compound");
	}
	
	public static void oreRegister(String oreName, ItemStack ore) {
		//Remove Project Red Gems and Ingots from the oredict as soon as they are registered
		if(ore.getItem().getRegistryName().toString().equals("projectred-core:resource_item") && removeOre.contains(oreName)) {
			try {
				Field OREDICTIONARY_IDTOSTACK = OreDictionary.class.getDeclaredField("idToStack");
				OREDICTIONARY_IDTOSTACK.setAccessible(true);
				List<NonNullList<ItemStack>> ores = (List<NonNullList<ItemStack>>) OREDICTIONARY_IDTOSTACK.get(null);
				ores.get(OreDictionary.getOreID(oreName)).remove(ore);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public static void registerRecipes(IForgeRegistry<IRecipe> registry) {
		ItemStack silverIngot = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "material")), 1, 130);
		ItemStack siliconWafer = new ItemStack(resItem, 1, 301);
		
		Thermal.addSmelterRecipe(8000, new ItemStack(Blocks.COAL_BLOCK), new ItemStack(Blocks.SAND, 8), new ItemStack(resItem, 1, 300), ItemStack.EMPTY, 0);//Silicon Boule
		Thermal.addSmelterRecipe(4000, siliconWafer, new ItemStack(Items.REDSTONE, 4), new ItemStack(resItem, 1, 320), ItemStack.EMPTY, 0);//Infused Silicon
		Thermal.addSmelterRecipe(4000, siliconWafer, new ItemStack(Items.GLOWSTONE_DUST, 4), new ItemStack(resItem, 1, 341), ItemStack.EMPTY, 0);//Energized Silicon
		Thermal.addSmelterRecipe(2000, silverIngot, new ItemStack(Items.REDSTONE, 4), new ItemStack(resItem, 1, 103), ItemStack.EMPTY, 0);//Red Alloy Ingot
	}
	
	public static void onLoadComplete() {
		ModMolester.removeItemStackFromJEI(new ItemStack(resPlate, 1, 0));
	}
	
}
