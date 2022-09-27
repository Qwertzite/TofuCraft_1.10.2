package remiliaMarine.tofu.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.init.TcBlocks;

public class TofuBlockUtils {
    public static void onFallenUponFragileTofu(World par1World, Entity par5Entity, Block block, float par6)
    {
        onFallenUponFragileTofu(par1World, par5Entity, block, par6, tofuWeightingHandler);
    }
    
    public static void onFallenUponFragileTofu(World par1World, Entity par5Entity, Block block, float par6, BlockUtils.IEntityWeightingBlockHandler handler)
    {
        if (!par1World.isRemote)
        {
            if (par6 > 0.5F)
            {
                if (!(par5Entity instanceof EntityPlayer) && !par1World.getGameRules().getBoolean("mobGriefing"))
                {
                    return;
                }
                BlockUtils.handleEntityWeightingBlock(par1World, par5Entity, block, handler);
            }
        }
    }
    
    private static BlockUtils.IEntityWeightingBlockHandler tofuWeightingHandler = new BlockUtils.IEntityWeightingBlockHandler()
    {
        @Override
        public void apply(World world, Entity entity, Block block, BlockPos pos)
        {
            float prob = block == TcBlocks.tofuKinu ? 0.4F : 1.0F;
            block.dropBlockAsItemWithChance(world, pos, world.getBlockState(pos), prob, 0);
            world.setBlockToAir(pos);

            if (entity instanceof EntityPlayer)
            {
            	TcAchievementMgr.achieve((EntityPlayer) entity, TcAchievementMgr.Key.tofuMental);
            }
        }
    };
}
