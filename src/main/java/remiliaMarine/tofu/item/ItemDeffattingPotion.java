package remiliaMarine.tofu.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDeffattingPotion extends TcItem implements ITcItemColoured {

	private int colour;
	
	public ItemDeffattingPotion(int colour) {
		super();
		this.colour = colour;
        this.setMaxStackSize(64);
	}
	
	@Override
	public int getItemColour(int meta) {
		return this.colour;
	}
	
    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.RARE;
    }
    
//    @Override
//    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack)
//    {
//        TcItemInfoBase info = this.getItemInfo(stack.getItemDamage());
//        return !((info.isNonDurabilityTool || info.isCraftingDurabilityTool) && !this.isRepairable());
//    }
    
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
