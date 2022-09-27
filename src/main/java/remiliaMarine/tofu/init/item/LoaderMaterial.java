package remiliaMarine.tofu.init.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.dispenser.DispenserBehaviorNigari;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemAltIcon;
import remiliaMarine.tofu.item.ItemDeffattingPotion;
import remiliaMarine.tofu.item.ItemDrinkBucket;
import remiliaMarine.tofu.item.ItemGelatin;
import remiliaMarine.tofu.item.ItemGelatin.EnumGelatin;
import remiliaMarine.tofu.item.ItemKoujiBase;
import remiliaMarine.tofu.item.ItemLeek;
import remiliaMarine.tofu.item.ItemNigari;
import remiliaMarine.tofu.item.ItemRollingPin;
import remiliaMarine.tofu.item.ItemSeasoningBottle;
import remiliaMarine.tofu.item.ItemSomenTsuyuBowl;
import remiliaMarine.tofu.item.ItemSoupBase;
import remiliaMarine.tofu.item.ItemSoybeans;
import remiliaMarine.tofu.item.ItemSoybeansHell;
import remiliaMarine.tofu.item.ItemTcBucket;
import remiliaMarine.tofu.item.ItemTcMaterials;
import remiliaMarine.tofu.item.ItemTcMaterials.EnumTcMaterialInfo;
import remiliaMarine.tofu.item.ItemTcSeeds;
import remiliaMarine.tofu.item.TcItem;

public class LoaderMaterial {
	public void load(FMLPreInitializationEvent event) {

		TcItems.soybeans = new ItemSoybeans().setUnlocalizedName("tofucraft:seeds_soybeans").setCreativeTab(TcCreativeTabs.MATERIALS);
		GameRegistry.register(TcItems.soybeans, new ResourceLocation(TofuCraftCore.MODID, "seeds_soybeans"));
        MinecraftForge.addGrassSeed(new ItemStack(TcItems.soybeans), 2);

        TcItems.soybeansHell = new ItemSoybeansHell().setUnlocalizedName("tofucraft:seeds_soybeansHell").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.soybeansHell, new ResourceLocation(TofuCraftCore.MODID, "seeds_soybeansHell"));

        /*
         * === Fluid Buckets ===
         */
        TcItems.bucketSoymilk = ((ItemDrinkBucket) new ItemDrinkBucket(TcBlocks.soymilkStill, 1, 0.1F)
        		.setUnlocalizedName("tofucraft:bucketSoymilk"))
        		.setAlwaysEdible()
        		.setCreativeTab(TcCreativeTabs.MATERIALS)
        		.setContainerItem(Items.BUCKET);
        GameRegistry.register(TcItems.bucketSoymilk, new ResourceLocation(TofuCraftCore.MODID, "bucketsoymilk"));

        TcItems.bucketSoymilkHell = ((ItemDrinkBucket) new ItemDrinkBucket(TcBlocks.soymilkHellStill, 2, 0.2F)
        		.setUnlocalizedName("tofucraft:bucketSoymilkHell"))
        		.setPotionEffect(Potion.REGISTRY.getObjectById(12), 60, 0, 1.0F)
                .setAlwaysEdible()
                .setCreativeTab(TcCreativeTabs.MATERIALS)
                .setContainerItem(Items.BUCKET);
        GameRegistry.register(TcItems.bucketSoymilkHell, new ResourceLocation(TofuCraftCore.MODID, "bucketsoymilkhell"));

