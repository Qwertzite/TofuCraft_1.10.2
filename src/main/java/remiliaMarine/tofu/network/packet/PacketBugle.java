package remiliaMarine.tofu.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.init.TcSoundEvents;
import remiliaMarine.tofu.network.AbstractPacket;
import remiliaMarine.tofu.network.MessageToClient;

public class PacketBugle extends AbstractPacket implements MessageToClient {
    private BlockPos pos;
    private int entityId;

    public PacketBugle() {}

    public PacketBugle(BlockPos pos, int entityId)
    {
        this.pos = pos;
        this.entityId = entityId;
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
        buffer.writeFloat(pos.getX());
        buffer.writeFloat(pos.getY());
        buffer.writeFloat(pos.getZ());
        buffer.writeInt(entityId);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        float x = buffer.readFloat();
        float y = buffer.readFloat();
        float z = buffer.readFloat();
        this.pos = new BlockPos(x, y, z);
        entityId = buffer.readInt();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IMessage handleClientSide(EntityPlayer player)
    {
        Minecraft mc = FMLClientHandler.instance().getClient();

        if (player.getEntityId() == entityId)
        {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(TcSoundEvents.BUGGLE, 1.0F));
        }
        else
        {
            mc.theWorld.playSound(pos, TcSoundEvents.BUGGLE, SoundCategory.MASTER, 4.0F, 1.0F, false);
        }
        return null;
    }
}
