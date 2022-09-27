package remiliaMarine.tofu.init.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemDiamondTofuAxe;
import remiliaMarine.tofu.item.ItemDiamondTofuPickaxe;
import remiliaMarine.tofu.item.ItemDiamondTofuSpade;
import remiliaMarine.tofu.item.ItemFukumame;
import remiliaMarine.tofu.item.ItemGoldenSalt;
import remiliaMarine.tofu.item.ItemTcAxe;
import remiliaMarine.tofu.item.ItemTcPickaxe;
import remiliaMarine.tofu.item.ItemTcSpade;
import remiliaMarine.tofu.item.ItemTofuBugle;
import remiliaMarine.tofu.item.ItemTofuHoe;
import remiliaMarine.tofu.item.ItemTofuScoop;
import remiliaMarine.tofu.item.ItemTofuSlimeRadar;
import remiliaMarine.tofu.item.ItemTofuStick;
import remiliaMarine.tofu.item.TofuToolMaterial;

public class LoaderTool {

	public void load(FMLPreInitializationEvent event) {

		boolean isRemote = event.getSide().isClient();
        /*
         * Basic Tools
         */
        TcItems.toolKinu = addToolSet(TofuToolMaterial.KINU, isRemote);
        TcItems.toolMomen = addToolSet(TofuToolMaterial.MOMEN, isRemote);
        TcItems.toolSolid = addToolSet(TofuToolMaterial.SOLID, isRemote);
        TcItems.toolMetal = addToolSet(TofuToolMaterial.METAL, isRemote);
        TcItems.toolDiamond = addToolSet(TofuToolMaterial.DIAMOND, isRemote);

        TcItems.tofuHoe = new ItemTofuHoe(TofuToolMaterial.GEM).setUnlocalizedName("tofucraft:tofuHoe").setCreativeTab(TcCreativeTabs.TOOLS);
        GameRegistry.register(TcItems.tofuHoe, new ResourceLocation(TofuCraftCore.MODID, "tofuHoe"));

        // Golden Salt
        TcItems.goldenSalt = new ItemGoldenSalt().setUnlocalizedName("tofucraft:goldensalt")
        		.setCreativeTab(TcCreativeTabs.TOOLS)
        		.setMaxDamage(180);
        GameRegistry.register(TcItems.goldenSalt, new ResourceLocation(TofuCraftCore.MODID, "goldensalt"));

        // Tofu Stick
        TcItems.tofuStick = new ItemTofuStick().setUnlocalizedName("tofucraft:tofuStick").setCreativeTab(TcCreativeTabs.TOOLS).setFull3D();
        GameRegistry.register(TcItems.tofuStick, new ResourceLocation(TofuCraftCore.MODID, "tofuStick"));

        // Tofu Bugle
        TcItems.bugle = new ItemTofuBugle().setUnlocalizedName("tofucraft:bugle").setCreativeTab(TcCreativeTabs.TOOLS);
        GameRegistry.register(TcItems.bugle, new ResourceLocation(TofuCraftCore.MODID, "bugle"));

        TcItems.tofuRadar = new ItemTofuSlimeRadar().setUnlocalizedName("tofucraft:tofuRadar").setCreativeTab(TcCreativeTabs.TOOLS);
        GameRegistry.register(TcItems.tofuRadar, new ResourceLocation(TofuCraftCore.MODID, "tofuRadar"));

        TcItems.tofuScoop = new ItemTofuScoop().setUnlocalizedName("tofucraft:tofuScoop").setCreativeTab(TcCreativeTabs.TOOLS);
        GameRegistry.register(TcItems.tofuScoop, new ResourceLocation(TofuCraftCore.MODID, "tofuScoop"));

        TcItems.fukumame = new ItemFukumame().setUnlocalizedName("tofucraft:fukumame").setCreativeTab(TcCreativeTabs.TOOLS);
        GameRegistry.register(TcItems.fukumame, new ResourceLocation(TofuCraftCore.MODID, "fukumame"));

        if (isRemote) {

        	ModelLoader.setCustomModelResourceLocation(TcItems.tofuHoe, 0, new ModelResourceLocation("tofucraft:tofuHoe", "inventory"));
        	ModelLoader.setCustomModelResourceLocation(TcItems.goldenSalt, 0, new ModelResourceLocation("tofucraft:goldensalt", "inventory"));
        	ModelLoader.setCustomModelResourceLocation(TcItems.tofuStick, 0, new ModelResourceLocation("tofucraft:tofuStick", "inventory"));
        	ModelLoader.setCustomModelResourceLocation(TcItems.bugle, 0, new ModelResourceLocation("tofucraft:bugle", "inventory"));

        	//TODO

        	ModelLoader.setCustomModelResourceLocation(TcItems.tofuRadar, 0, new ModelResourceLocation("tofucraft:tofuRadar", "inventory"));
        	ModelLoader.setCustomModelResourceLocation(TcItems.tofuScoop, 0, new ModelResourceLocation("tofucraft:tofuScoop", "inventory"));
        	ModelLoader.setCustomModelResourceLocation(TcItems.fukumame, 0, new ModelResourceLocation("tofucraft:fukumame", "inventory"));
        }
	}



