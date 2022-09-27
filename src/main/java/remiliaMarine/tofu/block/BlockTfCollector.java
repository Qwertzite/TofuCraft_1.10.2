package remiliaMarine.tofu.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.tileentity.TileEntityTfCollector;

public class BlockTfCollector extends BlockTfMachineBase {
    private static boolean keepMachineInventory = false;

    public BlockTfCollector(SoundType sound)
    {
        super(true);
        this.setSoundType(sound);
    }

    @Override
    protected Block getBlockActive()
    {
        return TcBlocks.tfCollector;
    }

    @Override
    protected Block getBlockIdle()
    {
        return TcBlocks.tfCollector;
    }

    @Override
    public void randomDisplayTick(IBlockState state, World par1World, BlockPos pos, Random par5Random)
    {
    }

    @Override
    public void breakBlock(World par1World, BlockPos pos, IBlockState state)
    {
        super.breakBlock(par1World, pos, state, keepMachineInventory);
    }

    @Override
    public TileEntity createNewTileEntity(World par1World, int i)
    {
        return new TileEntityTfCollector();
    }
}
