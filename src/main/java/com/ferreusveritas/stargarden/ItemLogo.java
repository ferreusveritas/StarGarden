package com.ferreusveritas.stargarden;

import java.util.Iterator;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemLogo extends Item {
	
	public static CreativeTabs projectRedCoreTab = null;
	
	public ItemLogo() {
		setRegistryName("logo");
		setUnlocalizedName("logo");
		
		
		setHasSubtypes(true);
		
		projectRedCoreTab = findCreativeTab("projectred.core");
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		
		if(tab.equals(projectRedCoreTab)) {
			items.addAll(getFilteredItemStacks(ProjectRed.resItem, null, ProjectRed.remove));
		}
		
		if(tab.equals(StarGarden.starGardenTab)) {
			items.add(new ItemStack(this, 1, 0));
		}
	}
	
	public static NonNullList<ItemStack> getFilteredItemStacks(Item item, CreativeTabs tab, List<ItemStack> remList) {
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
	
	public static CreativeTabs findCreativeTab(String label) {
		for(CreativeTabs iTab: CreativeTabs.CREATIVE_TAB_ARRAY) {
			if(iTab.getTabLabel().equals(label)) {
				return iTab;
			}
		}
		return null;
	}
	
}
