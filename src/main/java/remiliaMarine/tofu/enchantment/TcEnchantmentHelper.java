package remiliaMarine.tofu.enchantment;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class TcEnchantmentHelper {
    public static int getBatchModifier(EntityLivingBase par0EntityLivingBase, ItemStack stack)
    {
        return EnchantmentHelper.getEnchantmentLevel(TcEnchantment.enchantmentBatch, stack);
    }

    public static int getDrainModifier(EntityLivingBase par0EntityLivingBase, ItemStack stack)
    {
        return EnchantmentHelper.getEnchantmentLevel(TcEnchantment.enchantmentDrain, stack);
    }
}
