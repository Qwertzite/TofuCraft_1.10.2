package remiliaMarine.tofu.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.init.TcItems;

public class BlockLeek extends BlockBush implements IShearable, ITcBlockVariable {

    public static final int META_NATURAL = 0x8;
    public static final PropertyInteger META = PropertyInteger.create("meta", 0, META_NATURAL);
    
    protected static final AxisAlignedBB LEEK_AABB = new AxisAlignedBB(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
	
    public BlockLeek()
    {
        super();
        this.setDefaultState(this.getDefaultState().withProperty(BlockLeek.META, META_NATURAL));
        this.setSoundType(SoundType.PLANT);
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return LEEK_AABB;
    }
    
    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        return state.getValue(BlockLeek.META) > 0 ? 4 : 0;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return TcItems.leek;
    }
    
    /**
     * Called when the player destroys a block with an item that can harvest it. 
     */
    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
    {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
    }
    
    /**
     * This returns a complete list of items dropped from this block.
     *
     * @param world The current world
     * @param pos Block position in world
     * @param state Current state
     * @param fortune Breakers fortune level
     * @return A ArrayList containing all items this block drops
     */
    @SideOnly(Side.CLIENT)
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        return super.getDrops(world, pos, state, fortune);
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this, 1, 0));
        return ret;
	}
	
    @Override
    public boolean canSustainBush(IBlockState state) // canThisPlantGrowOnThisBlockID
    {
        return state.getBlock() instanceof BlockTofuBase;
    }
    
    @Override
    public String getVariantName(int meta) {
    	return "";
    }
    
    /**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState getStateFromMeta(int meta) {
    	return this.getDefaultState().withProperty(META, meta);
    }
    
    /**
     * Convert the BlockState into the correct metadata value
     */
	@Override
    public int getMetaFromState(IBlockState state)
    {
		return state.getValue(META);
    }
	
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {META});
    }

}
