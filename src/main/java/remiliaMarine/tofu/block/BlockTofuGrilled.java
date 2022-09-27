package remiliaMarine.tofu.block;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.TofuMaterial;

public class BlockTofuGrilled extends BlockTofuBase {
	
    public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.<EnumFacing>create("facing", EnumFacing.class);

    public BlockTofuGrilled(TofuMaterial material)
    {
        super(material);
        this.setSoundType(SoundType.SNOW);
        this.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
    }
    
    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(FACING, this.getFacingFromEntity(pos, placer));
    }
    
    public EnumFacing getFacingFromEntity(BlockPos pos, EntityLivingBase p_185647_1_)
    {
        if (MathHelper.abs((float)p_185647_1_.posX - (float)pos.getX()) < 2.0F && MathHelper.abs((float)p_185647_1_.posZ - (float)pos.getZ()) < 2.0F)
        {
            double d0 = p_185647_1_.posY + (double)p_185647_1_.getEyeHeight();

            if (d0 - (double)pos.getY() > 2.0D)
            {
                return EnumFacing.UP;
            }

            if ((double)pos.getY() - d0 > 0.0D)
            {
                return EnumFacing.DOWN;
            }
        }

        return p_185647_1_.getHorizontalFacing().getOpposite();
    }
    
    
	@Override
	public Item getItemDropped(IBlockState state, Random par2Random, int par3) {
        return TcItems.tofuGrilled;

	}
	
    /**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState getStateFromMeta(int meta) {
    	return this.getDefaultState().withProperty(BlockTofuGrilled.FACING, EnumFacing.getFront(meta));
    }
    
    /**
     * Convert the BlockState into the correct metadata value
     */
	@Override
    public int getMetaFromState(IBlockState state)
    {
		return state.getValue(FACING).getIndex();
    }
	
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
}
