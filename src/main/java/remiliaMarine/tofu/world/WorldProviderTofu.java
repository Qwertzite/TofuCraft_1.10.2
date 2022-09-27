package remiliaMarine.tofu.world;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.util.Utils;


public class WorldProviderTofu extends WorldProvider {

	public void init(){
		super.isHellWorld = false;
		super.biomeProvider = new BiomeProviderTofu(this.worldObj.getWorldInfo());
	}
	
	@Override
	public DimensionType getDimensionType() {
        return TofuCraftCore.TOFU_DIMENSION;
	}
	
    /**
     * creates a new world chunk manager for WorldProvider
     */
    @Override
    protected void createBiomeProvider()
    {
        this.biomeProvider = new BiomeProviderTofu(this.worldObj.getWorldInfo());
    }
	
    /**
     * Returns a new chunk provider register generates chunks for this world
     */
    @Override
    public IChunkGenerator createChunkGenerator()
    {
        long newSeed = Utils.getSeedForTofuWorld(this.worldObj);
        return new ChunkProviderTofu(this.worldObj, newSeed, true);
    }
    
    @Override
    public void resetRainAndThunder() {
    	
        super.resetRainAndThunder();

        if(this.worldObj.getGameRules().getBoolean("doDaylightCycle"))
        {
            WorldInfo worldInfo = ObfuscationReflectionHelper.getPrivateValue(DerivedWorldInfo.class, (DerivedWorldInfo) worldObj.getWorldInfo(), "theWorldInfo", "field_76115_a");
            long i = worldInfo.getWorldTime() + 24000L;
            worldInfo.setWorldTime(i - i % 24000L);
        }
    }
    
    @Override
    public boolean canRespawnHere()
    {
        return true;
    }
	
    
    @Override
    public String getWelcomeMessage()
    {
        return "Entering the Tofu World";
    }

    /**
     * A Message to display to the user when they transfer out of this dismension.
     *
     * @return The message to be displayed
     */
    @Override
    public String getDepartMessage()
    {
        return "Leaving the Tofu World";
    }
}
