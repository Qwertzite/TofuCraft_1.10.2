package remiliaMarine.tofu.gui.guiparts;

import com.google.common.collect.Lists;

import net.minecraft.util.text.translation.I18n;

public class GuiRedstoneLamp extends GuiLamp {

    @SuppressWarnings("deprecation")
	public GuiRedstoneLamp(int x, int y)
    {
        super(x, y, TfMachineGuiParts.redstoneOff, TfMachineGuiParts.redstoneOn);

        this.setInfoTip(HoverTextPosition.LOWER_CENTER);
        this.setTipMessage(
                Lists.newArrayList(
                        I18n.translateToLocal("tofucraft.redstone"),
                        I18n.translateToLocal("tofucraft.redstone.off")),
                Lists.newArrayList(
                		I18n.translateToLocal("tofucraft.redstone"),
                		I18n.translateToLocal("tofucraft.redstone.on")));
    }
}
