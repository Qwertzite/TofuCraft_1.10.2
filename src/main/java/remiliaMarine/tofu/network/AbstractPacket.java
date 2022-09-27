package remiliaMarine.tofu.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractPacket implements IMessage, IMessageHandler<AbstractPacket, IMessage> {

    public void fromBytes(ByteBuf buf)
    {
        this.decodeInto(buf);
    }

    public void toBytes(ByteBuf buf)
    {
        this.encodeInto(buf);
    }

    @Override
    public IMessage onMessage(AbstractPacket message, MessageContext ctx)
    {
        EntityPlayer player;
        IMessage reply = null;
        switch (FMLCommonHandler.instance().getEffectiveSide()) {
            case CLIENT:
                if (message instanceof MessageToClient)
                {
                    player = this.getClientPlayer();
                    if(player == null) break;
                    reply = ((MessageToClient)message).handleClientSide(player);
                }
                break;

            case SERVER:
                if (message instanceof MessageToServer)
                {
                    player = ((NetHandlerPlayServer) ctx.netHandler).playerEntity;
                    reply = ((MessageToServer)message).handleServerSide(player);
                }
                break;

            default:
        }
        return reply;
    }

    @SideOnly(Side.CLIENT)
    private EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }

    public abstract void encodeInto(ByteBuf buffer);

    public abstract void decodeInto(ByteBuf buffer);

}
