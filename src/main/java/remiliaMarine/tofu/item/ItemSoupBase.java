package remiliaMarine.tofu.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSoupBase extends ItemTcFood {

    public ItemSoupBase(int recoveryAmount, float saturation, boolean isWolfsFavorite)
    {
        super(recoveryAmount, saturation, isWolfsFavorite);
        this.setMaxStackSize(1);
        this.setContainerItem(Items.BOWL);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack par1ItemStack, World par2World, EntityLivingBase entityLiving)
    {
        super.onItemUseFinish(par1ItemStack, par2World, entityLiving);
        return this.getContainerItem(par1ItemStack);
    }
}
