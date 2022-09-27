package remiliaMarine.tofu.world;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.event.terraingen.TerrainGen;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.util.ModLog;
import remiliaMarine.tofu.util.Utils;
import remiliaMarine.tofu.world.gen.TcMapGenBase;
import remiliaMarine.tofu.world.gen.TcMapGenCaves;
import remiliaMarine.tofu.world.gen.TcMapGenMineshaft;
import remiliaMarine.tofu.world.gen.TcMapGenRavine;
import remiliaMarine.tofu.world.gen.feature.WorldGenTofuDungeons;

public class ChunkProviderTofu implements IChunkGenerator {
	
	public static IBlockState TERRAIN = TcBlocks.tofuTerrain.getDefaultState();
	public static IBlockState BLKMILK = TcBlocks.soymilkStill.getDefaultState();

    /** RNG. */
    private final Random rand;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves perlin1;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves perlin2;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves perlinMain;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorPerlin surfaceHeight;

    /** A NoiseGeneratorOctaves used in generating terrain */
    public NoiseGeneratorOctaves scaleNoise;

    /** A NoiseGeneratorOctaves used in generating terrain */
    public NoiseGeneratorOctaves depthNoise;
    public NoiseGeneratorOctaves mobSpawnerNoise;

    /** Reference to the World object. */
    private final World worldObj;

    /** are map structures going to be generated (e.g. strongholds) */
    private final boolean mapFeaturesEnabled;
    private WorldType worldType;

    /** Holds the overall noise array used in chunk generation */
    private double[] noiseArray;
    private final float[] parabolicField;
    private double[] stoneNoise = new double[256];
    private final TcMapGenBase caveGenerator = new TcMapGenCaves();

    /** Holds Stronghold Generator */
    //private MapGenStronghold strongholdGenerator = new MapGenStronghold();

    /** Holds Village Generator */
    private final MapGenVillage villageGenerator = new MapGenVillage();

    /** Holds Mineshaft Generator */
    private final TcMapGenMineshaft mineshaftGenerator = new TcMapGenMineshaft();
    //private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();

    /** Holds ravine generator */
    private final TcMapGenRavine ravineGenerator = new TcMapGenRavine();

    /** The biomes that are used to generate the chunk */
    private Biome[] biomesForGeneration;

    double[] noise1;
    double[] noise2;
    double[] noise3;
    double[] noise4;

    int[][] field_73219_j = new int[32][32];
	
	
    public ChunkProviderTofu(World par1World, long par2, boolean par4)
    {
        this.worldObj = par1World;
        this.mapFeaturesEnabled = par4;
        this.worldType = par1World.getWorldInfo().getTerrainType();
        this.rand = new Random(par2);
        this.perlin1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.perlin2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.perlinMain = new NoiseGeneratorOctaves(this.rand, 8);
        this.surfaceHeight = new NoiseGeneratorPerlin(this.rand, 4);
        this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
        this.depthNoise = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseArray = new double[825];
        this.parabolicField = new float[25];

        for (int j = -2; j <= 2; ++j)
        {
            for (int k = -2; k <= 2; ++k)
            {
                float f = 10.0F / MathHelper.sqrt_float((float)(j * j + k * k) + 0.2F);
                this.parabolicField[j + 2 + (k + 2) * 5] = f;
            }
        }
        ContextTofu ctx = new ContextTofu(perlin1, perlin2, perlinMain, surfaceHeight, scaleNoise, depthNoise, mobSpawnerNoise);
        ctx = TerrainGen.getModdedNoiseGenerators(par1World, this.rand, ctx);
        this.perlin1 = ctx.getLPerlin1();
        this.perlin2 = ctx.getLPerlin2();
        this.perlinMain = ctx.getPerlin();
        this.surfaceHeight = ctx.getHeight();
        this.scaleNoise = ctx.getScale();
        this.depthNoise = ctx.getDepth();
        this.mobSpawnerNoise = ctx.getMobSpawner();
    }
	
    /**
     * Generates the shape of the terrain for the chunk though its all stone though the water is frozen if the
     * temperature is low enough
     */
    public void generateTerrain(int par1, int par2, ChunkPrimer chunkprimer)
    {
        byte b0 = 63;
        this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, par1 * 4 - 2, par2 * 4 - 2, 10, 10);
        this.initializeNoiseField(par1 * 4, 0, par2 * 4);

