package remiliaMarine.tofu.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.material.TcMaterial;
import remiliaMarine.tofu.util.TofuBlockUtils;

public abstract class BlockTofuSlabBase extends BlockSlab {

    public BlockTofuSlabBase()
    {
        super(TcMaterial.TOFU);
        this.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
        this.setTickRandomly(true);
        this.setSoundType(SoundType.SNOW);
        this.useNeighborBrightness = true;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    @Nullable
    abstract public Item getItemDropped(IBlockState state, Random rand, int fortune);

    @Override
    abstract public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state);

    /**
     * Block's chance to react to an entity falling on it.
     */
    @Override
    public void onFallenUpon(World par1World, BlockPos pos, Entity par5Entity, float par6)
    {
        IBlockState state = par1World.getBlockState(pos);

        if (isFragile(state))
        {
            TofuBlockUtils.onFallenUponFragileTofu(par1World, par5Entity, this, par6);
        }
    }

    abstract public boolean isFragile(IBlockState state);

	@Override
	abstract public String getUnlocalizedName(int meta);

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random)
    {
        super.updateTick(par1World, pos, state, par5Random);

        if (isFragile(state) && this.isDouble())
        {
            IBlockState weightBlock = par1World.getBlockState(pos.up());

            if (weightBlock != null)
            {
               if (weightBlock.getMaterial() == Material.ROCK || weightBlock.getMaterial() == Material.IRON)
               {
                   dropBlockAsItem(par1World, pos, state, 0);
                   par1World.setBlockToAir(pos);
               }
            }
        }
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    public abstract void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list);

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    abstract public int damageDropped(IBlockState state);

    // ======== block state ========

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    abstract public IBlockState getStateFromMeta(int meta);

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    abstract public int getMetaFromState(IBlockState state);

    @Override
    abstract protected BlockStateContainer createBlockState();
}
