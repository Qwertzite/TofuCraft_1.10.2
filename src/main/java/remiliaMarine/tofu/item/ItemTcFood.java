package remiliaMarine.tofu.item;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;

public class ItemTcFood extends ItemFood {
    private int customDuration = -1;

    public ItemTcFood(int par2, float par3, boolean par4)
    {
        super(par2, par3, par4);
        this.setCreativeTab(TcCreativeTabs.FOOD);
    }

    public ItemTcFood setEatingDuration(int duration)
    {
        this.customDuration = duration;
        return this;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        if (customDuration == -1)
        {
            return super.getMaxItemUseDuration(par1ItemStack);
        }
        else
        {
            return customDuration;
        }
    }

    @Override
    public ItemTcFood setUnlocalizedName(String name)
    {
        super.setUnlocalizedName(name);
        //this.setTextureName(name);
        return this;
    }
}
