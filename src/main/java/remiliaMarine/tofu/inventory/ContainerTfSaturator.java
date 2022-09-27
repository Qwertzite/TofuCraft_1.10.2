package remiliaMarine.tofu.inventory;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.api.tileentity.ContainerTfMachine;
import remiliaMarine.tofu.network.packet.PacketTfMachineData;
import remiliaMarine.tofu.tileentity.TileEntityTfSaturator;

public class ContainerTfSaturator extends ContainerTfMachine<TileEntityTfSaturator> {
    private int lastTickCounter;
    private int lastInterval;
    private double lastTfPooled;

    public ContainerTfSaturator(InventoryPlayer invPlayer, TileEntityTfSaturator machine)
    {
        super(machine);
        this.addContainerParam(machine.paramSuffocating);
    }

    @Override
    public void addListener(IContainerListener par1ICrafting)
    {
        super.addListener(par1ICrafting);

        par1ICrafting.sendProgressBarUpdate(this, 0, this.machine.tickCounter);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.machine.interval);

        this.sendTfMachineData(par1ICrafting, 0, new PacketTfMachineData.DataHandler()
        {
            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeDouble(ContainerTfSaturator.this.machine.tfPooled);
            }
        });
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int var1 = 0; var1 < this.listeners.size(); ++var1)
        {
        	IContainerListener var2 = (IContainerListener)this.listeners.get(var1);

            if (this.lastTickCounter != this.machine.tickCounter)
            {
                var2.sendProgressBarUpdate(this, 0, this.machine.tickCounter);
            }
            if (this.lastInterval != this.machine.interval)
            {
                var2.sendProgressBarUpdate(this, 1, this.machine.interval);
            }
            if (this.lastTfPooled != this.machine.tfPooled)
            {
                this.sendTfMachineData(var2, 0, new PacketTfMachineData.DataHandler()
                {
                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeDouble(ContainerTfSaturator.this.machine.tfPooled);
                    }
                });
            }
        }

        this.lastTickCounter = this.machine.tickCounter;
        this.lastInterval = this.machine.interval;
        this.lastTfPooled = this.machine.tfPooled;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.machine.tickCounter = par2;
        }
        if (par1 == 1)
        {
            this.machine.interval = par2;
        }
    }

    @Override
    public void updateTfMachineData(int id, ByteBuf data)
    {
        super.updateTfMachineData(id, data);

        if (id == 0)
        {
            this.machine.tfPooled = data.readDouble();
        }
    }
}
