package remiliaMarine.tofu.api.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;
import remiliaMarine.tofu.versionAdapter.SlotAdapter;

public class SlotTfMachine extends Slot {
	
    protected TfMachineGuiParts guiPart;

    public int xBgPosition;
    public int yBgPosition;

    public SlotTfMachine(IInventory par1IInventory, int par2, int par3, int par4, TfMachineGuiParts guiPart)
    {
        super(par1IInventory, par2, par3, par4);
        this.guiPart = guiPart;

        this.xBgPosition = par3;
        this.yBgPosition = par4;

        // For item stack's icon
        SlotAdapter.setXDisplayPos(this, par3 + guiPart.xSize / 2 - 8);
        SlotAdapter.setYDisplayPos(this, par4 + guiPart.ySize / 2 - 8);
    }

    public TfMachineGuiParts getGuiPart()
    {
        return guiPart;
    }
}
