package com.ferreusveritas.stargarden.features;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ferreusveritas.stargarden.ModConstants;
import com.ferreusveritas.stargarden.StarGarden;
import com.ferreusveritas.stargarden.util.Util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

public class Vanilla extends BaseFeature {
	
	public static final CreativeTabs spawnEggs = new CreativeTabs("spawneggs") {
		@SideOnly(Side.CLIENT)
		@Override
		public ItemStack getTabIconItem() {
			ItemStack egg = new ItemStack(Items.SPAWN_EGG);
			ItemMonsterPlacer.applyEntityIdToItemStack(egg, new ResourceLocation("minecraft", "zombie"));
			return egg;
		}
	};
	
	public static HashMap<CreativeTabs, HashSet<ItemStack>> contraband = new HashMap<>();
	public static HashMap<CreativeTabs, HashSet<ItemStack>> additions = new HashMap<>();
	
	//This will allow itemStacks(subItems) to be removed when they are registered in a creative tab as part of a greater item.
	public static void addContraband(ItemStack stack, CreativeTabs tab) {
		contraband.computeIfAbsent(tab, k -> new HashSet<ItemStack>()).add(stack);
	}
	
	//This will allow itemStacks to be added to creative tabs without moving the entire item
	public static void addItem(ItemStack stack, CreativeTabs tab) {
		additions.computeIfAbsent(tab, k -> new HashSet<ItemStack>()).add(stack);
	}
	
	public static void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		
		if(tab == CreativeTabs.SEARCH) {
			Vanilla.contraband.forEach((t, i) -> {
				HashSet<Item> itemSet = new HashSet<Item>();
				HashSet<ItemStack> relocation = i;//Get the set of itemStacks for this tab
				relocation.forEach( i2 -> itemSet.add(i2.getItem()) );//Create a set of items from the itemStacks
				itemSet.forEach(i2 -> items.addAll(getFilteredItemStacks(i2, null, relocation)));//Add all of the ItemStacks that are not contraband
			});
		}
		else if(Vanilla.contraband.containsKey(tab)) {
			HashSet<Item> itemSet = new HashSet<Item>();
			HashSet<ItemStack> relocation = Vanilla.contraband.get(tab);//Get the set of itemStacks for this tab
			relocation.forEach( i -> itemSet.add(i.getItem()) );//Create a set of items from the itemStacks
			itemSet.forEach(i -> items.addAll(getFilteredItemStacks(i, null, relocation)));//Add all of the ItemStacks that are not contraband
		}
		
