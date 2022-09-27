package remiliaMarine.tofu.init.block;

import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.block.BlockTofu;
import remiliaMarine.tofu.block.BlockTofuAnnin;
import remiliaMarine.tofu.block.BlockTofuFarmland;
import remiliaMarine.tofu.block.BlockTofuGrilled;
import remiliaMarine.tofu.block.BlockTofuIshi;
import remiliaMarine.tofu.block.BlockTofuMinced;
import remiliaMarine.tofu.block.BlockTofuTerrain;
import remiliaMarine.tofu.data.TofuInfo;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.item.ItemFoodSet;
import remiliaMarine.tofu.item.TofuMaterial;
import remiliaMarine.tofu.material.TcMaterial;

public class LoaderTofuBlock extends TcBlockLoader{
	static {
        registerTofuInfo(new TofuInfo(TofuMaterial.KINU,
                0.1F, TcMaterial.TOFU, SoundType.SNOW).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.MOMEN,
                0.4F, TcMaterial.TOFU, SoundType.SNOW).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.ISHI,
                2.0F, 10.0F, TcMaterial.TOFU, SoundType.WOOD).setHarvestLevel("pickaxe", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.METAL,
                6.0F, 15.0F, TcMaterial.IRON, SoundType.METAL).setHarvestLevel("pickaxe", 1));

        registerTofuInfo(new TofuInfo(TofuMaterial.GRILLED,
                1.0F, 50.0F, TcMaterial.TOFU, SoundType.SNOW).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.DRIED,
                1.0F, 2.0F, TcMaterial.SPONGE, SoundType.STONE).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.FRIEDPOUCH,
                1.0F, 8.0F, TcMaterial.TOFU, SoundType.SAND).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.FRIED,
                2.0F, 10.0F, TcMaterial.TOFU, SoundType.GROUND).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.EGG,
                0.2F, TcMaterial.TOFU, SoundType.SNOW).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.ANNIN,
                0.2F, TcMaterial.TOFU, SoundType.SNOW).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.SESAME,
                0.2F, TcMaterial.TOFU, SoundType.SNOW).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.ZUNDA,
                0.3F, TcMaterial.TOFU, SoundType.SNOW).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.STRAWBERRY,
                0.5F, TcMaterial.TOFU, SoundType.SNOW).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.MISO,
                0.5F, TcMaterial.TOFU, SoundType.SNOW).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.HELL,
                0.5F, TcMaterial.TOFU, SoundType.SNOW).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.GLOW,
                0.5F, TcMaterial.TOFU, SoundType.SNOW).setHarvestLevel("shovel", 0).setLightLevel(0.9375F));

        registerTofuInfo(new TofuInfo(TofuMaterial.DIAMOND,
                8.0F, 15.0F, TcMaterial.TOFU, SoundType.GLASS).setHarvestLevel("pickaxe", 1));

	}
    public static void registerTofuInfo(TofuInfo info)
    {
        TcBlocks.tofuInfoMap.put(info.tofuMaterial, info);
    }


    public void load(FMLPreInitializationEvent event) {

        TcBlocks.tofuTerrain = $("blockTofuTerrain", new BlockTofuTerrain(SoundType.SNOW))
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.4F)
        ;

        TcBlocks.tofuFarmland = $("tofuFarmland", new BlockTofuFarmland(SoundType.SNOW))
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.4F)
        ;

        TcBlocks.tofuKinu = $("blockTofuKinu", new BlockTofu(TofuMaterial.KINU, SoundType.SNOW))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setFragile()
                .setFreeze(5)
        ;

        TcBlocks.tofuMomen = $("blockTofuMomen", new BlockTofu(TofuMaterial.MOMEN, SoundType.SNOW))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setDrain(3)
        ;

        TcBlocks.tofuIshi = $("blockTofuIshi", new BlockTofuIshi(TofuMaterial.ISHI))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setDrain(8)
                .setScoopable(false)
        ;

        TcBlocks.tofuMetal = $("blockTofuMetal", new BlockTofu(TofuMaterial.METAL, SoundType.METAL))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setScoopable(false)
        ;

        TcBlocks.tofuGrilled = $("blockTofuGrilled", new BlockTofuGrilled(TofuMaterial.GRILLED))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuDried = $("blockTofuDried", new BlockTofu(TofuMaterial.DRIED, SoundType.STONE))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setScoopable(false)
        ;

        TcBlocks.tofuFriedPouch = $("blockTofuFriedPouch", new BlockTofu(TofuMaterial.FRIEDPOUCH, SoundType.SAND))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuFried = $("blockTofuFried", new BlockTofu(TofuMaterial.FRIED, SoundType.GROUND))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuEgg = $("blockTofuEgg", new BlockTofu(TofuMaterial.EGG, SoundType.SNOW))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuAnnin = $("blockTofuAnnin", new BlockTofuAnnin(TofuMaterial.ANNIN))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;


        TcBlocks.tofuSesame = $("blockTofuSesame", new BlockTofu(TofuMaterial.SESAME, SoundType.SNOW))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;


        TcBlocks.tofuZunda = $("blockTofuZunda", new BlockTofu(TofuMaterial.ZUNDA, SoundType.SNOW))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuStrawberry = $("blockTofuStrawberry", new BlockTofu(TofuMaterial.STRAWBERRY, SoundType.SNOW))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuMiso = $("blockTofuMiso", new BlockTofu(TofuMaterial.MISO, SoundType.SNOW))
        		.wrappedByItemTcBlock()
        		.registerBlock()
        ;

		TcBlocks.tofuHell = $("blockTofuHell", new BlockTofu(TofuMaterial.HELL, SoundType.SNOW))
		      .wrappedByItemTcBlock()
		      .registerBlock()
		;

        TcBlocks.tofuGlow = $("blockTofuGlow", new BlockTofu(TofuMaterial.GLOW, SoundType.SNOW))
        		.wrappedByItemTcBlock()
        		.registerBlock()
        ;

        TcBlocks.tofuDiamond = $("blockTofuDiamond", new BlockTofu(TofuMaterial.DIAMOND, SoundType.GLASS))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setScoopable(false)
        ;

        TcBlocks.tofuMinced = $("blockTofuMinced", new BlockTofuMinced())
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.4F)
        ;

	    if (event.getSide().isClient()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuTerrain), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuTerrain", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuFarmland), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuFarmland", "inventory"));

	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuKinu), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuKinu", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuMomen), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuMomen", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuIshi), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuIshi", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuMetal), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuMetal", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuGrilled), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuGrilled", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuDried), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuDried", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuFriedPouch), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuFriedPouch", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuFried), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuFried", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuEgg), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuEgg", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuAnnin), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuAnnin", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuSesame), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuSesame", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuZunda), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuZunda", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStrawberry), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuStrawberry", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuMiso), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuMiso", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuHell), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuHell", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuGlow), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuGlow", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuDiamond), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuDiamond", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuMinced), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockTofuMinced", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuMinced), ItemFoodSet.EnumSetFood.TOFU_MINCED.getId(), new ModelResourceLocation(TofuCraftCore.RES + "blockTofuMinced", "inventory"));
	    }

    }




}
