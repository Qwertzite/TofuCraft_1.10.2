package remiliaMarine.tofu.init.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.block.BlockChikuwaPlatform;
import remiliaMarine.tofu.block.BlockLeek;
import remiliaMarine.tofu.block.BlockNatto;
import remiliaMarine.tofu.block.BlockSoymilkStill;
import remiliaMarine.tofu.block.BlockTcFalling;
import remiliaMarine.tofu.block.BlockTcLeaves;
import remiliaMarine.tofu.block.BlockTcLog;
import remiliaMarine.tofu.block.BlockTcOre;
import remiliaMarine.tofu.block.BlockTcOreDiamond;
import remiliaMarine.tofu.block.BlockTcStationary;
import remiliaMarine.tofu.block.BlockTfMachineCase;
import remiliaMarine.tofu.block.BlockTofuDoor;
import remiliaMarine.tofu.block.BlockTofuFenceGate;
import remiliaMarine.tofu.block.BlockTofuLadder;
import remiliaMarine.tofu.block.BlockTofuSlabDouble1;
import remiliaMarine.tofu.block.BlockTofuSlabDouble2;
import remiliaMarine.tofu.block.BlockTofuSlabFacesDouble;
import remiliaMarine.tofu.block.BlockTofuSlabFacesSingle;
import remiliaMarine.tofu.block.BlockTofuSlabGlowDouble;
import remiliaMarine.tofu.block.BlockTofuSlabGlowSingle;
import remiliaMarine.tofu.block.BlockTofuSlabSingle1;
import remiliaMarine.tofu.block.BlockTofuSlabSingle2;
import remiliaMarine.tofu.block.BlockTofuStairs;
import remiliaMarine.tofu.block.BlockTofuTorch;
import remiliaMarine.tofu.block.BlockTofuTrapdoor;
import remiliaMarine.tofu.block.BlockTofuWall;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcFluids;
import remiliaMarine.tofu.item.ItemBlockTofuMaterial;
import remiliaMarine.tofu.item.TofuMaterial;
import remiliaMarine.tofu.itemblock.ItemBlockLeekDense;
import remiliaMarine.tofu.itemblock.ItemBlockTcLeaves;
import remiliaMarine.tofu.itemblock.ItemTcBlock;
import remiliaMarine.tofu.itemblock.ItemTcBlockVariable;
import remiliaMarine.tofu.material.TcMaterial;
import remiliaMarine.tofu.tileentity.TileEntityChikuwaPlatform;
import remiliaMarine.tofu.util.Utils;
import remiliaMarine.tofu.versionAdapter.block.TcBlock;

