package remiliaMarine.tofu.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.data.DataType;
import remiliaMarine.tofu.data.EntityInfo;
import remiliaMarine.tofu.item.ItemZundaBow.EnumArrowType;
import remiliaMarine.tofu.network.AbstractPacket;
import remiliaMarine.tofu.network.MessageToClient;

public class PacketZundaArrowType extends AbstractPacket implements MessageToClient {
    private int entityId;
    private int type;

    public PacketZundaArrowType() {}

    public PacketZundaArrowType(int entityId, EnumArrowType type)
    {
        this.entityId = entityId;
        this.type = type.ordinal();
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
        buffer.writeInt(entityId);
        buffer.writeByte(type);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        entityId = buffer.readInt();
        type = buffer.readByte();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage handleClientSide(EntityPlayer player)
    {
        EntityInfo.instance().set(entityId, DataType.ZundaArrowType, EnumArrowType.values()[type]);
        return null;
    }
}
