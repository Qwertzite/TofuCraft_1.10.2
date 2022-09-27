package remiliaMarine.tofu.gui.guiparts;

import remiliaMarine.tofu.gui.inventory.GuiTfMachineBase;

public class GuiPartGaugeH<T extends GuiPartGaugeH<?>> extends GuiPartGaugeBase<T> {
    public GuiPartGaugeH(int x, int y, TfMachineGuiParts frame, TfMachineGuiParts indicator)
    {
        super(x, y, frame, indicator);
    }

    public GuiPartGaugeH(int x, int y, TfMachineGuiParts frame, TfMachineGuiParts indicator, int xIndicatorOffset, int yIndicatorOffset)
    {
        super(x, y, frame, indicator, xIndicatorOffset, yIndicatorOffset);
    }

    @Override
    protected void drawFrame(GuiTfMachineBase gui)
    {
        gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y, part.ox, part.oy, part.xSize, part.ySize, frameColor);
    }

    @Override
    protected void drawIndicator(GuiTfMachineBase gui)
    {
        int xIndicatorSize = (int)(indicator.xSize * percentage);
        gui.drawTexturedModalRect(
                gui.getGuiLeft() + x + xIndicatorOffset, gui.getGuiTop() + y + yIndicatorOffset,
                indicator.ox, indicator.oy,
                xIndicatorSize, indicator.ySize,
                indicatorColor);
    }
}
