package remiliaMarine.tofu.world.gen.feature;

import net.minecraft.block.BlockLog;
import remiliaMarine.tofu.init.TcBlocks;

public class WorldGenApricotTrees extends WorldGenTcTreesBase {
    
	public WorldGenApricotTrees(boolean par1)
    {
        super(par1, 4, TcBlocks.tcLog.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), TcBlocks.tcLeaves.getDefaultState(), false);
    }

}
