package remiliaMarine.tofu.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemTofuSlimeRadar;
import remiliaMarine.tofu.network.AbstractPacket;
import remiliaMarine.tofu.network.MessageToClient;

public class PacketTofuRadar extends AbstractPacket implements MessageToClient {

    boolean isSpawnChunk;

    public PacketTofuRadar() {}

    public PacketTofuRadar(boolean isSpawnChunk)
    {
        this.isSpawnChunk = isSpawnChunk;
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
        buffer.writeBoolean(isSpawnChunk);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        isSpawnChunk = buffer.readBoolean();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage handleClientSide(EntityPlayer player)
    {
        ItemStack itemstack = player.getHeldItemMainhand();

        if (itemstack != null && itemstack.getItem() == TcItems.tofuRadar)
        {
            ((ItemTofuSlimeRadar)TcItems.tofuRadar).onUse(isSpawnChunk, itemstack, player.worldObj, player);
        }
        return null;
    }

}
