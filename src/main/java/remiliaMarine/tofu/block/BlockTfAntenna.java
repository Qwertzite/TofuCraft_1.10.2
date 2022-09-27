package remiliaMarine.tofu.block;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.gui.TcGuiHandler;
import remiliaMarine.tofu.tileentity.TileEntityTfAntenna;

public class BlockTfAntenna extends BlockContainer4Directions {

    public static int MAX_RADIUS = 0;
    public static final WaveFreq[] WAVE_LIST = new WaveFreq[4];
    public static final WaveFreq MEDIUMWAVE = new WaveFreq(0, 10);
    public static final WaveFreq ULTRAWAVE = new WaveFreq(1, 16);
    public static final WaveFreq SUPERWAVE = new WaveFreq(2, 24);
    private static float size = 0.4375F;
    private static float half = size / 2;
    public static final AxisAlignedBB ANTENNA_AABB = new AxisAlignedBB(0.5F - half, 0.0F, 0.5F - half, 0.5F + half, 1.0F, 0.5F + half);

    private WaveFreq waveFreq;

    public BlockTfAntenna(WaveFreq waveFreq, SoundType sound)
    {
        super(Material.CIRCUITS);
        this.setSoundType(sound);
        this.waveFreq = waveFreq;
    }

    public WaveFreq getAntennaType()
    {
        return waveFreq;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
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

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return ANTENNA_AABB;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = worldIn.getTileEntity(pos);

        if (tile != null)
        {
        	playerIn.openGui(TofuCraftCore.INSTANCE, TcGuiHandler.GUIID_TF_ANTENNA, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World, int par1)
    {
        return new TileEntityTfAntenna();
    }

    public static class WaveFreq
    {
        public final int id;
        public final int radius;

        public WaveFreq(int id, int radius)
        {
            this.id = id;
            this.radius = radius;
            WAVE_LIST[id] = this;

            if (radius > MAX_RADIUS) MAX_RADIUS = radius;
        }

        public WaveFreq(int id)
        {
            this.id = id;
            this.radius = -1;
            WAVE_LIST[id] = this;
        }
    }
}
