package remiliaMarine.tofu.inventory;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.api.tileentity.ContainerTfMachine;
import remiliaMarine.tofu.api.tileentity.SlotTfMachine;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;
import remiliaMarine.tofu.network.packet.PacketTfMachineData;
import remiliaMarine.tofu.tileentity.SlotTfOvenAccelerator;
import remiliaMarine.tofu.tileentity.SlotTfOvenResult;
import remiliaMarine.tofu.tileentity.TileEntityTfOven;

public class ContainerTfOven extends ContainerTfMachine<TileEntityTfOven> {

    private double lastCookTime = 0;
    private double lastTfPooled = 0;
    private double lastWholeCookTime = 0;
    private boolean lastWorking = false;

    public ContainerTfOven(InventoryPlayer invPlayer, TileEntityTfOven machine)
    {
        super(machine);
        this.addSlotToContainer(new SlotTfMachine(machine, TileEntityTfOven.SLOT_ITEM_INPUT, 45, 30, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfOvenResult(invPlayer.player, machine, TileEntityTfOven.SLOT_ITEM_RESULT, 111, 28, TfMachineGuiParts.itemSlotL2));
        this.addSlotToContainer(new SlotTfOvenAccelerator(invPlayer.player, machine, TileEntityTfOven.SLOT_ACCELERATION, 86, 53, TfMachineGuiParts.itemSlot));

        this.preparePlayerInventory(invPlayer);
    }

    @Override
    public void addListener(IContainerListener par1ICrafting)
    {
        super.addListener(par1ICrafting);
        this.sendTfMachineData(par1ICrafting, 0, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeFloat((float)machine.ovenCookTime);
            }
        });
        this.sendTfMachineData(par1ICrafting, 1, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeFloat((float)machine.wholeCookTime);
            }
        });

        this.sendTfMachineData(par1ICrafting, 2, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeDouble(machine.tfPooled);
            }
        });
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int var1 = 0; var1 < this.listeners.size(); ++var1)
        {
        	IContainerListener var2 = (IContainerListener)this.listeners.get(var1);

            if (this.lastCookTime != this.machine.ovenCookTime)
            {
                this.sendTfMachineData(var2, 0, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeFloat((float)ContainerTfOven.this.machine.ovenCookTime);
                    }
                });
            }
            if (this.lastWholeCookTime != this.machine.wholeCookTime)
            {
                this.sendTfMachineData(var2, 1, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeFloat((float)ContainerTfOven.this.machine.wholeCookTime);
                    }
                });
            }
            if (this.lastTfPooled != this.machine.tfPooled)
            {
                this.sendTfMachineData(var2, 2, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeDouble(machine.tfPooled);
                    }
                });
            }
            if (this.lastWorking != this.machine.isWorking)
            {
                this.sendTfMachineData(var2, 3, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeBoolean(machine.isWorking);
                    }
                });
            }
        }

        this.lastCookTime = this.machine.ovenCookTime;
        this.lastWholeCookTime = this.machine.wholeCookTime;
        this.lastTfPooled = this.machine.tfPooled;
        this.lastWorking = this.machine.isWorking;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int par1, int par2)
    {
    }


    @Override
    public void updateTfMachineData(int id, ByteBuf data)
    {
        if (id == 0)
        {
            this.machine.ovenCookTime = data.readFloat();
        }
        if (id == 1)
        {
            this.machine.wholeCookTime = data.readFloat();
        }

        if (id == 2)
        {
            this.machine.tfPooled = data.readDouble();
        }
        if (id == 3)
        {
            this.machine.isWorking = data.readBoolean();
        }
    }

    public TransferResult transferStackInMachineSlot(EntityPlayer player, int slotId, ItemStack itemStack)
    {
        if (FurnaceRecipes.instance().getSmeltingResult(itemStack) != null)
        {
            if (!this.mergeToSingleItemStack(itemStack, TileEntityTfOven.SLOT_ITEM_INPUT))
            {
                return TransferResult.MISMATCHED;
            }
            return TransferResult.MATCHED;
        }
        return TransferResult.SKIPPING;
    }


}
