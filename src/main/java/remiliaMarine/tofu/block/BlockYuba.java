package remiliaMarine.tofu.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.material.TcMaterial;
import remiliaMarine.tofu.versionAdapter.block.TcBlock;

public class BlockYuba extends TcBlock {

	public static final AxisAlignedBB YUBA_AABB = new AxisAlignedBB(0, 0, 0, 1, 0.015625F, 1);

    public BlockYuba(SoundType sound)
    {
        super(TcMaterial.TOFU);
        this.setTickRandomly(true);
        float f = 0.5F;
        float f1 = 0.015625F;
        this.setSoundType(sound);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return YUBA_AABB;
    }

    @Override
    public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random)
    {
        if (!this.canBlockStay(par1World, pos))
        {
            par1World.setBlockToAir(pos);
        }
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    @Override
    public void onEntityCollidedWithBlock(World par1World, BlockPos pos, IBlockState state, Entity par5Entity)
    {
        if (!par1World.isRemote)
        {
            par1World.setBlockToAir(pos);
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
    {
        player.addStat(StatList.getBlockStats(this), 1);
        player.addExhaustion(0.025F);

        if (this.canSilkHarvest(worldIn, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0)
        {
            this.dropYuba(worldIn, pos, state);
        }
        else
        {
            if (stack != null && stack.getItem() == Items.STICK)
            {
                this.dropYuba(worldIn, pos, state);
            }
        }
    }

    protected void dropYuba(World par1World, BlockPos pos, IBlockState state)
    {

        BlockYuba.spawnAsEntity(par1World, pos, new ItemStack(TcItems.yuba));
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World par1World, BlockPos pos)
    {
        return pos.getY() >= 0 && pos.getY() < 256 && par1World.getBlockState(pos.down()).getMaterial() == Material.WATER;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World par1World, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

}
