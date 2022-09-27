package remiliaMarine.tofu.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.init.TcItems;

public abstract class BlockBarrelBase extends BlockFermentable {

//    private static final Field fldChunkCacheWorldObj = ReflectionHelper.findField(ChunkCache.class, "field_72815_e", "worldObj");
	public static final PropertyBool ENVIRONMENT = PropertyBool.create("environment");

    public BlockBarrelBase(Material par3Material)
    {
        super(par3Material);
    }

    public boolean isUnderWeight(IBlockAccess blockAccess, BlockPos pos)
    {
        IBlockState stateAbove = blockAccess.getBlockState(
        		pos.up());
        Block blockAbove = stateAbove.getBlock();
        IBlockState stateBelow = blockAccess.getBlockState(pos.down());

        boolean isWeightValid = blockAbove != null
                && (stateAbove.getMaterial() == Material.ROCK || stateAbove.getMaterial() == Material.IRON);

//        float baseHardness;
//        if (blockAccess instanceof ChunkCache)
//        {
//            try
//            {
//                baseHardness = baseBlock.getBlockHardness((World)fldChunkCacheWorldObj.get(blockAccess), x, y, z);
//            }
//            catch (IllegalAccessException e)
//            {
//                throw new RuntimeException("Failed to call worldObj in ChunkCache", e);
//            }
//        }
//        else if (blockAccess instanceof World)
//        {
//            baseHardness = baseBlock.getBlockHardness((World)blockAccess, x, y, z);
//        }
//        else
//        {
//            baseHardness = 0.0F;
//        }
//        boolean isBaseValid = baseBlock.isNormalCube() && (baseHardness >= 1.0F || baseHardness < 0.0F);
        boolean isBaseValid = stateBelow.isNormalCube();

        return isWeightValid && isBaseValid;
    }

    @Override
    public ItemStack getItem(World par1World, BlockPos pos, IBlockState state)
    {
        return new ItemStack(TcItems.barrelMisoTofu);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random par2Random, int fortune)
    {
        return TcItems.barrelEmpty;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
//    	switch(tintindex) {
//    		case 2:
//    			return 0x885511;
//    		case 1: return 0xffd399;
//    		default: return 0xffffff;
//    	}
    	if(pos == null) return 0xffffff;
        return state.getValue(FERM_STEP) == 7 ? 0x885511 : state.getValue(ENVIRONMENT) ? 0xffd399 : 0xffffff;
    }

    abstract public boolean checkEnvironment(IBlockAccess blockAccess, BlockPos pos);

    @Override
    public boolean checkEnvironment(World world, BlockPos pos)
    {
        return this.checkEnvironment((IBlockAccess) world, pos);
    }

	@Override
    @SuppressWarnings("deprecation")
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    	return super.getActualState(state, worldIn, pos).withProperty(ENVIRONMENT, this.checkEnvironment(worldIn, pos));
    }

    // ======== metadata ========

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FERM_STEP, ENVIRONMENT});
    }
}
