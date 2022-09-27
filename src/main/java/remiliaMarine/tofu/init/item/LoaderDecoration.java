package remiliaMarine.tofu.init.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemBlockBarrel;
import remiliaMarine.tofu.item.ItemMorijio;

public class LoaderDecoration {
	public void load(FMLPreInitializationEvent event) {

		TcItems.morijio = new ItemMorijio().setUnlocalizedName("tofucraft:morijio").setCreativeTab(TcCreativeTabs.DECORATIONS);
		GameRegistry.register(TcItems.morijio, new ResourceLocation(TofuCraftCore.MODID, "morijio"));

		TcItems.barrelMiso = new ItemBlockBarrel(TcBlocks.barrelMiso).setUnlocalizedName("tofucraft:barrelMiso").setCreativeTab(TcCreativeTabs.DECORATIONS);
		GameRegistry.register(TcItems.barrelMiso, new ResourceLocation(TofuCraftCore.MODID, "barrelMiso"));

		TcItems.barrelMisoTofu = new ItemBlockBarrel(TcBlocks.barrelMisoTofu).setUnlocalizedName("tofucraft:barrelMisoTofu").setCreativeTab(TcCreativeTabs.DECORATIONS);
		GameRegistry.register(TcItems.barrelMisoTofu, new ResourceLocation(TofuCraftCore.MODID, "barrelMisoTofu"));

		TcItems.barrelGlowtofu = new ItemBlockBarrel(TcBlocks.barrelGlowtofu).setUnlocalizedName("tofucraft:barrelGlowtofu").setCreativeTab(TcCreativeTabs.DECORATIONS);
		GameRegistry.register(TcItems.barrelGlowtofu, new ResourceLocation(TofuCraftCore.MODID, "barrelGlowtofu"));

		TcItems.barrelAdvTofuGem = new ItemBlockBarrel(TcBlocks.barrelAdvTofuGem).setUnlocalizedName("tofucraft:barrelAdvTofuGem").setCreativeTab(TcCreativeTabs.DECORATIONS);
		GameRegistry.register(TcItems.barrelAdvTofuGem, new ResourceLocation(TofuCraftCore.MODID, "barrelAdvTofuGem"));

	    if (event.getSide().isClient()) {
			ModelLoader.setCustomModelResourceLocation(TcItems.morijio, 0, new ModelResourceLocation(TofuCraftCore.RES + "morijio", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.barrelMiso, 0, new ModelResourceLocation(TofuCraftCore.RES + "barrelMiso", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.barrelMisoTofu, 0, new ModelResourceLocation(TofuCraftCore.RES + "barrelMisoTofu", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.barrelGlowtofu, 0, new ModelResourceLocation(TofuCraftCore.RES + "barrelGlowtofu", "inventory"));
			ModelLoader.setCustomModelResourceLocation(TcItems.barrelAdvTofuGem, 0, new ModelResourceLocation(TofuCraftCore.RES + "barrelAdvTofuGem", "inventory"));

	    }

	}
}
