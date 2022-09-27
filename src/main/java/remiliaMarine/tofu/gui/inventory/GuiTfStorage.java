package remiliaMarine.tofu.gui.inventory;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.text.translation.I18n;
import remiliaMarine.tofu.gui.guiparts.GuiPartGaugeBase;
import remiliaMarine.tofu.gui.guiparts.GuiPartGaugeH;
import remiliaMarine.tofu.gui.guiparts.HoverTextPosition;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;
import remiliaMarine.tofu.inventory.ContainerTfStorage;
import remiliaMarine.tofu.tileentity.TileEntityTfStorage;

public class GuiTfStorage extends GuiTfMachineBase {
	
    private final TileEntityTfStorage machineInventory;

    private GuiPartGaugeH<GuiPartGaugeH<?>> progress = new GuiPartGaugeH<GuiPartGaugeH<?>>(69, 36, TfMachineGuiParts.progressArrowBg, TfMachineGuiParts.progressArrow);

    @SuppressWarnings("unchecked")
	private GuiPartGaugeBase<GuiPartGaugeH<?>> tfGauge = (GuiPartGaugeBase<GuiPartGaugeH<?>>)new GuiPartGaugeH<GuiPartGaugeH<?>>(100, 39, TfMachineGuiParts.gaugeFrame, TfMachineGuiParts.gauge)
            .setInfoTip(57, 22, HoverTextPosition.LOWER_CENTER);

    public GuiTfStorage(InventoryPlayer par1InventoryPlayer, TileEntityTfStorage tfstorage)
    {
        super(new ContainerTfStorage(par1InventoryPlayer, tfstorage));
        this.ySize = 180;
        this.machineInventory = tfstorage;
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

        tfGauge.showInfoTip(this, par1, par2, new IHoverDrawingHandler()
        {
            @Override
            public void draw(int ox, int oy, int fw, int fh)
            {
                String s;
                s = String.format("%.0f", machineInventory.tfAmount);
                fontRendererObj.drawString(s, ox + 33 - fontRendererObj.getStringWidth(s), oy + 2, 0xf1f2e6);

                s = String.format("/%.0f", machineInventory.tfCapacity);
                fontRendererObj.drawString(s, ox + 41 - fontRendererObj.getStringWidth(s), oy + 12, 0xb4b5aa);

                printTfSign(ox + 33, oy + 2, 0xf1f2e6);
                printTfSign(ox + 41, oy + 12, 0xb4b5aa);
            }
        });
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

        this.drawGuiPart(50, 44, TfMachineGuiParts.smallArrowDown);

        progress
                .setPercentage(this.machineInventory.getProgressScaledInput())
                .draw(this);

        tfGauge
                .setPercentage(this.machineInventory.getProgressScaledTfAmount())
                .draw(this);
    }
}
