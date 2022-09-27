package remiliaMarine.tofu.world.gen.feature;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLeekBush extends WorldGenerator {

    private final IBlockState bushState;
    
    /** Block has to be an instance of BlockBush */
    public WorldGenLeekBush(IBlockState state)
    {
        this.bushState = state;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getBlock().isAir(iblockstate, worldIn, position) || iblockstate.getBlock().isLeaves(iblockstate, worldIn, position)) && position.getY() > 0; iblockstate = worldIn.getBlockState(position))
        {
            position = position.down();
        }

        for (int i = 0; i < 128; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && ((BlockBush)bushState.getBlock()).canBlockStay(worldIn, blockpos, this.bushState))
            {
                worldIn.setBlockState(blockpos, this.bushState, 2);
            }
        }

        return true;
    }

}
