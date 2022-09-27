package remiliaMarine.tofu.gui.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import remiliaMarine.tofu.gui.guiparts.GuiPart;
import remiliaMarine.tofu.gui.guiparts.GuiPartGaugeRevH;
import remiliaMarine.tofu.gui.guiparts.GuiPartTfCharge;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;
import remiliaMarine.tofu.inventory.ContainerTfReformer;
import remiliaMarine.tofu.tileentity.TileEntityTfReformer;

public class GuiTfReformer extends GuiTfReformerBase {
    public GuiTfReformer(InventoryPlayer par1InventoryPlayer, TileEntityTfReformer machine)
    {
        super(new ContainerTfReformer(par1InventoryPlayer, machine), machine);
        progress = new GuiPartGaugeRevH(80, 36, TfMachineGuiParts.progressArrowRevBg, TfMachineGuiParts.progressArrowRev);
        smallArrow = new GuiPart<GuiPart<?>>(60, 43, TfMachineGuiParts.smallArrowDown);
        tfCharged = new GuiPartTfCharge(112, 39, TileEntityTfReformer.COST_TF_PER_TICK / TileEntityTfReformer.TF_CAPACITY);
    }
}
