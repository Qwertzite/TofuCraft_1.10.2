package remiliaMarine.tofu.api.tileentity;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityTfMachineSidedInventoryBase extends TileEntityTfMachineBase implements ISidedInventory {
	
    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing direction)
    {
        return true;
    }
}
