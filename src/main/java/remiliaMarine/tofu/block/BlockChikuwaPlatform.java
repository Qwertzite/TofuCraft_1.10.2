package remiliaMarine.tofu.block;

import java.util.EnumSet;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.entity.EntityFallingChikuwaPlatform;
import remiliaMarine.tofu.material.TcMaterial;
import remiliaMarine.tofu.tileentity.TileEntityChikuwaPlatform;
import remiliaMarine.tofu.util.TcMathHelper;
import remiliaMarine.tofu.versionAdapter.WorldAdapter;
import remiliaMarine.tofu.versionAdapter.block.TcBlockContainer;

public class BlockChikuwaPlatform extends TcBlockContainer {

	/** first bit of meta data*/
	public static final PropertyEnum<Dir> DIRECTION = PropertyEnum.<Dir>create("dir", Dir.class);
	/** 2nd and 3rd bits of meta data */
	public static final PropertyEnum<ConnectionStat> CONNECTION = PropertyEnum.<ConnectionStat>create("con", ConnectionStat.class);

	public static final AxisAlignedBB CHIKUWA_COLLISION_BOUNDING_BOX = new AxisAlignedBB(0.0d, 0.0d, 0.0d,  1.0d, 1-0.125d, 1.0d);

    //private final String typeName;

    public BlockChikuwaPlatform(String typeName, SoundType sound)
    {
        super(TcMaterial.TOFU);
        //this.typeName = typeName;
        this.setSoundType(sound);
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityChikuwaPlatform();
	}

