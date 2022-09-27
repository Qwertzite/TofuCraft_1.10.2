package remiliaMarine.tofu.item;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.util.ItemUtils;

public class ItemTcAxe extends ItemAxe {
    
	public static final float[] ATTACK_DAMAGES = new float[] {-1.0f, -1.0f, 5.0f, 8.0f, 9.0f};//{6.0F, 8.0F, 8.0F, 8.0F, 6.0F}; wood stone iron diamond gold
    public static final float[] ATTACK_SPEEDS = new float[] {-3.3f, -3.2f, -3.0f, -3.1f, -2.9f};//{ -3.2F, -3.2F, -3.1F, -3.0F, -3.0F};
    
    public ItemTcAxe(ToolMaterial par2EnumToolMaterial, float damage, float speed)
    {
        super(par2EnumToolMaterial, damage, speed);
        this.setCreativeTab(TcCreativeTabs.TOOLS);
        ItemUtils.tweakToolAttackDamage(this);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return ImmutableSet.of("axe");
    }
}
