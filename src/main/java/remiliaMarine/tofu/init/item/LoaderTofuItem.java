package remiliaMarine.tofu.init.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemTcFood;
import remiliaMarine.tofu.item.TcItem;
import remiliaMarine.tofu.item.TofuMaterial;

public class LoaderTofuItem {

	public void load(FMLPreInitializationEvent event) {

        TcItems.tofuKinu = new ItemTcFood(2, 0.1F, false).setUnlocalizedName("tofucraft:tofuKinu").setAlwaysEdible();
        TcItems.TOFU_ITEMS.put(TofuMaterial.KINU, TcItems.tofuKinu);
        GameRegistry.register(TcItems.tofuKinu, new ResourceLocation(TofuCraftCore.MODID, "tofukinu"));

        TcItems.tofuMomen = new ItemTcFood(2, 0.1F, false).setUnlocalizedName("tofucraft:tofuMomen").setAlwaysEdible();
        TcItems.TOFU_ITEMS.put(TofuMaterial.MOMEN, TcItems.tofuMomen);
        GameRegistry.register(TcItems.tofuMomen, new ResourceLocation(TofuCraftCore.MODID, "tofumomen"));

        TcItems.tofuIshi = new ItemTcFood(3, 0.4F, false).setUnlocalizedName("tofucraft:tofuIshi").setCreativeTab(TcCreativeTabs.MATERIALS);
        TcItems.TOFU_ITEMS.put(TofuMaterial.ISHI, TcItems.tofuIshi);
        GameRegistry.register(TcItems.tofuIshi, new ResourceLocation(TofuCraftCore.MODID, "tofuishi"));

        TcItems.tofuMetal = new TcItem().setUnlocalizedName("tofucraft:tofuMetal").setCreativeTab(TcCreativeTabs.MATERIALS);
        TcItems.TOFU_ITEMS.put(TofuMaterial.METAL, TcItems.tofuMetal);
        GameRegistry.register(TcItems.tofuMetal, new ResourceLocation(TofuCraftCore.MODID, "tofumetal"));

        TcItems.tofuGrilled = new ItemTcFood(3, 0.2F, false).setUnlocalizedName("tofucraft:tofuGrilled").setAlwaysEdible();
        TcItems.TOFU_ITEMS.put(TofuMaterial.GRILLED, TcItems.tofuGrilled);
        GameRegistry.register(TcItems.tofuGrilled, new ResourceLocation(TofuCraftCore.MODID, "tofugrilled"));

        TcItems.tofuDried = new TcItem().setUnlocalizedName("tofucraft:tofuDried").setCreativeTab(TcCreativeTabs.MATERIALS);
        TcItems.TOFU_ITEMS.put(TofuMaterial.DRIED, TcItems.tofuDried);
        GameRegistry.register(TcItems.tofuDried, new ResourceLocation(TofuCraftCore.MODID, "tofudried"));

        TcItems.tofuFriedPouch = new ItemTcFood(4, 0.2F, false).setUnlocalizedName("tofucraft:tofuFriedPouch").setAlwaysEdible();
        TcItems.TOFU_ITEMS.put(TofuMaterial.FRIEDPOUCH, TcItems.tofuFriedPouch);
        GameRegistry.register(TcItems.tofuFriedPouch, new ResourceLocation(TofuCraftCore.MODID, "tofupouchfried"));

        TcItems.tofuFried = new ItemTcFood(4, 0.2f, false).setUnlocalizedName("tofucraft:tofuFried").setAlwaysEdible();
        TcItems.TOFU_ITEMS.put(TofuMaterial.FRIED, TcItems.tofuFried);
        GameRegistry.register(TcItems.tofuFried, new ResourceLocation(TofuCraftCore.MODID, "tofufried"));

        TcItems.tofuEgg = new ItemTcFood(4, 0.2F, false).setUnlocalizedName("tofucraft:tofuEgg").setAlwaysEdible();
        TcItems.TOFU_ITEMS.put(TofuMaterial.EGG, TcItems.tofuEgg);
        GameRegistry.register(TcItems.tofuEgg, new ResourceLocation(TofuCraftCore.MODID, "tofuegg"));

        TcItems.tofuAnnin = new ItemTcFood(4, 0.2f, false).setUnlocalizedName("tofucraft:tofuAnnin").setAlwaysEdible();
        TcItems.TOFU_ITEMS.put(TofuMaterial.ANNIN, TcItems.tofuAnnin);
        GameRegistry.register(TcItems.tofuAnnin, new ResourceLocation(TofuCraftCore.MODID, "tofuannin"));

        TcItems.tofuSesame = new ItemTcFood(4, 0.2F, false).setUnlocalizedName("tofucraft:tofuSesame").setAlwaysEdible();
        TcItems.TOFU_ITEMS.put(TofuMaterial.SESAME, TcItems.tofuSesame);
        GameRegistry.register(TcItems.tofuSesame, new ResourceLocation(TofuCraftCore.MODID, "tofusesame"));

        TcItems.tofuZunda = new ItemTcFood(4, 0.2F, false).setUnlocalizedName("tofucraft:tofuZunda").setAlwaysEdible();
        TcItems.TOFU_ITEMS.put(TofuMaterial.ZUNDA, TcItems.tofuZunda);
        GameRegistry.register(TcItems.tofuZunda, new ResourceLocation(TofuCraftCore.MODID, "tofuzunda"));

        TcItems.tofuStrawberry = new ItemTcFood(3, 0.2f, false).setUnlocalizedName("tofucraft:tofuStrawberry").setAlwaysEdible();
        TcItems.TOFU_ITEMS.put(TofuMaterial.STRAWBERRY, TcItems.tofuStrawberry);
        GameRegistry.register(TcItems.tofuStrawberry, new ResourceLocation(TofuCraftCore.MODID, "tofustrawberry"));

        TcItems.tofuMiso = new ItemTcFood(5, 0.8F, false).setUnlocalizedName("tofucraft:tofuMiso").setAlwaysEdible();
        TcItems.TOFU_ITEMS.put(TofuMaterial.MISO, TcItems.tofuMiso);
        GameRegistry.register(TcItems.tofuMiso, new ResourceLocation(TofuCraftCore.MODID, "tofumiso"));

        TcItems.tofuHell = new ItemTcFood(2, 0.2F, false).setUnlocalizedName("tofucraft:tofuHell")
        		.setPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 600, 0), 1.0F)
        		.setAlwaysEdible();
        TcItems.TOFU_ITEMS.put(TofuMaterial.HELL, TcItems.tofuHell);
        GameRegistry.register(TcItems.tofuHell, new ResourceLocation(TofuCraftCore.MODID, "tofuhell"));

		TcItems.tofuGlow = new ItemTcFood(2, 0.2F, false).setUnlocalizedName("tofucraft:tofuGlow").setAlwaysEdible();
        TcItems.TOFU_ITEMS.put(TofuMaterial.GLOW, TcItems.tofuGlow);//asTofu()
        GameRegistry.register(TcItems.tofuGlow, new ResourceLocation(TofuCraftCore.MODID, "tofuGlow"));

		TcItems.tofuDiamond = new TcItem().setUnlocalizedName("tofucraft:tofuDiamond").setCreativeTab(TcCreativeTabs.MATERIALS);
        TcItems.TOFU_ITEMS.put(TofuMaterial.DIAMOND, TcItems.tofuDiamond);//asTofu()
        GameRegistry.register(TcItems.tofuDiamond, new ResourceLocation(TofuCraftCore.MODID, "tofuDiamond"));

	    if (event.getSide().isClient()) {
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuKinu, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuKinu", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuMomen, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuMomen", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuIshi, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuIshi", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuMetal, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuMetal", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuGrilled, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuGrilled", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuDried, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuDried", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuFriedPouch, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuFriedPouch", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuFried, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuFried", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuEgg, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuEgg", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuAnnin, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuAnnin", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuSesame, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuSesame", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuZunda, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuZunda", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuStrawberry, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStrawberry", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuMiso, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuMiso", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuHell, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuHell", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuGlow, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuGlow", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.tofuDiamond, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuDiamond", "inventory"));

	    }
	}

}
