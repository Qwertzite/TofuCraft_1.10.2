package remiliaMarine.tofu.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.network.AbstractPacket;
import remiliaMarine.tofu.network.MessageToClient;

public class PacketDimTrip extends AbstractPacket implements MessageToClient {

    @SideOnly(Side.CLIENT)
    @Override
    public IMessage handleClientSide(EntityPlayer player)
    {
        Minecraft mc = FMLClientHandler.instance().getClient();
        mc.getSoundHandler().playSound(
                PositionedSoundRecord.getMasterRecord(SoundEvents.BLOCK_PORTAL_TRIGGER, player.worldObj.rand.nextFloat() * 0.4F + 0.8F));
        return null;
    }

    @Override
    public void encodeInto(ByteBuf buffer) {}

    @Override
    public void decodeInto(ByteBuf buffer) {}

}
