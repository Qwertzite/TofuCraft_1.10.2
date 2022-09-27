package remiliaMarine.tofu.block;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.tileentity.TileEntityMorijio;
import remiliaMarine.tofu.util.TcMathHelper;
import remiliaMarine.tofu.versionAdapter.block.TcBlockContainer;

public class BlockMorijio extends TcBlockContainer {
	
    public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.<EnumFacing>create("facing", EnumFacing.class);
	
    public static final int EFFECTIVE_RANGE = 20;
    public static final float SIZE = 0.3125F;
    public static final float HALF = SIZE / 2;
    public static final AxisAlignedBB MORIJIO_UP = new AxisAlignedBB(0.5F - HALF, 0.0F, 0.5F - HALF, 0.5F + HALF, SIZE, 0.5F + HALF);//0,1
    public static final AxisAlignedBB MORIJIO_NORTH = new AxisAlignedBB(0.5F - HALF, 0.5F - HALF, 1.0F - SIZE, 0.5F + HALF, 0.5F + HALF, 1.0F);//2
    public static final AxisAlignedBB MORIJIO_SOUTH = new AxisAlignedBB(0.5F - HALF, 0.5F - HALF, 0.0F, 0.5F + HALF, 0.5F + HALF, SIZE);//3
    public static final AxisAlignedBB MORIJIO_WEST = new AxisAlignedBB(0.5F + HALF, 0.5F - HALF, 0.5F - HALF, 1.0F, 0.5F + HALF, 0.5F + HALF);//4
    public static final AxisAlignedBB MORIJIO_EAST = new AxisAlignedBB(0.0F, 0.5F - HALF, 0.5F - HALF, SIZE, 0.5F + HALF, 0.5F + HALF);//5

    public BlockMorijio(SoundType sound)
    {
        super(Material.CIRCUITS);
        this.setSoundType(sound);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch(state.getValue(FACING)) {
        case UP:
        default:
        	return MORIJIO_UP;
        case NORTH:
        	return MORIJIO_NORTH;
        case EAST:
        	return MORIJIO_EAST;
        case SOUTH:
        	return MORIJIO_SOUTH;
        case WEST:
        	return MORIJIO_WEST;
        }
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMorijio();
	}
	
    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        EnumFacing facing = EnumFacing.getFront(TcMathHelper.floor((placer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3);
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, facing), 2);
    }

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    @Override
    public ItemStack getItem(World par1World, BlockPos pos, IBlockState state)
    {
        return new ItemStack(TcItems.morijio);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        ((TileEntityMorijio)worldIn.getTileEntity(pos)).removeInfo();
        super.breakBlock(worldIn, pos, state);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random par2Random, int fortune)
    {
        return TcItems.morijio;
    }
    
	// ======== block state ========

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

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