    private Item[] addToolSet(Item.ToolMaterial material, boolean isRemote)
    {
        String key;
        int id;
        if (material == TofuToolMaterial.KINU) { key = "kinu"; id = 0;}
        else if (material == TofuToolMaterial.MOMEN) { key = "momen"; id = 1;}
        else if (material == TofuToolMaterial.SOLID) { key = "solid"; id = 2;}
        else if (material == TofuToolMaterial.METAL) { key = "metal"; id = 3;}
        else if (material == TofuToolMaterial.DIAMOND) { key = "diamond"; id = 4;}
        else throw new IllegalArgumentException("Unknown material for armor");

        Item[] tools = new Item[3];
        if (material == TofuToolMaterial.DIAMOND)
        {
            tools[0] = new ItemDiamondTofuSpade(material).setUnlocalizedName("tofucraft:" + TcItems.getToolName(key, 0));
        	GameRegistry.register(tools[0], new ResourceLocation(TofuCraftCore.MODID, TcItems.getToolName(key, 0)));
            tools[1] = new ItemDiamondTofuPickaxe(material).setUnlocalizedName("tofucraft:" + TcItems.getToolName(key, 1));
        	GameRegistry.register(tools[1], new ResourceLocation(TofuCraftCore.MODID, TcItems.getToolName(key, 1)));
            tools[2] = new ItemDiamondTofuAxe(material, ItemTcAxe.ATTACK_DAMAGES[id], ItemTcAxe.ATTACK_SPEEDS[id])
            		.setUnlocalizedName("tofucraft:" + TcItems.getToolName(key, 2));
        	GameRegistry.register(tools[2], new ResourceLocation(TofuCraftCore.MODID, TcItems.getToolName(key, 2)));
        }
        else
        {
        	tools[0] = new ItemTcSpade(material).setUnlocalizedName("tofucraft:" + TcItems.getToolName(key, 0));
        	GameRegistry.register(tools[0], new ResourceLocation(TofuCraftCore.MODID, TcItems.getToolName(key, 0)));
        	tools[1] = new ItemTcPickaxe(material).setUnlocalizedName("tofucraft:" + TcItems.getToolName(key, 1));
        	GameRegistry.register(tools[1], new ResourceLocation(TofuCraftCore.MODID, TcItems.getToolName(key, 1)));
        	tools[2] = new ItemTcAxe(material, ItemTcAxe.ATTACK_DAMAGES[id], ItemTcAxe.ATTACK_SPEEDS[id])
        			.setUnlocalizedName("tofucraft:" + TcItems.getToolName(key, 2));
        	GameRegistry.register(tools[2], new ResourceLocation(TofuCraftCore.MODID, TcItems.getToolName(key, 2)));
        }
        if (isRemote) {
        	ModelLoader.setCustomModelResourceLocation(tools[0], 0, new ModelResourceLocation("tofucraft:" + TcItems.getToolName(key, 0), "inventory"));
        	ModelLoader.setCustomModelResourceLocation(tools[1], 0, new ModelResourceLocation("tofucraft:" + TcItems.getToolName(key, 1), "inventory"));
        	ModelLoader.setCustomModelResourceLocation(tools[2], 0, new ModelResourceLocation("tofucraft:" + TcItems.getToolName(key, 2), "inventory"));
        }

        return tools;
    }


}
