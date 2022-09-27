package remiliaMarine.tofu.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.util.Utils;

public class ItemDiamondTofuArmor extends ItemTofuArmor {
    public static Potion[][] registanceList = new Potion[][]{
        {MobEffects.BLINDNESS, MobEffects.NAUSEA}, // Helmet
        {MobEffects.POISON, MobEffects.HUNGER, MobEffects.WITHER}, // Chestplate
        {MobEffects.WEAKNESS, MobEffects.MINING_FATIGUE}, // Leggings
        {MobEffects.SLOWNESS} // Boots
    };
    
    public ItemDiamondTofuArmor(ArmorMaterial material, EntityEquipmentSlot equipmentSlotIn)
    {
        super(material, equipmentSlotIn);
    }
    
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4)
    {
        StringBuilder sb = new StringBuilder();
        for (Potion id : registanceList[Utils.getIdFromEquipmentSlot(this.armorType)])
        {
            if (sb.length() > 0) sb.append(", ");
            sb.append(net.minecraft.client.resources.I18n.format(id.getName()));
        }
        par3List.add(net.minecraft.util.text.translation.I18n.translateToLocalFormatted("item.tofucraft:" + TcItems.getArmorName("diamond", this.armorType) + ".desc", sb.toString()));

        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
    }
}
