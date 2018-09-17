package com.ferreusveritas.stargarden.features;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ferreusveritas.mcf.features.IFeature;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.registries.IForgeRegistry;

public class Malisis implements IFeature {

	public static final String MALISISDOORS = "malisisdoors";
	
	public static Item getMalisisDoorsItem(String name) {
		return Item.REGISTRY.getObject(new ResourceLocation(MALISISDOORS, name));
	}
	
	public static Block getMalisisDoorsBlock(String name) {
		return Block.REGISTRY.getObject(new ResourceLocation(MALISISDOORS, name));
	}
	
	public static void removeItemFromMalisisTab(Item item) {
		removeObjectFromMalisisTab(item);
	}

	public static void removeBlockFromMalisisTab(Block block) {
		removeObjectFromMalisisTab(block);
	}
	
	private static List<Object> malisisTabItemList = null;
	
	public static List<Object> getMalisisTabItemList() {
		if(malisisTabItemList == null) {
			try {
				Class malisisDoorsClass = Class.forName("net.malisis.doors.MalisisDoors");
				Class malisisTabClass = Class.forName("net.malisis.core.inventory.MalisisTab");
				Field malisisTabField = malisisDoorsClass.getDeclaredField("tab");
				Object malisisTab = malisisTabField.get(null); 
				Field itemsField = malisisTabClass.getDeclaredField("items");
				itemsField.setAccessible(true);
				malisisTabItemList = (List<Object>)itemsField.get(malisisTab);
			} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}
		
		if(malisisTabItemList == null) {
			malisisTabItemList = new ArrayList<Object>();
		}
		
		return malisisTabItemList;
	}
	
	private static void removeObjectFromMalisisTab(Object object) {
		if(object instanceof Block || object instanceof Item) {
			getMalisisTabItemList().remove(object);
		}
	}
	
	public static ArrayList<ItemStack> getRemoveItemList() {
		ArrayList<ItemStack> list = new ArrayList<>();
		
		Arrays.asList("carriage_door", "medieval_door", "rustyhatch", "rustyladder")
		.forEach(name -> list.add(new ItemStack(getMalisisDoorsBlock(name))));

		Arrays.asList("saloon", "verticalhatch", "rustyhandle", "forcefielditem")
		.forEach(name -> list.add(new ItemStack(getMalisisDoorsItem(name))));
		
		return list;
	}
	
	public static ArrayList<ResourceLocation> getRemoveRecipeList() {
		ArrayList<ResourceLocation> list = new ArrayList<>();
		Arrays.asList("forcefield_item", "saloon_door", "vertical_hatch", "rusty_hatch", "rusty_handle", "rusty_ladder")
			.forEach(name -> list.add(new ResourceLocation(MALISISDOORS, name)));
		return list;
	}
	
	@Override
	public void preInit() { }

	@Override
	public void createBlocks() { }

	@Override
	public void createItems() { }

	@Override
	public void registerEvents() { }

	@Override
	public void init() { }

	@Override
	public void postInit() {

		if(Loader.isModLoaded(MALISISDOORS)) {
			
			//Remove items from creative 
			for(ItemStack stack: getRemoveItemList()) {
				Item item = stack.getItem();
				item.setCreativeTab(null);
				
				if(item instanceof ItemBlock ) {
					Block block = ((ItemBlock)item).getBlock();
					removeBlockFromMalisisTab(block);
				}
				
				removeItemFromMalisisTab(item);
			}
			
			//Remove Recipes
			getRemoveRecipeList().forEach(Vanilla::removeRecipe);
			
		}
	}

	@Override
	public void onLoadComplete() {
		getRemoveItemList().forEach( Vanilla::removeItemStackFromJEI );
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) { }

	@Override
	public void registerItems(IForgeRegistry<Item> registry) { }

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) { }

	@Override
	public void registerModels() { }
	
}
