package remiliaMarine.tofu.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import remiliaMarine.tofu.api.tileentity.SlotTfMachineOutput;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;
import remiliaMarine.tofu.tileentity.SlotTfReformer;
import remiliaMarine.tofu.tileentity.TileEntityTfReformer;

public class ContainerTfReformer extends ContainerTfReformerBase {


    public ContainerTfReformer(InventoryPlayer invPlayer, TileEntityTfReformer machine)
    {
        super(invPlayer, machine);
    }

    @Override
    public void prepareMachineInventory()
    {
        this.addSlotToContainer(new SlotTfReformer(machine, TileEntityTfReformer.SLOT_INPUT_ITEM, 53, 20, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfMachineOutput(machine, TileEntityTfReformer.SLOT_OUTPUT_ITEM, 51, 49, TfMachineGuiParts.itemSlotL2));
    }


}
