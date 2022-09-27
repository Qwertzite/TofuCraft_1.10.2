package remiliaMarine.tofu.gui.guiparts;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.util.text.translation.I18n;
import remiliaMarine.tofu.gui.inventory.GuiTfMachineBase;

public class GuiPartTfCharge extends GuiPartGaugeV {
    protected double tfThreshold;

    public GuiPartTfCharge(int x, int y, double tfThreshold)
    {
        super(x, y, TfMachineGuiParts.gaugeTfChargedBg, TfMachineGuiParts.gaugeTfCharged);
        this.setInfoTip(HoverTextPosition.LOWER_CENTER);
        this.tfThreshold = tfThreshold;
    }

    public GuiPartTfCharge setTfThreshold(double tfThreshold)
    {
        this.tfThreshold = tfThreshold;
        return this;
    }

    @SuppressWarnings("deprecation")
	public void showInfoTip(GuiTfMachineBase gui, int px, int py)
    {
        List<String> list = Lists.newArrayList();
        list.add(I18n.translateToLocal("tofucraft.tfPowered"));
        list.add(I18n.translateToLocal("tofucraft.tfPowered." + (this.percentage >= tfThreshold ? "true" : "false")));
        super.showInfoTip(gui, px, py, list);
    }
}
