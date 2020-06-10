package com.ferreusveritas.stargarden.features.orrery;

import com.ferreusveritas.stargarden.ModConstants;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStoneSymbol extends Block {

	public static final PropertyEnum<BlockStoneSymbol.EnumType> VARIANT = PropertyEnum.<BlockStoneSymbol.EnumType>create("variant", BlockStoneSymbol.EnumType.class);

	public BlockStoneSymbol() {
		super(Material.ROCK, MapColor.GRAY);
		setRegistryName(new ResourceLocation(ModConstants.MODID, "symbol_stone"));
		setUnlocalizedName("symbol_stone");
		setHardness(6.0f);
		setResistance(1.5f);
		setCreativeTab(com.ferreusveritas.cathedral.CathedralMod.tabCathedral);
		
        setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.ARIES));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumType.fromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).getMeta();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return ((EnumType)state.getValue(VARIANT)).getMeta();
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for(EnumType type : EnumType.values()) {
			items.add(new ItemStack(this, 1, type.getMeta()));
		}
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		System.out.println("Meta: " + meta);
		
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
	}
	
	public static enum EnumType implements IStringSerializable {
		ARIES("aries", "aries"),//For first point of Aries 0 degrees
		LIBRA("libra", "libra");//For first point of Libra 180 degrees

		private final String name;
		private final String unlocalizedName;

		private EnumType(String name, String unlocalizedName) {
			this.name = name;
			this.unlocalizedName = unlocalizedName;
		}

		@Override
		public String getName() {
			return name;
		}

		public String getUnlocalizedName() {
			return unlocalizedName;
		}

		public int getMeta() {
			return ordinal();
		}

		public static EnumType fromMeta(int meta) {
			return EnumType.values()[meta];
		}
	}

}
