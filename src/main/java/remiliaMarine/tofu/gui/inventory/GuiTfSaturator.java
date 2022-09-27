package remiliaMarine.tofu.gui.inventory;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.text.translation.I18n;
import remiliaMarine.tofu.gui.guiparts.GuiPartGaugeBase;
import remiliaMarine.tofu.gui.guiparts.GuiPartGaugeV;
import remiliaMarine.tofu.gui.guiparts.GuiPartTfCharge;
import remiliaMarine.tofu.gui.guiparts.GuiRedstoneLamp;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;
import remiliaMarine.tofu.inventory.ContainerTfSaturator;
import remiliaMarine.tofu.tileentity.TileEntityTfSaturator;

public class GuiTfSaturator extends GuiTfMachineBase {
    private final TileEntityTfSaturator machineInventory;

    private GuiPartGaugeBase<GuiPartGaugeV> chargeIcon = new GuiPartGaugeV(45, 36, TfMachineGuiParts.saturatorChargingBg, TfMachineGuiParts.saturatorCharging);
    private GuiRedstoneLamp redstoneLamp = new GuiRedstoneLamp(34, 53);
    private GuiPartTfCharge tfCharged = new GuiPartTfCharge(54, 52, TileEntityTfSaturator.COST_TF_PER_TICK / TileEntityTfSaturator.TF_CAPACITY);

    public GuiTfSaturator(InventoryPlayer par1InventoryPlayer, TileEntityTfSaturator tfSaturator)
    {
        super(new ContainerTfSaturator(par1InventoryPlayer, tfSaturator));
        this.xSize = 100;
        this.ySize = 80;
        this.machineInventory = tfSaturator;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int px, int py)
    {
        @SuppressWarnings("deprecation")
		String s = this.machineInventory.hasCustomName() ? this.machineInventory.getName() : I18n.translateToLocal(this.machineInventory.getName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 0x5c5e54);

        redstoneLamp.showInfoTip(this, px, py);
        tfCharged.showInfoTip(this, px, py);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawBasePanel(this.xSize, this.ySize);

        if (machineInventory.paramSuffocating.get())
        {
            this.drawGuiPart(37, 28, TfMachineGuiParts.saturatorSuffocating);
        }
        else if(machineInventory.saturatingTime > 0)
        {
            this.drawGuiPart(37, 28, TfMachineGuiParts.saturatorSaturating);
        }
        else
        {
            chargeIcon.setPercentage(machineInventory.getProgressScaled()).draw(this);
        }

        redstoneLamp.setSwitch(machineInventory.isRedstonePowered()).draw(this);
        tfCharged.setPercentage(machineInventory.getTfCharged()).draw(this);
    }
}
