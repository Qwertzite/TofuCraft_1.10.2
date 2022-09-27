package remiliaMarine.tofu.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IContainerListener;
import remiliaMarine.tofu.api.tileentity.ContainerTfMachine;
import remiliaMarine.tofu.network.packet.PacketTfMachineData;

public abstract class ContainerParam<T> {

    public final int id;
    protected T value;
    protected T lastValue;

    public ContainerParam(int id, T value)
    {
        this.id = id;
        this.value = value;
    }

    public void send(IContainerListener iCrafting, ContainerTfMachine container)
    {
        container.sendTfMachineData(iCrafting, this.id, this.getDataHandler());
    }

    public void sendIfChanged(IContainerListener iCrafting, ContainerTfMachine container)
    {
        if (!value.equals(lastValue))
        {
            this.send(iCrafting, container);
        }

        this.lastValue = this.value;
    }

    abstract public void receive(ByteBuf data);

    public T get()
    {
        return value;
    }

    public void set(T value)
    {
        this.value = value;
    }

    abstract public PacketTfMachineData.DataHandler getDataHandler();
}
