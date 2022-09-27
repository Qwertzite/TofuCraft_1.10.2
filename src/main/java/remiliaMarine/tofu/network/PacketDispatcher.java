package remiliaMarine.tofu.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * Handles PacketPipeline and dispatches a packet
 *
 * @author Tsuteto
 *
 */
public class PacketDispatcher {
    private final SimpleNetworkWrapper networkHandler;
    private final AbstractPacket packet;

    public static PacketDispatcher packet(AbstractPacket packet)
    {
        return new PacketDispatcher(packet);
    }

    private PacketDispatcher(AbstractPacket packet)
    {
        this.networkHandler = PacketManager.getNetworkHandler();
        this.packet = packet;
    }

    public void sendToServer()
    {
        networkHandler.sendToServer(packet);
    }

    public void sendToPlayer(EntityPlayer player)
    {
        networkHandler.sendTo(packet, (EntityPlayerMP) player);
    }

    public void sendToPlayer(EntityPlayerMP player)
    {
        networkHandler.sendTo(packet, player);
    }

    public void sendToAllInDimension(int dimId)
    {
        networkHandler.sendToDimension(packet, dimId);
    }

    public void sendToAllAround(double X, double Y, double Z, int range, int dimensionId)
    {
        NetworkRegistry.TargetPoint targetPoint = new NetworkRegistry.TargetPoint(dimensionId, X, Y, Z, range);
        networkHandler.sendToAllAround(packet, targetPoint);
    }
    
    public void sendPacketToAllPlayers()
    {
        networkHandler.sendToAll(packet);
    }

}
