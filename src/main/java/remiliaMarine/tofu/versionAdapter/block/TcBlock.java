package remiliaMarine.tofu.versionAdapter.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * for 1.10.2
 */
public class TcBlock extends Block {

    public TcBlock(Material par2Material)
    {
        super(par2Material);
    }
    
    @Override
    public TcBlock setUnlocalizedName(String name) {
    	super.setUnlocalizedName(name);
    	return this;
    }
    
    public TcBlock setHarvestLevelTc(String toolClass, int level) {
    	super.setHarvestLevel(toolClass, level);
    	return this;
    }
    
    @Override
    public TcBlock setResistance(float resistance) {
    	super.setResistance(resistance);
    	return this;
    }
    
    @Override
    public TcBlock setSoundType(SoundType sound) {
    	super.setSoundType(sound);
    	return this;
    }
    
    @Override
    public TcBlock setCreativeTab(CreativeTabs tab) {
    	super.setCreativeTab(tab);
    	return this;
    }
    
    @Override
    public TcBlock setHardness(float hardness) {
    	super.setHardness(hardness);
    	return this;
    }
    
    /**
     * 1.10.2: {@link Block#neighborChanged(IBlockState, World, BlockPos, Block)}<br>
     * 1.12.2: {@link Block#neighborChanged(IBlockState, World, BlockPos, Block, BlockPos)}
     */
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {}

    /**
     * 1.10.2: {@link Block#getCollisionBoundingBox(IBlockState, World, BlockPos)}<br>
     * 1.12.2: {@link Block#getCollisionBoundingBox(IBlockState, IBlockAccess, BlockPos)}
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
    	return super.getCollisionBoundingBox(blockState, worldIn, pos);
    }

    @Deprecated
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    	return super.getCollisionBoundingBox(blockState, (World)worldIn, pos);
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
}
