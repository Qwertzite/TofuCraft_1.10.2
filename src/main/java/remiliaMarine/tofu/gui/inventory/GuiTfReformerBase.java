package remiliaMarine.tofu.gui.inventory;

import org.lwjgl.opengl.GL11;

import net.minecraft.util.text.translation.I18n;
import remiliaMarine.tofu.gui.guiparts.GuiPart;
import remiliaMarine.tofu.gui.guiparts.GuiPartGaugeRevH;
import remiliaMarine.tofu.gui.guiparts.GuiPartTfCharge;
import remiliaMarine.tofu.inventory.ContainerTfReformerBase;
import remiliaMarine.tofu.tileentity.TileEntityTfReformer;

public abstract class GuiTfReformerBase extends GuiTfMachineBase {

    private final TileEntityTfReformer machineInventory;

    protected GuiPartGaugeRevH progress;
    protected GuiPart<GuiPart<?>> smallArrow;
    protected GuiPartTfCharge tfCharged;

    public GuiTfReformerBase(ContainerTfReformerBase container, TileEntityTfReformer machine)
    {
        super(container);
        this.ySize = 180;
        this.machineInventory = machine;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @SuppressWarnings("deprecation")
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.machineInventory.hasCustomName() ? this.machineInventory.getName() : I18n.translateToLocal(this.machineInventory.getName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 0x5c5e54);
        this.fontRendererObj.drawString(I18n.translateToLocal("container.inventory"), 8, this.ySize - 96 + 3, 0x5c5e54);

        this.tfCharged.showInfoTip(this, par1, par2);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawStandardBasePanel();
        this.drawTfMachineSlot();

        smallArrow.draw(this);

        progress
                .setPercentage(this.machineInventory.getProgressScaledOutput())
                .draw(this);

        tfCharged
                .setPercentage(this.machineInventory.tfAmount / this.machineInventory.tfCapacity)
                .draw(this);
    }

}
