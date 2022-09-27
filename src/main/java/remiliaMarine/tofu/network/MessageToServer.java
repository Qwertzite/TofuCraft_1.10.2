package remiliaMarine.tofu.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public interface MessageToServer {
    public IMessage handleServerSide(EntityPlayer player);
}
