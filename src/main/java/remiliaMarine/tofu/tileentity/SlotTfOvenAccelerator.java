package remiliaMarine.tofu.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.api.tileentity.SlotTfMachine;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemTcMaterials;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class SlotTfOvenAccelerator extends SlotTfMachine {

    EntityPlayer thePlayer;

    public SlotTfOvenAccelerator(EntityPlayer player, IInventory par1IInventory, int par2, int par3, int par4, TfMachineGuiParts guiPart)
    {
        super(par1IInventory, par2, par3, par4, guiPart);
        this.thePlayer = player;
    }

    @Override
    public void onSlotChanged()
    {
        super.onSlotChanged();

        ItemStack stack = this.getStack();
        if (stack != null
                && stack.getItem() == TcItems.materials && stack.getItemDamage() == ItemTcMaterials.EnumTcMaterialInfo.activatedHellTofu.getMetadata()
                && ItemStackAdapter.getSize(stack) == 64)
        {
            TcAchievementMgr.achieve(thePlayer, TcAchievementMgr.Key.ultimateOven);
        }
    }
}
