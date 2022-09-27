package remiliaMarine.tofu.init;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.item.ItemTcMaterials;

public class TcFluids {
    public static final Fluid SOYMILK =
    		new Fluid("soymilk", new ResourceLocation(TofuCraftCore.MODID, "blocks/soymilk"), new ResourceLocation(TofuCraftCore.MODID, "blocks/soymilk_flow")) {
    			@Override public int getColor() { return 0xFFFFFFFF; }
    		};
    public static final Fluid SOYMILK_HELL =
    		new Fluid("soymilkhell", new ResourceLocation(TofuCraftCore.MODID, "blocks/soymilkHell"), new ResourceLocation(TofuCraftCore.MODID, "blocks/soymilkHell_flow")) {
				@Override public int getColor() { return 0xFFB5311D; }//181 49 29
    		};
    public static final Fluid SOY_SAUCE = new Fluid("soysauce", new ResourceLocation(TofuCraftCore.MODID, "blocks/soySauce"), new ResourceLocation(TofuCraftCore.MODID, "blocks/soySauce_flow"));

    public static final Fluid NIGARI =
    		new Fluid("Nigari", new ResourceLocation(TofuCraftCore.MODID, "blocks/nigari"), new ResourceLocation(TofuCraftCore.MODID, "blocks/nigari")) {
    			@Override public int getColor() { return 0xff809cff; }
    		};
    public static final Fluid MINERAL_SOYMILK =
    		new Fluid("MineralSoymilk", new ResourceLocation(TofuCraftCore.MODID, "blocks/nigari"), new ResourceLocation(TofuCraftCore.MODID, "blocks/nigari")) {
    			@Override public int getColor() { return 0xFFBF9770; }// 191 151 112
			};
    public static final Fluid STRAWBERRY_JAM =
    		new Fluid("StrawberryJam", new ResourceLocation(TofuCraftCore.MODID, "blocks/strawberryJam"), new ResourceLocation(TofuCraftCore.MODID, "blocks/strawberryJam")) {
    			@Override public int getColor() { return 0xFF63080F; }// 99 8 15
			};
    public static final Fluid SOUP_STOCK = new Fluid("SoupStock", new ResourceLocation(TofuCraftCore.MODID, "blocks/soupStock"), new ResourceLocation(TofuCraftCore.MODID, "blocks/soupStock"));
//    public static final Fluid MAGMA_CREAM = new Fluid("Magma Cream");

    public static ModelResourceLocation ResourceLocSOYMILK;
    public static ModelResourceLocation ResourceLocSOYMILK_HELL;
    public static ModelResourceLocation ResourceLocSOY_SAUCE;

    public static void registerFluid()
    {
        FluidRegistry.registerFluid(SOYMILK);
        ResourceLocSOYMILK = new ModelResourceLocation("tofucraft:soymilk", "fluid");
        FluidRegistry.registerFluid(SOYMILK_HELL);
        ResourceLocSOYMILK_HELL = new ModelResourceLocation("tofucraft:soymilkHell", "fluid");
        FluidRegistry.registerFluid(SOY_SAUCE);
        ResourceLocSOY_SAUCE = new ModelResourceLocation("tofucraft:soysauce", "fluid");

        FluidRegistry.registerFluid(NIGARI);
        FluidRegistry.registerFluid(MINERAL_SOYMILK);
        FluidRegistry.registerFluid(STRAWBERRY_JAM);
        FluidRegistry.registerFluid(SOUP_STOCK);
//        FluidRegistry.registerFluid(MAGMA_CREAM);
    }

