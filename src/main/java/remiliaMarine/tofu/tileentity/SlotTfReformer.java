package remiliaMarine.tofu.tileentity;

import remiliaMarine.tofu.api.tileentity.SlotTfMachine;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;

public class SlotTfReformer extends SlotTfMachine {

    TileEntityTfReformer machine;

    public SlotTfReformer(TileEntityTfReformer machine, int slotInputItem, int x, int y, TfMachineGuiParts guiPart)
    {
        super(machine, slotInputItem, x, y, guiPart);
        this.machine = machine;
    }

    @Override
    public void onSlotChanged()
    {
        super.onSlotChanged();
    }


}
