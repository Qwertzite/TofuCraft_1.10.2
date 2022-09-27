package remiliaMarine.tofu.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenTallGrassRanged extends WorldGenerator {

    private BlockBush tallGrass;
    private IBlockState tallGrassState;
    private int radius = 8;

    public WorldGenTallGrassRanged(BlockBush block, IBlockState state, int radius)
    {
        this.tallGrass = block;
        this.tallGrassState = state;
        this.radius = radius;
    }
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		
        Block block = null;
        int par3 = position.getX();
        int par4 = position.getY();
        int par5 = position.getZ();
        
        do 
        {
        	IBlockState state = worldIn.getBlockState(position);
            block = state.getBlock();
            if (block != null && !block.isLeaves(state, worldIn, position))
            {
                break;
            }
            par4--;
            
        } while (par4 > 0);

        int planting = radius * radius * 2;
        BlockPos.MutableBlockPos mutablepos = new BlockPos.MutableBlockPos();
        
        for (int i1 = 0; i1 < planting; ++i1)
        {
            int j1 = par3 + rand.nextInt(radius) - rand.nextInt(radius);
            int k1 = par4 + rand.nextInt(4) - rand.nextInt(4);
            int l1 = par5 + rand.nextInt(radius) - rand.nextInt(radius);
            mutablepos.setPos(j1, k1, l1);
            
            if (worldIn.isAirBlock(mutablepos) && this.tallGrass.canBlockStay(worldIn, mutablepos, this.tallGrassState))
            {
                worldIn.setBlockState(mutablepos, this.tallGrassState, 2);
            }
        }

        return true;
	}

}
