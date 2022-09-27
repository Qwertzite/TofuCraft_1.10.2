package remiliaMarine.tofu.data;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import remiliaMarine.tofu.block.BlockTofuBase;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.item.TofuMaterial;

public class TofuInfo {
    public final TofuMaterial tofuMaterial;
    public final float hardness;
    public final boolean hasCustomResistance;
    public final float resistance;
    public final Material material;
    public final SoundType stepSound;
    
    public float lightLevel = 0.0F;
    public String harvestTool = null;
    public int harvestLevel;
    
    public TofuInfo(TofuMaterial tofuMaterial, float hardness, Material material, SoundType stepSound)
    {
        this(tofuMaterial, hardness, false, 0.0F, material, stepSound);
    }
    
    public TofuInfo(TofuMaterial tofuMaterial, float hardness, float resistance, Material material, SoundType stepSound)
    {
        this(tofuMaterial, hardness, true, resistance, material, stepSound);
    }
    
    public TofuInfo(TofuMaterial tofuMaterial, float hardness, boolean hasCustomResistance, float resistance, Material material, SoundType stepSound)
    {
        this.tofuMaterial = tofuMaterial;
        this.hardness = hardness;
        this.hasCustomResistance = hasCustomResistance;
        this.resistance = resistance;
        this.material = material;
        this.stepSound = stepSound;
    }
    
    public TofuInfo setLightLevel(float lightLevel)
    {
        this.lightLevel = lightLevel;
        return this;
    }
    
    public TofuInfo setHarvestLevel(String harvestTool, int harvestLevel)
    {
        this.harvestTool = harvestTool;
        this.harvestLevel = harvestLevel;
        return this;
    }
    
    public BlockTofuBase getBlock()
    {
        return TcBlocks.tofuBlockMap.get(this.tofuMaterial);
    }
    
    public SoundType getSoundType() {
    	return this.stepSound;
    }
    
    public void setBasicFeature(Block block)
    {
//TODO	block.setStepSound(this.stepSound);		has to be done from Block class
        block.setHardness(this.hardness);
        if (this.hasCustomResistance)
        {
            block.setResistance(this.resistance);
        }
        if (this.harvestTool != null)
        {
            block.setHarvestLevel(harvestTool, harvestLevel);
        }
        if (this.lightLevel != 0.0F)
        {
            block.setLightLevel(this.lightLevel);
        }
    }
    
}
