package remiliaMarine.tofu.world.gen;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public abstract class TcMapGenStructure extends TcMapGenBase {
    /**
     * Used to store a list of all structures that have been recursively generated. Used so that during recursive
     * generation, the structure generator can avoid generating structures that intersect ones that have already been
     * placed.
     */
    protected Long2ObjectMap<StructureStart> structureMap = new Long2ObjectOpenHashMap<StructureStart>();
    
    /**
     * Recursively called by generate() (generate) and optionally by itself.
     */
    @Override
    protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn)
    {
        if (!this.structureMap.containsKey(ChunkPos.asLong(chunkX, chunkZ)))
        {
            this.rand.nextInt();

            try
            {
                if (this.canSpawnStructureAtCoords(chunkX, chunkZ))
                {
                    StructureStart structurestart = this.getStructureStart(chunkX, chunkZ);
                    this.structureMap.put(ChunkPos.asLong(chunkX, chunkZ), structurestart);
                }
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception preparing structure feature");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Feature being prepared");
                crashreportcategory.setDetail("Is feature chunk", new CrashReportDetailIsFeatureChunk(this, chunkX, chunkZ));
                crashreportcategory.addCrashSection("Chunk location", String.format("%d,%d", new Object[] {Integer.valueOf(chunkX), Integer.valueOf(chunkZ)}));
                crashreportcategory.setDetail("Chunk pos hash", new CrashReportDetailChunkPosHash(this, chunkX, chunkZ));
                crashreportcategory.setDetail("Structure type", new CrashReportDetailStructureType(this));
                throw new ReportedException(crashreport);
            }
        }
    }
    
    /**
     * Generates structures in specified chunk next to existing structures. Does *not* generate StructureStarts.
     */
    public boolean generateStructuresInChunk(World par1World, Random par2Random, int par3, int par4)
    {
        int k = (par3 << 4) + 8;
        int l = (par4 << 4) + 8;
        boolean flag = false;
        Iterator<StructureStart> iterator = this.structureMap.values().iterator();

        while (iterator.hasNext())
        {
            StructureStart structurestart = (StructureStart)iterator.next();

            if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().intersectsWith(k, l, k + 15, l + 15))
            {
                structurestart.generateStructure(par1World, par2Random, new StructureBoundingBox(k, l, k + 15, l + 15));
                flag = true;
            }
        }

        return flag;
    }
    
    /**
     * Returns true if the structure generator has generated a structure located at the given position tuple.
     */
    public boolean hasStructureAt(BlockPos pos)
    {
    	int par1 = pos.getX();
    	//int par2 = pos.getY();
    	int par3 = pos.getZ();    	
        Iterator<StructureStart> iterator = this.structureMap.values().iterator();

        while (iterator.hasNext())
        {
            StructureStart structurestart = (StructureStart)iterator.next();

            if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().intersectsWith(par1, par3, par1, par3))
            {
                Iterator<StructureComponent> iterator1 = structurestart.getComponents().iterator();

                while (iterator1.hasNext())
                {
                    StructureComponent structurecomponent = (StructureComponent)iterator1.next();

                    if (structurecomponent.getBoundingBox().isVecInside(pos))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    
    public BlockPos getNearestInstance(World par1World, int par2, int par3, int par4)
    {
        this.worldObj = par1World;
        this.rand.setSeed(par1World.getSeed());
        long l = this.rand.nextLong();
        long i1 = this.rand.nextLong();
        long j1 = (par2 >> 4) * l;
        long k1 = (par4 >> 4) * i1;
        this.rand.setSeed(j1 ^ k1 ^ par1World.getSeed());
        this.recursiveGenerate(par1World, par2 >> 4, par4 >> 4, 0, 0, null);
        double d0 = Double.MAX_VALUE;
        BlockPos chunkposition = null;
        Iterator<StructureStart> iterator = this.structureMap.values().iterator();
        BlockPos chunkposition1;
        int l1;
        int i2;
        double d1;
        int j2;

        while (iterator.hasNext())
        {
            StructureStart structurestart = (StructureStart)iterator.next();

            if (structurestart.isSizeableStructure())
            {
                StructureComponent structurecomponent = (StructureComponent)structurestart.getComponents().get(0);
                chunkposition1 = structurecomponent.getBoundingBoxCenter(); // getCenter
                i2 = chunkposition1.getX() - par2;
                l1 = chunkposition1.getY() - par3;
                j2 = chunkposition1.getZ() - par4;
                d1 = (i2 + i2 * l1 * l1 + j2 * j2);

                if (d1 < d0)
                {
                    d0 = d1;
                    chunkposition = chunkposition1;
                }
            }
        }

        if (chunkposition != null)
        {
            return chunkposition;
        }
        else
        {
            List<BlockPos> list = this.getCoordList();

            if (list != null)
            {
                BlockPos chunkposition2 = null;
                Iterator<BlockPos> iterator1 = list.iterator();

                while (iterator1.hasNext())
                {
                    chunkposition1 = (BlockPos)iterator1.next();
                    i2 = chunkposition1.getX() - par2;
                    l1 = chunkposition1.getY() - par3;
                    j2 = chunkposition1.getZ() - par4;
                    d1 = (i2 + i2 * l1 * l1 + j2 * j2);

                    if (d1 < d0)
                    {
                        d0 = d1;
                        chunkposition2 = chunkposition1;
                    }
                }

                return chunkposition2;
            }
            else
            {
                return null;
            }
        }
    }
    
    /**
     * Returns a list of other locations at register the structure generation has been run, or null if not relevant to this
     * structure generator.
     */
    protected List<BlockPos> getCoordList()
    {
        return null;
    }
    
    protected abstract boolean canSpawnStructureAtCoords(int i, int j);

    protected abstract StructureStart getStructureStart(int i, int j);
    
}