		if(Vanilla.additions.containsKey(tab)) {
			items.addAll(Vanilla.additions.get(tab));
		}
	}
	
	/**
	 * Gets a list of subItems from {@link Item} "item" that belong in parameter {@link CreativeTabs} "tab" and removes from it the items in
	 * parameter "remList".
	 * 
	 * @param item
	 * @param tab
	 * @param remList
	 * @return
	 */
	public static NonNullList<ItemStack> getFilteredItemStacks(Item item, CreativeTabs tab, Collection<ItemStack> remList) {
		NonNullList<ItemStack> itemList = NonNullList.<ItemStack>create();
		item.getSubItems(tab, itemList);
		
		Iterator<ItemStack> it = itemList.iterator();
		while (it.hasNext()) {
			ItemStack stack = it.next();
			for(ItemStack rem : remList) {
				if(ItemStack.areItemsEqual(stack, rem)) {
					it.remove();
				}
			}
		}
		return itemList;
	}
	
	@Override
	public void postInit() {
		Items.SPAWN_EGG.setCreativeTab(spawnEggs);
		
		removeOre(new ItemStack(Items.DYE, 1, 0), "dyeBlack");//Ink Sac
		removeOre(new ItemStack(Items.DYE, 1, 2), "dyeGreen");//Cactus
		removeOre(new ItemStack(Items.DYE, 1, 3), "dyeBrown");//Cocoa Beans
		removeOre(new ItemStack(Items.DYE, 1, 4), "dyeBlue");//Lapis Lazuli
		removeOre(new ItemStack(Items.DYE, 1, 15), "dyeWhite");//Bonemeal
		
		removeOre(new ItemStack(Items.DYE, 1, 0), "dye");//Ink Sac
		removeOre(new ItemStack(Items.DYE, 1, 2), "dye");//Cactus
		removeOre(new ItemStack(Items.DYE, 1, 3), "dye");//Cocoa Beans
		removeOre(new ItemStack(Items.DYE, 1, 4), "dye");//Lapis Lazuli
		removeOre(new ItemStack(Items.DYE, 1, 15), "dye");//Bonemeal
		
		removeRecipe("minecraft:light_gray_dye_from_white_tulip");
	}
	
	@Override
	public void onLoadComplete() {
		super.onLoadComplete();
		
		Blocks.WATER.setLightOpacity(1);
		Blocks.FLOWING_WATER.setLightOpacity(1);
		
		//Optifine will set this back to 3 if "ofClearWater:false" in optionsof.txt
		//Set the optifine option to true to make it 1 in single player worlds.
		//Optifine does not run on dedicated servers
	}
	
	private static class DummyRecipe implements IRecipe {
		private static ItemStack result = new ItemStack(Items.DIAMOND, 64);
		private ResourceLocation name;
		
		@Override
		public IRecipe setRegistryName(ResourceLocation name) {
			this.name = name;
			return this;
		}
		@Override public ResourceLocation getRegistryName() { return name; }
		@Override public Class<IRecipe> getRegistryType() { return IRecipe.class; }
		@Override public boolean matches(InventoryCrafting inv, World worldIn) { return false; } //dirt?
		@Override public ItemStack getCraftingResult(InventoryCrafting inv) { return result; }
		@Override public boolean canFit(int width, int height) { return false; }
		@Override public ItemStack getRecipeOutput() { return result; }
		@Override public boolean isDynamic() { return true; }
	}
	
	public static void removeRecipe(ResourceLocation location) {
		((IForgeRegistryModifiable)ForgeRegistries.RECIPES).register(new DummyRecipe().setRegistryName(location));
	}
	
	public static void removeRecipe(String location) {
		removeRecipe(new ResourceLocation(location));
	}
	
	public static void removeSmelterRecipe(ItemStack output) {
		Map<ItemStack, ItemStack> smeltingList = FurnaceRecipes.instance().getSmeltingList();
		
		for(Map.Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
			if(output.getItem().equals(entry.getValue().getItem())) {
				if(output.getItemDamage() == entry.getValue().getItemDamage()) {
					smeltingList.remove(entry.getKey());
					return;
				}
			}
		}
	}
	
	public static void removeItemStackFromJEI(ItemStack stack) {
		StarGarden.proxy.removeItemStackFromJEI(stack);
	}
	
	public static List<NonNullList<ItemStack>> ores = null;
	public static List<NonNullList<ItemStack>> oresUn = null;
	
	/**
	 * Effectively removes an ore entry or all ore entries for an item or ore name
	 * 
	 * @param oreItem
	 * @param oreName
	 */
	public static void removeOre(ItemStack oreItem, String oreName) {
		
		if(oreName == null && oreItem.isEmpty()) {
			return;
		}
		
		if(oreName == null && !oreItem.isEmpty()) {
			for(int id : OreDictionary.getOreIDs(oreItem)) {
				removeOre(oreItem, OreDictionary.getOreName(id));
			}
			return;
		}
		
		if(oreName != null && oreItem.isEmpty()) {
			for(ItemStack item : OreDictionary.getOres(oreName)) {
				removeOre(item, oreName);
			}
			return;
		}
		
		if(ores == null || oresUn == null) {
			ores = (List<NonNullList<ItemStack>>) Util.getRestrictedObject(OreDictionary.class, null, "idToStack");//Minecraft forge is not obfuscated
			oresUn = (List<NonNullList<ItemStack>>) Util.getRestrictedObject(OreDictionary.class, null, "idToStackUn");//Minecraft forge is not obfuscated
		}
		
		int oreId = OreDictionary.getOreID(oreName);
		for(ItemStack item : OreDictionary.getOres(oreName)) {
			if(ItemStack.areItemsEqual(oreItem, item)) {
				oreItem = item;//get actual reference
				break;
			}
		}
		ores.get(oreId).remove(oreItem);
		oresUn.get(oreId).remove(oreItem);
	}
	
	public static void listAllOres() {
		for(String oreName : OreDictionary.getOreNames()) {
			int oreId = OreDictionary.getOreID(oreName);
			System.out.println("Ore Name: " + oreName + " ID: " + oreId);
			for(ItemStack item : OreDictionary.getOres(oreName)) {
				System.out.println("	Item: " + item.getItem().getRegistryName() + ":" + item.getMetadata() + " x " + item.getCount() );
			}
		}
	}
	
	public static String[] dyeValues = new String[] { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White" };
	
	void addSafeColorRecipe (EnumDyeColor color, int count) {
		ItemStack safeDye = Thermal.getSafeDyesList().get(color.getDyeDamage()).copy();
		safeDye.setCount(count);
		GameRegistry.addShapedRecipe( new ResourceLocation(ModConstants.MODID, "dye" + color.getName()), null, safeDye, "x", 'x', new ItemStack(Items.DYE, 1, color.getDyeDamage()) );
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		addSafeColorRecipe(EnumDyeColor.BLACK, 2);//Ink Sac to 2 Black Dye
		addSafeColorRecipe(EnumDyeColor.GREEN, 2);//Cactus Green to 2 Green Dye
		addSafeColorRecipe(EnumDyeColor.BROWN, 2);//Cocoa Beans to 2 Brown Dye
		addSafeColorRecipe(EnumDyeColor.BLUE, 2);//Lapis Lazuli to 2 Blue Dye
		addSafeColorRecipe(EnumDyeColor.WHITE, 1);//Bone Meal to 1 White Dye
		
		//Make white tulip produce white dye
		ItemStack whiteDye = Thermal.getSafeDyesList().get(EnumDyeColor.WHITE.getDyeDamage()).copy();
		GameRegistry.addShapedRecipe(new ResourceLocation(ModConstants.MODID, "white_gray_dye_from_white_tulip"), null, whiteDye, "x", 'x', new ItemStack(Blocks.RED_FLOWER, 1, 6));
	}
	
	public static String getDyeName(EnumDyeColor color) {
		switch(color) {
			case WHITE: return "white";
			case ORANGE: return "orange";
			case MAGENTA: return "magenta";
			case LIGHT_BLUE: return "light_blue";
			case YELLOW: return "yellow";
			case LIME: return "lime";
			case PINK: return "pink";
			case GRAY: return "gray";
			case SILVER: return "silver";
			case CYAN: return "cyan";
			case PURPLE: return "purple";
			case BLUE: return "blue";
			case BROWN: return "brown";
			case GREEN: return "green";
			case RED: return "red";
			case BLACK: return "black";
		}
		
		return "";
	}
	
}
