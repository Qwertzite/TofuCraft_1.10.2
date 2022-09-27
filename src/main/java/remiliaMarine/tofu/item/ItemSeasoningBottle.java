package remiliaMarine.tofu.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemSeasoningBottle extends TcItem implements ITcItemColoured {

	private final int colour;
	
    public ItemSeasoningBottle(int colour, int durability)
    {
        super();
        this.colour = colour;
        this.setMaxDamage(durability);
        this.setNoRepair();
        this.setContainerItem(Items.GLASS_BOTTLE);
        this.setMaxStackSize(1);
    }
	
	@Override
	public int getItemColour(int meta) {
		return this.colour;
	}
	
    @Override
    public ItemStack getContainerItem(ItemStack stack)
    {
        int dmg = stack.getItemDamage();
        
        if (dmg < stack.getMaxDamage())
        {
            stack.setItemDamage(dmg + 1);
            ItemStack ret = new ItemStack(this, 1, stack.getMetadata());
            ret.setItemDamage(dmg + 1);
            return ret;
        }
        else {
        	return super.getContainerItem(stack);
        }
    }
    
    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        int dmg = stack.getItemDamage();
        if (dmg < this.getMaxDamage(stack))
        {
            return true;
        }
        else
        {
            return super.hasContainerItem(stack);
        }
    }

}
