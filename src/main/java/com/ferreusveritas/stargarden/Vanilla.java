package com.ferreusveritas.stargarden;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class Vanilla implements IFeature {

	@Override
	public void preInit() { }

	@Override
	public void init() { }

	@Override
	public void postInit() { 
		
		ItemStack blue = new ItemStack(Items.DYE, 1, 4);

		try {
			Field OREDICTIONARY_IDTOSTACK = OreDictionary.class.getDeclaredField("idToStack");
			OREDICTIONARY_IDTOSTACK.setAccessible(true);
			List<NonNullList<ItemStack>> ores = (List<NonNullList<ItemStack>>) OREDICTIONARY_IDTOSTACK.get(null);
			ores.get(OreDictionary.getOreID("dyeBlue")).remove(blue);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			System.err.println(e.getMessage());
		}

	}

	@Override
	public void onLoadComplete() { }

	@Override
	public void oreRegister(String oreName, ItemStack ore) { }

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) { }

}
