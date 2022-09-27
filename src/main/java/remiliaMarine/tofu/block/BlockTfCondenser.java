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
import remiliaMarine.tofu.tileentity.TileEntityTfCondenser;

public class BlockTfCondenser extends BlockTfMachineBase {
	
    private static boolean keepMachineInventory = false;

    public BlockTfCondenser(boolean isActive, SoundType sound)
    {
        super(isActive);
        this.setGuiScreen(TcGuiHandler.GUIID_TF_CONDENSER);
        this.setSoundType(sound);
    }

    @Override
    protected Block getBlockActive()
    {
        return TcBlocks.tfCondenserActive;
    }

    @Override
    protected Block getBlockIdle()
    {
        return TcBlocks.tfCondenserIdle;
    }

    /**
     * Update which block ID the furnace is using depending on whether or not it is burning
     */
    public static void updateMachineState(boolean isActive, World worldIn, BlockPos pos)
    {
        EnumFacing facing = worldIn.getBlockState(pos).getValue(FACING);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepMachineInventory = true;

        if (isActive)
        {
            worldIn.setBlockState(pos, TcBlocks.tfCondenserActive.getDefaultState().withProperty(FACING, facing));
        }
        else
        {
            worldIn.setBlockState(pos, TcBlocks.tfCondenserIdle.getDefaultState().withProperty(FACING, facing));
        }

        keepMachineInventory = false;

        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

//    @Override
//    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
//    {
//        BlockUtils.onNeighborBlockChange_RedstoneSwitch(this, p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
//    }

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
        return new TileEntityTfCondenser();
    }
}
