package remiliaMarine.tofu.block;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidContainerItem;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.gui.TcGuiHandler;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.tileentity.TileEntityTfStorage;

/**
 * Tofu Force Storage block
 * 
 * @author Tsuteto
 *
 */
public class BlockTfStorage extends BlockTfMachineBase {
	
    private static boolean keepMachineInventory = false;

    public BlockTfStorage(boolean par2, SoundType sound)
    {
        super(par2);
        this.setSoundType(sound);
    }

    @Override
    protected Block getBlockActive()
    {
        return TcBlocks.tfStorageActive;
    }

    @Override
    protected Block getBlockIdle()
    {
        return TcBlocks.tfStorageIdle;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @SuppressWarnings("deprecation")
	@Override
    public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = par1World.getTileEntity(pos);
        
        if (tile != null && (heldItem == null || !(heldItem.getItem() instanceof IFluidContainerItem)))
        {
            par5EntityPlayer.openGui(TofuCraftCore.INSTANCE, TcGuiHandler.GUIID_TF_STORAGE, par1World, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    /**
     * Update which block ID the furnace is using depending on whether or not it is burning
     */
    public static void updateMachineState(boolean par0, World par1World, BlockPos pos)
    {
        EnumFacing facing = par1World.getBlockState(pos).getValue(FACING);
        TileEntity tileEntity = par1World.getTileEntity(pos);
        keepMachineInventory = true;

        if (par0)
        {
            par1World.setBlockState(pos, TcBlocks.tfStorageActive.getDefaultState().withProperty(FACING, facing));
        }
        else
        {
            par1World.setBlockState(pos, TcBlocks.tfStorageIdle.getDefaultState().withProperty(FACING, facing));
        }

        keepMachineInventory = false;

        if (tileEntity != null)
        {
            tileEntity.validate();
            par1World.setTileEntity(pos, tileEntity);
        }
    }

    public void breakBlock(World par1World, BlockPos pos, IBlockState state, int par6)
    {
        super.breakBlock(par1World, pos, state, keepMachineInventory);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World, int i)
    {
        return new TileEntityTfStorage();
    }
}
