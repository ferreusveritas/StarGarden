package com.ferreusveritas.stargarden;

import java.lang.reflect.Field;
import java.util.ArrayList;

import cofh.core.item.ItemMulti;
import cofh.thermalexpansion.util.managers.machine.CompactorManager;
import cofh.thermalexpansion.util.managers.machine.PulverizerManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.registries.IForgeRegistry;

public class Thermal {
	
	private static final String MOD_ID = "thermalexpansion";
	private static final String INPUT = "input";
	//private static final String OUTPUT = "output";
	private static final String ENERGY = "energy";
	private static final String PRIMARY_INPUT = "primaryInput";
	private static final String SECONDARY_INPUT = "secondaryInput";
	private static final String PRIMARY_OUTPUT = "primaryOutput";
	private static final String SECONDARY_OUTPUT = "secondaryOutput";
	private static final String SECONDARY_CHANCE = "secondaryChance";
	private static final String REMOVE_FURNACE_RECIPE = "removefurnacerecipe";
	private static final String ADD_SMELTER_RECIPE = "addsmelterrecipe";
	
	public static void removeFurnaceRecipe(ItemStack input) {
		
		if (input.isEmpty()) {
			return;
		}
		NBTTagCompound toSend = new NBTTagCompound();
		toSend.setTag(INPUT, new NBTTagCompound());
		
		input.writeToNBT(toSend.getCompoundTag(INPUT));
		FMLInterModComms.sendMessage(MOD_ID, REMOVE_FURNACE_RECIPE, toSend);
	}
	
	public static void removeCompactorMintRecipe(ItemStack input) {
		
		if (input.isEmpty()) {
			return;
		}
		
		CompactorManager.removeRecipe(input, CompactorManager.Mode.COIN);
	}
	
	public static void addSmelterRecipe(int energy, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
		
		if (primaryInput.isEmpty() || secondaryInput.isEmpty() || primaryOutput.isEmpty()) {
			return;
		}
		NBTTagCompound toSend = new NBTTagCompound();
		
		toSend.setInteger(ENERGY, energy);
		toSend.setTag(PRIMARY_INPUT, new NBTTagCompound());
		toSend.setTag(SECONDARY_INPUT, new NBTTagCompound());
		toSend.setTag(PRIMARY_OUTPUT, new NBTTagCompound());
		
		primaryInput.writeToNBT(toSend.getCompoundTag(PRIMARY_INPUT));
		secondaryInput.writeToNBT(toSend.getCompoundTag(SECONDARY_INPUT));
		primaryOutput.writeToNBT(toSend.getCompoundTag(PRIMARY_OUTPUT));
		
		if (!secondaryOutput.isEmpty()) {
			toSend.setTag(SECONDARY_OUTPUT, new NBTTagCompound());
			secondaryOutput.writeToNBT(toSend.getCompoundTag(SECONDARY_OUTPUT));
			toSend.setInteger(SECONDARY_CHANCE, secondaryChance);
		}
		FMLInterModComms.sendMessage(MOD_ID, ADD_SMELTER_RECIPE, toSend);
	}
	
	public static void addPulverizerRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
		
		if (input.isEmpty() || primaryOutput.isEmpty()) {
			return;
		}
		
