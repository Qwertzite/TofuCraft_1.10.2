package remiliaMarine.tofu.inventory;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.api.recipe.TfReformerRecipeRegistry;
import remiliaMarine.tofu.api.tileentity.ContainerTfMachine;
import remiliaMarine.tofu.network.packet.PacketTfMachineData;
import remiliaMarine.tofu.tileentity.TileEntityTfReformer;
import remiliaMarine.tofu.tileentity.TileEntityTfStorage;

public abstract class ContainerTfReformerBase extends ContainerTfMachine<TileEntityTfReformer> {

    private double lastWholeTfOutput;
    private double lastTfOutput;
    private double lastTfCapacity;
    private double lastTfAmount;

    public ContainerTfReformerBase(InventoryPlayer invPlayer, TileEntityTfReformer machine)
    {
        super(machine);
        this.prepareMachineInventory();
        super.preparePlayerInventory(invPlayer, playerInventoryPosX, playerInventoryPosY);
    }

    abstract public void prepareMachineInventory();

    @Override
    public void addListener(IContainerListener par1ICrafting)
    {
        super.addListener(par1ICrafting);

        this.sendTfMachineData(par1ICrafting, 0, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeDouble(machine.wholeTfOutput);
            }
        });

        this.sendTfMachineData(par1ICrafting, 1, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeDouble(machine.tfOutput);
            }
        });

        this.sendTfMachineData(par1ICrafting, 2, new PacketTfMachineData.DataHandler() {
            
            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeDouble(machine.tfCapacity);
            }
        });
    
        this.sendTfMachineData(par1ICrafting, 3, new PacketTfMachineData.DataHandler() {
            
            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeDouble(machine.tfAmount);
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

            if (this.lastWholeTfOutput != this.machine.wholeTfOutput)
            {
                this.sendTfMachineData(var2, 0, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeDouble(machine.wholeTfOutput);
                    }
                });
            }

            if (this.lastTfOutput != this.machine.tfOutput)
            {
                this.sendTfMachineData(var2, 1, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeDouble(machine.tfOutput);
                    }
                });
            }

            if (this.lastTfCapacity != this.machine.tfCapacity)
            {
                this.sendTfMachineData(var2, 2, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeDouble(machine.tfCapacity);
                    }
                });
            }
            
            if (this.lastTfAmount != this.machine.tfAmount)
            {
                this.sendTfMachineData(var2, 3, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeDouble(machine.tfAmount);
                    }
                });
            }
        }

        this.lastWholeTfOutput = this.machine.wholeTfOutput;
        this.lastTfOutput = this.machine.tfOutput;
        this.lastTfCapacity = this.machine.tfCapacity;
        this.lastTfAmount = this.machine.tfAmount;
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
            this.machine.wholeTfOutput = data.readDouble();
        }

        if (id == 1)
        {
            this.machine.tfOutput = data.readDouble();
        }

        if (id == 2)
        {
            this.machine.tfCapacity = data.readDouble();
        }

        if (id == 3)
        {
            this.machine.tfAmount = data.readDouble();
        }
    }

    public TransferResult transferStackInMachineSlot(EntityPlayer player, int slot, ItemStack itemStack)
    {
        if (TfReformerRecipeRegistry.isContainerItem(itemStack))
        {
            if (!this.mergeToSingleItemStack(itemStack, TileEntityTfStorage.SLOT_INPUT_ITEM))
            {
                return TransferResult.MISMATCHED;
            }
            return TransferResult.MATCHED;
        }
        return TransferResult.SKIPPING;
    }


}
