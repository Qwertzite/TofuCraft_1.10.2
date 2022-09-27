package remiliaMarine.tofu.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.TofuCraftCore;

abstract public class CreativeTabTofuCraft extends CreativeTabs
{
	public CreativeTabTofuCraft(String name)
    {
        super(TofuCraftCore.MODID + "." + name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    abstract public Item getTabIconItem();

}
