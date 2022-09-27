package remiliaMarine.tofu.item;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.util.ItemUtils;

public class ItemTcPickaxe extends ItemPickaxe {

    public ItemTcPickaxe(ToolMaterial par2EnumToolMaterial)
    {
        super(par2EnumToolMaterial);
        this.setCreativeTab(TcCreativeTabs.TOOLS);
        ItemUtils.tweakToolAttackDamage(this);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return ImmutableSet.of("pickaxe");
    }
}
