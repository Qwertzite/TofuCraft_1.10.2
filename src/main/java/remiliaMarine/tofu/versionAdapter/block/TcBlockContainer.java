package remiliaMarine.tofu.versionAdapter.block;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * for 1.10.2
 */
public abstract class TcBlockContainer extends BlockContainer {

    protected TcBlockContainer(Material materialIn)
    {
        this(materialIn, materialIn.getMaterialMapColor());
    }

    protected TcBlockContainer(Material materialIn, MapColor color)
    {
        super(materialIn, color);
    }
    
    
    /**
     * 1.10.2: {@link Block#getCollisionBoundingBox(IBlockState, World, BlockPos)}<br>
     * 1.12.2: {@link Block#getCollisionBoundingBox(IBlockState, IBlockAccess, BlockPos)}
     */
    @Deprecated
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    	return super.getCollisionBoundingBox(blockState, (World)worldIn, pos);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
    	return this.getCollisionBoundingBox(blockState, (IBlockAccess)worldIn, pos);
    }
    
    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     * 1.10.2: {@link Block#onBlockPlaced(World, BlockPos, EnumFacing, float, float, float, int, EntityLivingBase)}<br>
     * 1.12.2: {@link Block#getStateForPlacement(World, BlockPos, EnumFacing, float, float, float, int, EntityLivingBase)}
     */
    @SuppressWarnings("deprecation")
	@Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
    }
    
    /**
     * Called when the block is right clicked by a player.
     * 1.10.2: {@link Block#onBlockActivated(World, BlockPos, IBlockState, EntityPlayer, EnumHand, ItemStack, EnumFacing, float, float, float)}<br>
     * 1.12.2: {@link Block#onBlockActivated(World, BlockPos, IBlockState, EntityPlayer, EnumHand, EnumFacing, float, float, float)}
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return false;
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	return this.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