	@Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return CHIKUWA_COLLISION_BOUNDING_BOX;
    }

    public static Dir getDirection(IBlockState state)
    {
        return state.getValue(DIRECTION);
    }

    public static ConnectionStat getConnectionStat(int meta)
    {
        return ConnectionStat.values()[(meta & 6) >> 1];
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        int dir = TcMathHelper.floor((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int blockDir = (dir + 1) % 2;
        Dir direction = Dir.values()[blockDir];
        IBlockState newState = this.getDefaultState().withProperty(DIRECTION, direction);
        ConnectionStat stat = calcChikuwaConnection(worldIn, pos, newState);
        worldIn.setBlockState(pos, newState.withProperty(CONNECTION, stat), 2);
    }

    @Override
    /**
     * Called when a tile entity on a side of this block changes is created or is destroyed.
     * @param world The world
     * @param pos Block position in world
     * @param neighbor Block position of neighbor
     */
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        IBlockState meta = world.getBlockState(pos);
        this.updateChikuwaConnection((World)world, pos, meta);
    }

    public void updateChikuwaConnection(World world, BlockPos pos, IBlockState state)
    {
        ConnectionStat stat = this.calcChikuwaConnection(world, pos, state);
        world.setBlockState(pos, state.withProperty(CONNECTION, stat), 2);
    }

    public ConnectionStat calcChikuwaConnection(World world, BlockPos pos, IBlockState state)
    {
        Dir blockDir = getDirection(state);

        boolean canConnectToLeft = this.canChikuwaConnectTo(world, pos, state, blockDir.left);
        boolean canConnectToRight = this.canChikuwaConnectTo(world, pos, state, blockDir.right);

        int stat = 0;
        if (canConnectToLeft) stat += 1;
        if (canConnectToRight) stat += 2;

        return ConnectionStat.values()[stat];
    }

    public boolean canChikuwaConnectTo(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing dir)
    {
        BlockPos anotherPos = pos.offset(dir);

        IBlockState anotherState = world.getBlockState(anotherPos);
        Block anotherBlock = anotherState.getBlock();

        if(anotherBlock != this) return false;
        Dir blockDir = anotherState.getValue(DIRECTION);

        return getDirection(state) == getDirection(anotherState)
                && blockDir.connectedTo.contains(dir);
    }

    public boolean dropBlock(World world, BlockPos pos, Block block, int chunkUID)
    {
        if (canGoThrough(world, pos.down()) && pos.getY() >= 0)
        {
            byte b0 = 32;

            if (world.isAreaLoaded(pos.add(-b0, -b0, -b0), pos.add(b0, b0, b0)))
            {
                if (!world.isRemote)
                {
                    IBlockState meta = world.getBlockState(pos);
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);

                    EntityFallingChikuwaPlatform fallingBlock = new EntityFallingChikuwaPlatform(world, pos.getX()+0.5d, pos.getY()+0.0d, pos.getZ()+0.5d, meta, block.getMetaFromState(meta), chunkUID);
                    fallingBlock.setHome(pos);
                    WorldAdapter.spawnEntity(world, fallingBlock);
                }
            }
            else
            {
                world.setBlockToAir(pos);

                while (canGoThrough(world, pos.down()) && pos.getY() > 0)
                {
                    pos = pos.down();
                }

                if (pos.getY() > 0)
                {
                    world.setBlockState(pos, block.getDefaultState());
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean canDropBlock(World world, BlockPos pos) {
        if (canGoThrough(world, pos.down()) && pos.getY() >= 0) return true;
        else return false;
    }

    public void dropBlockNoCheck(World world, BlockPos pos, IBlockState block, int chunkUID) {

        byte b0 = 32;

        if (world.isAreaLoaded(pos.add(-b0, -b0, -b0), pos.add(b0, b0, b0)))
        {
            if (!world.isRemote)
            {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);

                EntityFallingChikuwaPlatform fallingBlock = new EntityFallingChikuwaPlatform(world, pos.getX()+0.5d, pos.getY()+0.0d, pos.getZ()+0.5d, block, block.getBlock().getMetaFromState(block), chunkUID);
                fallingBlock.setHome(pos);
                WorldAdapter.spawnEntity(world, fallingBlock);
            }
        }
        else
        {
            world.setBlockToAir(pos);

            while (canGoThrough(world, pos.down()) && pos.getY() > 0)
            {
                pos = pos.down();
            }

            if (pos.getY() > 0)
            {
                world.setBlockState(pos, block.getBlock().getDefaultState());
            }
        }
    }

    public static boolean canGoThrough(World worldIn, BlockPos pos)
    {
    	IBlockState state = worldIn.getBlockState(pos);
        Block block = state.getBlock();

        if (block.isAir(state, worldIn, pos))
        {
            return true;
        }
        else if (block == Blocks.FIRE)
        {
            return true;
        }
        else
        {
            Material material = state.getMaterial();
            return material == Material.WATER || material == Material.LAVA;
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    // ======== block state ========

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
        		.withProperty(CONNECTION, ConnectionStat.values()[(meta >> 1) & 3])
        		.withProperty(DIRECTION, Dir.values()[meta & 1]);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(CONNECTION).ordinal() << 1) + state.getValue(DIRECTION).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {DIRECTION, CONNECTION});
    }

    public static enum Dir implements IStringSerializable
    {
        X(EnumFacing.WEST, EnumFacing.EAST, "x"),
        Z(EnumFacing.NORTH, EnumFacing.SOUTH, "z");

        public final EnumSet<EnumFacing> connectedTo;
        public final EnumFacing left;
        public final EnumFacing right;
        public final String name;

        Dir(EnumFacing left, EnumFacing right, String name)
        {
            this.left = left;
            this.right = right;
            this.connectedTo = EnumSet.of(left, right);
            this.name = name;
        }

		@Override
		public String getName() {
			return this.name;
		}
    }

    public static enum ConnectionStat implements IStringSerializable
    {
        SINGLE("single"), LEFT("left"), RIGHT("right"), MIDDLE("middle");

        private static ConnectionStat[] opposites = new ConnectionStat[]{SINGLE, RIGHT, LEFT, MIDDLE};

        String texSign;

        ConnectionStat(String texSign)
        {
            this.texSign = texSign;
        }

        public ConnectionStat opposite()
        {
            return opposites[this.ordinal()];
        }

		@Override
		public String getName() {
			return this.texSign;
		}
    }

}
