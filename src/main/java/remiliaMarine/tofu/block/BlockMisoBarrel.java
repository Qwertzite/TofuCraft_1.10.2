package remiliaMarine.tofu.block;

import java.util.List;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import remiliaMarine.tofu.init.TcItems;

public class BlockMisoBarrel extends BlockBarrelBase {

	public static final PropertyBool SAUCE_REMOVED = PropertyBool.create("sauce_removed");

    public BlockMisoBarrel(Material par3Material)
    {
        super(par3Material);
        this.setTickRandomly(true);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public ItemStack getItem(World par1World, BlockPos pos, IBlockState state)
    {
        return new ItemStack(TcItems.barrelMiso);
    }

    public boolean removeSoySauce(World world, BlockPos pos)
    {
        IBlockState metadata = world.getBlockState(pos);
        if (this.hasSoySauce(metadata))
        {
        	world.setBlockState(pos, this.getDefaultState().withProperty(FERM_STEP, 7).withProperty(SAUCE_REMOVED, true), 2);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean hasSoySauce(IBlockState metadata)
    {
        return metadata.getValue(FERM_STEP) == 7 && !metadata.getValue(SAUCE_REMOVED);
    }

    @Override
    public void addFermentedItem(List<ItemStack> list)
    {
        list.add(new ItemStack(TcItems.miso, 6));
    }

    @Override
    public void addIngredients(List<ItemStack> list)
    {
        list.add(new ItemStack(TcItems.salt, 3));
        list.add(new ItemStack(TcItems.kouji, 3));
    }


	@Override
	public boolean checkEnvironment(IBlockAccess blockAccess, BlockPos pos) {
        return this.isUnderWeight(blockAccess, pos);
	}

    // ======== metadata ========

    /**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState getStateFromMeta(int meta) {
    	return this.getDefaultState().withProperty(FERM_STEP, meta&7).withProperty(SAUCE_REMOVED, (meta&8) > 0);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
	@Override
    public int getMetaFromState(IBlockState state)
    {
		return state.getValue(FERM_STEP) + (state.getValue(SAUCE_REMOVED) ? 8 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FERM_STEP, SAUCE_REMOVED, ENVIRONMENT});
    }
}
