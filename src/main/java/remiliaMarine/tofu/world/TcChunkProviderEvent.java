package remiliaMarine.tofu.world;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.block.BlockSoybeanHell;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.world.gen.feature.WorldGenCrops;

public class TcChunkProviderEvent {

    @SubscribeEvent
    public void decorateBiome(DecorateBiomeEvent.Post event)
    {
        World worldObj = event.getWorld();
        Random rand = event.getRand();
        BlockPos pos = event.getPos();

        // Hellsoybeans
        if (rand.nextInt(600) < Math.min((Math.abs(pos.getX()) + Math.abs(pos.getZ())) / 2, 400) - 100)
        {
            if (Biome.getIdForBiome(worldObj.getBiome(pos)) == Biome.getIdForBiome(Biomes.HELL))
            {
                int k = pos.getX();
                int l = pos.getZ();
                BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
                
                for (int i = 0; i < 10; ++i)
                {
                    int j1 = k + rand.nextInt(16) + 8;
                    int k1 = rand.nextInt(128);
                    int l1 = l + rand.nextInt(16) + 8;
                    mutable.setPos(j1, k1, l1);
                    
                    (new WorldGenCrops((BlockBush)TcBlocks.soybeanHell)
                    		{ 
                    			@Override
								protected IBlockState getStateToPlace() {
                    				return this.plantBlock.getDefaultState().withProperty(BlockSoybeanHell.AGE, 7);
                    			}
                    		})
                    		.generate(worldObj, rand, mutable);
                    	
                    
                }
            }
        }
    }
	
    private final WorldGenerator tofuOreGen = new WorldGenMinable(TcBlocks.oreTofu.getDefaultState(), 4);

    @SubscribeEvent
    public void generateTofuOres(OreGenEvent.Post event)
    {
        World worldObj = event.getWorld();
        Random rand = event.getRand();
        BlockPos pos = event.getPos();

        for (int l = 0; l < 8; ++l)
        {
            int i1 = rand.nextInt(16);
            int j1 = rand.nextInt(82 - 45) + 45;
            int k1 = rand.nextInt(16);
            tofuOreGen.generate(worldObj, rand, pos.add(i1, j1, k1));
        }
    }
}
