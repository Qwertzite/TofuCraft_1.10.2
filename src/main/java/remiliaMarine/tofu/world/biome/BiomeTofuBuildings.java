package remiliaMarine.tofu.world.biome;

import net.minecraft.world.biome.Biome;

public class BiomeTofuBuildings extends BiomeTofu {
	
    public BiomeTofuBuildings(Biome.BiomeProperties property)
    {
        super(property);
        this.treesPerChunk = -999;
        this.tofuPerChunk = 5;
    }
}
