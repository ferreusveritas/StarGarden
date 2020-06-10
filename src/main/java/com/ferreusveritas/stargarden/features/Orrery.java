package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.cathedral.proxy.ModelHelper;
import com.ferreusveritas.stargarden.ModConstants;
import com.ferreusveritas.stargarden.features.orrery.BlockStoneSymbol;
import com.ferreusveritas.stargarden.features.orrery.EntityOrreryArm;
import com.ferreusveritas.stargarden.features.orrery.RenderEntityOrreryArm;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class Orrery extends BaseFeature {

	public static final String ORRERY = "orrery";

	public static final int OrreryArmEntityId = 1;

	public Block symbolStone;

	@Override
	public void postInit() {
	}

	@Override
	public void createBlocks() {
		symbolStone = new BlockStoneSymbol();
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		registry.register(symbolStone);
	}

	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		registry.register(new ItemMultiTexture(symbolStone, symbolStone, new ItemMultiTexture.Mapper() {
			public String apply(ItemStack stack) {
				return BlockStoneSymbol.EnumType.fromMeta(stack.getMetadata()).getUnlocalizedName();
			}
		}).setRegistryName(symbolStone.getRegistryName()));
	}

	@Override
	public void registerEntities(IForgeRegistry<EntityEntry> registry) {
		EntityRegistry.registerModEntity(new ResourceLocation(ModConstants.MODID, "orrery_arm"), EntityOrreryArm.class, "orrery_arm", OrreryArmEntityId, ModConstants.MODID, 32, 1, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityOrreryArm.class, manager -> new RenderEntityOrreryArm(manager));

		for(BlockStoneSymbol.EnumType type: BlockStoneSymbol.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(symbolStone), type.getMeta(), new ResourceLocation(ModConstants.MODID, symbolStone.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
	}

}