		PulverizerManager.addRecipe(energy, input, primaryOutput, secondaryOutput, secondaryChance);
	}
	
	public static void removePulverizerRecipe(ItemStack input) {
		
		if (input.isEmpty()) {
			return;
		}
		
		PulverizerManager.removeRecipe(input);
	}
	
	public static ArrayList<ItemStack> getMintRemoveList() {
		ArrayList<ItemStack> mintRemoveList = new ArrayList<ItemStack>();
		
		mintRemoveList.add(new ItemStack(Blocks.IRON_BLOCK));
		mintRemoveList.add(new ItemStack(Items.IRON_INGOT));
		mintRemoveList.add(new ItemStack(Items.IRON_NUGGET));
		
		mintRemoveList.add(new ItemStack(Blocks.GOLD_BLOCK));
		mintRemoveList.add(new ItemStack(Items.GOLD_INGOT));
		mintRemoveList.add(new ItemStack(Items.GOLD_NUGGET));
		
		for(int i = 0; i <= 9; i++) { //Copper, Tin, Silver, Lead, Aluminum, Nickle, Platinum, Iridium, Mana Infused
			mintRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "storage")), 1, i));//Blocks
			mintRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "material")), 1, i + 128));//Ingots
			mintRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "material")), 1, i + 192));//Nuggets
		}
		
		for(int i = 0; i <= 8; i++) { //Steel, Electrum, Invar, Bronze, Constantan, Signalum, Lumium, Enderium
			mintRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "storage_alloy")), 1, i));//Blocks
			mintRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "material")), 1, i + 160));//Ingots
			mintRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "material")), 1, i + 224));//Nuggets
		}
		
		return mintRemoveList;
	}

	public static ArrayList<ItemStack> getSafeDyesList() {
		ArrayList<ItemStack> safeDyes = new ArrayList<ItemStack>();
		
		safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("biomesoplenty", "black_dye"))));//Black
		safeDyes.add(new ItemStack(Items.DYE, 1, 1));//Red
		safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("biomesoplenty", "green_dye"))));//Green
		safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("biomesoplenty", "brown_dye"))));//Brown
		safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("biomesoplenty", "blue_dye"))));//Blue
		safeDyes.add(new ItemStack(Items.DYE, 1, 5));//Purple
		safeDyes.add(new ItemStack(Items.DYE, 1, 6));//Cyan
		safeDyes.add(new ItemStack(Items.DYE, 1, 7));//Light Gray
		safeDyes.add(new ItemStack(Items.DYE, 1, 8));//Gray
		safeDyes.add(new ItemStack(Items.DYE, 1, 9));//Pink
		safeDyes.add(new ItemStack(Items.DYE, 1, 10));//Lime
		safeDyes.add(new ItemStack(Items.DYE, 1, 11));//Yellow
		safeDyes.add(new ItemStack(Items.DYE, 1, 12));//Light Blue
		safeDyes.add(new ItemStack(Items.DYE, 1, 13));//Magenta
		safeDyes.add(new ItemStack(Items.DYE, 1, 14));//Orange Dye
		safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("biomesoplenty", "white_dye"))));//White
		
		return safeDyes;
	}
	
	public static ArrayList<ItemStack> getPulverizerRemoveList() {
		ArrayList<ItemStack> pulverizerRemoveList = new ArrayList<ItemStack>();

		for(EnumDyeColor color : EnumDyeColor.values()) {
			pulverizerRemoveList.add(new ItemStack(Blocks.WOOL, 1, color.getMetadata()));
			pulverizerRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("quark", "quilted_wool")), 1, color.getMetadata()));
		}
		
		return pulverizerRemoveList;
	}

	public static ArrayList<ItemStack> getJeiRemoveList() {
		ArrayList<ItemStack> jeiRemoveList = new ArrayList<ItemStack>();
		
		jeiRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "coin")), 1, 0));
		jeiRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalexpansion", "dynamo")), 1, 5));
		jeiRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalexpansion", "augment")), 1, 336));
		jeiRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalexpansion", "augment")), 1, 720));
		
		//Remove thermal foundation pigment
		Item dye = Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "dye"));
		
		for(EnumDyeColor color : EnumDyeColor.values()) {
			jeiRemoveList.add(new ItemStack(dye, 1, color.getMetadata()));
		}
		
		return jeiRemoveList;
	}
	
	public static ArrayList<String> getRecipeRemoveList() {
		ArrayList<String> recipesRemoveList = new ArrayList<String>();
		recipesRemoveList.add("thermalexpansion:dynamo_5");//Remove the recipe for the stupid Numismatic Dynamo
		recipesRemoveList.add("thermalexpansion:augment_13");//Numismatic Press
		recipesRemoveList.add("thermalexpansion:augment_38");//Lapidary Calibration
		
		return recipesRemoveList;
	}
	
	public static void preInit() {
	}
	
	public static void init() {		
		//int coinMetas[] = { 0, 1, 64, 65, 66, 67, 68, 69, 70, 71, 72, 96, 97, 98, 99, 100, 101, 102, 103 };
		Item coin = Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "coin"));
		coin.setCreativeTab(null);//Remove the coin from the creative tabs
		
		Item dye = Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "dye"));
		dye.setCreativeTab(null);
	}
	
	public static void postInit() {
		getRecipeRemoveList().forEach(i -> ModMolester.removeRecipe(i));
		getPulverizerRemoveList().forEach(i -> removePulverizerRecipe(i));
		getMintRemoveList().forEach(i -> removeCompactorMintRecipe(i));

		Item quiltedWool = Item.REGISTRY.getObject(new ResourceLocation("quark", "quilted_wool"));
		
		for(ItemStack dye: getSafeDyesList()) {
			addPulverizerRecipe(3000, new ItemStack(Blocks.WOOL, 1, 15 - dye.getMetadata()), new ItemStack(Items.STRING, 4), dye, 15);
			addPulverizerRecipe(3000, new ItemStack(quiltedWool, 1, 15 - dye.getMetadata()), new ItemStack(Items.STRING, 4), dye, 15);
		}
		
		cofh.thermalexpansion.block.dynamo.BlockDynamo.enable[5] = false;
	}
	
	public static void registerRecipes(IForgeRegistry<IRecipe> registry) {
	}
	
	public static void onLoadComplete() {		
		getJeiRemoveList().forEach(i -> ModMolester.removeItemStackFromJEI(i));
		
		//Dirty hack to remove Numismatic Press and Lapidary Calibration from the creative tabs
		try {
			ItemMulti augment = (ItemMulti) Item.REGISTRY.getObject(new ResourceLocation("thermalexpansion", "augment"));
			Field declaredField = ItemMulti.class.getDeclaredField("itemList");
			declaredField.setAccessible(true);
			ArrayList<Integer> list = (ArrayList<Integer>) declaredField.get(augment);
			for(int meta : new int[] {336, 720}) {//meta for Numismatic Press and Lapidary Calibration respectively
				for(int i = 0; i < list.size(); i++) {
					if(list.get(i) == meta) {
						list.remove(i);
						break;
					}
				}
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
}
