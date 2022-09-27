package remiliaMarine.tofu.gui.inventory;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.text.translation.I18n;
import remiliaMarine.tofu.gui.guiparts.GuiPartGaugeH;
import remiliaMarine.tofu.gui.guiparts.GuiPartGaugeRevH;
import remiliaMarine.tofu.gui.guiparts.GuiPartTfCharge;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;
import remiliaMarine.tofu.inventory.ContainerTfOven;
import remiliaMarine.tofu.tileentity.TileEntityTfOven;

public class GuiTfOven extends GuiTfMachineBase {
    private final TileEntityTfOven machineInventory;

    private GuiPartGaugeH<GuiPartGaugeH<?>> progress = new GuiPartGaugeH<GuiPartGaugeH<?>>(83, 31, TfMachineGuiParts.progressArrowBg, TfMachineGuiParts.progressArrow);

    private GuiPartGaugeH<GuiPartGaugeH<?>> heaterLeft  = new GuiPartGaugeH<GuiPartGaugeH<?>>(31, 32, TfMachineGuiParts.heaterBgLeft, TfMachineGuiParts.heaterLeft);
    private GuiPartGaugeRevH heaterRight = new GuiPartGaugeRevH(67, 32, TfMachineGuiParts.heaterBgRight, TfMachineGuiParts.heaterRight);

    private GuiPartTfCharge tfCharge = new GuiPartTfCharge(49, 54, TileEntityTfOven.COST_TF_PER_TICK / TileEntityTfOven.TF_CAPACITY);

    public GuiTfOven(InventoryPlayer par1InventoryPlayer, TileEntityTfOven machine)
    {
        super(new ContainerTfOven(par1InventoryPlayer, machine));
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

        tfCharge.showInfoTip(this, par1, par2);
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

        progress.setPercentage(machineInventory.getCookProgressScaled())
                .draw(this);

        double heaterScaled = machineInventory.isWorking ? 1.0D : 0.0D;
        heaterLeft.setPercentage(heaterScaled)
                .draw(this);
        heaterRight.setPercentage(heaterScaled)
                .draw(this);

        tfCharge.setTfThreshold(machineInventory.getTfAmountNeeded() / TileEntityTfOven.TF_CAPACITY)
                .setPercentage(machineInventory.getTfCharged())
                .draw(this);
    }

}
