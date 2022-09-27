package remiliaMarine.tofu.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.data.MorijioManager;
import remiliaMarine.tofu.util.TileCoord;

public class TileEntityMorijio extends TileEntity implements ITickable{
    /** The morijio's rotation. */
    private int rotation;

    private boolean isInit = false;
    private MorijioInfo info;

    @Override
    public void update()
    {
        if (!worldObj.isRemote && !this.isInit)
        {
            this.info = new MorijioInfo();
            this.info.coord = new TileCoord(this.getPos());

            MorijioManager infoHandler = TofuCraftCore.getMorijioManager();
            if (infoHandler != null)
            {
                if (!infoHandler.isInfoRegistered(info))
                {
                    infoHandler.registerInfo(info);
                }
                this.isInit = true;
            }
        }
    }

    public void removeInfo()
    {
        MorijioManager infoHandler = TofuCraftCore.getMorijioManager();
        infoHandler.removeInfo(this.info);
    }

    /**
     * Writes a tile entity to NBT.
     * @return
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("Rot", (byte)(this.rotation & 255));
        return par1NBTTagCompound;
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.rotation = par1NBTTagCompound.getByte("Rot");
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.getNbtCompound());
    }

    /**
     * Set the morijio's rotation
     */
    public void setRotation(int par1)
    {
        this.rotation = par1;
    }

    @SideOnly(Side.CLIENT)
    public int getRotation()
    {
        return this.rotation;
    }

    public static class MorijioInfo
    {
        public TileCoord coord;

        @Override
        public boolean equals(Object obj)
        {
            if (coord == null || !(obj instanceof MorijioInfo)) return false;
            return coord.equals(((MorijioInfo)obj).coord);
        }

        @Override
        public int hashCode()
        {
            return coord.hashCode();
        }
    }
}
