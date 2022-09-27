package remiliaMarine.tofu.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRollingPin extends TcItem {

	public ItemRollingPin() {
		super();
        this.setMaxStackSize(1);
	}
	
    @Override
    public Item getContainerItem()
    {
    	return this;
    }
    
    @Override
    public ItemStack getContainerItem(ItemStack stack)
    { 	
    	return new ItemStack(this, 1);
    }
    
    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }
}
