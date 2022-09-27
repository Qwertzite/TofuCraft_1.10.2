package remiliaMarine.tofu.world.gen;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.util.Utils;

public class TcMapGenBase extends MapGenBase {
    @Override
    public void generate(World worldIn, int x, int z, ChunkPrimer primer)
    {
        long tofuSeed = Utils.getSeedForTofuWorld(worldIn);
        int k = this.range;
        this.worldObj = worldIn;
        this.rand.setSeed(tofuSeed);
        long l = this.rand.nextLong();
        long i1 = this.rand.nextLong();

        for (int j1 = x - k; j1 <= x + k; ++j1)
        {
            for (int k1 = z - k; k1 <= z + k; ++k1)
            {
                long l1 = j1 * l;
                long i2 = k1 * i1;
                this.rand.setSeed(l1 ^ i2 ^ tofuSeed);
                this.recursiveGenerate(worldIn, j1, k1, x, z, primer); // recursiveGenerate
            }
        }
    }
    
    protected boolean canReplaceBlock(IBlockState p_175793_1_, IBlockState p_175793_2_)
    {
    	if(p_175793_1_.getBlock() == TcBlocks.tofuTerrain) return true;
    	if(p_175793_1_.getBlock() == Blocks.STONE) return true;
    	if(p_175793_1_.getBlock() == Blocks.DIRT) return true;
    	if(p_175793_1_.getBlock() == Blocks.GRASS) return true;
    	if(p_175793_1_.getBlock() == Blocks.HARDENED_CLAY) return true;
    	if(p_175793_1_.getBlock() == Blocks.STAINED_HARDENED_CLAY) return true;
    	if(p_175793_1_.getBlock() == Blocks.SANDSTONE) return true;
    	if(p_175793_1_.getBlock() == Blocks.RED_SANDSTONE) return true;
    	if(p_175793_1_.getBlock() == Blocks.MYCELIUM) return true;
    	if(p_175793_1_.getBlock() == Blocks.SNOW_LAYER) return true;
    	else {
    		return (p_175793_1_.getBlock() == TcBlocks.tofuMinced || p_175793_1_.getBlock() == Blocks.GRAVEL)
    				&& p_175793_2_.getMaterial() != Material.WATER;
    	}
//TODO write all possible blocks in this world.
    }
}
