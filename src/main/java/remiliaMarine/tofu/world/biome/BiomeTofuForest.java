package remiliaMarine.tofu.world.biome;

import java.util.Random;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeTofuForest extends BiomeTofu {
    public BiomeTofuForest(Biome.BiomeProperties property)
    {
        super(property);
        this.treesPerChunk = 10;
        this.chanceOfLeeks = 10;
    }
    
    /**
     * Gets a WorldGen appropriate for this biome.
     */
    @Override
    public WorldGenAbstractTree genBigTreeChance(Random par1Random) // getRandomWorldGenForTrees
    {
        return this.worldGeneratorTrees;
    }
    
}
