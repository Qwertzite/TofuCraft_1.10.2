package remiliaMarine.tofu.item;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBucket;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.init.TcBlocks;

public class ItemTcBucket extends ItemBucket {
    public final Block isFull;

	public ItemTcBucket(Block par2) {
		super(par2);
        this.isFull = par2;
	}
	
    @Override
    public boolean tryPlaceContainedLiquid(@Nullable EntityPlayer player, World worldIn, BlockPos posIn)
    {
        if (this.isFull == Blocks.AIR)
        {
            return false;
        }
        else
        {
            Material material = worldIn.getBlockState(posIn).getMaterial();
            boolean isSolid = material.isSolid();
            if (!worldIn.isAirBlock(posIn) && isSolid)
            {
                return false;
            }
            else
            {
                if (worldIn.provider.doesWaterVaporize() && this.isFull != TcBlocks.soymilkHellStill)
                {
                	worldIn.playSound(player, posIn.add(0.5F, 0.5F, 0.5F), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

                    for (int l = 0; l < 8; ++l)
                    {
                    	worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posIn.getX() + Math.random(), posIn.getY() + Math.random(), posIn.getZ() + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
                    }
                }
                else
                {
                    if (!worldIn.isRemote && !isSolid && !material.isLiquid())
                    {
                    	worldIn.destroyBlock(posIn, true);
                    }
                    worldIn.setBlockState(posIn, this.isFull.getDefaultState(), 3);
                }

                return true;
            }
        }
    }
	
	
	
	
	
	
	
	
}
