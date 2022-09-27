package remiliaMarine.tofu.init.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.block.BlockAdvTofuGemBarrel;
import remiliaMarine.tofu.block.BlockGlowtofuBarrel;
import remiliaMarine.tofu.block.BlockMisoBarrel;
import remiliaMarine.tofu.block.BlockMisoTofuBarrel;
import remiliaMarine.tofu.block.BlockMorijio;
import remiliaMarine.tofu.block.BlockNattoBed;
import remiliaMarine.tofu.block.BlockSaltFurnace;
import remiliaMarine.tofu.block.BlockSaltPan;
import remiliaMarine.tofu.block.BlockSesame;
import remiliaMarine.tofu.block.BlockSoybean;
import remiliaMarine.tofu.block.BlockSoybeanHell;
import remiliaMarine.tofu.block.BlockSprouts;
import remiliaMarine.tofu.block.BlockTcSapling;
import remiliaMarine.tofu.block.BlockTfAntenna;
import remiliaMarine.tofu.block.BlockTfCollector;
import remiliaMarine.tofu.block.BlockTfCondenser;
import remiliaMarine.tofu.block.BlockTfOven;
import remiliaMarine.tofu.block.BlockTfReformer;
import remiliaMarine.tofu.block.BlockTfSaturator;
import remiliaMarine.tofu.block.BlockTfStorage;
import remiliaMarine.tofu.block.BlockTofuCake;
import remiliaMarine.tofu.block.BlockTofuPortal;
import remiliaMarine.tofu.block.BlockYuba;
import remiliaMarine.tofu.block.render.RenderSaltPan;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.itemblock.ItemTcBlock;
import remiliaMarine.tofu.itemblock.ItemTcBlockVariable;
import remiliaMarine.tofu.tileentity.TileEntityMorijio;
import remiliaMarine.tofu.tileentity.TileEntityMorijioRenderer;
import remiliaMarine.tofu.tileentity.TileEntitySaltFurnace;
import remiliaMarine.tofu.tileentity.TileEntitySaltPan;
import remiliaMarine.tofu.tileentity.TileEntityTfAntenna;
import remiliaMarine.tofu.tileentity.TileEntityTfAntennaRenderer;
import remiliaMarine.tofu.tileentity.TileEntityTfCollector;
import remiliaMarine.tofu.tileentity.TileEntityTfCondenser;
import remiliaMarine.tofu.tileentity.TileEntityTfOven;
import remiliaMarine.tofu.tileentity.TileEntityTfReformer;
import remiliaMarine.tofu.tileentity.TileEntityTfSaturator;
import remiliaMarine.tofu.tileentity.TileEntityTfStorage;

