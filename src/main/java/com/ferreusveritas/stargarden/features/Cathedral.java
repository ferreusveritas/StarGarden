package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.cathedral.features.lectern.BookManager;
import com.ferreusveritas.stargarden.StarGarden;
import com.ferreusveritas.stargarden.features.cathedral.CCBookHandler;
import com.ferreusveritas.stargarden.features.cathedral.GuiManager;
import com.ferreusveritas.stargarden.features.cathedral.WarpBookHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;

public class Cathedral extends BaseFeature {

	private static int guiIndex = 42;
	
	public static final int WarpBookLecternGuiIndex = guiIndex++;
	public static final int CCBookLecternGuiIndex = guiIndex++;

	@Override
	public void init() {
		super.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(StarGarden.instance, new GuiManager());
	}
	
	@Override
	public void postInit() {
		super.postInit();
		BookManager.add(new WarpBookHandler());
		BookManager.add(new CCBookHandler());
	}
	
}
