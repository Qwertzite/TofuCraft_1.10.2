package remiliaMarine.tofu.enchantment;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.init.TcItems;

public class TcEnchantment {
    //private static final String CONF_CATEGORY = "enchantment";
    public static final EnumEnchantmentType typeDiamondTofu = EnumHelper.addEnchantmentType("diamond_tofu");

    public static Enchantment enchantmentBatch = null;
    public static Enchantment enchantmentDrain = null;

    public static void register(FMLPreInitializationEvent event)
    {
        EntityEquipmentSlot[] handHeldSlot = new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND};
        enchantmentBatch = new EnchantmentBatch(Enchantment.Rarity.COMMON, typeDiamondTofu, handHeldSlot);
        enchantmentDrain = new EnchantmentDrain(Enchantment.Rarity.COMMON, typeDiamondTofu, handHeldSlot);
        GameRegistry.register(enchantmentBatch, new ResourceLocation(TofuCraftCore.MODID, "ench_batch"));
        GameRegistry.register(enchantmentDrain, new ResourceLocation(TofuCraftCore.MODID, "ench_drain"));

    }

    private static class EnchantmentBatch extends Enchantment{
        public EnchantmentBatch(Enchantment.Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots){
        	super(rarityIn, typeIn, slots);
            this.setName("batch");
        }
        @Override
        public int getMinEnchantability(int par1)
        {
            return 15 + (par1 - 1) * 9;
        }
        @Override
        public int getMaxEnchantability(int par1)
        {
            return this.getMinEnchantability(par1) + 50;
        }

        @Override
        public int getMaxLevel()
        {
            return 3;
        }

        @Override
        public boolean canApply(ItemStack par1ItemStack)
        {
            return ArrayUtils.contains(TcItems.toolDiamond, par1ItemStack.getItem());
        }

        /**
         * This applies specifically to applying at the enchanting table. The other method {@link #canApply(ItemStack)}
         * applies for <i>all possible</i> enchantments.
         * @param stack
         * @return
         */
        @Override
        public boolean canApplyAtEnchantingTable(ItemStack stack)
        {
            return this.canApply(stack);
        }
    }

    private static class EnchantmentDrain extends Enchantment {
    	public EnchantmentDrain(Enchantment.Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        	super(rarityIn, typeIn, slots);
            this.setName("drain");
    	}

        @Override
        public int getMinEnchantability(int par1)
        {
            return 1 + 10 * (par1 - 1);
        }
        @Override
        public int getMaxEnchantability(int par1)
        {
            return this.getMinEnchantability(par1) + 50;
        }

        @Override
        public int getMaxLevel()
        {
            return 5;
        }

        @Override
        public boolean canApply(ItemStack par1ItemStack)
        {
            return par1ItemStack.getItem() == TcItems.swordDiamond;
        }

        /**
         * This applies specifically to applying at the enchanting table. The other method {@link #canApply(ItemStack)}
         * applies for <i>all possible</i> enchantments.
         * @param stack
         * @return
         */
        @Override
        public boolean canApplyAtEnchantingTable(ItemStack stack)
        {
            return this.canApply(stack);
        }

    }

}
