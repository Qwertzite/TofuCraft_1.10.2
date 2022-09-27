package remiliaMarine.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.gui.TcGuiHandler;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.tileentity.TileEntityTfOven;

public class BlockTfOven extends BlockTfMachineBase {

    private static boolean keepMachineInventory = false;

    public BlockTfOven(boolean isActive, SoundType sound)
    {
        super(isActive);
        setGuiScreen(TcGuiHandler.GUIID_TF_OVEN);
        this.setSoundType(sound);
    }
    
    @Override
    protected Block getBlockActive()
    {
        return TcBlocks.tfOvenActive;
    }

    @Override
    protected Block getBlockIdle()
    {
        return TcBlocks.tfOvenIdle;
    }

    /**
     * Update which block ID the furnace is using depending on whether or not it is burning
     */
    public static void updateMachineState(boolean par0, World par1World, BlockPos pos)
    {
        EnumFacing facing = par1World.getBlockState(pos).getValue(FACING);
        TileEntity tileentity = par1World.getTileEntity(pos);
        keepMachineInventory = true;

        if (par0)
        {
            par1World.setBlockState(pos, TcBlocks.tfOvenActive.getDefaultState().withProperty(FACING, facing));
        }
        else
        {
            par1World.setBlockState(pos, TcBlocks.tfOvenIdle.getDefaultState().withProperty(FACING, facing));
        }

        keepMachineInventory = false;

        if (tileentity != null)
        {
            tileentity.validate();
            par1World.setTileEntity(pos, tileentity);
        }
    }

    @Override
    public void breakBlock(World par1World, BlockPos pos, IBlockState state)
    {
        super.breakBlock(par1World, pos, state, keepMachineInventory);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World, int i)
    {
        return new TileEntityTfOven();
    }
}
