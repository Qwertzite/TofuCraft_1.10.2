package remiliaMarine.tofu.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public interface MessageToClient {
    public IMessage handleClientSide(EntityPlayer player);
}