public class LoaderConstructionBlock {
    public void load(FMLPreInitializationEvent event) {
        /*
         * Ore
         */
        // Contained ore is set in TcItem.
    	TcBlocks.oreTofu = (BlockTcOre) new BlockTcOre(2, 5).setUnlocalizedName("tofucraft:blockOreTofu")
    			.setHarvestLevelTc("pickaxe", 1)
    			.setCreativeTab(TcCreativeTabs.CONSTRUCTION)
    			.setHardness(3.0F)
    			.setResistance(5.0F)
    			.setSoundType(SoundType.STONE);
    	ItemTcBlock itemblockOeTofu = new ItemTcBlock(TcBlocks.oreTofu);
    	GameRegistry.register(TcBlocks.oreTofu, new ResourceLocation(TofuCraftCore.MODID, "blockOreTofu"));
    	GameRegistry.register(itemblockOeTofu, new ResourceLocation(TofuCraftCore.MODID, "blockOreTofu"));

    	TcBlocks.oreTofuDiamond = (BlockTcOre) new BlockTcOreDiamond(3, 7).setUnlocalizedName("tofucraft:blockOreTofuDiamond")
    			.setHarvestLevelTc("shovel", 0)
                .setCreativeTab(TcCreativeTabs.CONSTRUCTION)
                .setHardness(1.0F)
                .setResistance(5.0F)
                .setSoundType(SoundType.SNOW);
    	ItemTcBlock itemblockOreTofuDiamond = new ItemTcBlock(TcBlocks.oreTofuDiamond);
    	GameRegistry.register(TcBlocks.oreTofuDiamond, new ResourceLocation(TofuCraftCore.MODID, "blockOreTofuDiamond"));
    	GameRegistry.register(itemblockOreTofuDiamond, new ResourceLocation(TofuCraftCore.MODID, "blockOreTofuDiamond"));

        /*
         * Fluid
         */
    	TcBlocks.soymilkStill = (BlockTcStationary) new BlockSoymilkStill(Material.WATER, new String[] {"tofucraft:soymilk", "tofucraft:soymilk_flow"})
    			.setUnlocalizedName("tofucraft:soymilk")
    			.setHardness(100.0F)
    			.setLightOpacity(8);
    	ItemBlock itemBlockSoymilkStill = new ItemBlock(TcBlocks.soymilkStill);
    	GameRegistry.register(TcBlocks.soymilkStill, new ResourceLocation(TofuCraftCore.MODID, "soymilk"));
    	GameRegistry.register(itemBlockSoymilkStill, new ResourceLocation(TofuCraftCore.MODID, "soymilk"));

    	TcBlocks.soymilkHellStill = (BlockTcStationary) new BlockTcStationary(TcFluids.SOYMILK_HELL, Material.WATER, new String[] {"tofucraft:soymilkHell", "tofucraft:soymilkHell_flow"})
    			.setUnlocalizedName("tofucraft:soymilkHell")
    			.setHardness(100.0F)
    			.setLightOpacity(8);
    	ItemBlock itemBlockSoymilkHellStill = new ItemBlock(TcBlocks.soymilkHellStill);
    	GameRegistry.register(TcBlocks.soymilkHellStill, new ResourceLocation(TofuCraftCore.MODID, "soymilkHell"));
    	GameRegistry.register(itemBlockSoymilkHellStill, new ResourceLocation(TofuCraftCore.MODID, "soymilkHell"));

    	TcBlocks.soySauceStill = (BlockTcStationary) new BlockTcStationary(TcFluids.SOY_SAUCE, Material.WATER, new String[]{"tofucraft:soySauce", "tofucraft:soySauce_flow"})
        		.setUnlocalizedName("tofucraft:soysauce")
        		.setHardness(100.0f)
        		.setLightOpacity(8);
    	ItemBlock itemblockSoySauceStill = new ItemBlock(TcBlocks.soySauceStill);
    	GameRegistry.register(TcBlocks.soySauceStill, new ResourceLocation(TofuCraftCore.MODID, "soysauce"));
    	GameRegistry.register(itemblockSoySauceStill, new ResourceLocation(TofuCraftCore.MODID, "soysauce"));

        /*
         * Trees
         */
    	TcBlocks.tcLeaves = (BlockTcLeaves) new BlockTcLeaves().setUnlocalizedName("tofucraft:leaves")
    			.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
    	if (event.getSide().isClient()) TcBlocks.tcLeaves.setGraphicsLevel(true);
    	ItemTcBlock itemblockTcLeaves = new ItemBlockTcLeaves(TcBlocks.tcLeaves);
    	GameRegistry.register(TcBlocks.tcLeaves, new ResourceLocation(TofuCraftCore.MODID, "tcLeaves"));
    	GameRegistry.register(itemblockTcLeaves, new ResourceLocation(TofuCraftCore.MODID, "tcLeaves"));

    	TcBlocks.tcLog = new BlockTcLog().setUnlocalizedName("tofucraft:log")
    			.setCreativeTab(TcCreativeTabs.CONSTRUCTION)
    			.setHardness(2.0f);
    	ItemBlock itemblockTcLog = new ItemTcBlockVariable(TcBlocks.tcLog);
    	GameRegistry.register(TcBlocks.tcLog, new ResourceLocation(TofuCraftCore.MODID, "tcWood"));
    	GameRegistry.register(itemblockTcLog, new ResourceLocation(TofuCraftCore.MODID, "tcWood"));

        /*
         * TF machine
         */
    	TcBlocks.tfMachineCase = new BlockTfMachineCase().setUnlocalizedName("tofucraft:tfMachineCase")
    			.setHarvestLevelTc("pickaxe", 0)
    			.setHardness(2.5F)
    			.setSoundType(SoundType.METAL)
    			.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
    	ItemBlock itemblockTfMachineCase = new ItemTcBlock(TcBlocks.tfMachineCase);
    	GameRegistry.register(TcBlocks.tfMachineCase, new ResourceLocation(TofuCraftCore.MODID, "tfMachineCase"));
    	GameRegistry.register(itemblockTfMachineCase, new ResourceLocation(TofuCraftCore.MODID, "tfMachineCase"));
 
        /*
         * Misc
         */
    	TcBlocks.leek = (BlockBush)new BlockLeek()
    			.setUnlocalizedName("tofucraft:blockLeek")
    			.setHardness(0.0F)
    			.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
    	ItemBlock itemBlockLeek = new ItemBlockLeekDense(TcBlocks.leek);
    	GameRegistry.register(TcBlocks.leek, new ResourceLocation(TofuCraftCore.MODID, "blockleek"));
    	GameRegistry.register(itemBlockLeek, new ResourceLocation(TofuCraftCore.MODID, "blockleek"));

    	TcBlocks.natto = new BlockNatto(SoundType.SNOW).setUnlocalizedName("tofucraft:blockNatto")
                .setHardness(0.3F)
                .setCreativeTab(TcCreativeTabs.CONSTRUCTION);
    	ItemBlock itemblockNatto = new ItemBlock(TcBlocks.natto);
    	GameRegistry.register(TcBlocks.natto, new ResourceLocation(TofuCraftCore.MODID, "blockNatto"));
    	GameRegistry.register(itemblockNatto, new ResourceLocation(TofuCraftCore.MODID, "blockNatto"));

    	TcBlocks.salt = new BlockTcFalling(TcMaterial.SAND)
    			.setSoundType(SoundType.SAND)
    			.setUnlocalizedName("tofucraft:blockSalt")
    			.setHardness(0.5f)
    			.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
    	ItemBlock itemblockSalt = new ItemTcBlock(TcBlocks.salt);
    	GameRegistry.register(TcBlocks.salt, new ResourceLocation(TofuCraftCore.MODID, "blockSalt"));
    	GameRegistry.register(itemblockSalt, new ResourceLocation(TofuCraftCore.MODID, "blockSalt"));

    	TcBlocks.advTofuGem = new TcBlock(Material.IRON)
    			.setSoundType(SoundType.METAL)
    			.setHarvestLevelTc("pickaxe", 0)
    			.setUnlocalizedName("tofucraft:blockAdvTofuGem")
    			.setLightLevel(0.625f)
    			.setHardness(5.0f)
    			.setResistance(10.0f)
    			.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
    	ItemBlock itemblockAdvTofuGem = new ItemTcBlock(TcBlocks.advTofuGem);
    	GameRegistry.register(TcBlocks.advTofuGem, new ResourceLocation(TofuCraftCore.MODID, "blockAdvTofuGem"));
    	GameRegistry.register(itemblockAdvTofuGem, new ResourceLocation(TofuCraftCore.MODID, "blockAdvTofuGem"));

    	TcBlocks.chikuwaPlatformTofu = new BlockChikuwaPlatform("tofu", SoundType.SNOW)
    			.setUnlocalizedName("tofucraft:chikuwaPlatformTofu")
    			.setHardness(0.6f)
    			.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
    	ItemTcBlock itemblockChikuwaPlatFormTofu = new ItemTcBlock(TcBlocks.chikuwaPlatformTofu);
    	GameRegistry.register(TcBlocks.chikuwaPlatformTofu, new ResourceLocation(TofuCraftCore.MODID, "chikuwaPlatformTofu"));
    	GameRegistry.register(itemblockChikuwaPlatFormTofu, new ResourceLocation(TofuCraftCore.MODID, "chikuwaPlatformtofu"));
    	GameRegistry.registerTileEntity(TileEntityChikuwaPlatform.class, "ChikuwaPlatform");

    	TcBlocks.chikuwaPlatformPlain = new BlockChikuwaPlatform("plain", SoundType.SNOW)
    			.setUnlocalizedName("tofucraft:chikuwaPlatformPlain")
    			.setHardness(0.6f)
    			.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
    	ItemTcBlock itemblockChikuwaPlatformPlain = new ItemTcBlock(TcBlocks.chikuwaPlatformPlain);
        GameRegistry.register(TcBlocks.chikuwaPlatformPlain,  new ResourceLocation(TofuCraftCore.MODID, "chikuwaPlatformPlain"));
        GameRegistry.register(itemblockChikuwaPlatformPlain,  new ResourceLocation(TofuCraftCore.MODID, "chikuwaPlatformPlain"));

        /*
         * Stairs
         */
        TcBlocks.tofuStairsKinu = new BlockTofuStairs(TcBlocks.tofuKinu.getDefaultState())
        		.setFragile()
        		.setUnlocalizedName("tofucraft:stairsTofuKinu");
        TcBlocks.tofuStairsKinu.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuStairsKinu = new ItemTcBlock(TcBlocks.tofuStairsKinu);
        GameRegistry.register(TcBlocks.tofuStairsKinu, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsKinu"));
        GameRegistry.register(itemblockTofuStairsKinu, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsKinu"));

        TcBlocks.tofuStairsMomen = new BlockTofuStairs(TcBlocks.tofuMomen.getDefaultState())
        		.setUnlocalizedName("tofucraft:stairsTofuMomen");
        TcBlocks.tofuStairsMomen.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuStairsMomen = new ItemTcBlock(TcBlocks.tofuStairsMomen);
        GameRegistry.register(TcBlocks.tofuStairsMomen, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsMomen"));
        GameRegistry.register(itemblockTofuStairsMomen, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsMomen"));

        TcBlocks.tofuStairsIshi = new BlockTofuStairs(TcBlocks.tofuIshi.getDefaultState())
        		.setUnlocalizedName("tofucraft:stairsTofuIshi");
        TcBlocks.tofuStairsIshi.setHarvestLevel("pickaxe", 0);
        ItemBlock itemblockTofuStairsIshi = new ItemTcBlock(TcBlocks.tofuStairsIshi);
        GameRegistry.register(TcBlocks.tofuStairsIshi, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsIshi"));
        GameRegistry.register(itemblockTofuStairsIshi, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsIshi"));

        TcBlocks.tofuStairsMetal = new BlockTofuStairs(TcBlocks.tofuMetal.getDefaultState())
        		.setUnlocalizedName("tofucraft:stairsTofuMetal");
        TcBlocks.tofuStairsMetal.setHarvestLevel("pickaxe", 1);
        ItemBlock itemblockTofuStairsMetal = new ItemTcBlock(TcBlocks.tofuStairsMetal);
        GameRegistry.register(TcBlocks.tofuStairsMetal, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsMetal"));
        GameRegistry.register(itemblockTofuStairsMetal, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsMetal"));

        TcBlocks.tofuStairsGrilled = new BlockTofuStairs(TcBlocks.tofuGrilled.getDefaultState())
        		.setUnlocalizedName("tofucraft:stairsTofuGrilled");
        TcBlocks.tofuStairsGrilled.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuStairsGrilled = new ItemTcBlock(TcBlocks.tofuStairsGrilled);
        GameRegistry.register(TcBlocks.tofuStairsGrilled, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsGrilled"));
        GameRegistry.register(itemblockTofuStairsGrilled, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsGrilled"));

        TcBlocks.tofuStairsDried = new BlockTofuStairs(TcBlocks.tofuDried.getDefaultState())
        		.setUnlocalizedName("tofucraft:stairsTofuDried");
        TcBlocks.tofuStairsDried.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuStairsDried = new ItemTcBlock(TcBlocks.tofuStairsDried);
        GameRegistry.register(TcBlocks.tofuStairsDried, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsDried"));
        GameRegistry.register(itemblockTofuStairsDried, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsDried"));

        TcBlocks.tofuStairsFriedPouch = new BlockTofuStairs(TcBlocks.tofuFriedPouch.getDefaultState())
        		.setUnlocalizedName("tofucraft:tofuStairsFriedPouch");
        TcBlocks.tofuStairsFriedPouch.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuStairsFriedPouch = new ItemTcBlock(TcBlocks.tofuStairsFriedPouch);
        GameRegistry.register(TcBlocks.tofuStairsFriedPouch, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsFriedPouch"));
        GameRegistry.register(itemblockTofuStairsFriedPouch, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsFriedPouch"));

        TcBlocks.tofuStairsFried = new BlockTofuStairs(TcBlocks.tofuFried.getDefaultState())
        		.setUnlocalizedName("tofucraft:tofuStairsFried");
        TcBlocks.tofuStairsFried.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuStairsFried = new ItemTcBlock(TcBlocks.tofuStairsFried);
        GameRegistry.register(TcBlocks.tofuStairsFried, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsFried"));
        GameRegistry.register(itemblockTofuStairsFried, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsFried"));

        TcBlocks.tofuStairsEgg = new BlockTofuStairs(TcBlocks.tofuEgg.getDefaultState())
        		.setUnlocalizedName("tofucraft:tofuStairsEgg");
        TcBlocks.tofuStairsEgg.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuStairsEgg = new ItemTcBlock(TcBlocks.tofuStairsEgg);
        GameRegistry.register(TcBlocks.tofuStairsEgg, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsEgg"));
        GameRegistry.register(itemblockTofuStairsEgg, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsEgg"));

        TcBlocks.tofuStairsAnnin = new BlockTofuStairs(TcBlocks.tofuAnnin.getDefaultState())
        		.setUnlocalizedName("tofucraft:tofuStairsAnnin");
        TcBlocks.tofuStairsAnnin.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuStairsAnnin = new ItemTcBlock(TcBlocks.tofuStairsAnnin);
        GameRegistry.register(TcBlocks.tofuStairsAnnin, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsAnnin"));
        GameRegistry.register(itemblockTofuStairsAnnin, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsAnnin"));

        TcBlocks.tofuStairsSesame = new BlockTofuStairs(TcBlocks.tofuSesame.getDefaultState())
        		.setUnlocalizedName("tofucraft:tofuStairsSesame");
        TcBlocks.tofuStairsSesame.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuStairsSesame = new ItemTcBlock(TcBlocks.tofuStairsSesame);
        GameRegistry.register(TcBlocks.tofuStairsSesame, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsSesame"));
        GameRegistry.register(itemblockTofuStairsSesame, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsSesame"));

        TcBlocks.tofuStairsZunda = new BlockTofuStairs(TcBlocks.tofuZunda.getDefaultState())
        		.setUnlocalizedName("tofucraft:tofuStairsZunda");
        TcBlocks.tofuStairsZunda.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuStairsZunda = new ItemTcBlock(TcBlocks.tofuStairsZunda);
        GameRegistry.register(TcBlocks.tofuStairsZunda, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsZunda"));
        GameRegistry.register(itemblockTofuStairsZunda, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsZunda"));

        TcBlocks.tofuStairsStrawberry = new BlockTofuStairs(TcBlocks.tofuStrawberry.getDefaultState())
        		.setUnlocalizedName("tofucraft:tofuStairsStrawberry");
        TcBlocks.tofuStairsStrawberry.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuStairsStrawberry = new ItemTcBlock(TcBlocks.tofuStairsStrawberry);
        GameRegistry.register(TcBlocks.tofuStairsStrawberry, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsStrawberry"));
        GameRegistry.register(itemblockTofuStairsStrawberry, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsStrawberry"));

        TcBlocks.tofuStairsHell = new BlockTofuStairs(TcBlocks.tofuHell.getDefaultState())
        		.setUnlocalizedName("tofucraft:tofuStairsHell");
        TcBlocks.tofuStairsHell.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuStairsHell = new ItemTcBlock(TcBlocks.tofuStairsHell);
        GameRegistry.register(TcBlocks.tofuStairsHell, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsHell"));
        GameRegistry.register(itemblockTofuStairsHell, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsHell"));

        TcBlocks.tofuStairsMiso = new BlockTofuStairs(TcBlocks.tofuMiso.getDefaultState())
        		.setUnlocalizedName("tofucraft:tofuStairsMiso");
        TcBlocks.tofuStairsMiso.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuStairsMiso = new ItemTcBlock(TcBlocks.tofuStairsMiso);
        GameRegistry.register(TcBlocks.tofuStairsMiso, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsMiso"));
        GameRegistry.register(itemblockTofuStairsMiso, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsMiso"));

        TcBlocks.tofuStairsGlow = new BlockTofuStairs(TcBlocks.tofuGlow.getDefaultState())
        		.setUnlocalizedName("tofucraft:tofuStairsGlow")
                .setLightLevel(1.0F);
        TcBlocks.tofuStairsGlow.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuStairsGlow = new ItemTcBlock(TcBlocks.tofuStairsGlow);
        GameRegistry.register(TcBlocks.tofuStairsGlow, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsGlow"));
        GameRegistry.register(itemblockTofuStairsGlow, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsGlow"));

        TcBlocks.tofuStairsDiamond = new BlockTofuStairs(TcBlocks.tofuDiamond.getDefaultState())
        		.setUnlocalizedName("tofucraft:tofuStairsDiamond");
        TcBlocks.tofuStairsDiamond.setHarvestLevel("pickaxe", 1);
        ItemBlock itemblockTofuStairsDiamond = new ItemTcBlock(TcBlocks.tofuStairsDiamond);
        GameRegistry.register(TcBlocks.tofuStairsDiamond, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsDiamond"));
        GameRegistry.register(itemblockTofuStairsDiamond, new ResourceLocation(TofuCraftCore.MODID, "tofuStairsDiamond"));

        /*
         * Slabs
         */
        TcBlocks.tofuSingleSlab1 = new BlockTofuSlabSingle1();
        TcBlocks.tofuSingleSlab1.setUnlocalizedName("tofucraft:tofuSlab")
        		.setHardness(1.0f)
        		.setHarvestLevel("shovel", 0);
        TcBlocks.tofuDoubleSlab1 = new BlockTofuSlabDouble1();
        TcBlocks.tofuDoubleSlab1.setUnlocalizedName("tofucraft:tofuSlab")
				.setHardness(1.0f)
				.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuSlab1 = new ItemSlab(TcBlocks.tofuSingleSlab1, TcBlocks.tofuSingleSlab1, TcBlocks.tofuDoubleSlab1);
        GameRegistry.register(TcBlocks.tofuSingleSlab1, new ResourceLocation(TofuCraftCore.MODID, "tofuSlabSingle1"));
        GameRegistry.register(TcBlocks.tofuDoubleSlab1, new ResourceLocation(TofuCraftCore.MODID, "tofuSlabDouble1"));
        GameRegistry.register(itemblockTofuSlab1, new ResourceLocation(TofuCraftCore.MODID, "tofuSlabSingle1"));

        TcBlocks.tofuSingleSlab2 = new BlockTofuSlabSingle2();
        TcBlocks.tofuSingleSlab2.setUnlocalizedName("tofucraft:tofuSlab")
        		.setHardness(1.0f)
        		.setHarvestLevel("shovel", 0);
        TcBlocks.tofuDoubleSlab2 = new BlockTofuSlabDouble2();
        TcBlocks.tofuDoubleSlab2.setUnlocalizedName("tofucraft:tofuSlab")
				.setHardness(1.0f)
				.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuSlab2 = new ItemSlab(TcBlocks.tofuSingleSlab2, TcBlocks.tofuSingleSlab2, TcBlocks.tofuDoubleSlab2);
        GameRegistry.register(TcBlocks.tofuSingleSlab2, new ResourceLocation(TofuCraftCore.MODID, "tofuSlabSingle2"));
        GameRegistry.register(TcBlocks.tofuDoubleSlab2, new ResourceLocation(TofuCraftCore.MODID, "tofuSlabDouble2"));
        GameRegistry.register(itemblockTofuSlab2, new ResourceLocation(TofuCraftCore.MODID, "tofuSlabSingle2"));

        TcBlocks.tofuSingleSlabFaces = new BlockTofuSlabFacesSingle();
        TcBlocks.tofuSingleSlabFaces.setUnlocalizedName("tofucraft:tofuSlab")
        		.setHardness(1.0f)
        		.setResistance(50.0F)
        		.setHarvestLevel("shovel", 0);
        TcBlocks.tofuDoubleSlabFaces = new BlockTofuSlabFacesDouble();
        TcBlocks.tofuDoubleSlabFaces.setUnlocalizedName("tofucraft:tofuSlab")
				.setHardness(1.0f)
        		.setResistance(50.0F)
				.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuSlabFaces = new ItemSlab(TcBlocks.tofuSingleSlabFaces, TcBlocks.tofuSingleSlabFaces, TcBlocks.tofuDoubleSlabFaces);
        GameRegistry.register(TcBlocks.tofuSingleSlabFaces, new ResourceLocation(TofuCraftCore.MODID, "tofuSlabFacesSingle"));
        GameRegistry.register(TcBlocks.tofuDoubleSlabFaces, new ResourceLocation(TofuCraftCore.MODID, "tofuSlabFacesDouble"));
        GameRegistry.register(itemblockTofuSlabFaces, new ResourceLocation(TofuCraftCore.MODID, "tofuSlabFacesSingle"));

        TcBlocks.tofuSingleSlabGlow = new BlockTofuSlabGlowSingle();
        TcBlocks.tofuSingleSlabGlow.setUnlocalizedName("tofucraft:tofuSlab")
        		.setHardness(1.0f)
                .setLightLevel(0.9375F)
        		.setHarvestLevel("shovel", 0);
        TcBlocks.tofuDoubleSlabGlow = new BlockTofuSlabGlowDouble();
        TcBlocks.tofuDoubleSlabGlow.setUnlocalizedName("tofucraft:tofuSlab")
				.setHardness(1.0f)
                .setLightLevel(0.9375F)
				.setHarvestLevel("shovel", 0);
        ItemBlock itemblockTofuSlabGlow = new ItemSlab(TcBlocks.tofuSingleSlabGlow, TcBlocks.tofuSingleSlabGlow, TcBlocks.tofuDoubleSlabGlow);
        GameRegistry.register(TcBlocks.tofuSingleSlabGlow, new ResourceLocation(TofuCraftCore.MODID, "tofuSlabGlowSingle"));
        GameRegistry.register(TcBlocks.tofuDoubleSlabGlow, new ResourceLocation(TofuCraftCore.MODID, "tofuSlabGlowDouble"));
        GameRegistry.register(itemblockTofuSlabGlow, new ResourceLocation(TofuCraftCore.MODID, "tofuSlabGlowSingle"));

        /*
         * Tofu Doors
         */
        for (TofuMaterial material : TofuMaterial.values())
        {
            String blockName = "tofuDoor" + Utils.capitalize(material.getName());
            Block tofuDoor = new BlockTofuDoor(material).setUnlocalizedName("tofucraft:" + blockName);
            GameRegistry.register(tofuDoor, new ResourceLocation(TofuCraftCore.MODID, blockName));

            TcBlocks.tofuDoors.put(material, tofuDoor);
        }

        /*
         * Walls
         */
        for (TofuMaterial material : TofuMaterial.values())
        {
            String blockName = "tofuWall" + Utils.capitalize(material.getName());
            Block tofuWall = new BlockTofuWall(material).setUnlocalizedName(TofuCraftCore.RES + "tofuWall")
            		.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
            ItemBlock itemblockTofuWall = new ItemBlockTofuMaterial(tofuWall);
            GameRegistry.register(tofuWall, new ResourceLocation(TofuCraftCore.MODID, blockName));
            GameRegistry.register(itemblockTofuWall, new ResourceLocation(TofuCraftCore.MODID, blockName));

            TcBlocks.tofuWalls.put(material, tofuWall);
        }

        /*
         * Torches
         */
        for (TofuMaterial material : TofuMaterial.values())
        {
            if (material != TofuMaterial.GLOW)
            {
                String blockName = "tofuTorch" + Utils.capitalize(material.getName());
                Block tofuTorch = new BlockTofuTorch(material).setUnlocalizedName("tofucraft:tofuTorch")
                		.setLightLevel(0.9375F)
                		.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
                ItemBlock itemblockTofuTorch = new ItemBlockTofuMaterial(tofuTorch);
                GameRegistry.register(tofuTorch, new ResourceLocation(TofuCraftCore.MODID, blockName));
                GameRegistry.register(itemblockTofuTorch, new ResourceLocation(TofuCraftCore.MODID, blockName));

                TcBlocks.tofuTorches.put(material, tofuTorch);
            }
        }

        /*
         * Fence Gates
         */
        for (TofuMaterial material : TofuMaterial.values())
        {
            String blockName = "tofuFenceGate" + Utils.capitalize(material.getName());
            Block tofuFence = new BlockTofuFenceGate(material)
            		.setUnlocalizedName("tofucraft:tofuFenceGate")
            		.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
            ItemBlock itemblockTofuFence = new ItemBlockTofuMaterial(tofuFence);
            GameRegistry.register(tofuFence, new ResourceLocation(TofuCraftCore.MODID, blockName));
            GameRegistry.register(itemblockTofuFence, new ResourceLocation(TofuCraftCore.MODID, blockName));

            TcBlocks.tofuFenceGates.put(material, tofuFence);
        }

        /*
         * Trapdoors
         */
        for (TofuMaterial material : TofuMaterial.values())
        {
            String blockName = "tofuTrapdoor" + Utils.capitalize(material.getName());
            Block tofuTrapdoor = new BlockTofuTrapdoor(material).setUnlocalizedName("tofucraft:tofuTrapdoor")
            		.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
            ItemBlock itemblockTofuTrapdoor = new ItemBlockTofuMaterial(tofuTrapdoor);
            GameRegistry.register(tofuTrapdoor, new ResourceLocation(TofuCraftCore.MODID, blockName));
            GameRegistry.register(itemblockTofuTrapdoor, new ResourceLocation(TofuCraftCore.MODID, blockName));

            TcBlocks.tofuTrapdoors.put(material, tofuTrapdoor);
        }

        /*
         * Ladders
         */
        for (TofuMaterial material : TofuMaterial.values())
        {
            String blockName = "tofuLadder" + Utils.capitalize(material.getName());
            Block tofuLadder = new BlockTofuLadder(material)
            		.setUnlocalizedName("tofucraft:tofuLadder")
            		.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
            ItemBlock itemblockTofuLadder = new ItemBlockTofuMaterial(tofuLadder);
            GameRegistry.register(tofuLadder, new ResourceLocation(TofuCraftCore.MODID, blockName));
            GameRegistry.register(itemblockTofuLadder, new ResourceLocation(TofuCraftCore.MODID, blockName));

            TcBlocks.tofuLadders.put(material, tofuLadder);
        }


	    if (event.getSide().isClient()) {

	    	/* Ore */
	        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.oreTofu), 0, new ModelResourceLocation(TofuCraftCore.RES + "oreTofu", "inventory"));
	        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.oreTofuDiamond), 0, new ModelResourceLocation(TofuCraftCore.RES + "oreTofuDiamond", "inventory"));

	    	/* fluid */
			//ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.soymilkStill), 0, new ModelResourceLocation("tofucraft:soymilk", "inventory"));
			//ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.soymilkHellStill), 0, new ModelResourceLocation("tofucraft:soymilkHell", "inventory"));
			//ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.soySauceStill), 0, new ModelResourceLocation("tofucraft:soySauce", "inventory"));

	        /* Trees */
	        ModelBakery.registerItemVariants(Item.getItemFromBlock(TcBlocks.tcLeaves),
	        		new ModelResourceLocation("tofucraft:leaves_apricot", "inventory"),
	        		new ModelResourceLocation("tofucraft:leaves_apricot_f", "inventory"),
	        		new ModelResourceLocation("tofucraft:leaves_tofu", "inventory"));
	        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tcLeaves), 0, new ModelResourceLocation(TofuCraftCore.RES + "leaves_apricot", "inventory"));
	        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tcLeaves), 1, new ModelResourceLocation(TofuCraftCore.RES + "leaves_apricot_f", "inventory"));
	        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tcLeaves), 2, new ModelResourceLocation(TofuCraftCore.RES + "leaves_tofu", "inventory"));
	        ModelBakery.registerItemVariants(Item.getItemFromBlock(TcBlocks.tcLog),
	        		new ModelResourceLocation("tofucraft:log", "inventory"));
	        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tcLog), 0, new ModelResourceLocation(TofuCraftCore.RES + "log", "inventory"));

	        /* TF machine */
	        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfMachineCase), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfMachineCase", "inventory"));

	        /* Misc */
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.leek), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockleek", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.natto), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockNatto", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.salt), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockSalt", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.advTofuGem), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockAdvTofuGem", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.chikuwaPlatformTofu), 0, new ModelResourceLocation(TofuCraftCore.RES + "chikuwaPlatformTofu", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.chikuwaPlatformPlain), 0, new ModelResourceLocation(TofuCraftCore.RES + "chikuwaPlatformPlain", "inventory"));

	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsKinu), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsKinu", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsMomen), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsMomen", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsIshi), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsIshi", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsMetal), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsMetal", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsGrilled), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsGrilled", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsDried), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsDried", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsFriedPouch), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsFriedPouch", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsFried), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsFried", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsEgg), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsEgg", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsAnnin), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsAnnin", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsSesame), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsSesame", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsZunda), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsZunda", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsStrawberry), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsStrawberry", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsHell), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsHell", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsMiso), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsMiso", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsGlow), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsGlow", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuStairsDiamond), 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuStairsDiamond", "inventory"));

	    	{
	    		ModelResourceLocation[] res = ((BlockTofuSlabSingle1)TcBlocks.tofuSingleSlab1).getResourceLocation();
	    		Item item = Item.getItemFromBlock(TcBlocks.tofuSingleSlab1);
	    		ModelBakery.registerItemVariants(item, res);
	    		for (int i = 0; i < res.length; i++) {
	    			ModelLoader.setCustomModelResourceLocation(item, i, res[i]);
	    		}
	    	}
	    	{
	    		ModelResourceLocation[] res = ((BlockTofuSlabSingle2)TcBlocks.tofuSingleSlab2).getResourceLocation();
	    		Item item = Item.getItemFromBlock(TcBlocks.tofuSingleSlab2);
	    		ModelBakery.registerItemVariants(item, res);
	    		for (int i = 0; i < res.length; i++) {
	    			ModelLoader.setCustomModelResourceLocation(item, i, res[i]);
	    		}
	    	}
	    	{
	    		ModelResourceLocation[] res = ((BlockTofuSlabFacesSingle)TcBlocks.tofuSingleSlabFaces).getResourceLocation();
	    		Item item = Item.getItemFromBlock(TcBlocks.tofuSingleSlabFaces);
	    		ModelBakery.registerItemVariants(item, res);
	    		for (int i = 0; i < res.length; i++) {
	    			ModelLoader.setCustomModelResourceLocation(item, i, res[i]);
	    		}
	    	}
	    	{
	    		ModelResourceLocation[] res = ((BlockTofuSlabGlowSingle)TcBlocks.tofuSingleSlabGlow).getResourceLocation();
	    		Item item = Item.getItemFromBlock(TcBlocks.tofuSingleSlabGlow);
	    		ModelBakery.registerItemVariants(item, res);
	    		for (int i = 0; i < res.length; i++) {
	    			ModelLoader.setCustomModelResourceLocation(item, i, res[i]);
	    		}
	    	}

	    	for (TofuMaterial material :TofuMaterial.values()) {
	            String wallName = "tofuWall" + Utils.capitalize(material.getName());
	    		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuWalls.get(material)), 0, new ModelResourceLocation(TofuCraftCore.RES + wallName, "inventory"));
	    	}

	    	for (TofuMaterial material : TofuMaterial.values()) {
	    		if(material != TofuMaterial.GLOW) {
	    			String torchName = "tofuTorch" + Utils.capitalize(material.getName());
	    			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuTorches.get(material)), 0, new ModelResourceLocation(TofuCraftCore.RES + torchName, "inventory"));
	    		}
	    	}

	    	for (TofuMaterial material :TofuMaterial.values()) {
	            String blockName = "tofuFenceGate" + Utils.capitalize(material.getName());
	    		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuFenceGates.get(material)), 0, new ModelResourceLocation(TofuCraftCore.RES + blockName, "inventory"));
	    	}

	    	for (TofuMaterial material :TofuMaterial.values()) {
	            String blockName = "tofuTrapdoor" + Utils.capitalize(material.getName());
	    		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuTrapdoors.get(material)), 0, new ModelResourceLocation(TofuCraftCore.RES + blockName, "inventory"));
	    	}

	    	for (TofuMaterial material :TofuMaterial.values()) {
	            String blockName = "tofuLadder" + Utils.capitalize(material.getName());
	    		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tofuLadders.get(material)), 0, new ModelResourceLocation(TofuCraftCore.RES + blockName, "inventory"));
	    	}
	    }


    }

}
