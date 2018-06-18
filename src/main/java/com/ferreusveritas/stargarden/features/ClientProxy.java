package com.ferreusveritas.stargarden.features;

import net.minecraft.client.util.RecipeBookClient;

public class ClientProxy extends CommonProxy {

	@Override
	public void onLoadComplete() {
		super.onLoadComplete();
		RecipeBookClient.rebuildTable();
	}
	
}