        TcItems.bucketSoySauce = new ItemTcBucket(TcBlocks.soySauceStill)
        		.setUnlocalizedName("tofucraft:bucketSoySauce")
        		.setContainerItem(Items.BUCKET)
        		.setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.bucketSoySauce, new ResourceLocation(TofuCraftCore.MODID, "bucketsoysauce"));

        IBehaviorDispenseItem dispenserbehaviorfilledbucket =  BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.getObject(Items.WATER_BUCKET);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(TcItems.bucketSoymilk, dispenserbehaviorfilledbucket);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(TcItems.bucketSoymilkHell, dispenserbehaviorfilledbucket);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(TcItems.bucketSoySauce, dispenserbehaviorfilledbucket);

        // Salt
        TcItems.salt = new ItemAltIcon()
        		.setUnlocalizedName("tofucraft:salt")
        		.setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.salt, new ResourceLocation(TofuCraftCore.MODID, "sugar"));

        // Nigari
        TcItems.nigari = new ItemNigari()
        		.setUnlocalizedName("tofucraft:nigari")
        		.setCreativeTab(TcCreativeTabs.MATERIALS)
                .setContainerItem(Items.GLASS_BOTTLE);
        GameRegistry.register(TcItems.nigari, new ResourceLocation(TofuCraftCore.MODID, "nigari"));
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(TcItems.nigari, new DispenserBehaviorNigari());

        // Kouji
        TcItems.koujiBase = new ItemKoujiBase().setUnlocalizedName("tofucraft:koujiBase").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.koujiBase, new ResourceLocation(TofuCraftCore.MODID, "koujiBase"));

        TcItems.kouji = new TcItem().setUnlocalizedName("tofucraft:kouji").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.kouji, new ResourceLocation(TofuCraftCore.MODID, "kouji"));

        TcItems.miso = new TcItem().setUnlocalizedName("tofucraft:miso").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.miso, new ResourceLocation(TofuCraftCore.MODID, "miso"));

        TcItems.edamame = new TcItem().setUnlocalizedName("tofucraft:edamame").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.edamame, new ResourceLocation(TofuCraftCore.MODID, "edamame"));

        TcItems.zunda = new TcItem().setUnlocalizedName("tofucraft:zunda").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.zunda, new ResourceLocation(TofuCraftCore.MODID, "zunda"));

        TcItems.barrelEmpty = new TcItem().setUnlocalizedName("tofucraft:barrelEmpty").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.barrelEmpty, new ResourceLocation(TofuCraftCore.MODID, "barrelEmpty"));

