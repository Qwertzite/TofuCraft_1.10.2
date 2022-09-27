package remiliaMarine.tofu.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.item.TofuMaterial;
import remiliaMarine.tofu.util.Utils;

public class BlockTofuSlabSingle1 extends BlockTofuSlabBase {

	public static final PropertyEnum<TofuSlabVariant1> VARIANT = PropertyEnum.<TofuSlabVariant1>create("variant", TofuSlabVariant1.class);

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(TcBlocks.tofuSingleSlab1);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this, 1, state.getValue(VARIANT).getMetadata());
	}

	@Override
	public boolean isFragile(IBlockState state) {
		return state.getValue(VARIANT) == TofuSlabVariant1.KINU;
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        if (itemIn != Item.getItemFromBlock(TcBlocks.tofuDoubleSlab1))
        {
            for (TofuSlabVariant1 enumtype : TofuSlabVariant1.values())
            {
                list.add(new ItemStack(itemIn, 1, enumtype.getMetadata()));
            }
        }
    }

	@Override
	public String getUnlocalizedName(int meta) {
		return super.getUnlocalizedName() + "." + TofuSlabVariant1.byMetaData(meta).getUnlocalisedName();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(VARIANT).getMetadata();
	}

	@Override
	public boolean isDouble() {
		return false;
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return VARIANT;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return TofuSlabVariant1.byMetaData(stack.getMetadata());
	}

	public ModelResourceLocation[] getResourceLocation () {
		ModelResourceLocation[] location = new ModelResourceLocation[TofuSlabVariant1.values().length];
		for (int i = 0; i < location.length; i++) {
			location[i] = new ModelResourceLocation(TofuCraftCore.RES + "tofuSlab" + Utils.capitalize(TofuSlabVariant1.byMetaData(i).getUnlocalisedName()), "inventory");
		}

		return location;
	}

	// ======== block state ========

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = super.getDefaultState();
		if (!this.isDouble()) state = state.withProperty(HALF, (meta & 8) > 0 ? BlockSlab.EnumBlockHalf.TOP : BlockSlab.EnumBlockHalf.BOTTOM);
		return state.withProperty(VARIANT, TofuSlabVariant1.byMetaData(meta & 7));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i += state.getValue(VARIANT).getMetadata();
		if (!this.isDouble()) i += (state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) ? 8 : 0;
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
        return this.isDouble() ?
        		new BlockStateContainer(this, new IProperty[] {VARIANT}) :
        			new BlockStateContainer(this, new IProperty[] {HALF, VARIANT});
	}

	public static enum TofuSlabVariant1 implements IStringSerializable {
	    KINU(0, "kinu", TofuMaterial.KINU),
	    MOMEN(1, "momen", TofuMaterial.MOMEN),
	    ISHI(2, "ishi", TofuMaterial.ISHI),
	    METAL(3, "metal", TofuMaterial.METAL),
	    DRIED(4, "dried", TofuMaterial.DRIED),
	    FRIEDPOUCH(5, "friedpouch", TofuMaterial.FRIEDPOUCH, "friedPouch"),
	    FRIED(6, "fried", TofuMaterial.FRIED),
	    EGG(7, "egg", TofuMaterial.EGG);

        private static final TofuSlabVariant1[] META_LOOKUP = new TofuSlabVariant1[values().length];
		private int meta;
		private String name;
		private String unlocalisedName;
		private TofuMaterial material;

		TofuSlabVariant1(int meta, String name, TofuMaterial material) {
			this.meta = meta;
			this.name= name;
			this.unlocalisedName = name;
			this.material = material;
		}

		TofuSlabVariant1(int meta, String name, TofuMaterial material, String unlocalised) {
			this.meta = meta;
			this.name= name;
			this.unlocalisedName = unlocalised;
			this.material = material;
		}

        static
        {
            for (TofuSlabVariant1 enumtype : values())
            {
                META_LOOKUP[enumtype.getMetadata()] = enumtype;
            }
        }

        public int getMetadata() {
        	return this.meta;
        }

		@Override
		public String getName() {
			return this.name;
		}

		public String getUnlocalisedName() {
			return this.unlocalisedName;
		}

		public TofuMaterial getMaterial() {
			return this.material;
		}

    	public static TofuSlabVariant1 byMetaData(int meta) {

            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

    		return META_LOOKUP[meta];
    	}

	}
}