public class LoaderDecorationBlock {
    public void load(FMLPreInitializationEvent event)
    {
        /*
         * Plant
         */
    	TcBlocks.soybean = new BlockSoybean().setUnlocalizedName("tofucraft:blockSoybeans");
    	ItemBlock itemBlockSoybean = new ItemBlock(TcBlocks.soybean);
    	GameRegistry.register(TcBlocks.soybean, new ResourceLocation(TofuCraftCore.MODID, "blocksoybeans"));
    	GameRegistry.register(itemBlockSoybean, new ResourceLocation(TofuCraftCore.MODID, "blocksoybeans"));

    	TcBlocks.soybeanHell = new BlockSoybeanHell().setUnlocalizedName("tofucraft:blockSoybeansHell");
    	ItemBlock itemblockSoybeanHell = new ItemBlock(TcBlocks.soybeanHell);
    	GameRegistry.register(TcBlocks.soybeanHell, new ResourceLocation(TofuCraftCore.MODID, "blocksoybeansHell"));
    	GameRegistry.register(itemblockSoybeanHell, new ResourceLocation(TofuCraftCore.MODID, "blocksoybeansHell"));

    	TcBlocks.sprouts = new BlockSprouts().setUnlocalizedName("tofucraft:blockSprouts");
    	ItemBlock itemBlockSprouts = new ItemBlock(TcBlocks.sprouts);
    	GameRegistry.register(TcBlocks.sprouts, new ResourceLocation(TofuCraftCore.MODID, "blocksprouts"));
    	GameRegistry.register(itemBlockSprouts, new ResourceLocation(TofuCraftCore.MODID, "blocksprouts"));

    	TcBlocks.sesame = new BlockSesame().setUnlocalizedName("tofucraft:blockSesame");
    	ItemBlock itemblockSesame = new ItemBlock(TcBlocks.sesame);
    	GameRegistry.register(TcBlocks.sesame, new ResourceLocation(TofuCraftCore.MODID, "blockSesame"));
    	GameRegistry.register(itemblockSesame, new ResourceLocation(TofuCraftCore.MODID, "blockSesame"));

    	TcBlocks.tcSapling = new BlockTcSapling(0).setUnlocalizedName("tofucraft:sapling")
    			.setCreativeTab(TcCreativeTabs.DECORATIONS)
    			.setHardness(0.0f);
    	ItemBlock itemBlockTcSapling = new ItemTcBlockVariable(TcBlocks.tcSapling);
    	GameRegistry.register(TcBlocks.tcSapling, new ResourceLocation(TofuCraftCore.MODID, "tcSapling"));
    	GameRegistry.register(itemBlockTcSapling, new ResourceLocation(TofuCraftCore.MODID, "tcSapling"));

        /*
         * Salt Furnace
         */
    	TcBlocks.saltFurnaceIdle = new BlockSaltFurnace(false).setUnlocalizedName("tofucraft:saltFurnace")
    			.setHardness(3.5F)
    			.setCreativeTab(TcCreativeTabs.DECORATIONS);
    	ItemTcBlock itemBlockSaltFurnaceIdle = new ItemTcBlock(TcBlocks.saltFurnaceIdle);
    	GameRegistry.register(TcBlocks.saltFurnaceIdle, new ResourceLocation(TofuCraftCore.MODID, "saltFurnaceIdle"));
    	GameRegistry.register(itemBlockSaltFurnaceIdle, new ResourceLocation(TofuCraftCore.MODID, "saltFurnaceIdle"));

    	TcBlocks.saltFurnaceActive = new BlockSaltFurnace(true).setUnlocalizedName("tofucraft:saltFurnace")
    			.setHardness(3.5F)
    			.setLightLevel(0.875F);
    	ItemTcBlock itemBlockSaltFurnaceActive = new ItemTcBlock(TcBlocks.saltFurnaceActive);
    	GameRegistry.register(TcBlocks.saltFurnaceActive, new ResourceLocation(TofuCraftCore.MODID, "saltFurnaceActive"));
    	GameRegistry.register(itemBlockSaltFurnaceActive, new ResourceLocation(TofuCraftCore.MODID, "saltFurnaceActive"));
        GameRegistry.registerTileEntity(TileEntitySaltFurnace.class, "SaltFurnace");

        /*
         * Barrels
         */
        TcBlocks.barrelMiso = new BlockMisoBarrel(Material.WOOD)
                .setFermRate(3)
        		.setUnlocalizedName("tofucraft:barrelMiso")
                .setHardness(2.0F);
        TcBlocks.barrelMiso.setHarvestLevel("axe", 0);
    	GameRegistry.register(TcBlocks.barrelMiso, new ResourceLocation(TofuCraftCore.MODID, "blockBarrelMiso"));

    	TcBlocks.barrelMisoTofu = new BlockMisoTofuBarrel(Material.WOOD)
                .setFermRate(2)
                .setUnlocalizedName("tofucraft:barrelMisoTofu")
                .setHardness(2.0F);
        TcBlocks.barrelMisoTofu.setHarvestLevel("axe", 0);
    	GameRegistry.register(TcBlocks.barrelMisoTofu, new ResourceLocation(TofuCraftCore.MODID, "blockBarrelMisoTofu"));

    	TcBlocks.barrelGlowtofu = new BlockGlowtofuBarrel(Material.WOOD)
                .setFermRate(4)
    			.setUnlocalizedName("tofucraft:barrelGlowtofu")
                .setHardness(2.0F);
    	TcBlocks.barrelGlowtofu.setHarvestLevel("axe", 0);
    	GameRegistry.register(TcBlocks.barrelGlowtofu, new ResourceLocation(TofuCraftCore.MODID, "blockBarrelGlowtofu"));

    	TcBlocks.barrelAdvTofuGem = new BlockAdvTofuGemBarrel(Material.WOOD)
    			.setFermRate(4)
    			.setUnlocalizedName("tofucraft:barrelAdvTofuGem")
    			.setHardness(2.0f);
    	TcBlocks.barrelAdvTofuGem.setHarvestLevel("axe", 0);
    	GameRegistry.register(TcBlocks.barrelAdvTofuGem, new ResourceLocation(TofuCraftCore.MODID, "blockBarrelAdvTofuGem"));

        /*
         * Misc
         */
    	TcBlocks.tofuCake = new BlockTofuCake(SoundType.CLOTH)
    			.disableStats()
    			.setUnlocalizedName("tofucraft:tofuCake")
    			.setHardness(0.5f);
    	GameRegistry.register(TcBlocks.tofuCake, new ResourceLocation(TofuCraftCore.MODID, "blockTofuCake"));

        TcBlocks.tofuPortal = (BlockTofuPortal) new BlockTofuPortal().setUnlocalizedName("tofucraft:blockTofuPortal")
                .setHardness(-1.0F)
                .setLightLevel(0.75F);
        GameRegistry.register(TcBlocks.tofuPortal, new ResourceLocation(TofuCraftCore.MODID, "tofuPortal"));

        TcBlocks.morijio = new BlockMorijio(SoundType.CLOTH).setUnlocalizedName("tofucraft:morijio")
        		.setHardness(0.5F);
        GameRegistry.register(TcBlocks.morijio, new ResourceLocation(TofuCraftCore.MODID, "blockMorijio"));
        GameRegistry.registerTileEntity(TileEntityMorijio.class, "Morijio");

        TcBlocks.nattoBed = new BlockNattoBed(Material.GRASS, SoundType.PLANT)
        		.setFermRate(3)
        		.setUnlocalizedName("tofucraft:nattoBed")
        		.setHardness(0.8f)
        		.setCreativeTab(TcCreativeTabs.DECORATIONS);
        ItemBlock itemblockNattoBed = new ItemTcBlock(TcBlocks.nattoBed);
        GameRegistry.register(TcBlocks.nattoBed, new ResourceLocation(TofuCraftCore.MODID, "nattoBed"));
        GameRegistry.register(itemblockNattoBed, new ResourceLocation(TofuCraftCore.MODID, "nattoBed"));

        TcBlocks.yuba = new BlockYuba(SoundType.SNOW)
        		.setUnlocalizedName("tofucraft:blockYuba")
        		.setHardness(0.0f);
        ItemBlock itemblockYuba = new ItemBlock(TcBlocks.yuba);
        GameRegistry.register(TcBlocks.yuba, new ResourceLocation(TofuCraftCore.MODID, "blockYuba"));
        GameRegistry.register(itemblockYuba, new ResourceLocation(TofuCraftCore.MODID, "blockYuba"));

        TcBlocks.saltPan = new BlockSaltPan().setUnlocalizedName("tofucraft:blockSaltPan")
        		.setHardness(0.2f)
        		.setCreativeTab(TcCreativeTabs.DECORATIONS);
        ItemTcBlock itemblockSaltPan = new ItemTcBlock(TcBlocks.saltPan);
        GameRegistry.register(TcBlocks.saltPan, new ResourceLocation(TofuCraftCore.MODID, "blockSaltPan"));
        GameRegistry.register(itemblockSaltPan, new ResourceLocation(TofuCraftCore.MODID, "blockSaltPan"));

        /*
         * TF machine
         */
        TcBlocks.tfStorageIdle = new BlockTfStorage(false, SoundType.METAL).setUnlocalizedName("tofucraft:tfStorage")
        		.setHardness(2.5f)
        		.setCreativeTab(TcCreativeTabs.DECORATIONS);
        TcBlocks.tfStorageIdle.setHarvestLevel("pickaxe", 0);
        ItemBlock itemblockTfStorageIdle = new ItemTcBlock(TcBlocks.tfStorageIdle);
        GameRegistry.register(TcBlocks.tfStorageIdle, new ResourceLocation(TofuCraftCore.MODID, "tfStorageIdle"));
        GameRegistry.register(itemblockTfStorageIdle, new ResourceLocation(TofuCraftCore.MODID, "tfStorageIdle"));
        
        TcBlocks.tfStorageActive = new BlockTfStorage(true, SoundType.METAL).setUnlocalizedName("tofucraft:tfStorage")
        		.setHardness(2.5f)
        		.setLightLevel(0.875f);
        TcBlocks.tfStorageActive.setHarvestLevel("pickaxe", 0);
        ItemBlock itemblockTfStorageActive = new ItemTcBlock(TcBlocks.tfStorageActive);
        GameRegistry.register(TcBlocks.tfStorageActive, new ResourceLocation(TofuCraftCore.MODID, "tfStorageActive"));
        GameRegistry.register(itemblockTfStorageActive, new ResourceLocation(TofuCraftCore.MODID, "tfStorageActive"));
        GameRegistry.registerTileEntity(TileEntityTfStorage.class, "TfStorage");

        TcBlocks.tfAntennaMedium = new BlockTfAntenna(BlockTfAntenna.MEDIUMWAVE, SoundType.CLOTH)
        		.setUnlocalizedName("tofucraft:tfAntenna")
        		.setHardness(0.5f)
        		.setCreativeTab(TcCreativeTabs.DECORATIONS);
        ItemBlock itemblockTfAntennaMedium = new ItemTcBlock(TcBlocks.tfAntennaMedium);
        GameRegistry.register(TcBlocks.tfAntennaMedium, new ResourceLocation(TofuCraftCore.MODID, "tfAntenna"));
        GameRegistry.register(itemblockTfAntennaMedium, new ResourceLocation(TofuCraftCore.MODID, "tfAntenna"));
        GameRegistry.registerTileEntity(TileEntityTfAntenna.class, "TfAntenna");

        TcBlocks.tfAntennaUltra = new BlockTfAntenna(BlockTfAntenna.ULTRAWAVE, SoundType.CLOTH)
        		.setUnlocalizedName("tofucraft:tfAntennaU")
        		.setHardness(0.5f)
        		.setCreativeTab(TcCreativeTabs.DECORATIONS);
        ItemBlock itemblockTfAntennaUltra = new ItemTcBlock(TcBlocks.tfAntennaUltra);
        GameRegistry.register(TcBlocks.tfAntennaUltra, new ResourceLocation(TofuCraftCore.MODID, "tfAntennaU"));
        GameRegistry.register(itemblockTfAntennaUltra, new ResourceLocation(TofuCraftCore.MODID, "tfAntennaU"));

        // This is hidden now
        TcBlocks.tfAntennaSuper = new BlockTfAntenna(BlockTfAntenna.SUPERWAVE, SoundType.CLOTH)
        		.setUnlocalizedName("tofucraft:tfAntennaS")
        		.setHardness(0.5f);
        		//.setCreativeTab(TcCreativeTabs.DECORATIONS);
//        ItemBlock itemblockTfAntennaSuper = new ItemTcBlock(TcBlocks.tfAntennaSuper);
        GameRegistry.register(TcBlocks.tfAntennaSuper, new ResourceLocation(TofuCraftCore.MODID, "tfAntennaS"));
//        GameRegistry.register(itemblockTfAntennaSuper, new ResourceLocation(TofuCraftCore.MODID, "tfAntennaS"));
        
        TcBlocks.tfSaturatorIdle = new BlockTfSaturator(false, SoundType.METAL).setUnlocalizedName("tofucraft:tfSaturator")
        		.setHardness(2.5f)
        		.setCreativeTab(TcCreativeTabs.DECORATIONS);
        TcBlocks.tfSaturatorIdle.setHarvestLevel("pickaxe", 0);
        ItemBlock itemblockTfSaturatorIdle = new ItemTcBlock(TcBlocks.tfSaturatorIdle);
        GameRegistry.register(TcBlocks.tfSaturatorIdle, new ResourceLocation(TofuCraftCore.MODID, "tfSaturatorIdle"));
        GameRegistry.register(itemblockTfSaturatorIdle, new ResourceLocation(TofuCraftCore.MODID, "tfSaturatorIdle"));
        
        TcBlocks.tfSaturatorActive = new BlockTfSaturator(true, SoundType.METAL).setUnlocalizedName("tofucraft:tfSaturator")
        		.setHardness(2.5f)
                .setLightLevel(0.875F);
        TcBlocks.tfSaturatorActive.setHarvestLevel("pickaxe", 0);
        ItemBlock itemblockTfSaturatorActive = new ItemTcBlock(TcBlocks.tfSaturatorActive);
        GameRegistry.register(TcBlocks.tfSaturatorActive, new ResourceLocation(TofuCraftCore.MODID, "tfSaturatorActive"));
        GameRegistry.register(itemblockTfSaturatorActive, new ResourceLocation(TofuCraftCore.MODID, "tfSaturatorActive"));
        GameRegistry.registerTileEntity(TileEntityTfSaturator.class, "TfSaturator");
        
        TcBlocks.tfCollector = new BlockTfCollector(SoundType.METAL).setUnlocalizedName("tofucraft:tfCollector")
        		.setHardness(2.5f)
        		.setCreativeTab(TcCreativeTabs.DECORATIONS);
        TcBlocks.tfCollector.setHarvestLevel("pickaxe", 0);
        ItemBlock itemblockTfCollector = new ItemTcBlock(TcBlocks.tfCollector);
        GameRegistry.register(TcBlocks.tfCollector, new ResourceLocation(TofuCraftCore.MODID, "tfCollector"));
        GameRegistry.register(itemblockTfCollector, new ResourceLocation(TofuCraftCore.MODID, "tfCollector"));
        GameRegistry.registerTileEntity(TileEntityTfCollector.class, "tfCollector");

        TcBlocks.tfCondenserIdle = new BlockTfCondenser(false, SoundType.METAL).setUnlocalizedName("tofucraft:tfCondenser")
        		.setHardness(2.5f)
        		.setCreativeTab(TcCreativeTabs.DECORATIONS);
        TcBlocks.tfCondenserIdle.setHarvestLevel("pickaxe", 0);
        ItemBlock itemblockTfCondenserIdle = new ItemTcBlock(TcBlocks.tfCondenserIdle);
        GameRegistry.register(TcBlocks.tfCondenserIdle, new ResourceLocation(TofuCraftCore.MODID, "tfCondenserIdle"));
        GameRegistry.register(itemblockTfCondenserIdle, new ResourceLocation(TofuCraftCore.MODID, "tfCondenserIdle"));

        TcBlocks.tfCondenserActive = new BlockTfCondenser(true, SoundType.METAL).setUnlocalizedName("tofucraft:tfCondenser")
                .setHardness(2.5F)
                .setLightLevel(0.875F);
        TcBlocks.tfCondenserActive.setHarvestLevel("pickaxe", 0);
        ItemBlock itemblockTfCondenserActive = new ItemTcBlock(TcBlocks.tfCondenserActive);
        GameRegistry.register(TcBlocks.tfCondenserActive, new ResourceLocation(TofuCraftCore.MODID, "tfCondenserActive"));
        GameRegistry.register(itemblockTfCondenserActive, new ResourceLocation(TofuCraftCore.MODID, "tfCondenserActive"));
        GameRegistry.registerTileEntity(TileEntityTfCondenser.class, "TfCondenser");

        TcBlocks.tfOvenIdle = new BlockTfOven(false, SoundType.METAL).setUnlocalizedName("tofucraft:tfOven")
                .setHardness(2.5F)
                .setCreativeTab(TcCreativeTabs.DECORATIONS);
        TcBlocks.tfOvenIdle.setHarvestLevel("pickaxe", 0);
        ItemBlock itemblockTfOvenIdle = new ItemTcBlock(TcBlocks.tfOvenIdle);
        GameRegistry.register(TcBlocks.tfOvenIdle, new ResourceLocation(TofuCraftCore.MODID, "tfOvenIdle"));
        GameRegistry.register(itemblockTfOvenIdle, new ResourceLocation(TofuCraftCore.MODID, "tfOvenIdle"));

        TcBlocks.tfOvenActive = new BlockTfOven(true, SoundType.METAL).setUnlocalizedName("tofucraft:tfOven")
                .setHardness(2.5F)
                .setLightLevel(0.875F);
        TcBlocks.tfOvenActive.setHarvestLevel("pickaxe", 0);
        ItemBlock itemblockTfOvenActive = new ItemTcBlock(TcBlocks.tfOvenActive);
        GameRegistry.register(TcBlocks.tfOvenActive, new ResourceLocation(TofuCraftCore.MODID, "tfOvenActive"));
        GameRegistry.register(itemblockTfOvenActive, new ResourceLocation(TofuCraftCore.MODID, "tfOvenActive"));
        GameRegistry.registerTileEntity(TileEntityTfOven.class, "TfOven");
        
        TcBlocks.tfReformerIdle = new BlockTfReformer(false, SoundType.METAL).setUnlocalizedName("tofucraft:tfReformer")
                .setHardness(2.5F)
                .setCreativeTab(TcCreativeTabs.DECORATIONS);
        TcBlocks.tfReformerIdle.setHarvestLevel("pickaxe", 0);
        ItemBlock itemblockTfReformerIdle = new ItemTcBlockVariable(TcBlocks.tfReformerIdle);
        GameRegistry.register(TcBlocks.tfReformerIdle, new ResourceLocation(TofuCraftCore.MODID, "tfReformerIdle"));
        GameRegistry.register(itemblockTfReformerIdle, new ResourceLocation(TofuCraftCore.MODID, "tfReformerIdle"));
        
        TcBlocks.tfReformerActive = new BlockTfReformer(true, SoundType.METAL).setUnlocalizedName("tofucraft:tfReformer")
                .setHardness(2.5F)
                .setLightLevel(0.875F);
        TcBlocks.tfReformerActive.setHarvestLevel("pickaxe", 0);
        ItemBlock itemblockTfReformerActive = new ItemTcBlockVariable(TcBlocks.tfReformerActive);
        GameRegistry.register(TcBlocks.tfReformerActive, new ResourceLocation(TofuCraftCore.MODID, "tfReformerActive"));
        GameRegistry.register(itemblockTfReformerActive, new ResourceLocation(TofuCraftCore.MODID, "tfReformerActive"));
        GameRegistry.registerTileEntity(TileEntityTfReformer.class, "tfReformer");
        
	    if (event.getSide().isClient()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.soybean), 0, new ModelResourceLocation(TofuCraftCore.RES + "blocksoybeans", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.soybeanHell), 0, new ModelResourceLocation(TofuCraftCore.RES + "blocksoybeans_hell", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.sprouts), 0, new ModelResourceLocation(TofuCraftCore.RES + "blocksprouts", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.sesame), 0, new ModelResourceLocation(TofuCraftCore.RES + "blockSesame", "inventory"));
			ModelBakery.registerItemVariants(Item.getItemFromBlock(TcBlocks.tcSapling),
					new ModelResourceLocation(TofuCraftCore.RES + "sapling_apricot", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "sapling_tofu", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tcSapling), 0, new ModelResourceLocation(TofuCraftCore.RES + "sapling_apricot", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tcSapling), 1, new ModelResourceLocation(TofuCraftCore.RES + "sapling_tofu", "inventory"));

			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.saltFurnaceIdle), 0, new ModelResourceLocation(TofuCraftCore.RES + "saltFurnaceIdle", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.saltFurnaceActive), 0, new ModelResourceLocation(TofuCraftCore.RES + "saltFurnaceActive", "inventory"));

			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.nattoBed), 0, new ModelResourceLocation(TofuCraftCore.RES + "nattoBed", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.yuba), 0, new ModelResourceLocation(TofuCraftCore.RES + "yuba", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.saltPan), 0, new ModelResourceLocation(TofuCraftCore.RES + "saltPan", "inventory"));

			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfStorageIdle), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfStorageIdle", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfStorageActive), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfStorageActive", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfAntennaMedium), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfAntenna", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfAntennaUltra), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfAntennaU", "inventory"));
