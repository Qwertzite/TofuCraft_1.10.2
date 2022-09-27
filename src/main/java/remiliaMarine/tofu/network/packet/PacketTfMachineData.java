package remiliaMarine.tofu.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.api.tileentity.ContainerTfMachine;
import remiliaMarine.tofu.network.AbstractPacket;
import remiliaMarine.tofu.network.MessageToClient;

public class PacketTfMachineData extends AbstractPacket implements MessageToClient {
    private int windowId;
    private int dataId;
    private DataHandler dataHandler;
    private ByteBuf buffer;

    public PacketTfMachineData() {}

    public PacketTfMachineData(int windowId, int dataId, DataHandler dataHandler)
    {
        this.windowId = windowId;
        this.dataId = dataId;

        this.dataHandler = dataHandler;
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
        buffer.writeByte(windowId);
        buffer.writeShort(dataId);
        dataHandler.addData(buffer);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        this.windowId = buffer.readByte();
        this.dataId = buffer.readShort();
        this.buffer = buffer;
   }

    @SuppressWarnings("rawtypes")
	@Override
    @SideOnly(Side.CLIENT)
    public IMessage handleClientSide(EntityPlayer player)
    {
        Minecraft mc = FMLClientHandler.instance().getClient();
        EntityPlayerSP entityclientplayersp = mc.thePlayer;

        if (entityclientplayersp.openContainer != null && entityclientplayersp.openContainer.windowId == windowId)
        {
            ((ContainerTfMachine)entityclientplayersp.openContainer).updateTfMachineData(dataId, buffer);
        }
        return null;
    }

    public static interface DataHandler
    {
        void addData(ByteBuf buffer);
    }
}
