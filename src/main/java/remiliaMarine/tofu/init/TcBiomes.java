package remiliaMarine.tofu.init;

import java.util.Arrays;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.util.ModLog;
import remiliaMarine.tofu.world.biome.BiomeLeekPlains;
import remiliaMarine.tofu.world.biome.BiomeTofu;
import remiliaMarine.tofu.world.biome.BiomeTofuBuildings;
import remiliaMarine.tofu.world.biome.BiomeTofuForest;
import remiliaMarine.tofu.world.biome.BiomeTofuHills;
import remiliaMarine.tofu.world.biome.BiomeTofuPlains;
import remiliaMarine.tofu.world.biome.BiomeTofuRiver;

public class TcBiomes {
    public static BiomeTofu[] TOFU_BIOME_LIST = new BiomeTofu[32];

    public static BiomeTofu TOFU_PLAINS;
    public static BiomeTofu TOFU_FOREST;
    public static BiomeTofu TOFU_BUILDINGS;
    public static BiomeTofu TOFU_EXTREME_HILLS;
    public static BiomeTofu TOFU_PLAIN_HILLS;
    public static BiomeTofu TOFU_FOREST_HILLS;
    public static BiomeTofu TOFU_HILLS_EDGE;
    public static BiomeTofu TOFU_LEEK_PLAINS;
    public static BiomeTofu TOFU_RIVER;
    
    public static BiomeTofu[] decorationBiomes;
    
