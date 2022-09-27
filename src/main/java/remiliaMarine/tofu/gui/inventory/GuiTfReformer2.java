package remiliaMarine.tofu.gui.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import remiliaMarine.tofu.gui.guiparts.GuiPart;
import remiliaMarine.tofu.gui.guiparts.GuiPartGaugeRevH;
import remiliaMarine.tofu.gui.guiparts.GuiPartTfCharge;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;
import remiliaMarine.tofu.inventory.ContainerTfReformer2;
import remiliaMarine.tofu.tileentity.TileEntityTfReformer;

public class GuiTfReformer2 extends GuiTfReformerBase {

    public GuiTfReformer2(InventoryPlayer par1InventoryPlayer, TileEntityTfReformer machine)
    {
        super(new ContainerTfReformer2(par1InventoryPlayer, machine), machine);
        progress = new GuiPartGaugeRevH(62, 36, TfMachineGuiParts.progressArrowRevBg, TfMachineGuiParts.progressArrowRev);
        smallArrow = new GuiPart<GuiPart<?>>(45, 43, TfMachineGuiParts.smallArrowDown);
        tfCharged = new GuiPartTfCharge(112, 52, TileEntityTfReformer.COST_TF_PER_TICK / TileEntityTfReformer.TF_CAPACITY);
    }
}
