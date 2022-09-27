package remiliaMarine.tofu.world.biome;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenerator;
import remiliaMarine.tofu.block.BlockLeek;
import remiliaMarine.tofu.entity.EntityTofuSlime;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.world.gen.feature.WorldGenLeekBush;
import remiliaMarine.tofu.world.gen.feature.WorldGenTofuBuilding;
import remiliaMarine.tofu.world.gen.feature.WorldGenTofuTrees;

public class BiomeTofu extends Biome {

    public static final float height_Tofu_Base = 0.05F;
    public static final float height_Tofu_Variation = 0.1F;
    public static final float height_ShallowWaters_Base = -0.5F;
    public static final float height_ShallowWaters_Variation = 0.0F;

    protected int treesPerChunk;
    protected int tofuPerChunk;
    protected int maxGrassPerChunk;
    protected int chanceOfLeeks;
    protected boolean generateLakes;
    
    protected WorldGenTofuTrees worldGeneratorTrees;
    protected WorldGenTofuBuilding worldGeneratorTofuBuilding;
    
    public BiomeTofu(String name) {
    	this(new Biome.BiomeProperties(name)
        		.setBaseHeight(BiomeTofu.height_Tofu_Base)
        		.setHeightVariation(BiomeTofu.height_Tofu_Variation));
    }
    
    public BiomeTofu(Biome.BiomeProperties property)
    {
        super(property);
        
        this.worldGeneratorTrees = new WorldGenTofuTrees(false);
        this.worldGeneratorTofuBuilding = new WorldGenTofuBuilding(false);

        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityTofuSlime.class, 3, 4, 4));

        this.topBlock = TcBlocks.tofuTerrain.getDefaultState();
        this.fillerBlock = TcBlocks.tofuTerrain.getDefaultState();


        this.treesPerChunk = 0;
        this.tofuPerChunk = 0;
        this.maxGrassPerChunk = 1;
        this.chanceOfLeeks = 50;
        this.generateLakes = true;

//        TcBiomes.TOFU_BIOME_LIST[localBiomeId] = this;
    }
    
    /**
     * Gets a WorldGen appropriate for this biome.
     */
    @Override
    public WorldGenAbstractTree genBigTreeChance(Random rand) // getRandomWorldGenForTrees
    {
        return this.worldGeneratorTrees;
    }
    
    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        int i, j, k, l, i1;

        i = this.treesPerChunk;
        if (rand.nextInt(10) == 0)
        {
            ++i;
        }
        {
        	BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        	for (j = 0; j < i; ++j)
        	{
            	k = pos.getX() + rand.nextInt(16) + 8;
            	l = pos.getZ() + rand.nextInt(16) + 8;
            	mutable.setPos( k, worldIn.getHeightmapHeight(k, l), l);
            	WorldGenerator worldgenerator = this.genBigTreeChance(rand);
            	worldgenerator.generate(worldIn, rand, mutable);
        	}
        }
        if (rand.nextInt(this.chanceOfLeeks) == 0)
        {
        	BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        	
            for (j = 0; j < maxGrassPerChunk; j++)
            {
                k = pos.getX() + rand.nextInt(16) + 8;
                l = rand.nextInt(128);
                i1 = pos.getZ() + rand.nextInt(16) + 8;
                mutable.setPos(k, l, i1);
                
                WorldGenerator var6 = new WorldGenLeekBush(TcBlocks.leek.getDefaultState().withProperty(BlockLeek.META, BlockLeek.META_NATURAL));
                var6.generate(worldIn, rand, mutable);
            }
        }

        if (this.generateLakes)
        {
        	BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        	
            for (j = 0; j < 50; ++j)
            {
                k = pos.getX() + rand.nextInt(16) + 8;
                l = rand.nextInt(rand.nextInt(120) + 8);
                i1 = pos.getZ() + rand.nextInt(16) + 8;
                mutable.setPos(k, l, i1);
                (new WorldGenLiquids(TcBlocks.soymilkStill)).generate(worldIn, rand, mutable);
            }
        }

        i = this.tofuPerChunk;
        if (rand.nextInt(50) == 0)
        {
            ++i;
        }
        {
        	BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        	for (j = 0; j < i; ++j)
        	{
            	k = pos.getX() + rand.nextInt(16) + 8;
            	l = pos.getZ() + rand.nextInt(16) + 8;
            	mutable.setPos(k, worldIn.getHeightmapHeight(k, l), l);
            	WorldGenerator worldgenerator = this.worldGeneratorTofuBuilding;
            	worldgenerator.generate(worldIn, rand, mutable);
        	}
        }
        
    }
    
    
    
    
    
    
}