//			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfAntennaSuper), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfAntennaS", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfSaturatorIdle), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfSaturatorIdle", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfSaturatorActive), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfSaturatorActive", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfCollector), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfCollector", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfCondenserIdle), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfCondenserIdle", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfCondenserActive), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfCondenserActive", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfOvenIdle), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfOvenIdle", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfOvenActive), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfOvenActive", "inventory"));
			ModelBakery.registerItemVariants(Item.getItemFromBlock(TcBlocks.tfReformerIdle),
					new ModelResourceLocation(TofuCraftCore.RES + "tfReformerIdle_simple", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tfReformerIdle_mix", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfReformerIdle), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfReformerIdle_simple", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfReformerIdle), 1, new ModelResourceLocation(TofuCraftCore.RES + "tfReformerIdle_mix", "inventory"));
			ModelBakery.registerItemVariants(Item.getItemFromBlock(TcBlocks.tfReformerActive),
					new ModelResourceLocation(TofuCraftCore.RES + "tfReformerActive_simple", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tfReformerActive_mix", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfReformerActive), 0, new ModelResourceLocation(TofuCraftCore.RES + "tfReformerActive_simple", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TcBlocks.tfReformerActive), 1, new ModelResourceLocation(TofuCraftCore.RES + "tfReformerActive_mix", "inventory"));
	    }
    }
    
    @SideOnly(Side.CLIENT)
    public static void registerBlockRenderer()
    {
    	ClientRegistry.registerTileEntity(TileEntitySaltPan.class, "SaltPan", new RenderSaltPan());

        ClientRegistry.registerTileEntity(TileEntityMorijio.class, "TmMorijio", new TileEntityMorijioRenderer());
        ClientRegistry.registerTileEntity(TileEntityTfAntenna.class, "TcTfAntenna", new TileEntityTfAntennaRenderer());
    }
}
