package remiliaMarine.tofu.init.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemDiamondTofuArmor;
import remiliaMarine.tofu.item.ItemDiamondTofuSword;
import remiliaMarine.tofu.item.ItemTofuArmor;
import remiliaMarine.tofu.item.ItemTofuSword;
import remiliaMarine.tofu.item.ItemZundaBow;
import remiliaMarine.tofu.item.TcItem;
import remiliaMarine.tofu.item.TofuArmorMaterial;
import remiliaMarine.tofu.item.TofuToolMaterial;
import remiliaMarine.tofu.util.Utils;

public class LoaderCombat {

	public void load(FMLPreInitializationEvent event) {
        /*
         * Armors & weapons
         */
        TcItems.armorKinu = addArmorSet(TofuArmorMaterial.KINU);
        TcItems.swordKinu = new ItemTofuSword(TofuToolMaterial.KINU).setUnlocalizedName("tofucraft:swordKinu");
        GameRegistry.register(TcItems.swordKinu, new ResourceLocation(TofuCraftCore.MODID, "swordKinu"));

        TcItems.armorMomen = addArmorSet(TofuArmorMaterial.MOMEN);
        TcItems.swordMomen = new ItemTofuSword(TofuToolMaterial.MOMEN).setUnlocalizedName("tofucraft:swordMomen");
        GameRegistry.register(TcItems.swordMomen, new ResourceLocation(TofuCraftCore.MODID, "swordMomen"));

        TcItems.armorSolid = addArmorSet(TofuArmorMaterial.SOLID);
        TcItems.swordSolid = new ItemTofuSword(TofuToolMaterial.SOLID).setUnlocalizedName("tofucraft:swordSolid");
        GameRegistry.register(TcItems.swordSolid, new ResourceLocation(TofuCraftCore.MODID, "swordSolid"));

        TcItems.armorMetal = addArmorSet(TofuArmorMaterial.METAL);
        TcItems.swordMetal = new ItemTofuSword(TofuToolMaterial.METAL).setUnlocalizedName("tofucraft:swordMetal");
        GameRegistry.register(TcItems.swordMetal, new ResourceLocation(TofuCraftCore.MODID, "swordMetal"));

        TcItems.armorDiamond = addArmorSet(TofuArmorMaterial.DIAMOND);
        TcItems.swordDiamond = new ItemDiamondTofuSword(TofuToolMaterial.DIAMOND).setUnlocalizedName("tofucraft:swordDiamond");
        GameRegistry.register(TcItems.swordDiamond, new ResourceLocation(TofuCraftCore.MODID, "swordDiamond"));

        /*
         * Zunda Arrow & Bow
         */
        TcItems.zundaBow = new ItemZundaBow().setUnlocalizedName("tofucraft:zundaBow").setCreativeTab(TcCreativeTabs.COMBAT);
        GameRegistry.register(TcItems.zundaBow, new ResourceLocation(TofuCraftCore.MODID, "zundaBow"));

        TcItems.zundaArrow = new TcItem().setUnlocalizedName("tofucraft:zundaArrow").setCreativeTab(TcCreativeTabs.COMBAT);
        GameRegistry.register(TcItems.zundaArrow, new ResourceLocation(TofuCraftCore.MODID, "zundaArrow"));

	    if (event.getSide().isClient()) {
	    	for (int slot = 0; slot < 4; slot++) {
	    		ModelLoader.setCustomModelResourceLocation(TcItems.armorKinu[slot], 0, new ModelResourceLocation("tofucraft:" + TcItems.getArmorName("kinu", slot), "inventory"));
	    		ModelLoader.setCustomModelResourceLocation(TcItems.armorMomen[slot], 0, new ModelResourceLocation("tofucraft:" + TcItems.getArmorName("momen", slot), "inventory"));
	    		ModelLoader.setCustomModelResourceLocation(TcItems.armorSolid[slot], 0, new ModelResourceLocation("tofucraft:" + TcItems.getArmorName("solid", slot), "inventory"));
	    		ModelLoader.setCustomModelResourceLocation(TcItems.armorMetal[slot], 0, new ModelResourceLocation("tofucraft:" + TcItems.getArmorName("metal", slot), "inventory"));
	    		ModelLoader.setCustomModelResourceLocation(TcItems.armorDiamond[slot], 0, new ModelResourceLocation("tofucraft:" + TcItems.getArmorName("diamond", slot), "inventory"));
	    	}

	    	ModelLoader.setCustomModelResourceLocation(TcItems.swordKinu, 0, new ModelResourceLocation("tofucraft:swordKinu", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.swordMomen, 0, new ModelResourceLocation("tofucraft:swordMomen", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.swordSolid, 0, new ModelResourceLocation("tofucraft:swordSolid", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.swordMetal, 0, new ModelResourceLocation("tofucraft:swordMetal", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.swordDiamond, 0, new ModelResourceLocation("tofucraft:swordDiamond", "inventory"));

	    	ModelLoader.setCustomModelResourceLocation(TcItems.zundaBow, 0, new ModelResourceLocation("tofucraft:zundaBow", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.zundaArrow, 0, new ModelResourceLocation("tofucraft:zundaArrow", "inventory"));

	    }
	}
	//register normal armors
    private Item[] addArmorSet(ItemArmor.ArmorMaterial material)
    {
        String key;
        if (material == TofuArmorMaterial.KINU) key = "kinu";
        else if (material == TofuArmorMaterial.MOMEN) key = "momen";
        else if (material == TofuArmorMaterial.SOLID) key = "solid";
        else if (material == TofuArmorMaterial.METAL) key = "metal";
        else if (material == TofuArmorMaterial.DIAMOND) key = "diamond";
        else throw new IllegalArgumentException("Unknown material for armor");

        Item[] armors = new Item[4];

        for(int id = 0; id < 4; id++)
        {
        	String armourName = TcItems.getArmorName(key, id);

        	if(material == TofuArmorMaterial.DIAMOND) {
            	armors[id] = new ItemDiamondTofuArmor(material, Utils.getSlotFromInt(id))
            			.setArmorTexture(String.format("tofucraft:textures/armor/armor_%s_%d.png", key, id == 2 ? 2 : 1))
            			.setUnlocalizedName(TofuCraftCore.RES + armourName);
        	} else {
        	armors[id] = new ItemTofuArmor(material, Utils.getSlotFromInt(id))
        			.setArmorTexture(String.format("tofucraft:textures/armor/armor_%s_%d.png", key, id == 2 ? 2 : 1))
        			.setUnlocalizedName(TofuCraftCore.RES + armourName);
        	}

        	GameRegistry.register(armors[id], new ResourceLocation(TofuCraftCore.MODID, armourName));
        }

        return armors;
    }
}
