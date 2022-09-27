package remiliaMarine.tofu.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.api.TfMaterialRegistry;
import remiliaMarine.tofu.api.tileentity.SlotTfMachine;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;

public class SlotTfStorage extends SlotTfMachine {
    private EntityPlayer thePlayer;

    public SlotTfStorage(EntityPlayer thePlayer, IInventory par1IInventory, int par2, int par3, int par4, TfMachineGuiParts guiPart)
    {
        super(par1IInventory, par2, par3, par4, guiPart);
        this.thePlayer = thePlayer;
    }

    public void putStack(ItemStack par1ItemStack)
    {
        super.putStack(par1ItemStack);

        if (TfMaterialRegistry.isTfMaterial(par1ItemStack))
        {
            TcAchievementMgr.achieve(thePlayer, TcAchievementMgr.Key.tofuForce);
        }
    }
}
