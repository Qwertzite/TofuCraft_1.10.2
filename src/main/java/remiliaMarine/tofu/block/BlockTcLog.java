package remiliaMarine.tofu.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.versionAdapter.block.TcBlockLog;

public class BlockTcLog extends TcBlockLog implements ITcBlockVariable{
    /** The type of tree this block came from. first two bits of old meta-data */
	public static final PropertyEnum<EnumTcLog> VARIANT = PropertyEnum.<EnumTcLog>create("variant", EnumTcLog.class);
	
    public BlockTcLog()
    {
        super();
        this.setSoundType(SoundType.WOOD);
        this.setHarvestLevel("axe", 0);
    }
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(TcBlocks.tcLog);
    }
    
	@Override
	public String getVariantName(int meta) {
		return "." + EnumTcLog.byMetadata(meta&3).getName();
	}
    
    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
	@Override
    public int damageDropped(IBlockState state)
    {
        return ((EnumTcLog)state.getValue(VARIANT)).getMeta();
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, List<ItemStack> list)
    {
        for (EnumTcLog saplingtype : EnumTcLog.values())
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
		
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, EnumTcLog.byMetadata((meta & 3) % 4));

        switch (meta & 12)
        {
            case 0:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 8:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }

        return iblockstate;    }
    
    /**
     * Convert the BlockState into the correct metadata value
     */
	@Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((EnumTcLog)state.getValue(VARIANT)).getMeta();

        switch ((BlockLog.EnumAxis)state.getValue(LOG_AXIS))
        {
            case X:
                i |= 4;
                break;
            case Z:
                i |= 8;
                break;
            case NONE:
                i |= 12;
                break;
            case Y:
            	break;
        }

        return i;    }
	
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT, LOG_AXIS});
    }
	
	public static enum EnumTcLog implements IStringSerializable{
		APRICOT(0, "apricot");
		
        private static final EnumTcLog[] META_LOOKUP = new EnumTcLog[values().length];
		public int meta;
		public String variantName;
		
		EnumTcLog(int meta, String name) {
			this.meta = meta;
			this.variantName = name;
		}
		
        public static EnumTcLog byMetadata(int meta)
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
        
        static
        {
            for (EnumTcLog enumtype : values())
            {
                META_LOOKUP[enumtype.getMeta()] = enumtype;
            }
        }
	}
}
