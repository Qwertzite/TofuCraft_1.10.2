package remiliaMarine.tofu.eventhandler;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemDiamondTofuArmor;
import remiliaMarine.tofu.util.TcPotionUtils;

public class PotionEventHook{
	
    public static PotionEffect onPotionEffectApplied(EntityLivingBase entity, PotionEffect potionEffect)
    {
        if (entity.worldObj.isRemote || potionEffect == null || potionEffect.getIsAmbient()) return potionEffect;

        int diamondArmors = 0;
        boolean[] armorsEquipped = new boolean[]{false, false, false, false};
        for (ItemStack armor : entity.getArmorInventoryList())
        {
            if (armor != null)
            {
                for (int i = 0; i < 4; i++)
                {
                    if (armor.getItem() == TcItems.armorDiamond[i])
                    {
                        armorsEquipped[i] = true;
                        diamondArmors++;
                    }
                }
            }
        }

        if (diamondArmors > 0)
        {
            Boolean isBadEffect = potionEffect.getPotion().isBadEffect();
            if (isBadEffect)
            {
            	Potion potion = potionEffect.getPotion();
                int amplifier = potionEffect.getAmplifier();
                int duration = potionEffect.getDuration();
                boolean isAmbient = potionEffect.getIsAmbient();
                boolean particle = potionEffect.doesShowParticles();
                List<ItemStack> curativeItems = potionEffect.getCurativeItems();

                switch (diamondArmors)
                {
                    case 4:
                        amplifier = 1;
                    case 3:
                        duration /= 2;
                    case 2:
                        amplifier = Math.max(0, amplifier - 1);
                    case 1:
                        duration /= 2;
                }

                for (int i = 0; i < 4; i++)
                {
                    if (armorsEquipped[i] && TcPotionUtils.contains(ItemDiamondTofuArmor.registanceList[i], potion))
                    {
                        duration = 0;
                    }
                }

                PotionEffect newPotionEffect = new PotionEffect(potion, duration, amplifier, isAmbient, particle);
                newPotionEffect.setCurativeItems(curativeItems);
                return newPotionEffect;
            }
        }
        return potionEffect;
    }
}

