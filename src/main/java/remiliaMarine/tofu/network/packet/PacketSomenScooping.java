package remiliaMarine.tofu.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import remiliaMarine.tofu.item.ItemSomenTsuyuBowl;
import remiliaMarine.tofu.network.AbstractPacket;
import remiliaMarine.tofu.network.MessageToServer;

public class PacketSomenScooping extends AbstractPacket implements MessageToServer
{
    private int entityId;

    public PacketSomenScooping() {}

    public PacketSomenScooping(int entityId)
    {
        this.entityId = entityId;
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
        buffer.writeInt(entityId);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        entityId = buffer.readInt();
    }

    @Override
    public IMessage handleServerSide(EntityPlayer player)
    {
        Entity entity = player.worldObj.getEntityByID(entityId);
        ItemStack itemHeld = player.getHeldItemMainhand();
        ItemStack newItem = ItemSomenTsuyuBowl.scoopSomen(entity, itemHeld, player, false);
        player.inventory.mainInventory[player.inventory.currentItem] = newItem;
        return null;
    }
}
