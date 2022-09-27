package remiliaMarine.tofu.eventhandler;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.achievement.TcAchievementMgr.Key;
import remiliaMarine.tofu.block.BlockLeek;
import remiliaMarine.tofu.block.BlockTcSapling;
import remiliaMarine.tofu.block.BlockTofuBase;
import remiliaMarine.tofu.init.TcBlocks;

public class EventBonemeal {
	
    @SubscribeEvent
    public void onBonemeal(BonemealEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();
        Random rand = world.rand;
        IBlockState blockstate = event.getBlock();
        Block block = blockstate.getBlock();
        BlockPos pos = event.getPos();
        int var12;
        int var13;
        
        // Saplings
        if (block == TcBlocks.tcSapling)
        {
            if (!world.isRemote)
            {
                ((BlockTcSapling) TcBlocks.tcSapling).growTree(world, pos, world.rand);
            }
            event.setResult(Event.Result.ALLOW);
        }
        
        // Leek
        if (block instanceof BlockTofuBase)
        {
            if (!world.isRemote)
            {
                label133:
            	
                	for (var12 = 0; var12 < 32; ++var12)
                    {
                        var13 = pos.getX();
                        int var14 = pos.getY() + 1;
                        int var15 = pos.getZ();
                        
                        for (int var16 = 0; var16 < var12 / 16; ++var16)
                        {
                            var13 += rand.nextInt(3) - 1;
                            var14 += (rand.nextInt(3) - 1) * rand.nextInt(3) / 2;
                            var15 += rand.nextInt(3) - 1;

                            if (!(world.getBlockState(new BlockPos(var13, var14 - 1, var15)).getBlock() instanceof BlockTofuBase) || world.getBlockState(new BlockPos(var13, var14, var15)).isNormalCube())
                            {
                                continue label133;
                            }
                        }
                        BlockPos blockpos = new BlockPos(var13, var14, var15);
                        if (world.isAirBlock(blockpos))
                        {
                            if (rand.nextInt(10) < 5)
                            {
                            	IBlockState leekstate = TcBlocks.leek.getDefaultState();
                                if (((BlockLeek)TcBlocks.leek).canBlockStay(world, blockpos, leekstate))
                                {
                                    world.setBlockState(blockpos, leekstate, 3);
                                    TcAchievementMgr.achieve(player, Key.leek);
                                }
                            }
                            else
                            {
                                world.getBiome(blockpos).plantFlower(world, rand, blockpos);
                            }
                        }
                        
                    }
            	
            }
            event.setResult(Event.Result.ALLOW);
            
        }
        
    }
    
}
