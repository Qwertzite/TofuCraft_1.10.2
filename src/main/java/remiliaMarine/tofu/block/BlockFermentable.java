package remiliaMarine.tofu.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.versionAdapter.block.TcBlock;

public abstract class BlockFermentable extends TcBlock {
	public static final PropertyInteger FERM_STEP = PropertyInteger.create("step", 0, 7);

    private int fermRate;

    public BlockFermentable(Material par2Material)
    {
        super(par2Material);
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random)
    {
        super.updateTick(par1World, pos, state, par5Random);

        if (this.checkEnvironment(par1World, pos))
        {
            int meta = this.getMetaFromState(state);
            int fermStep = getFermStep(meta);
            int extra = meta & 8;

            if (fermStep < 7 && par5Random.nextInt(fermRate) == 0)
            {
                par1World.setBlockState(pos, this.getStateFromMeta(++fermStep | extra), 3);
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) // getBlockDropped
    {
        List<ItemStack> ret = super.getDrops(world, pos, state, fortune);
        if (this.getFermStep(state.getValue(FERM_STEP)) == 7)
        {
            this.addFermentedItem(ret);
        }
        else
        {
            this.addIngredients(ret);
        }
        return ret;
    }

    public abstract void addFermentedItem(List<ItemStack> list);

    public abstract void addIngredients(List<ItemStack> list);

    public abstract boolean checkEnvironment(World world, BlockPos pos);

    public Block setFermRate(int rate)
    {
        this.fermRate = rate;
        return this;
    }

    public int getFermStep(int metadata)
    {
        return metadata & 7;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        int step = state.getValue(FERM_STEP);
        return step == 7 ? 0xcb944b : 0xffffff;    }

    // ======== metadata ========

    /**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState getStateFromMeta(int meta) {
    	return this.getDefaultState().withProperty(FERM_STEP, meta);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
	@Override
    public int getMetaFromState(IBlockState state)
    {
		return state.getValue(FERM_STEP);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FERM_STEP});
    }
}
