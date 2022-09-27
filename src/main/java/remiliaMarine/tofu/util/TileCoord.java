package remiliaMarine.tofu.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class TileCoord extends BlockPos{
    public final int dim;

    public TileCoord(int x, int y, int z)
    {
        this(x, y, z, 0);
    }

    public TileCoord(int x, int y, int z, int dim)
    {
    	super(x, y, z);
        this.dim = dim;
    }

    public TileCoord(Vec3i source)
    {
        this(source.getX(), source.getY(), source.getZ(), 0);
    }

    public TileCoord(Vec3i source, int dim)
    {
        super(source);
        this.dim = dim;
    }

    public int getDim() {
    	return this.dim;
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("CoordX", this.getX());
        nbt.setInteger("CoordY", this.getY());
        nbt.setInteger("CoordZ", this.getZ());
        nbt.setInteger("CoordD", this.dim);
    }

    public static TileCoord readFromNBT(NBTTagCompound nbt)
    {
        int x = nbt.getInteger("CoordX");
        int y = nbt.getInteger("CoordY");
        int z = nbt.getInteger("CoordZ");
        int dim = nbt.getInteger("CoordD");
        return new TileCoord(x, y, z, dim);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof TileCoord)
        {
            TileCoord another = (TileCoord)obj;
            return this.getX() == another.getX()
                    && this.getY() == another.getY()
                    && this.getZ() == another.getZ()
                    && this.dim == another.dim;
        } else if (obj instanceof BlockPos ) {
        	return super.equals(obj);
        }
        else return false;
    }

    @Override
    public int hashCode()
    {
        return ((this.getX() << 20) + (this.getZ() << 8) + this.getY()) + this.dim;
    }

    /**
     * Add the given coordinates to the coordinates of this BlockPos
     */
    public TileCoord add(int x, int y, int z)
    {
        return x == 0 && y == 0 && z == 0 ? this : new TileCoord(this.getX() + x, this.getY() + y, this.getZ() + z);
    }

    /**
     * Add the given Vector to this BlockPos
     */
    public TileCoord add(TileCoord vec)
    {
        return vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0 ? this : new TileCoord(this.getX() + vec.getX(), this.getY() + vec.getY(), this.getZ() + vec.getZ());
    }

    /**
     * Subtract the given Vector from this BlockPos
     */
    public TileCoord subtract(TileCoord vec)
    {
        return vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0 ? this : new TileCoord(this.getX() - vec.getX(), this.getY() - vec.getY(), this.getZ() - vec.getZ());
    }

    /**
     * Offset this BlockPos 1 block up
     */
    public TileCoord up()
    {
        return this.up(1);
    }

    /**
     * Offset this BlockPos n blocks up
     */
    public TileCoord up(int n)
    {
        return this.offset(EnumFacing.UP, n);
    }

    /**
     * Offset this BlockPos 1 block down
     */
    public TileCoord down()
    {
        return this.down(1);
    }

    /**
     * Offset this BlockPos n blocks down
     */
    public TileCoord down(int n)
    {
        return this.offset(EnumFacing.DOWN, n);
    }

    /**
     * Offset this BlockPos 1 block in northern direction
     */
    public TileCoord north()
    {
        return this.north(1);
    }

    /**
     * Offset this BlockPos n blocks in northern direction
     */
    public TileCoord north(int n)
    {
        return this.offset(EnumFacing.NORTH, n);
    }

    /**
     * Offset this BlockPos 1 block in southern direction
     */
    public TileCoord south()
    {
        return this.south(1);
    }

    /**
     * Offset this BlockPos n blocks in southern direction
     */
    public TileCoord south(int n)
    {
        return this.offset(EnumFacing.SOUTH, n);
    }

    /**
     * Offset this BlockPos 1 block in western direction
     */
    public TileCoord west()
    {
        return this.west(1);
    }

    /**
     * Offset this BlockPos n blocks in western direction
     */
    public TileCoord west(int n)
    {
        return this.offset(EnumFacing.WEST, n);
    }

    /**
     * Offset this BlockPos 1 block in eastern direction
     */
    public TileCoord east()
    {
        return this.east(1);
    }

    /**
     * Offset this BlockPos n blocks in eastern direction
     */
    public TileCoord east(int n)
    {
        return this.offset(EnumFacing.EAST, n);
    }

    /**
     * Offset this BlockPos 1 block in the given direction
     */
    public TileCoord offset(EnumFacing facing)
    {
        return this.offset(facing, 1);
    }

    /**
     * Offsets this BlockPos n blocks in the given direction
     */
    public TileCoord offset(EnumFacing facing, int n)
    {
        return n == 0 ? this : new TileCoord(this.getX() + facing.getFrontOffsetX() * n, this.getY() + facing.getFrontOffsetY() * n, this.getZ() + facing.getFrontOffsetZ() * n);
    }
}
