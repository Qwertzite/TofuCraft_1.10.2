package remiliaMarine.tofu.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.init.TcBlocks;

public class BlockTofuFarmland extends BlockTofuBase {

	public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, 7);
    protected static final AxisAlignedBB FARMLAND_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

    public BlockTofuFarmland(SoundType sound)
    {
        super();
        this.setTickRandomly(true);
        this.setLightOpacity(255);
        this.setSoundType(sound);
        this.useNeighborBrightness = true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return FARMLAND_AABB;
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

    @SuppressWarnings("deprecation")
	@Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        switch (side)
        {
            case UP:
                return true;
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
                Block block = iblockstate.getBlock();
                return !iblockstate.isOpaqueCube() && block != TcBlocks.tofuFarmland;
            default:
                return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
        }
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, net.minecraftforge.common.IPlantable plantable)
    {
        IBlockState plant = plantable.getPlant(world, pos.up());
        if (plant.getBlock() == TcBlocks.soybean)
        {
            return true;
        }
        else
        {
            return super.canSustainPlant(state, world, pos, direction, plantable);
        }
    }

    @Override
    public boolean isFertile(World world, BlockPos pos)
    {
        return world.getBlockState(pos).getValue(MOISTURE) > 0;
    }

    @Override
    public ItemStack createScoopedBlockStack()
    {
        return new ItemStack(TcBlocks.tofuMomen);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random p_149674_5_)
    {
        if (!this.hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.up()))
        {
            int l = worldIn.getBlockState(pos).getValue(MOISTURE);

            if (l > 0)
            {
                worldIn.setBlockState(pos, this.getDefaultState().withProperty(MOISTURE, l-1), 2);
            }
            else if (!this.hasCrops(worldIn, pos, state))
            {
                worldIn.setBlockState(pos, TcBlocks.tofuMomen.getDefaultState());
            }
        }
        else
        {
            worldIn.setBlockState(pos, this.getDefaultState().withProperty(MOISTURE, 7), 2);
        }
    }

    /**
     * Block's chance to react to an entity falling on it.
     */
    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if (!worldIn.isRemote && worldIn.rand.nextFloat() < fallDistance - 0.5F)
        {
            if (!(entityIn instanceof EntityPlayer) && !worldIn.getGameRules().getBoolean("mobGriefing"))
            {
                return;
            }

            worldIn.setBlockState(pos, TcBlocks.tofuMomen.getDefaultState());
        }
    }

    private boolean hasCrops(World worldIn, BlockPos pos, IBlockState state)
    {
        byte b0 = 0;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (int l = pos.getX() - b0; l <= pos.getX() + b0; ++l)
        {
            for (int i1 = pos.getZ() - b0; i1 <= pos.getZ() + b0; ++i1)
            {
            	mutable.setPos(l, pos.getY() + 1, i1);
                Block block = worldIn.getBlockState(mutable).getBlock();

                if (block instanceof IPlantable && canSustainPlant(state, worldIn, pos, EnumFacing.UP, (IPlantable)block))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasWater(World worldIn, BlockPos pos)
    {
    	BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
    	int xCentre = pos.getX();
    	int yCentre = pos.getY();
    	int zCentre = pos.getZ();

        for (int l = xCentre - 4; l <= xCentre + 4; ++l)
        {
            for (int i1 = yCentre; i1 <= yCentre + 1; ++i1)
            {
                for (int j1 = zCentre - 4; j1 <= zCentre + 4; ++j1)
                {
                	mutable.setPos(l,  i1,  j1);
                    if (worldIn.getBlockState(mutable).getMaterial() == Material.WATER)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    @SuppressWarnings("deprecation")
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
    	super.neighborChanged(state, worldIn, pos, blockIn);
        if (worldIn.getBlockState(pos.up()).getMaterial().isSolid())
        {
            worldIn.setBlockState(pos, TcBlocks.tofuMomen.getDefaultState());
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int p_149650_3_)
    {
        return TcBlocks.tofuMomen.getItemDropped(TcBlocks.tofuMomen.getDefaultState(), rand, p_149650_3_);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(TcBlocks.tofuMomen);
    }

    // ======== blockstate ========
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(MOISTURE, Integer.valueOf(meta & 7));
    }

    @Override
    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(MOISTURE)).intValue();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {MOISTURE});
    }

}
