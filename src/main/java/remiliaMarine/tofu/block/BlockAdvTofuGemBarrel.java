package remiliaMarine.tofu.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemTcMaterials;

public class BlockAdvTofuGemBarrel extends BlockBarrelBase {

	public static final PropertyBool POWERED = PropertyBool.create("powered");

    public BlockAdvTofuGemBarrel(Material materialIn)
    {
        super(materialIn);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public ItemStack getItem(World par1World, BlockPos pos, IBlockState state)
    {
        return new ItemStack(TcItems.barrelAdvTofuGem);
    }

    @Override
    public void addFermentedItem(List<ItemStack> list)
    {
        list.add(ItemTcMaterials.EnumTcMaterialInfo.advTofuGem.getStack());
    }

    @Override
    public void addIngredients(List<ItemStack> list)
    {
        list.add(ItemTcMaterials.EnumTcMaterialInfo.tofuGem.getStack(3));
        list.add(new ItemStack(Items.REDSTONE, 3));
    }

    @Override
    public boolean checkEnvironment(IBlockAccess blockAccess, BlockPos pos)
    {
        return blockAccess.getBlockState(pos.up()).getBlock() == Blocks.ANVIL
                && blockAccess.getBlockState(pos).getValue(POWERED);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
        boolean flag = worldIn.isBlockPowered(pos);
        boolean flag1 = state.getValue(POWERED);
        Block block = state.getBlock();

        if (flag && !flag1)
        {
        	worldIn.scheduleBlockUpdate(pos, block, 0, block.tickRate(worldIn));
        	worldIn.setBlockState(pos, state.withProperty(POWERED, true), 4);
        }
        else if (!flag && flag1)
        {
        	worldIn.setBlockState(pos, state.withProperty(POWERED, false), 4);
        }
    }

    // ======== block state ========

    /**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState getStateFromMeta(int meta) {
    	return this.getDefaultState().withProperty(FERM_STEP, meta & 7).withProperty(POWERED, (meta & 8) > 0);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
	@Override
    public int getMetaFromState(IBlockState state)
    {
		int i = 0;
		i += state.getValue(POWERED) ? 1 : 0;
		i = i << 3;
		i += state.getValue(FERM_STEP);
		return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FERM_STEP, ENVIRONMENT, POWERED});
    }
}