        for (int k = 0; k < 4; ++k)
        {
            int l = k * 5;
            int i1 = (k + 1) * 5;

            for (int j1 = 0; j1 < 4; ++j1)
            {
                int k1 = (l + j1) * 33;
                int l1 = (l + j1 + 1) * 33;
                int i2 = (i1 + j1) * 33;
                int j2 = (i1 + j1 + 1) * 33;

                for (int k2 = 0; k2 < 32; ++k2)
                {
                    double d0 = 0.125D;
                    double d1 = this.noiseArray[k1 + k2];
                    double d2 = this.noiseArray[l1 + k2];
                    double d3 = this.noiseArray[i2 + k2];
                    double d4 = this.noiseArray[j2 + k2];
                    double d5 = (this.noiseArray[k1 + k2 + 1] - d1) * d0;
                    double d6 = (this.noiseArray[l1 + k2 + 1] - d2) * d0;
                    double d7 = (this.noiseArray[i2 + k2 + 1] - d3) * d0;
                    double d8 = (this.noiseArray[j2 + k2 + 1] - d4) * d0;

                    for (int l2 = 0; l2 < 8; ++l2)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i3 = 0; i3 < 4; ++i3)
                        {
                        	@SuppressWarnings("unused")
							int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
                        	short short1 = 256;
                        	j3 -= short1;
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;

                            for (int k3 = 0; k3 < 4; ++k3)
                            {
                                if ((d15 += d16) > 0.0D)
                                {
                                	chunkprimer.setBlockState(k*4 + i3, k2*8 + l2, j1*4 + k3, ChunkProviderTofu.TERRAIN);
                                }
                                else if (k2 * 8 + l2 < b0)
                                {
                                	chunkprimer.setBlockState(k*4 + i3, k2*8 + l2, j1*4 + k3, ChunkProviderTofu.BLKMILK);
                                }
                            }
                            
                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }
    
    /**
     * Replaces the stone that was placed in with blocks that match the biome
     */
    public void replaceBlocksForBiome(int par1, int par2, ChunkPrimer primer, Biome[] biomeGenBases)
    {
        double d0 = 0.03125D;
        this.stoneNoise = this.surfaceHeight.getRegion(this.stoneNoise, (double) (par1 * 16), (double) (par2 * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

        for (int k = 0; k < 16; ++k)
        {
            for (int l = 0; l < 16; ++l)
            {
                Biome biomegenbase = biomeGenBases[l + k * 16];
                biomegenbase.genTerrainBlocks(this.worldObj, this.rand, primer, par1 * 16 + k, par2 * 16 + l, this.stoneNoise[l + k * 16]);
            }
        }
    }
    
    
    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    @Override
    public Chunk provideChunk(int x, int z)
    {
        this.rand.setSeed(x * 341873128711L + z * 132897987542L);
        ChunkPrimer chunkprimer = new ChunkPrimer();//var3

        this.generateTerrain(x, z, chunkprimer);
        this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.replaceBlocksForBiome(x, z, chunkprimer, this.biomesForGeneration);
        this.caveGenerator.generate(this.worldObj, x, z, chunkprimer);
        this.ravineGenerator.generate(this.worldObj, x, z, chunkprimer);

        if (this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.generate(this.worldObj, x, z, chunkprimer);
            this.villageGenerator.generate(this.worldObj, x, z, chunkprimer);
            //this.strongholdGenerator.func_151539_a(this, this.worldObj, par1, par2, chunkprimer); comment outed too in TC for mc1.7.10
            //this.scatteredFeatureGenerator.func_151539_a(this, this.worldObj, par1, par2, chunkprimer);
        }

        Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i)
        {
            abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
        }
        chunk.generateSkylightMap();
        return chunk;
        
    }
    
    /**
     * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
     * size.
     */
    private void initializeNoiseField(int par2, int par3, int par4)
    {
//        double var44 = 684.412D;
//        double var45 = 684.412D;
//        double var43 = 512.0D;
//        double var42 = 512.0D;
        this.noise4 = this.depthNoise.generateNoiseOctaves(this.noise4, par2, par4, 5, 5, 200.0D, 200.0D, 0.5D);
        this.noise1 = this.perlinMain.generateNoiseOctaves(this.noise1, par2, par3, par4, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
        this.noise2 = this.perlin1.generateNoiseOctaves(this.noise2, par2, par3, par4, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        this.noise3 = this.perlin2.generateNoiseOctaves(this.noise3, par2, par3, par4, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        int l = 0;
        int i1 = 0;

        for (int j1 = 0; j1 < 5; ++j1)
        {
            for (int k1 = 0; k1 < 5; ++k1)
            {
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                byte b0 = 2;
                Biome biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

                for (int l1 = -b0; l1 <= b0; ++l1)
                {
                    for (int i2 = -b0; i2 <= b0; ++i2)
                    {
                        Biome biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                        float f3 = biomegenbase1.getBaseHeight();
                        float f4 = biomegenbase1.getHeightVariation();

                        if (this.worldType == WorldType.AMPLIFIED && f3 > 0.0F)
                        {
                            f3 = 1.0F + f3 * 2.0F;
                            f4 = 1.0F + f4 * 4.0F;
                        }

                        float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);

                        if (biomegenbase1.getBaseHeight() > biomegenbase.getBaseHeight())
                        {
                            f5 /= 2.0F;
                        }

                        f += f4 * f5;
                        f1 += f3 * f5;
                        f2 += f5;
                    }
                }

                f /= f2;
                f1 /= f2;
                f = f * 0.9F + 0.1F;
                f1 = (f1 * 4.0F - 1.0F) / 8.0F;
                double d13 = this.noise4[i1] / 8000.0D;

                if (d13 < 0.0D)
                {
                    d13 = -d13 * 0.3D;
                }

                d13 = d13 * 3.0D - 2.0D;

                if (d13 < 0.0D)
                {
                    d13 /= 2.0D;

                    if (d13 < -1.0D)
                    {
                        d13 = -1.0D;
                    }

                    d13 /= 1.4D;
                    d13 /= 2.0D;
                }
                else
                {
                    if (d13 > 1.0D)
                    {
                        d13 = 1.0D;
                    }

                    d13 /= 8.0D;
                }

                ++i1;
                double d12 = (double)f1;
                double d14 = (double)f;
                d12 += d13 * 0.2D;
                d12 = d12 * 8.5D / 8.0D;
                double d5 = 8.5D + d12 * 4.0D;

                for (int j2 = 0; j2 < 33; ++j2)
                {
                    double d6 = ((double)j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

                    if (d6 < 0.0D)
                    {
                        d6 *= 4.0D;
                    }

                    double d7 = this.noise2[l] / 512.0D;
                    double d8 = this.noise3[l] / 512.0D;
                    double d9 = (this.noise1[l] / 10.0D + 1.0D) / 2.0D;
                    double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

                    if (j2 > 29)
                    {
                        double d11 = (double)((float)(j2 - 29) / 3.0F);
                        d10 = d10 * (1.0D - d11) + -10.0D * d11;
                    }

                    this.noiseArray[l] = d10;
                    ++l;
                }
            }
        }
    }

    /**
     * Populates chunk with ores etc etc
     */
    @Override
    public void populate(int x, int z)
    {
        BlockSand.fallInstantly = true;
        int var4 = x * 16;
        int var5 = z * 16;
        BlockPos blockpos = new BlockPos(var4, 0, var5);
        Biome biome = this.worldObj.getBiome(blockpos.add(16, 0, 16));
        long var7 = this.rand.nextLong() / 2L * 2L + 1L;
        long var9 = this.rand.nextLong() / 2L * 2L + 1L;
        long tofuSeed = Utils.getSeedForTofuWorld(this.worldObj);
        this.rand.setSeed(x * var7 + z * var9 ^ tofuSeed);
        boolean var11 = false;
        ChunkPos chunkpos = new ChunkPos(x, z);
        
        if (this.mapFeaturesEnabled)
        {
        	this.mineshaftGenerator.generateStructuresInChunk(this.worldObj, this.rand, x, z);
            var11 = this.villageGenerator.generateStructure(this.worldObj, this.rand, chunkpos);
            if (var11)
            {
                ModLog.debug("village generated: x=%d z=%d, biome: %s", x << 4, z << 4, biome);
            }
            //this.strongholdGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
            //this.scatteredFeatureGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
        }

        int var12;
        int var13;
        int var14;

        if (this.rand.nextInt(4) == 0)
        {
            var12 = this.rand.nextInt(16) + 8;
            var13 = this.rand.nextInt(128);
            var14 = this.rand.nextInt(16) + 8;
            (new WorldGenLakes(TcBlocks.soymilkStill)).generate(this.worldObj, this.rand, blockpos.add(var12, var13, var14));
        }

        int var15;
        WorldGenMinable worldgenminable;
        
        worldgenminable = new WorldGenMinable(TcBlocks.oreTofuDiamond.getDefaultState(), 4, BlockMatcher.forBlock(TcBlocks.tofuTerrain));

        for (var12 = 0; var12 < 6; ++var12)
        {
            var13 = this.rand.nextInt(16);
            var14 = this.rand.nextInt(20) + 5;
            var15 = this.rand.nextInt(16);
            worldgenminable.generate(this.worldObj, this.rand, blockpos.add(var13, var14, var15));
        }
        
		worldgenminable = new WorldGenMinable(TcBlocks.tofuKinu.getDefaultState(), 32, BlockMatcher.forBlock(TcBlocks.tofuTerrain));

		for (var12 = 0; var12 < 18; ++var12) {

			var13 = this.rand.nextInt(16);
			var14 = this.rand.nextInt(60);
			var15 = this.rand.nextInt(16);
			worldgenminable.generate(this.worldObj, this.rand, blockpos.add(var13, var14, var15));
		}

		worldgenminable = new WorldGenMinable(TcBlocks.tofuMinced.getDefaultState(), 32, BlockMatcher.forBlock(TcBlocks.tofuTerrain));

		for (var12 = 0; var12 < 12; ++var12) {

			var13 = this.rand.nextInt(16);
			var14 = this.rand.nextInt(256);
			var15 = this.rand.nextInt(16);
			worldgenminable.generate(this.worldObj, this.rand, blockpos.add(var13, var14, var15));
		}
        
		for (var14 = 0; var14 < 8; ++var14)
        {
            var13 = this.rand.nextInt(16) + 8;
            var14 = this.rand.nextInt(128);
            var15 = this.rand.nextInt(16) + 8;

            if ((new WorldGenTofuDungeons()).generate(this.worldObj, this.rand, new BlockPos(var13, var14, var15)))
            {
                ModLog.debug("Tofu dungeon spawned at (%d, %d, %d)", var13, var14, var15);
            }
        }


        biome.decorate(this.worldObj, this.rand, new BlockPos(var4, 0, var5));
        WorldEntitySpawner.performWorldGenSpawning(this.worldObj, biome, var4 + 8, var5 + 8, 16, 16, this.rand);
        var4 += 8;
        var5 += 8;

        for (var12 = 0; var12 < 16; ++var12)
        {
            for (var13 = 0; var13 < 16; ++var13)
            {
                BlockPos blockpos1 = this.worldObj.getPrecipitationHeight(blockpos.add(var12, 0, var13));
                BlockPos blockpos2 = blockpos1.down();
                //var14
                if (this.worldObj.canBlockFreezeWater(blockpos2))
                {
                    this.worldObj.setBlockState(blockpos2, Blocks.ICE.getDefaultState(), 2);
                }

                if (this.worldObj.canSnowAt(blockpos1, true))
                {
                    this.worldObj.setBlockState(blockpos1, Blocks.SNOW_LAYER.getDefaultState(), 2);
                }
            }
        }

        // Replace mob spawners
        for (int i = 0; i < 64; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                for (int k = 0; k < 16; k++)
                {
                	BlockPos pos = blockpos.add(j, 0, k);
                    if (worldObj.getBlockState(pos).getBlock() == Blocks.MOB_SPAWNER)
                    {
                        this.replaceMobSpawner(pos);
                    }
                }
            }
        }

        BlockSand.fallInstantly = false;
    }
    
    /**
     * Replace mob spawners to spawn mobs excepting Tofu Creeper
     * @param bx
     * @param by
     * @param bz
     */
    private void replaceMobSpawner(BlockPos pos)
    {
        TileEntityMobSpawner tile = (TileEntityMobSpawner)worldObj.getTileEntity(pos);
        if (tile != null && !tile.getSpawnerBaseLogic().getCachedEntity().getName().equals("TofuCreeper"))
        {
            tile.getSpawnerBaseLogic().setEntityName("TofuSlime");

            // Remove web in surroundings
            for (int x = -20; x < 20; x++)
            {
                for (int y = 0; y < 2; y++)
                {
                    for (int z = -20; z < 20; z++)
                    {
                    	BlockPos posAround = pos.add(x, y, z);
                        if ((x > -2 && x < 2 || z > -2 && z < 2)
                        		&& worldObj.getBlockState(posAround).getBlock() == Blocks.WEB)
                        {
                            worldObj.setBlockToAir(posAround);
                        }
                    }
                }
            }

            //ModLog.debug("Replaced spawner at (%d, %d, %d)", bx, by, bz);
        }
    }
    
    

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        Biome biome = this.worldObj.getBiome(pos);
        return biome == null ? null : biome.getSpawnableList(creatureType);
	}

	@Override
	public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
        return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
        if (this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.generate(this.worldObj, x, z, null);
            this.villageGenerator.generate(this.worldObj, x, z, null);
            //this.strongholdGenerator.func_151539_a(this, this.worldObj, par1, par2, (byte[])null);
            //this.scatteredFeatureGenerator.func_151539_a(this, this.worldObj, par1, par2, (byte[])null);
        }
	}
	

}
