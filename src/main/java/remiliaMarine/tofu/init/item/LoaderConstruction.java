package remiliaMarine.tofu.init.item;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemTofuDoor;

public class LoaderConstruction {
	public void load(FMLPreInitializationEvent event) {

		TcItems.tofuDoor = new ItemTofuDoor().setUnlocalizedName("tofucraft:tofuDoor").setCreativeTab(TcCreativeTabs.CONSTRUCTION);
		GameRegistry.register(TcItems.tofuDoor, new ResourceLocation(TofuCraftCore.MODID, "tofuDoor"));


		if (event.getSide().isClient()) {
			ModelBakery.registerItemVariants(TcItems.tofuDoor, new ResourceLocation[] {
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_kinu", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_momen", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_ishi", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_metal", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_grilled", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_dried", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_friedPouch", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_fried", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_egg", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_annin", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_sesame", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_zunda", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_strawberry", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_hell", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_glow", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_diamond", "inventory"),
					new ModelResourceLocation(TofuCraftCore.RES + "tofuDoor_miso", "inventory"),
			});
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor, 0, new ModelResourceLocation("tofucraft:tofuDoor_kinu", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor, 1, new ModelResourceLocation("tofucraft:tofuDoor_momen", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor, 2, new ModelResourceLocation("tofucraft:tofuDoor_ishi", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor, 3, new ModelResourceLocation("tofucraft:tofuDoor_metal", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor, 4, new ModelResourceLocation("tofucraft:tofuDoor_grilled", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor, 5, new ModelResourceLocation("tofucraft:tofuDoor_dried", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor, 6, new ModelResourceLocation("tofucraft:tofuDoor_friedPouch", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor, 7, new ModelResourceLocation("tofucraft:tofuDoor_fried", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor, 8, new ModelResourceLocation("tofucraft:tofuDoor_egg", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor, 9, new ModelResourceLocation("tofucraft:tofuDoor_annin", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor,10, new ModelResourceLocation("tofucraft:tofuDoor_sesame", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor,11, new ModelResourceLocation("tofucraft:tofuDoor_zunda", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor,12, new ModelResourceLocation("tofucraft:tofuDoor_strawberry", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor,13, new ModelResourceLocation("tofucraft:tofuDoor_hell", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor,14, new ModelResourceLocation("tofucraft:tofuDoor_glow", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor,15, new ModelResourceLocation("tofucraft:tofuDoor_diamond", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuDoor,16, new ModelResourceLocation("tofucraft:tofuDoor_miso", "inventory"));
		}
    }
}
