package remiliaMarine.tofu.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.versionAdapter.block.TcBlock;

public class BlockTfMachineCase extends TcBlock {
	
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockTfMachineCase()
    {
        super(Material.IRON);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    @Override
    public void onBlockAdded(World par1World, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(par1World, pos, state);
        this.setDefaultDirection(par1World, pos, state);
    }

    /**
     * set a blocks direction
     */
    private void setDefaultDirection(World par1World, BlockPos pos, IBlockState state)
    {
        if (!par1World.isRemote)
        {
            IBlockState blockN = par1World.getBlockState(pos.north());
            IBlockState blockS = par1World.getBlockState(pos.south());
            IBlockState blockW = par1World.getBlockState(pos.west());
            IBlockState blockE = par1World.getBlockState(pos.east());
            EnumFacing facing = state.getValue(FACING);

            if (facing == EnumFacing.NORTH && blockN.isFullBlock() && !blockS.isFullBlock())
            {
                facing = EnumFacing.SOUTH;
            }

            if (facing == EnumFacing.SOUTH && blockS.isFullBlock() && !blockN.isFullBlock())
            {
                facing = EnumFacing.NORTH;
            }

            if (facing == EnumFacing.WEST && blockW.isFullBlock() && !blockE.isFullBlock())
            {
                facing = EnumFacing.EAST;
            }

            if (facing == EnumFacing.EAST && blockE.isFullBlock() && !blockW.isFullBlock())
            {
                facing = EnumFacing.WEST;
            }

            par1World.setBlockState(pos, state.withProperty(FACING, facing), 2);
        }
    }
    
    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }
    
    // ======== state ========

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
}
