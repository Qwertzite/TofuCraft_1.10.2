package remiliaMarine.tofu.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.versionAdapter.block.TcBlockLeaves;

public class BlockTcLeaves extends TcBlockLeaves implements ITcBlockVariable{
	/**
	 * VARIANT: first two bits of meta-data<br>
	 * DACAYABLE: 3rd bit. 0-true<br>
	 * CHECK_DECAY: 4th bit. 1-true
	 * */
	public static final PropertyEnum<EnumLeaveTypes> VARIANT = PropertyEnum.<EnumLeaveTypes>create("variant", EnumLeaveTypes.class);
	
    public BlockTcLeaves()
    {
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumLeaveTypes.APRICOT).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
    }
    
    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        if (!worldIn.isRemote)
        {
            if (state.getValue(CHECK_DECAY) && state.getValue(DECAYABLE))
            {
                int type = state.getValue(VARIANT).getMeta();
                byte var8 = 20;

                if (type == 0 && worldIn.rand.nextInt(var8) == 0)
                {
                    Item var9 = this.getItemDropped(state, worldIn.rand, fortune);
                    Block.spawnAsEntity(worldIn, pos, new ItemStack(var9, 1, 0));
                }
                if (type == 1 && worldIn.rand.nextInt(3) == 0)
                {
                    Block.spawnAsEntity(worldIn, pos, new ItemStack(TcItems.apricot, 1, 0));
                }
                if (type == 2 && worldIn.rand.nextInt(var8) == 0)
                {
                    Item var9 = this.getItemDropped(state, worldIn.rand, fortune);
                    Block.spawnAsEntity(worldIn, pos, new ItemStack(var9, 1, 1));
                }
            }
        }
    }
    
	@Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
    {
        if (!worldIn.isRemote && stack != null && stack.getItem() == Items.SHEARS)
        {
            player.addStat(StatList.getBlockStats(this));
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }
	
    @Override
    public List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        return Arrays.asList(new ItemStack(this, 1, world.getBlockState(pos).getValue(VARIANT).getMeta()));
    }
	
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random)
    {
        return par1Random.nextInt(1) == 0 ? 1 : 0;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(TcBlocks.tcSapling);
    }
    
	@Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockTcLeaves.EnumLeaveTypes)state.getValue(VARIANT)).getMeta());
    }
    
    /** just to implement. always returns BlockPlanks.EnumType.OAK */
	@Override
    @Deprecated
    public BlockPlanks.EnumType getWoodType(int meta)
    {
        return BlockPlanks.EnumType.OAK;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return this.leavesFancy ? BlockRenderLayer.CUTOUT_MIPPED: BlockRenderLayer.SOLID;
    }
    
	@Override
	public String getVariantName(int meta) {
		return "." + EnumLeaveTypes.byMetadata(meta&3).getUnlocalizedName();
	}
	
    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
	@Override
    public int damageDropped(IBlockState state)
    {
        return ((EnumLeaveTypes)state.getValue(VARIANT)).getMeta();
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, List<ItemStack> list)
    {
        for (EnumLeaveTypes saplingtype : EnumLeaveTypes.values())
        {
            list.add(new ItemStack(this, 1, saplingtype.getMeta()));
        }
    }
	
	// ========  property  ========
    /**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState getStateFromMeta(int meta) {
    	return this.getDefaultState().withProperty(VARIANT, EnumLeaveTypes.byMetadata(meta & 3)).withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }
    
    /**
     * Convert the BlockState into the correct metadata value
     */
	@Override
    public int getMetaFromState(IBlockState state)
    {
		int i = 0;
		
		i |= state.getValue(VARIANT).getMeta();
		
        if (!((Boolean)state.getValue(DECAYABLE)).booleanValue())
        {
            i |= 4;
        }

        if (((Boolean)state.getValue(CHECK_DECAY)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }
	
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT, CHECK_DECAY, DECAYABLE});
    }
	
	public static enum EnumLeaveTypes implements IStringSerializable {
		APRICOT(0, "apricot", "apricot"),
		APRICOT_F(1, "apricot_f", "apricot"),
		TOFU(2, "tofu", "tofu");
		
        private static final EnumLeaveTypes[] META_LOOKUP = new EnumLeaveTypes[values().length];
		public int meta;
		public String variantName;
		public String unlocalizedName;
		
		EnumLeaveTypes(int meta, String name, String local) {
			this.meta = meta;
			this.variantName = name;
			this.unlocalizedName = local;
		}
		
        public static EnumLeaveTypes byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }
            return META_LOOKUP[meta];
        }
		
		public int getMeta() {
			return this.meta;
		}
		
		@Override
		public String getName() {
			return this.variantName;
		}
		
		public String getUnlocalizedName() {
			return this.unlocalizedName;
		}
		
        static
        {
            for (EnumLeaveTypes enumtype : values())
            {
                META_LOOKUP[enumtype.getMeta()] = enumtype;
            }
        }
	}
}
