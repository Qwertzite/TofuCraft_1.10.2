package remiliaMarine.tofu.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockTfMachineWithMultipleTypesOnNBT extends BlockTfMachineBase implements ITcBlockVariable {

	/** 4th bit of meta-data */
	public static final PropertyBool NO_DROP = PropertyBool.create("no_drop");
	
    protected BlockTfMachineWithMultipleTypesOnNBT(boolean isActive)
    {
        super(isActive);
    }

    @Override
    public void onBlockHarvested(World par1World, BlockPos pos, IBlockState state, EntityPlayer par6EntityPlayer)
    {
        if (par6EntityPlayer.capabilities.isCreativeMode)
        {
            // Set 8 for no drops
            par1World.setBlockState(pos, state.withProperty(NO_DROP, true), 4);

        }

        // Force to drop items here before removing tileentity
        this.dropBlockAsItem(par1World, pos, state, 0);

        super.onBlockHarvested(par1World, pos, state, par6EntityPlayer);
    }

    @Override
    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;
        
        if (!state.getValue(NO_DROP)) // no drops when the metadata is 8
        {
            // Called twice, but drops items once when the tileentity is alive. Based on BlockSkull
            TileEntity te = world.getTileEntity(pos);
            if (te != null)
            {
                Item drop = this.getItemDropped(state, rand, fortune);
                if (drop != null)
                {
                    ret.add(new ItemStack(drop, 1, this.damageDropped(state)));
                }
            }
        }
        return ret;
    }
    
    // ======== blockstate ========
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(NO_DROP, (meta & 8) > 0);
    }

    @Override
    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
    	int i = 0;
    	i += ((EnumFacing)state.getValue(FACING)).getIndex();
    	i += state.getValue(NO_DROP) ? 8 : 0;
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, NO_DROP});
    }
}
