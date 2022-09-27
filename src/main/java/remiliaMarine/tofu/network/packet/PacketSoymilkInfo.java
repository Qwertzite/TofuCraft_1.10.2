package remiliaMarine.tofu.network.packet;

import java.io.IOException;

import org.apache.logging.log4j.Level;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.item.iteminfo.SoymilkPlayerInfo;
import remiliaMarine.tofu.network.AbstractPacket;
import remiliaMarine.tofu.network.MessageToClient;
import remiliaMarine.tofu.util.ModLog;

public class PacketSoymilkInfo extends AbstractPacket implements MessageToClient {

    private NBTTagCompound rootNBT;

    public PacketSoymilkInfo() {}

    public PacketSoymilkInfo(SoymilkPlayerInfo info)
    {
        rootNBT = new NBTTagCompound();
        info.writeNBTTo(rootNBT);
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
        PacketBuffer packet = new PacketBuffer(buffer);

        try
        {
            packet.writeNBTTagCompoundToBuffer(rootNBT);
        }
        catch (EncoderException e)
        {
            ModLog.log(Level.WARN, e, "Failed to send NBT tag!");
        }
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        PacketBuffer packet = new PacketBuffer(buffer);
        try
        {
            rootNBT = packet.readNBTTagCompoundFromBuffer();
        }
        catch (IOException e)
        {
            ModLog.log(Level.WARN, e, "Failed to receive NBT tag!");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage handleClientSide(EntityPlayer player)
    {
        SoymilkPlayerInfo info = SoymilkPlayerInfo.of(player).readNBTFrom(rootNBT);
        info.writeNBTToPlayer();
        return null;
    }
    
}
