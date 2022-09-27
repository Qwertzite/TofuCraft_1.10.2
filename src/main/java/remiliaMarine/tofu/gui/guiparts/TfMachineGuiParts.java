package remiliaMarine.tofu.gui.guiparts;

import net.minecraftforge.common.util.EnumHelper;

public enum TfMachineGuiParts {
    baseLeftTop(0, 0, 5, 5),
    baseRightTop(5, 0, 5, 5),
    baseLeftBottom(0, 5, 5, 5),
    baseRightBottom(5, 5, 5, 5),

    tfMark(36, 135, 12, 12),

    playerInventory(0, 158, 176, 98),

    progressArrow(0, 10, 24, 17),
    progressArrowRev(0, 27, 24, 17),
    progressArrowBg(24, 10, 24, 17),
    progressArrowRevBg(24, 27, 24, 17),

    gaugeTfCharged(106, 0, 13, 13),
    gaugeTfChargedBg(119, 0, 13, 13),

    itemSlotL2(0, 44, 24, 24),
    itemSlotL1(24, 44, 20, 20),
    itemSlot(44, 44, 16, 16),

    gauge(0, 68, 55, 8),
    gaugeFrame(0, 76, 61, 12),
    gaugeV(0, 88, 8, 55),
    gaugeVFrame(8, 88, 12, 61),
    gaugeV2(20, 88, 8, 41),
    gaugeV2Frame(28, 88, 12, 47),

    gaugeVItemDisplay(20, 135, 16, 17),

    smallArrowLeft(10, 0, 3, 6),
    smallArrowRight(13, 0, 3, 6),
    smallArrowUp(10, 0, 6, 3),
    smallArrowDown(10, 3, 6, 3),

    btnSmallEnabled(48, 0, 8, 8),
    btnSmallHover(56, 0, 8, 8),
    btnSmallDisabled(64, 0, 8, 8),

    heaterBgLeft(48, 8, 12, 18),
    heaterBgRight(60, 8, 12, 18),
    heaterLeft(48, 26, 12, 18),
    heaterRight(60, 26, 12, 18),

    antennaGuide(72, 0, 34, 18),

    redstoneOff(72, 18, 12, 11),
    redstoneOn(84, 18, 12, 11),

    saturatorChargingBg(72, 29, 10, 10),
    saturatorCharging(82, 29, 10, 10),
    saturatorSaturating(96, 13, 26, 18),
    saturatorSuffocating(122, 13, 26, 18),
    ;

    public int ox, oy, xSize, ySize;

    TfMachineGuiParts(int ox, int oy, int xSize, int ySize)
    {
        this.ox = ox;
        this.oy = oy;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public static TfMachineGuiParts addPart(String name, int ox, int oy, int xSize, int ySize)
    {
        return EnumHelper.addEnum(TfMachineGuiParts.class, name,
                new Class[]{int.class, int.class, int.class, int.class},
                new Object[]{ox, oy, xSize, ySize});
    }
}
