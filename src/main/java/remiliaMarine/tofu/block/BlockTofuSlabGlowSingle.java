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

public class BlockTofuSlabGlowSingle extends BlockTofuSlabBase {

	public static final PropertyEnum<TofuSlabGlowVariant> VARIANT = PropertyEnum.<TofuSlabGlowVariant>create("variant", TofuSlabGlowVariant.class);

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(TcBlocks.tofuSingleSlabGlow);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this, 1, state.getValue(VARIANT).getMetadata());
	}

	@Override
	public boolean isFragile(IBlockState state) {
		return false;
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        if (itemIn != Item.getItemFromBlock(TcBlocks.tofuDoubleSlabGlow))
        {
            for (TofuSlabGlowVariant enumtype : TofuSlabGlowVariant.values())
            {
                list.add(new ItemStack(itemIn, 1, enumtype.getMetadata()));
            }
        }
    }

	public ModelResourceLocation[] getResourceLocation () {
		ModelResourceLocation[] location = new ModelResourceLocation[TofuSlabGlowVariant.values().length];
		for (int i = 0; i < location.length; i++) {
			location[i] = new ModelResourceLocation(TofuCraftCore.RES + "tofuSlab" + Utils.capitalize(TofuSlabGlowVariant.byMetaData(i).getName()), "inventory");
		}

		return location;
	}

	@Override
	public String getUnlocalizedName(int meta) {
		return super.getUnlocalizedName() + "." + TofuSlabGlowVariant.byMetaData(meta).getName();
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
		return TofuSlabGlowVariant.byMetaData(stack.getMetadata());
	}

	// ======== block state ========

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = super.getDefaultState();
		if (!this.isDouble()) state = state.withProperty(HALF, (meta & 8) > 0 ? BlockSlab.EnumBlockHalf.TOP : BlockSlab.EnumBlockHalf.BOTTOM);
		return state.withProperty(VARIANT, TofuSlabGlowVariant.byMetaData(meta & 7));
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

	public static enum TofuSlabGlowVariant implements IStringSerializable {
	    GLOW(0, "glow", TofuMaterial.GLOW);

        private static final TofuSlabGlowVariant[] META_LOOKUP = new TofuSlabGlowVariant[values().length];
		private int meta;
		private String name;
		private TofuMaterial material;

		TofuSlabGlowVariant(int meta, String name, TofuMaterial material) {
			this.meta = meta;
			this.name= name;
			this.material = material;
		}

        static
        {
            for (TofuSlabGlowVariant enumtype : values())
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

		public TofuMaterial getMaterial() {
			return this.material;
		}

    	public static TofuSlabGlowVariant byMetaData(int meta) {

            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

    		return META_LOOKUP[meta];
    	}

	}
}
