package com.ferreusveritas.stargarden;

import dan200.computercraft.shared.media.items.ItemDiskExpanded;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;

public class ComputerCraft implements IFeature {

	public static final String COMPUTERCRAFT = "computercraft";
	
	@Override
	public void preInit() { }

	@Override
	public void init() { }

	@Override
	public void postInit() {
		//Remove oreDict insensitive recipes
		for(int i = 0; i < 16; i++) {
			Vanilla.removeRecipe(COMPUTERCRAFT + ":disk_imposter_" + i);
			Vanilla.removeRecipe(COMPUTERCRAFT + ":disk_imposter_convert_" + i);
		}
	}

	@Override
	public void onLoadComplete() { }

	public void addColoredFloppyRecipe(EnumDyeColor color) {
		
		ItemStack output = ItemDiskExpanded.createFromIDAndColour(-1, null, color.getColorValue());

		GameRegistry.addShapelessRecipe(
			new ResourceLocation(ModConstants.MODID, "coloredFloppy_" + color.getDyeColorName()),//Name
			null,//Group
			output,//Output
			new Ingredient[] {
				Ingredient.fromStacks(new ItemStack(Items.PAPER)),
				Ingredient.fromStacks(new ItemStack(Items.REDSTONE)),
				new OreIngredient("dye" + Vanilla.dyeValues[color.getDyeDamage()])
			}
		);
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		for(EnumDyeColor color : EnumDyeColor.values()) {
			addColoredFloppyRecipe(color);
		}
	}

}
