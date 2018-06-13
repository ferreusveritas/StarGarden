package com.ferreusveritas.stargarden.items;

import com.ferreusveritas.stargarden.features.Vanilla;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemLogo extends Item {
	
	public ItemLogo() {
		setRegistryName("logo");
		setUnlocalizedName("logo");
		
		setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		
		Vanilla.getSubItems(tab, items);//Item Laundering.
		
		if(tab.equals(CreativeTabs.MISC)) {
			items.add(new ItemStack(this, 1, 0));
		}
	}
	

	
}
