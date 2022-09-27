package remiliaMarine.tofu.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class TcItem extends Item {
    public TcItem()
    {
    	super();
    }
    
    public void breakItem(ItemStack itemstack, EntityLivingBase entityLiving)
    {
        entityLiving.renderBrokenItemStack(itemstack);

        if (entityLiving instanceof EntityPlayer)
        {
            ((EntityPlayer)entityLiving).addStat(StatList.getObjectBreakStats(this), 1);
        }

        ItemStackAdapter.addSize(itemstack, -1);

        if (ItemStackAdapter.getSize(itemstack) < 0)
        {
        	ItemStackAdapter.setSize(itemstack, 0);
        }

        itemstack.setItemDamage(0);

        if (ItemStackAdapter.getSize(itemstack) == 0 && entityLiving instanceof EntityPlayer)
        {
            ((EntityPlayer)entityLiving).inventory.mainInventory[((EntityPlayer)entityLiving).inventory.currentItem] = null;
        }
    }
    
    
}
