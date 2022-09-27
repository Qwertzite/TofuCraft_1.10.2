package remiliaMarine.tofu.item;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;

public class ItemTofuArmor extends ItemArmor {
    private String armorTextureFile;

    public ItemTofuArmor(ArmorMaterial material, EntityEquipmentSlot equipmentSlotIn)
    {
        super(material, 0, equipmentSlotIn);
        this.setCreativeTab(TcCreativeTabs.COMBAT);

    }

    public ItemTofuArmor setArmorTexture(String file)
    {
        armorTextureFile = file;
        return this;
    }

    @Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
    	return this.armorTextureFile;
    }
}
