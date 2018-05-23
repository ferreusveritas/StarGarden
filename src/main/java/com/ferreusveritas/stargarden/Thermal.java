package com.ferreusveritas.stargarden;

import java.lang.reflect.Field;
import java.util.ArrayList;

import cofh.core.item.ItemMulti;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class Thermal {
	
	public static final String MOD_ID = "thermalexpansion";
	public static final String INPUT = "input";
	public static final String OUTPUT = "output";
	public static final String ENERGY = "energy";
	public static final String PRIMARY_INPUT = "primaryInput";
	public static final String SECONDARY_INPUT = "secondaryInput";
	public static final String PRIMARY_OUTPUT = "primaryOutput";
	public static final String SECONDARY_OUTPUT = "secondaryOutput";
	public static final String SECONDARY_CHANCE = "secondaryChance";
	public static final String REMOVE_COMPACTOR_RECIPE = "removecompactorrecipe";
	public static final String REMOVE_COMPACTOR_MINT_RECIPE = "removecompactormintrecipe";
	public static final String REMOVE_FURNACE_RECIPE = "removefurnacerecipe";
	public static final String ADD_SMELTER_RECIPE = "addsmelterrecipe";
	
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
		NBTTagCompound toSend = new NBTTagCompound();
		
		toSend.setTag(INPUT, new NBTTagCompound());
		
		input.writeToNBT(toSend.getCompoundTag(INPUT));
		FMLInterModComms.sendMessage(MOD_ID, REMOVE_COMPACTOR_MINT_RECIPE, toSend);
	}
	
	public static void removeCompactorRecipe(ItemStack input) {
		if (input.isEmpty()) {
			return;
		}
		
		NBTTagCompound toSend = new NBTTagCompound();
		toSend.setTag(INPUT, new NBTTagCompound());
		
		input.writeToNBT(toSend.getCompoundTag(INPUT));
		FMLInterModComms.sendMessage(MOD_ID, REMOVE_COMPACTOR_RECIPE, toSend);
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
	
	public static void init() {
		
		ArrayList<ItemStack> inputs = new ArrayList<>();
		
		inputs.add(new ItemStack(Blocks.IRON_BLOCK));
		inputs.add(new ItemStack(Items.IRON_INGOT));
		inputs.add(new ItemStack(Items.IRON_NUGGET));
		
		inputs.add(new ItemStack(Blocks.GOLD_BLOCK));
		inputs.add(new ItemStack(Items.GOLD_INGOT));
		inputs.add(new ItemStack(Items.GOLD_NUGGET));
		
		for(int i = 0; i <= 9; i++) { //Copper, Tin, Silver, Lead, Aluminum, Nickle, Platinum, Iridium, Mana Infused
			inputs.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "storage")), 1, i));//Blocks
			inputs.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "material")), 1, i + 128));//Ingots
			inputs.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "material")), 1, i + 192));//Nuggets
		}
		
		for(int i = 0; i <= 8; i++) { //Steel, Electrum, Invar, Bronze, Constantan, Signalum, Lumium, Enderium
			inputs.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "storage_alloy")), 1, i));//Blocks
			inputs.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "material")), 1, i + 160));//Ingots
			inputs.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "material")), 1, i + 224));//Nuggets
		}
		
		inputs.forEach(i -> removeCompactorMintRecipe(i));
		
		//int coinMetas[] = { 0, 1, 64, 65, 66, 67, 68, 69, 70, 71, 72, 96, 97, 98, 99, 100, 101, 102, 103 };
		Item coin = Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "coin"));
		coin.setCreativeTab(null);//Remove the coin from the creative tabs
	}
	
	public static void postInit() {
		ModMolester.removeRecipe("thermalexpansion:dynamo_5");//Remove the recipe for the stupid Numismatic Dynamo
		ModMolester.removeRecipe("thermalexpansion:augment_13");//Numismatic Press
		ModMolester.removeRecipe("thermalexpansion:augment_38");//Lapidary Calibration
		
		cofh.thermalexpansion.block.dynamo.BlockDynamo.enable[5] = false;
	}
	
	public static void onLoadComplete() {
		ModMolester.removeItemStackFromJEI(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "coin")), 1, 0));
		ModMolester.removeItemStackFromJEI(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalexpansion", "dynamo")), 1, 5));
		ModMolester.removeItemStackFromJEI(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalexpansion", "augment")), 1, 336));
		ModMolester.removeItemStackFromJEI(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalexpansion", "augment")), 1, 720));
		
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
