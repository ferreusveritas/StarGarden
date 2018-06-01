package com.ferreusveritas.stargarden;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import cofh.core.item.ItemMulti;
import cofh.thermalexpansion.util.managers.machine.CentrifugeManager;
import cofh.thermalexpansion.util.managers.machine.CompactorManager;
import cofh.thermalexpansion.util.managers.machine.FurnaceManager;
import cofh.thermalexpansion.util.managers.machine.PulverizerManager;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;

public class Thermal implements IFeature {

	//private static final String INPUT = "input";
	//private static final String OUTPUT = "output";
	private static final String ENERGY = "energy";
	private static final String PRIMARY_INPUT = "primaryInput";
	private static final String SECONDARY_INPUT = "secondaryInput";
	private static final String PRIMARY_OUTPUT = "primaryOutput";
	private static final String SECONDARY_OUTPUT = "secondaryOutput";
	private static final String SECONDARY_CHANCE = "secondaryChance";
	private static final String ADD_SMELTER_RECIPE = "addsmelterrecipe";
	
	private static final String BIOMESOPLENTY = "biomesoplenty";
	private static final String QUARK = "quark";
	private static final String THERMALEXPANSION = "thermalexpansion";
	private static final String THERMALFOUNDATION = "thermalfoundation";
	
	public static void removeFurnaceRecipe(ItemStack input) {
		
		if (input.isEmpty()) {
			return;
		}
		
		FurnaceManager.removeRecipe(input);
	}
	
