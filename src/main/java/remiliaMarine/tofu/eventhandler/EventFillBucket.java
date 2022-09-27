package remiliaMarine.tofu.eventhandler;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.achievement.TcAchievementMgr.Key;
import remiliaMarine.tofu.block.BlockMisoBarrel;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcItems;

public class EventFillBucket {
    @SubscribeEvent
    public void onFillBucket(FillBucketEvent event)
    {
        World par2World = event.getWorld();
        ItemStack emptyBucket = event.getEmptyBucket();
        EntityPlayer par3EntityPlayer = event.getEntityPlayer();
        RayTraceResult target = event.getTarget();
        if(target == null){
        	return;
        }
        BlockPos pos = target.getBlockPos();

//        if (!par2World.canMineBlock(par3EntityPlayer, new BlockPos(0,0,0)))
//        {
//            return;
//        }

        if (emptyBucket.getItem() == Items.BUCKET)
        {
            if (target.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                Block block = par2World.getBlockState(pos).getBlock();
                ItemStack filledBucket = this.pickUpFluid(par3EntityPlayer, par2World, pos, block);
                if (filledBucket != null)
                {
                	event.setResult(Result.ALLOW);
                    event.setFilledBucket(filledBucket);
                }
            }
        }
    }

    private ItemStack pickUpFluid(EntityPlayer player, World world, BlockPos pos, Block block)
    {
        if (block == TcBlocks.soymilkStill)
        {
        	world.setBlockToAir(pos); // replace with an air block
            return new ItemStack(TcItems.bucketSoymilk);
        }

        if (block == TcBlocks.soymilkHellStill)
        {
            world.setBlockToAir(pos);
            return new ItemStack(TcItems.bucketSoymilkHell);
        }

        if (block == TcBlocks.soySauceStill)
        {
        	world.setBlockToAir(pos);
            return new ItemStack(TcItems.bucketSoySauce);
        }

        if (block == TcBlocks.barrelMiso)
        {
            if (((BlockMisoBarrel) TcBlocks.barrelMiso).removeSoySauce(world, pos))
            {
                TcAchievementMgr.achieve(player, Key.soySauce);
                return new ItemStack(TcItems.bucketSoySauce);
            }

        }

        return null;
    }


}
