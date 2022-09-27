package remiliaMarine.tofu.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class ItemFoodContainer extends ItemTcFood {
    private ItemStack itemContained = null;

    public ItemFoodContainer(int par2, float par3, boolean par4, ItemStack itemContained)
    {
        super(par2, par3, par4);
        this.itemContained = itemContained;
    }
    
    public ItemFoodContainer(int par2, float par3, boolean par4)
    {
        super(par2, par3, par4);
    }
    
    public ItemFoodContainer setContainedItem(ItemStack itemstack)
    {
        this.itemContained = itemstack;
        return this;
    }
    
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;

        if (itemContained != null)
        {
            if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
            {
                if (ItemStackAdapter.getSize(stack) <= 0)
                {
                    return this.itemContained.copy();
                }

                if (entityplayer != null)
                {
                    entityplayer.inventory.addItemStackToInventory(this.itemContained.copy());
                }
            }
        }
        
        return stack;
    }  
}