	@SuppressWarnings("deprecation")
	public static void register(FMLPreInitializationEvent event) {
		
		if (event.getSide().isClient()) {
			MinecraftForge.EVENT_BUS.register(new TcFluids());
		}

        // Soymilk
        SOYMILK.setBlock(TcBlocks.soymilkStill)
                .setUnlocalizedName(TcBlocks.soymilkStill.getUnlocalizedName());
        FluidContainerRegistry.registerFluidContainer(SOYMILK, new ItemStack(TcItems.bucketSoymilk), FluidContainerRegistry.EMPTY_BUCKET);

        // Hell Soymilk
        SOYMILK_HELL.setBlock(TcBlocks.soymilkHellStill)
                .setUnlocalizedName(TcBlocks.soymilkHellStill.getUnlocalizedName());
        FluidContainerRegistry.registerFluidContainer(SOYMILK_HELL, new ItemStack(TcItems.bucketSoymilkHell), FluidContainerRegistry.EMPTY_BUCKET);
        
        // Soy Sauce
        SOY_SAUCE.setBlock(TcBlocks.soySauceStill)
                .setUnlocalizedName(TcBlocks.soySauceStill.getUnlocalizedName());
        FluidContainerRegistry.registerFluidContainer(SOY_SAUCE,
                new ItemStack(TcItems.bucketSoySauce), FluidContainerRegistry.EMPTY_BUCKET);
        FluidContainerRegistry.registerFluidContainer(SOY_SAUCE,
                new ItemStack(TcItems.bottleSoySauce), FluidContainerRegistry.EMPTY_BOTTLE);
        
        // Nigari
        NIGARI.setUnlocalizedName("tofucraft:nigari");
        FluidContainerRegistry.registerFluidContainer(new FluidStack(NIGARI, 10),
                new ItemStack(TcItems.nigari), FluidContainerRegistry.EMPTY_BOTTLE);

        // Mineral Soymilk
        MINERAL_SOYMILK.setUnlocalizedName("tofucraft:mineralSoymilk");
        FluidContainerRegistry.registerFluidContainer(new FluidStack(MINERAL_SOYMILK, 100),
                new ItemStack(TcItems.materials, 1, ItemTcMaterials.EnumTcMaterialInfo.mineralSoymilk.getMetadata()), FluidContainerRegistry.EMPTY_BOTTLE);
		
        // Strawberry Jam
        STRAWBERRY_JAM.setUnlocalizedName("tofucraft:strawberryJam");
        FluidContainerRegistry.registerFluidContainer(new FluidStack(STRAWBERRY_JAM, 500),
                new ItemStack(TcItems.strawberryJam), FluidContainerRegistry.EMPTY_BOTTLE);
        
        // Dashi
        SOUP_STOCK.setUnlocalizedName("tofucraft:soupStock");
        FluidContainerRegistry.registerFluidContainer(new FluidStack(SOUP_STOCK, 500),
                new ItemStack(TcItems.dashi), FluidContainerRegistry.EMPTY_BOTTLE);
        
	}
	
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event)
    {
//        SOYMILK.setIcons(TcBlocks.soymilkStill.getIcon(0, 0), TcBlocks.soymilkStill.getIcon(2, 0));
//        SOYMILK_HELL.setIcons(TcBlocks.soymilkHellStill.getIcon(0, 0), TcBlocks.soymilkHellStill.getIcon(2, 0));
//        SOY_SAUCE.setIcons(TcBlocks.soySauceStill.getIcon(0, 0), TcBlocks.soySauceStill.getIcon(2, 0));

    	TextureMap textureMap = event.getMap();
    	textureMap.registerSprite(new ResourceLocation(TofuCraftCore.MODID, "blocks/nigari"));
    	textureMap.registerSprite(new ResourceLocation(TofuCraftCore.MODID, "blocks/soupStock"));
    	textureMap.registerSprite(new ResourceLocation(TofuCraftCore.MODID, "blocks/strawberryJam"));
    	textureMap.registerSprite(new ResourceLocation(TofuCraftCore.MODID, "blocks/soymilkMineral"));
    	
//        if ((event.getMap()).getTextureType() == 0)
//        {
//            IIcon icon;
//            icon = event.map.registerIcon(TofuCraftCore.RES + "nigari");
//            NIGARI.setIcons(icon);
//            icon = event.map.registerIcon(TofuCraftCore.RES + "soupStock");
//            SOUP_STOCK.setIcons(icon);
//            icon = event.map.registerIcon(TofuCraftCore.RES + "strawberryJam");
//            STRAWBERRY_JAM.setIcons(icon);
//            icon = event.map.registerIcon(TofuCraftCore.RES + "soymilkMineral");
//            MINERAL_SOYMILK.setIcons(icon);
//        }
    }

}
