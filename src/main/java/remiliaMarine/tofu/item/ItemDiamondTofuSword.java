package remiliaMarine.tofu.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.data.DataType;
import remiliaMarine.tofu.data.EntityInfo;
import remiliaMarine.tofu.enchantment.TcEnchantmentHelper;

public class ItemDiamondTofuSword extends ItemTofuSword {
    public ItemDiamondTofuSword(ToolMaterial material)
    {
        super(material);
    }
    
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        if (entity instanceof EntityLivingBase)
        {
            EntityInfo.instance().set(player.getEntityId(), DataType.DiamondSwordAttack, ((EntityLivingBase) entity).getHealth());
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
    @Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase targetEntity, EntityLivingBase attacker)
    {
        // Drain
        Float healthBeforeAttack = EntityInfo.instance().<Float>get(attacker.getEntityId(), DataType.DiamondSwordAttack);
        if (healthBeforeAttack != null)
        {
            float damage = healthBeforeAttack - targetEntity.getHealth();
            if (damage > 0.0f)
            {
                int lvl = TcEnchantmentHelper.getDrainModifier(attacker, itemStack);
                attacker.heal(damage * (lvl * 0.1f + 0.1f));
            }

            EntityInfo.instance().remove(attacker.getEntityId(), DataType.DiamondSwordAttack);
        }

        return super.hitEntity(itemStack, targetEntity, attacker);
    }
}
