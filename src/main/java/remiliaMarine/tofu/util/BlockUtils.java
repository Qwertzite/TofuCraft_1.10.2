package remiliaMarine.tofu.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockUtils {

//    public static void onNeighborBlockChange_RedstoneSwitch(Block block, World worldIn, BlockPos pos, Block neighbour)
//    {
//        boolean flag = worldIn.isBlockIndirectlyGettingPowered(pos) > 0 || worldIn.isBlockIndirectlyGettingPowered(pos.up()) > 0;
//        boolean flag1 = worldIn.isBlockPowered(pos);
//
//        if (flag && !flag1)
//        {
//            worldIn.scheduleBlockUpdate(pos, block, block.tickRate(worldIn), 0);
//            worldIn.power(pos, l | 8, 4);
//        }
//        else if (!flag && flag1)
//        {
//            worldIn.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, l & -9, 4);
//        }
//    }
	
    public static void handleEntityWeightingBlock(World world, Entity entity, Block block, IEntityWeightingBlockHandler handler)
    {
        int i = MathHelper.floor_double(entity.getEntityBoundingBox().minX + 0.001D);
        int j = MathHelper.floor_double(entity.getEntityBoundingBox().minY + 0.001D);
        int k = MathHelper.floor_double(entity.getEntityBoundingBox().minZ + 0.001D);
        int l = MathHelper.floor_double(entity.getEntityBoundingBox().maxX - 0.001D);
        int i1 = MathHelper.floor_double(entity.getEntityBoundingBox().maxY - 0.001D);
        int j1 = MathHelper.floor_double(entity.getEntityBoundingBox().maxZ - 0.001D);
        
        world.getCollisionBoxes(entity.getEntityBoundingBox());
        //if (entity.worldObj.checkChunksExist(i, j, k, l, i1, j1))
        {
            for (int k1 = i; k1 <= l; ++k1)
            {
                for (int l1 = j; l1 <= i1; ++l1)
                {
                    for (int i2 = k; i2 <= j1; ++i2)
                    {
                        int bx = k1;
                        int by = l1 - 1;
                        int bz = i2;
                        BlockPos pos = new BlockPos(bx, by, bz);
                        if (world.getBlockState(pos).getBlock() == block)
                        {
                            handler.apply(world, entity, block, pos);
                        }
                    }
                }
            }
        }
    }

    public interface IEntityWeightingBlockHandler
    {
        public void apply(World world, Entity entity, Block block, BlockPos pos);
    }
}