//      phialEmpty = $("", new ItemPhial())
//      .setCreativeTab(TofuCraftCore.tabTofuCraft)
//      .setUnlocalizedName("tofucraft:phialEmpty");

        TcItems.bottleSoySauce = new ItemSeasoningBottle(0x432709, 19).setUnlocalizedName("tofucraft:bottleSoySauce").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.bottleSoySauce, new ResourceLocation(TofuCraftCore.MODID, "bottleSoySauce"));

        TcItems.soybeansParched = new TcItem().setUnlocalizedName("tofucraft:soybeansParched").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.soybeansParched, new ResourceLocation(TofuCraftCore.MODID, "soybeansParched"));

        TcItems.kinako = new TcItem().setUnlocalizedName("tofucraft:kinako").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.kinako, new ResourceLocation(TofuCraftCore.MODID, "kinako"));

        TcItems.defattingPotion = new ItemDeffattingPotion(0xada1cc).setUnlocalizedName("tofucraft:defattingPotion").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.defattingPotion, new ResourceLocation(TofuCraftCore.MODID, "defattingPotion"));

        TcItems.dashi = new ItemSeasoningBottle(0xfcf6ac, 9).setUnlocalizedName("tofucraft:dashi").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.dashi, new ResourceLocation(TofuCraftCore.MODID, "dashi"));

        TcItems.soyOil = new ItemSeasoningBottle(0xfeff82, 19).setUnlocalizedName("tofucraft:soyOil").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.soyOil, new ResourceLocation(TofuCraftCore.MODID, "soyOil"));

        TcItems.natto = new TcItem().setUnlocalizedName("tofucraft:natto").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.natto, new ResourceLocation(TofuCraftCore.MODID, "natto"));

        TcItems.nattoHiyayakko = new ItemSoupBase(8, 0.8f, false).setUnlocalizedName("tofucraft:nattoHiyayakko");
        GameRegistry.register(TcItems.nattoHiyayakko, new ResourceLocation(TofuCraftCore.MODID, "nattoHiyayakko"));

        TcItems.apricotSeed = new TcItem().setUnlocalizedName("tofucraft:apricotSeed").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.apricotSeed, new ResourceLocation(TofuCraftCore.MODID, "apricotSeed"));

        TcItems.filterCloth = new TcItem().setUnlocalizedName("tofucraft:filterCloth").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.filterCloth, new ResourceLocation(TofuCraftCore.MODID, "filterCloth"));

        TcItems.okara = new TcItem().setUnlocalizedName("tofucraft:okara").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.okara, new ResourceLocation(TofuCraftCore.MODID, "okara"));

        TcItems.mincedPotato = new TcItem().setUnlocalizedName("tofucraft:mincedPotato").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.mincedPotato, new ResourceLocation(TofuCraftCore.MODID, "mincedPotato"));

        TcItems.starchRaw = new TcItem().setUnlocalizedName("tofucraft:starchRaw").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.starchRaw, new ResourceLocation(TofuCraftCore.MODID, "starchRaw"));

        TcItems.starch = new TcItem().setUnlocalizedName("tofucraft:starch").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.starch, new ResourceLocation(TofuCraftCore.MODID, "starch"));

        TcItems.kyoninso = new TcItem().setUnlocalizedName("tofucraft:kyoninso").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.kyoninso, new ResourceLocation(TofuCraftCore.MODID, "kyoninso"));

        TcItems.leek = new ItemLeek().setUnlocalizedName("tofucraft:leek").setCreativeTab(TcCreativeTabs.MATERIALS).setFull3D();
        GameRegistry.register(TcItems.leek, new ResourceLocation(TofuCraftCore.MODID, "leek"));

        TcItems.sesame = new ItemTcSeeds(TcBlocks.sesame, Blocks.FARMLAND).setUnlocalizedName("tofucraft:sesame").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.sesame, new ResourceLocation(TofuCraftCore.MODID, "sesame"));

        TcItems.zundama = new TcItem().setUnlocalizedName("tofucraft:zundama").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.zundama, new ResourceLocation(TofuCraftCore.MODID, "zundama"));

        // Gelatin and Gelatin Base
        TcItems.gelatin = new ItemGelatin().setUnlocalizedName("tofucraft:gelatin").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.gelatin, new ResourceLocation(TofuCraftCore.MODID, "gelatin"));

        TcItems.doubanjiang = new ItemSeasoningBottle(0xFFFFFF, 57).setUnlocalizedName("tofucraft:doubanjiang").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.doubanjiang, new ResourceLocation(TofuCraftCore.MODID, "doubanjiang"));

        TcItems.strawberryJam = new ItemSeasoningBottle(0xFFFFFF, 99).setUnlocalizedName("tofucraft:strawberryJam").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.strawberryJam, new ResourceLocation(TofuCraftCore.MODID, "strawberryJam"));

        TcItems.somenTsuyuBowl = new ItemSomenTsuyuBowl(2, 0.1F, false).setUnlocalizedName("tofucraft:tsuyuBowl_glass").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.somenTsuyuBowl, new ResourceLocation(TofuCraftCore.MODID, "somenTsuyuBowlGl"));

        /*
         * === Material Items ===
         */
        TcItems.materials = (ItemTcMaterials)new ItemTcMaterials().setUnlocalizedName("tofucraft:materials").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.materials, new ResourceLocation(TofuCraftCore.MODID, "materials"));

        TcItems.rollingPin = new ItemRollingPin().setUnlocalizedName("tofucraft:rollingPin").setCreativeTab(TcCreativeTabs.MATERIALS);
        GameRegistry.register(TcItems.rollingPin, new ResourceLocation(TofuCraftCore.MODID, "rollingPin"));

        TcBlocks.oreTofu.setItemContained(ItemTcMaterials.EnumTcMaterialInfo.tofuGem.getStack());
        TcBlocks.oreTofuDiamond.setItemContained(ItemTcMaterials.EnumTcMaterialInfo.tofuDiamondNugget.getStack());

	    if (event.getSide().isClient()) {
			ModelLoader.setCustomModelResourceLocation(TcItems.soybeans, 0, new ModelResourceLocation(TofuCraftCore.RES + "seeds_soybeans", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.soybeansHell, 0, new ModelResourceLocation(TofuCraftCore.RES + "seeds_soybeansHell", "inventory"));

			ModelLoader.setCustomModelResourceLocation(TcItems.bucketSoymilk, 0, new ModelResourceLocation(TofuCraftCore.RES + "bucketsoymilk", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.bucketSoymilkHell, 0, new ModelResourceLocation(TofuCraftCore.RES + "bucketsoymilkhell", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.bucketSoySauce, 0, new ModelResourceLocation(TofuCraftCore.RES + "bucketsoysauce", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.salt, 0, new ModelResourceLocation("minecraft:sugar", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.nigari, 0, new ModelResourceLocation("tofucraft:coloured_bottle", "inventory"));
			ModelBakery.registerItemVariants(TcItems.koujiBase, ItemKoujiBase.getResourceLocation());
			for (int i = 0; i < 60; i++) {
				ModelLoader.setCustomModelResourceLocation(TcItems.koujiBase, i, new ModelResourceLocation(TofuCraftCore.RES + "koujiBase", "inventory"));
			}
			ModelLoader.setCustomModelResourceLocation(TcItems.kouji, 0, new ModelResourceLocation(TofuCraftCore.RES + "kouji", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.miso, 0, new ModelResourceLocation(TofuCraftCore.RES + "miso", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.edamame, 0, new ModelResourceLocation(TofuCraftCore.RES + "edamame", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.zunda, 0, new ModelResourceLocation(TofuCraftCore.RES + "zunda", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.barrelEmpty, 0, new ModelResourceLocation(TofuCraftCore.RES + "barrelEmpty", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.bottleSoySauce, 0, new ModelResourceLocation(TofuCraftCore.RES + "coloured_bottle", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.soybeansParched, 0, new ModelResourceLocation(TofuCraftCore.RES + "soybeansParched", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.kinako, 0, new ModelResourceLocation(TofuCraftCore.RES + "kinako", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.defattingPotion, 0, new ModelResourceLocation(TofuCraftCore.RES + "coloured_bottle", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.dashi, 0, new ModelResourceLocation(TofuCraftCore.RES + "coloured_bottle", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.soyOil, 0, new ModelResourceLocation(TofuCraftCore.RES + "coloured_bottle", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.natto, 0, new ModelResourceLocation(TofuCraftCore.RES + "natto", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.nattoHiyayakko, 0, new ModelResourceLocation(TofuCraftCore.RES + "nattoHiyayakko", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.apricotSeed, 0, new ModelResourceLocation(TofuCraftCore.RES + "apricotSeed", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.filterCloth, 0, new ModelResourceLocation(TofuCraftCore.RES + "filterCloth", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.okara, 0, new ModelResourceLocation(TofuCraftCore.RES + "okara", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.mincedPotato, 0, new ModelResourceLocation(TofuCraftCore.RES + "mincedPotato", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.starchRaw, 0, new ModelResourceLocation(TofuCraftCore.RES + "starchRaw", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.starch, 0, new ModelResourceLocation(TofuCraftCore.RES + "starch", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.kyoninso, 0, new ModelResourceLocation(TofuCraftCore.RES + "kyoninso", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.leek, 0, new ModelResourceLocation(TofuCraftCore.RES + "leek", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.sesame, 0, new ModelResourceLocation(TofuCraftCore.RES + "sesame", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.zundama, 0, new ModelResourceLocation(TofuCraftCore.RES + "zundama", "inventory"));
			ModelBakery.registerItemVariants(TcItems.gelatin, ((ItemGelatin) TcItems.gelatin).getResourceLocation());
			for(EnumGelatin info : EnumGelatin.getTypes()) {
				ModelLoader.setCustomModelResourceLocation(TcItems.gelatin, info.getMetadata(), new ModelResourceLocation(TofuCraftCore.RES + info.getName(), "inventory"));
			}
			ModelLoader.setCustomModelResourceLocation(TcItems.doubanjiang, 0, new ModelResourceLocation(TofuCraftCore.RES + "doubanjiang", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.strawberryJam, 0, new ModelResourceLocation(TofuCraftCore.RES + "strawberryJam", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.somenTsuyuBowl, 0, new ModelResourceLocation(TofuCraftCore.RES + "somenTsuyuBowlGl", "inventory"));

			// materials
			ModelBakery.registerItemVariants(TcItems.materials, TcItems.materials.getResourceLocation());
			for (EnumTcMaterialInfo item : TcItems.materials.getItemInfoArray()) {
				ModelLoader.setCustomModelResourceLocation(TcItems.materials, item.getMetadata(), new ModelResourceLocation(TofuCraftCore.RES + item.getResName(), "inventory"));
			}
			ModelLoader.setCustomModelResourceLocation(TcItems.rollingPin, 0, new ModelResourceLocation(TofuCraftCore.RES + "rollingPin", "inventory"));


			//TODO

	    }
	}
}