	private static void removeCompactorMintRecipe(ItemStack input) {
		
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
		FMLInterModComms.sendMessage(THERMALEXPANSION, ADD_SMELTER_RECIPE, toSend);
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
			mintRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(THERMALFOUNDATION, "storage")), 1, i));//Blocks
			mintRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(THERMALFOUNDATION, "material")), 1, i + 128));//Ingots
			mintRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(THERMALFOUNDATION, "material")), 1, i + 192));//Nuggets
		}
		
		for(int i = 0; i <= 8; i++) { //Steel, Electrum, Invar, Bronze, Constantan, Signalum, Lumium, Enderium
			mintRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(THERMALFOUNDATION, "storage_alloy")), 1, i));//Blocks
			mintRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(THERMALFOUNDATION, "material")), 1, i + 160));//Ingots
			mintRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(THERMALFOUNDATION, "material")), 1, i + 224));//Nuggets
		}
		
		return mintRemoveList;
	}

	public static ArrayList<ItemStack> getSafeDyesList() {
		ArrayList<ItemStack> safeDyes = new ArrayList<ItemStack>();
		
		safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(BIOMESOPLENTY, "black_dye"))));//Black
		safeDyes.add(new ItemStack(Items.DYE, 1, 1));//Red
		safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(BIOMESOPLENTY, "green_dye"))));//Green
		safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(BIOMESOPLENTY, "brown_dye"))));//Brown
		safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(BIOMESOPLENTY, "blue_dye"))));//Blue
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
		safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(BIOMESOPLENTY, "white_dye"))));//White
		
		return safeDyes;
	}
	
	public static ArrayList<ItemStack> getPulverizerRemoveList() {
		ArrayList<ItemStack> pulverizerRemoveList = new ArrayList<ItemStack>();

		Item quiltedWool = Item.REGISTRY.getObject(new ResourceLocation(QUARK, "quilted_wool"));
		
		for(EnumDyeColor color : EnumDyeColor.values()) {
			pulverizerRemoveList.add(new ItemStack(Blocks.WOOL, 1, color.getMetadata()));
			pulverizerRemoveList.add(new ItemStack(quiltedWool, 1, color.getMetadata()));
			pulverizerRemoveList.add(new ItemStack(Block.REGISTRY.getObject(new ResourceLocation(QUARK, "colored_flowerpot_" + color.getDyeColorName()))));
		}
		
		return pulverizerRemoveList;
	}

	public static ArrayList<ItemStack> getJeiRemoveList() {
		ArrayList<ItemStack> jeiRemoveList = new ArrayList<ItemStack>();
		jeiRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(THERMALFOUNDATION, "coin")), 1, 0));
		jeiRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(THERMALEXPANSION, "dynamo")), 1, 5));
		jeiRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(THERMALEXPANSION, "augment")), 1, 336));
		jeiRemoveList.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(THERMALEXPANSION, "augment")), 1, 720));
		return jeiRemoveList;
	}
	
	public static ArrayList<String> getRecipeRemoveList() {
		ArrayList<String> recipesRemoveList = new ArrayList<String>();
		recipesRemoveList.add(THERMALFOUNDATION + ":dynamo_5");//Remove the recipe for the stupid Numismatic Dynamo
		recipesRemoveList.add(THERMALFOUNDATION + ":augment_13");//Numismatic Press
		recipesRemoveList.add(THERMALFOUNDATION + ":augment_38");//Lapidary Calibration

		for(int i = 5; i <= 9; i++) {
			recipesRemoveList.add(THERMALEXPANSION + ":capacitor_" + i);//Capacitor Coloring
		}
		
		for(int i = 5; i <= 9; i++) {
			recipesRemoveList.add(THERMALEXPANSION + ":reservoir_" + i);//Reservoir Coloring
		}
		
		for(int i = 13; i <= 22; i++) {
			recipesRemoveList.add(THERMALEXPANSION + ":satchel_" + i);//Satchel Coloring
		}

		for(int i = 0; i <= 15; i++) {
			recipesRemoveList.add(THERMALFOUNDATION + ":rockwool" +  (i != 0 ? ("_" + i) : ""));//Rockwool Coloring
		}
		
		return recipesRemoveList;
	}
	
	@Override
	public void preInit() {
	}
	
	@Override
	public void init() {		
	}
	
	@Override
	public void postInit() {
		
		//int coinMetas[] = { 0, 1, 64, 65, 66, 67, 68, 69, 70, 71, 72, 96, 97, 98, 99, 100, 101, 102, 103 };
		Item coin = Item.REGISTRY.getObject(new ResourceLocation(THERMALFOUNDATION, "coin"));
		coin.setCreativeTab(null);//Remove the coin from the creative tabs
		
		Item pigment = Item.REGISTRY.getObject(new ResourceLocation(THERMALFOUNDATION, "dye"));
		pigment.setCreativeTab(null);
		
		getRecipeRemoveList().forEach(i -> Vanilla.removeRecipe(i));
		getPulverizerRemoveList().forEach(i -> PulverizerManager.removeRecipe(i));
		getMintRemoveList().forEach(i -> removeCompactorMintRecipe(i));
		
		for(EnumDyeColor c: EnumDyeColor.values()) {
			//Remove Thermal Expansion Pigments
			Vanilla.removeOre(new ItemStack(pigment, 1, c.getMetadata()), null);
			//Remove concrete powder recipes from Centrifugal Separator
			CentrifugeManager.removeRecipe(new ItemStack(Blocks.CONCRETE_POWDER, 2, c.getMetadata()));
		}
		
		Item quiltedWool = Item.REGISTRY.getObject(new ResourceLocation(QUARK, "quilted_wool"));
		ItemStack sand = new ItemStack(Blocks.SAND);
		ItemStack gravel = new ItemStack(Blocks.GRAVEL);

		int meta = 15;
		
		ArrayList<ItemStack> safeDyesList = getSafeDyesList();
		
		//Recreate pulverizer recipes to produce safe dyes
		for(ItemStack dye: safeDyesList) {
			PulverizerManager.addRecipe(3000, new ItemStack(Blocks.WOOL, 1, meta), new ItemStack(Items.STRING, 4), dye, 15);
			PulverizerManager.addRecipe(3000, new ItemStack(quiltedWool, 1, meta), new ItemStack(Items.STRING, 4), dye, 15);
			CentrifugeManager.addRecipe(2000, new ItemStack(Blocks.CONCRETE_POWDER, 2, meta), Arrays.asList(sand, gravel, dye), Arrays.asList(100, 100, 10), null);
			meta--;
		}
		
		//Create pulverizer recipes for vanilla to safe dyes
		for(EnumDyeColor color : new EnumDyeColor[] { EnumDyeColor.BLACK, EnumDyeColor.GREEN, EnumDyeColor.BROWN, EnumDyeColor.BLUE }) {
			ItemStack safeDye = safeDyesList.get(color.getDyeDamage()).copy();
			safeDye.setCount(4);
			PulverizerManager.addRecipe(2000, new ItemStack(Items.DYE, 1, color.getDyeDamage()), safeDye);
		}
		
		//Force options to disable Numismatic Dynamo
		cofh.thermalexpansion.block.dynamo.BlockDynamo.enable[5] = false;
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		
		//Recreate rockwool recipes to respect Ore Dictionary Dyes
		for(EnumDyeColor color : EnumDyeColor.values()) {
			GameRegistry.addShapelessRecipe(
					new ResourceLocation(ModConstants.MODID, "coloredRockwool_" + color.getDyeColorName()),//Name
					null,//Group
					new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(THERMALFOUNDATION, "rockwool")), 1, color.getDyeDamage()),//Output
					new Ingredient[] {
							new OreIngredient("blockRockwool"),
							new OreIngredient("dye" + Vanilla.dyeValues[color.getDyeDamage()])
					}
				);
		}

	}
	
	@Override
	public void onLoadComplete() {		
		getJeiRemoveList().forEach(i -> Vanilla.removeItemStackFromJEI(i));
		
		//Dirty hack to remove Numismatic Press and Lapidary Calibration from the creative tabs
		try {
			ItemMulti augment = (ItemMulti) Item.REGISTRY.getObject(new ResourceLocation(THERMALEXPANSION, "augment"));
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