    public static void register(Configuration conf)
    {
    	//TofuPlains
    	TcBiomes.TOFU_PLAINS = (BiomeTofu) (new BiomeTofuPlains(new Biome.BiomeProperties("TofuPlains")
        		.setBaseHeight(BiomeTofu.height_Tofu_Base)
        		.setHeightVariation(BiomeTofu.height_Tofu_Variation)
    			.setWaterColor(9286496)
    			.setTemperature(0.422f)
    			.setRainfall(0.917f)));
    	TcBiomes.TOFU_BIOME_LIST[0] = TcBiomes.TOFU_PLAINS;
    	GameRegistry.register(TcBiomes.TOFU_PLAINS, new ResourceLocation(TofuCraftCore.MODID, "TofuPlains"));
    	
    	//TofuForest
    	TcBiomes.TOFU_FOREST = (BiomeTofu) (new BiomeTofuForest(new Biome.BiomeProperties("TofuForest")
        		.setBaseHeight(BiomeTofu.height_Tofu_Base)
        		.setHeightVariation(BiomeTofu.height_Tofu_Variation)
    			.setWaterColor(9286496)
    			.setTemperature(0.475F)
    			.setRainfall(0.969F)));
    	TcBiomes.TOFU_BIOME_LIST[1] = TcBiomes.TOFU_FOREST;
    	GameRegistry.register(TcBiomes.TOFU_FOREST, new ResourceLocation(TofuCraftCore.MODID, "TofuForest"));
    	
    	//TofuBuildings
    	TcBiomes.TOFU_BUILDINGS = (BiomeTofu) (new BiomeTofuBuildings(new Biome.BiomeProperties("TofuBuildings")
        		.setBaseHeight(BiomeTofu.height_Tofu_Base)
        		.setHeightVariation(BiomeTofu.height_Tofu_Variation)
    			.setWaterColor(9286496)
    			.setTemperature(0.422F)
    			.setRainfall(0.917F)));
    	TcBiomes.TOFU_BIOME_LIST[2] = TcBiomes.TOFU_BUILDINGS;
    	GameRegistry.register(TcBiomes.TOFU_BUILDINGS, new ResourceLocation(TofuCraftCore.MODID, "TofuBuildings"));
    	
    	//TofuExtremeHills
    	TcBiomes.TOFU_EXTREME_HILLS = (BiomeTofu) (new BiomeTofuHills(new Biome.BiomeProperties("TofuExtremeHills")
        		.setBaseHeight(1.2F)
        		.setHeightVariation(0.3F)
    			.setWaterColor(9286496)
    			.setTemperature(0.317F)
    			.setRainfall(0.759F)));
    	TcBiomes.TOFU_BIOME_LIST[3] = TcBiomes.TOFU_EXTREME_HILLS;
    	GameRegistry.register(TcBiomes.TOFU_EXTREME_HILLS, new ResourceLocation(TofuCraftCore.MODID, "TofuExtremeHills"));

        
    	TcBiomes.TOFU_PLAIN_HILLS = (BiomeTofu) (new BiomeTofuPlains(new Biome.BiomeProperties("TofuPlainHills")
        		.setBaseHeight(0.3F)
        		.setHeightVariation(0.7F)
    			.setWaterColor(9286496)
    			.setTemperature(0.422F)
    			.setRainfall(0.917F)));
    	TcBiomes.TOFU_BIOME_LIST[4] = TcBiomes.TOFU_PLAIN_HILLS;
    	GameRegistry.register(TcBiomes.TOFU_PLAIN_HILLS, new ResourceLocation(TofuCraftCore.MODID, "TofuPlainHills"));
        
        
    	TcBiomes.TOFU_FOREST_HILLS = (BiomeTofu) (new BiomeTofuForest(new Biome.BiomeProperties("TofuForestHills")
        		.setBaseHeight(0.3F)
        		.setHeightVariation(0.7F)
    			.setWaterColor(9286496)
    			.setTemperature(0.475F)
    			.setRainfall(0.969F)));
    	TcBiomes.TOFU_BIOME_LIST[5] = TcBiomes.TOFU_FOREST_HILLS;
    	GameRegistry.register(TcBiomes.TOFU_FOREST_HILLS, new ResourceLocation(TofuCraftCore.MODID, "TofuForestHills"));
        
        
    	TcBiomes.TOFU_HILLS_EDGE = (BiomeTofu) (new BiomeTofuHills(new Biome.BiomeProperties("TofuExtremeHillsEdge")
        		.setBaseHeight(0.2F)
        		.setHeightVariation(0.8F)
    			.setWaterColor(9286496)
    			.setTemperature(0.317F)
    			.setRainfall(0.759F)));
    	TcBiomes.TOFU_BIOME_LIST[6] = TcBiomes.TOFU_HILLS_EDGE;
    	GameRegistry.register(TcBiomes.TOFU_HILLS_EDGE, new ResourceLocation(TofuCraftCore.MODID, "TofuExtremeHillsEdge"));
        
        
    	TcBiomes.TOFU_LEEK_PLAINS = (BiomeTofu) (new BiomeLeekPlains(new Biome.BiomeProperties("LeekPlains")
        		.setBaseHeight(BiomeTofu.height_Tofu_Base)
        		.setHeightVariation(BiomeTofu.height_Tofu_Variation)
    			.setWaterColor(9286496)
    			.setTemperature(0.510F)
    			.setRainfall(0.934F)));
    	TcBiomes.TOFU_BIOME_LIST[7] = TcBiomes.TOFU_LEEK_PLAINS;
    	GameRegistry.register(TcBiomes.TOFU_LEEK_PLAINS, new ResourceLocation(TofuCraftCore.MODID, "LeekPlains"));
        
        
    	TcBiomes.TOFU_RIVER = (BiomeTofu) (new BiomeTofuRiver(new Biome.BiomeProperties("TofuRiver")
        		.setBaseHeight(BiomeTofu.height_ShallowWaters_Base)
        		.setHeightVariation(BiomeTofu.height_ShallowWaters_Variation)
    			.setWaterColor(9286496)
    			.setTemperature(0.510F)
    			.setRainfall(0.934F)));
    	TcBiomes.TOFU_BIOME_LIST[8] = TcBiomes.TOFU_RIVER;
    	GameRegistry.register(TcBiomes.TOFU_RIVER, new ResourceLocation(TofuCraftCore.MODID, "TofuRiver"));
    	

        decorationBiomes = new BiomeTofu[]{
                TOFU_PLAINS, TOFU_LEEK_PLAINS, TOFU_PLAINS, TOFU_FOREST, TOFU_BUILDINGS, TOFU_EXTREME_HILLS};
    	
        // Register in the Forge Biome Dictionary
        for (BiomeTofu biome : TOFU_BIOME_LIST)
        {
            if (biome != null) BiomeDictionary.registerBiomeType(biome, TofuCraftCore.BIOME_TYPE_TOFU);
        }
        ModLog.debug("Registered biomes as TOFU: " + Arrays.toString(BiomeDictionary.getBiomesForType(TofuCraftCore.BIOME_TYPE_TOFU)));
    }
    
    
}
