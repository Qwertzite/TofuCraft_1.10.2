package remiliaMarine.tofu.world.gen.layer;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import remiliaMarine.tofu.init.TcBiomes;

public class GenLayerRiverMix extends GenLayerTofu {

    private GenLayer biomePatternGeneratorChain;
    private GenLayer riverPatternGeneratorChain;
    @SuppressWarnings("unused")
	private static final String __OBFID = "CL_00000567";

    public GenLayerRiverMix(long par1, GenLayer par3GenLayer, GenLayer par4GenLayer)
    {
        super(par1);
        this.biomePatternGeneratorChain = par3GenLayer;
        this.riverPatternGeneratorChain = par4GenLayer;
    }

    /**
     * Initialize layer's local worldGenSeed based on its own baseSeed and the world's global seed (passed in as an
     * argument).
     */
    public void initWorldGenSeed(long par1)
    {
        this.biomePatternGeneratorChain.initWorldGenSeed(par1);
        this.riverPatternGeneratorChain.initWorldGenSeed(par1);
        super.initWorldGenSeed(par1);
    }

    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    public int[] getInts(int par1, int par2, int par3, int par4)
    {
        int[] aint = this.biomePatternGeneratorChain.getInts(par1, par2, par3, par4);
        int[] aint1 = this.riverPatternGeneratorChain.getInts(par1, par2, par3, par4);
        int[] aint2 = IntCache.getIntCache(par3 * par4);

        for (int i1 = 0; i1 < par3 * par4; ++i1)
        {
            if (aint1[i1] == Biome.getIdForBiome(TcBiomes.TOFU_RIVER))
            {
                aint2[i1] = aint1[i1] & 255;
            }
            else
            {
                aint2[i1] = aint[i1];
            }
        }

        return aint2;
    }
}
