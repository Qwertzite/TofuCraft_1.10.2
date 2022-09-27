package remiliaMarine.tofu.api.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class SlotTfMachineCrafting extends SlotTfMachineOutput {

    private EntityPlayer thePlayer;
    private int amountCrafted;

    public SlotTfMachineCrafting(EntityPlayer player, IInventory par1IInventory, int par2, int par3, int par4, TfMachineGuiParts guiPart)
    {
        super(par1IInventory, par2, par3, par4, guiPart);
        this.thePlayer = player;
    }

    @Override
    public ItemStack decrStackSize(int par1)
    {
        if (this.getHasStack())
        {
            this.amountCrafted += Math.min(par1, ItemStackAdapter.getSize(this.getStack()));
        }

        return super.decrStackSize(par1);
    }

	/**
	 * 1.10.2: void {@link Slot#onPickupFromSlot(EntityPlayer, ItemStack)}<br>
	 * 1.12.2: ItemStack {@link Slot#onTake(EntityPlayer, ItemStack)}
	 */
    @Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
    {
        this.onCrafting(par2ItemStack);
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    }

    @Override
    protected void onCrafting(ItemStack par1ItemStack, int par2)
    {
        this.amountCrafted += par2;
        this.onCrafting(par1ItemStack);
    }

    @Override
    protected void onCrafting(ItemStack par1ItemStack)
    {
        super.onCrafting(par1ItemStack);
        par1ItemStack.onCrafting(this.thePlayer.getEntityWorld(), this.thePlayer, this.amountCrafted);
        TcAchievementMgr.achieveCraftingItem(par1ItemStack, thePlayer);
    }
}
