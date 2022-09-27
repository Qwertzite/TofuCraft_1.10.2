package remiliaMarine.tofu.item;

import net.minecraft.item.ItemSword;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;

public class ItemTofuSword extends ItemSword {
    public ItemTofuSword(ToolMaterial material)
    {
        super(material);
        this.setCreativeTab(TcCreativeTabs.COMBAT);
    }
}
