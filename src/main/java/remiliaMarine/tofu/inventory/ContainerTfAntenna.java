package remiliaMarine.tofu.inventory;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import remiliaMarine.tofu.api.tileentity.ContainerTfMachine;
import remiliaMarine.tofu.network.packet.PacketTfMachineData;
import remiliaMarine.tofu.tileentity.TileEntityTfAntenna;

public class ContainerTfAntenna extends ContainerTfMachine<TileEntityTfAntenna> {
	
    private static final int interval = 10;
    //private double lastTfSupply;
    //private double lastTfDemand;
    //private double lastTfBalance;

    private int ticks = 0;

    public ContainerTfAntenna(InventoryPlayer invPlayer, TileEntityTfAntenna tile)
    {
        super(tile);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        if (ticks >= interval)
        {
            for (int var1 = 0; var1 < this.listeners.size(); ++var1)
            {
            	IContainerListener var2 = (IContainerListener) this.listeners.get(var1);

                this.sendTfMachineData(var2, 0, new PacketTfMachineData.DataHandler()
                {
                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeDouble(machine.statTfSupply);
                        buffer.writeDouble(machine.statTfDemand);
                    }
                });
            }
            ticks = 0;
        }
        else
        {
            ticks++;
        }

        //this.lastTfSupply = this.machine.getStatTfSupply();
        //this.lastTfDemand = this.machine.getStatTfDemand();
        //this.lastTfBalance = this.machine.getStatTfBalance();
    }

    @Override
    public void updateTfMachineData(int id, ByteBuf data)
    {
        if (id == 0)
        {
            this.machine.statTfSupply = data.readDouble();
            this.machine.statTfDemand = data.readDouble();
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {
        return this.machine.isUseableByPlayer(var1);
    }
}
