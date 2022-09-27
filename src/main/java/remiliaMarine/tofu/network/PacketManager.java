package remiliaMarine.tofu.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import remiliaMarine.tofu.network.packet.PacketBugle;
import remiliaMarine.tofu.network.packet.PacketDimTrip;
import remiliaMarine.tofu.network.packet.PacketGlowingFinish;
import remiliaMarine.tofu.network.packet.PacketGuiControl;
import remiliaMarine.tofu.network.packet.PacketSomenScooping;
import remiliaMarine.tofu.network.packet.PacketSoymilkInfo;
import remiliaMarine.tofu.network.packet.PacketTfMachineData;
import remiliaMarine.tofu.network.packet.PacketTofuRadar;
import remiliaMarine.tofu.network.packet.PacketZundaArrowHit;
import remiliaMarine.tofu.network.packet.PacketZundaArrowType;
import remiliaMarine.tofu.util.ModLog;

public class PacketManager {
    private static SimpleNetworkWrapper networkHandler = null;
    private static int id = 0;

    public static void init(String modId)
    {
        networkHandler = NetworkRegistry.INSTANCE.newSimpleChannel(modId);

        registerPacket(PacketDimTrip.class);
        registerPacket(PacketBugle.class);
        registerPacket(PacketZundaArrowHit.class);
        registerPacket(PacketZundaArrowType.class);
        registerPacket(PacketTofuRadar.class);
        registerPacket(PacketGlowingFinish.class);
        registerPacket(PacketTfMachineData.class);
        registerPacket(PacketGuiControl.class);
        registerPacket(PacketSomenScooping.class);
        registerPacket(PacketSoymilkInfo.class);
//        registerPacket(PacketPotionIdCheck.class);
    }

    public static SimpleNetworkWrapper getNetworkHandler()
    {
        return networkHandler;
    }

    private PacketManager() {}

    @SuppressWarnings("unchecked")
    private static void registerPacket(Class<? extends AbstractPacket> packetClass)
    {
        Class<AbstractPacket> message = (Class<AbstractPacket>)packetClass;
        if (MessageToServer.class.isAssignableFrom(packetClass))
        {
            networkHandler.registerMessage(packetClass, message, id, Side.SERVER);
            ModLog.debug("Registered Packet: %s at ID %d", packetClass.getName(), id);
            id++;
        }

        if (MessageToClient.class.isAssignableFrom(packetClass))
        {
            networkHandler.registerMessage(packetClass, message, id, Side.CLIENT);
            ModLog.debug("Registered Packet: %s at ID %d", packetClass.getName(), id);
            id++;
        }
    }

}
