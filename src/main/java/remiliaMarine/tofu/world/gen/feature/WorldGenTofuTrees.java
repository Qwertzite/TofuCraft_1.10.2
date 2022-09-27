package remiliaMarine.tofu.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.block.BlockTcLeaves;
import remiliaMarine.tofu.init.TcBlocks;

public class WorldGenTofuTrees extends WorldGenTcTreesBase {
	
    public WorldGenTofuTrees(boolean par1)
    {
        super(par1, 4, TcBlocks.tofuIshi.getDefaultState(), TcBlocks.tcLeaves.getDefaultState().withProperty(BlockTcLeaves.VARIANT, BlockTcLeaves.EnumLeaveTypes.TOFU), false);
    }
    
    @Override
    public boolean isSoil(IBlockState state, World world, BlockPos pos)
    {
    	Block block = state.getBlock();
        return block == TcBlocks.tofuTerrain
                || block == TcBlocks.tofuMomen;
    }
    
    @Override
    protected void putLeaves(int ox, int oy, int oz, int height, World worldIn, Random rand)
    {
        //byte zero = 0;
        int var9 = 3;
        int leavesY;
//       int blocksHighFromTop;
        int radius;
        int leavesX;
        for (leavesY = oy - var9 + height; leavesY <= oy + height; ++leavesY)
        {
//            blocksHighFromTop = leavesY - (oy + height);
            radius = height / 3;

            for (leavesX = ox - radius; leavesX <= ox + radius; ++leavesX)
            {

                for (int leavesZ = oz - radius; leavesZ <= oz + radius; ++leavesZ)
                {
                    
                    BlockPos leavesPos = new BlockPos(leavesX, leavesY, leavesZ);
                    IBlockState state = worldIn.getBlockState(leavesPos);
                    Block block = state.getBlock();

                    if (block == null || block.canBeReplacedByLeaves(state, worldIn, leavesPos))
                    {
                        IBlockState metadata;
                        if (fruitChance > 0 && rand.nextInt(fruitChance) == 0)
                        {
                            metadata = this.stateFruit;
                        }
                        else
                        {
                            metadata = this.stateLeaves;
                        }
                        this.setBlockAndNotifyAdequately(worldIn, leavesPos, metadata);
                    }
                }
            }
        }
    }
    
    

}
