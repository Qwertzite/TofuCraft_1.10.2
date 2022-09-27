package remiliaMarine.tofu.init.item;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemBottleSoymilk;
import remiliaMarine.tofu.item.ItemFoodContainer;
import remiliaMarine.tofu.item.ItemFoodSet;
import remiliaMarine.tofu.item.ItemFoodSet.EnumSetFood;
import remiliaMarine.tofu.item.ItemSoupBase;
import remiliaMarine.tofu.item.ItemTcFood;
import remiliaMarine.tofu.item.ItemYuba;
import remiliaMarine.tofu.soymilk.SoymilkFlavour;

public class LoaderFood {

	public void load(FMLPreInitializationEvent event) {

        // Salty Melon
		TcItems.saltyMelon = new ItemTcFood(3, 0.5F, false).setUnlocalizedName("tofucraft:saltyMelon");
		GameRegistry.register(TcItems.saltyMelon, new ResourceLocation(TofuCraftCore.MODID, "saltyMelon"));

        // Tasty Stew
		TcItems.tastyStew = new ItemSoupBase(20, 1.0F, false).setUnlocalizedName("tofucraft:tastyStew");
		GameRegistry.register(TcItems.tastyStew, new ResourceLocation(TofuCraftCore.MODID, "tastyStew"));

        // Tasty Beef Stew
		TcItems.tastyBeefStew = new ItemSoupBase(20, 1.0F, false).setUnlocalizedName("tofucraft:tastyBeefStew");
		GameRegistry.register(TcItems.tastyBeefStew, new ResourceLocation(TofuCraftCore.MODID, "tastyBeefStew"));

        // Tofu Cake
		TcItems.tofuCake = new ItemBlockSpecial(TcBlocks.tofuCake).setUnlocalizedName("tofucraft:tofuCake")
				.setMaxStackSize(1)
				.setCreativeTab(TcCreativeTabs.FOOD);
		GameRegistry.register(TcItems.tofuCake, new ResourceLocation(TofuCraftCore.MODID, "tofuCake"));

		TcItems.yudofu = new ItemSoupBase(3, 0.3F, false).setUnlocalizedName("tofucraft:yudofu");
		GameRegistry.register(TcItems.yudofu, new ResourceLocation(TofuCraftCore.MODID, "yudofu"));

		TcItems.tttBurger = new ItemTcFood(8, 0.4F, false).setUnlocalizedName("tofucraft:tttBurger");
		GameRegistry.register(TcItems.tttBurger, new ResourceLocation(TofuCraftCore.MODID, "tttBurger"));

		TcItems.misoSoup = new ItemSoupBase(4, 0.6F, false).setUnlocalizedName("tofucraft:misoSoup");
		GameRegistry.register(TcItems.misoSoup, new ResourceLocation(TofuCraftCore.MODID, "misoSoup"));

		TcItems.misoDengaku = new ItemFoodContainer(5, 0.6F, true)
				.setContainedItem(new ItemStack(Items.STICK))
				.setUnlocalizedName("tofucraft:misoDengaku")
				.setFull3D();
		GameRegistry.register(TcItems.misoDengaku, new ResourceLocation(TofuCraftCore.MODID, "misoDengaku"));

		TcItems.edamameBoiled = new ItemTcFood(1, 0.25F, false).setUnlocalizedName("tofucraft:edamameBoiled").setAlwaysEdible();
		GameRegistry.register(TcItems.edamameBoiled, new ResourceLocation(TofuCraftCore.MODID, "edamameBoiled"));

		TcItems.zundaManju = new ItemTcFood(6, 0.8f, true).setUnlocalizedName("tofucraft:zundaManju");
		GameRegistry.register(TcItems.zundaManju, new ResourceLocation(TofuCraftCore.MODID, "zundaManju"));

		TcItems.nikujaga = new ItemSoupBase(10, 0.7F, false).setUnlocalizedName("tofucraft:nikujaga");
		GameRegistry.register(TcItems.nikujaga, new ResourceLocation(TofuCraftCore.MODID, "nikujaga"));

		TcItems.agedashiTofu = new ItemSoupBase(6, 0.6F, false).setUnlocalizedName("tofucraft:agedashiTofu");
		GameRegistry.register(TcItems.agedashiTofu, new ResourceLocation(TofuCraftCore.MODID, "agedashiTofu"));

		TcItems.kinakoManju = new ItemTcFood(4, 0.5F, true).setUnlocalizedName("tofucraft:kinakoManju");
		GameRegistry.register(TcItems.kinakoManju, new ResourceLocation(TofuCraftCore.MODID, "kinakoManju"));

		TcItems.bottleSoymilk = (ItemBottleSoymilk) new ItemBottleSoymilk().setUnlocalizedName("tofucraft:bottleSoymilk");
		GameRegistry.register(TcItems.bottleSoymilk, new ResourceLocation(TofuCraftCore.MODID, "bottleSoymilk"));

		TcItems.fukumeni = new ItemTcFood(3, 1.0f, true).setUnlocalizedName("tofucraft:fukumeni");
		GameRegistry.register(TcItems.fukumeni, new ResourceLocation(TofuCraftCore.MODID, "fukumeni"));

		TcItems.koyadofuStew = new ItemSoupBase(8, 0.8f, false).setUnlocalizedName("tofucraft:koyadofuStew");
		GameRegistry.register(TcItems.koyadofuStew, new ResourceLocation(TofuCraftCore.MODID, "koyadofuStew"));

		TcItems.apricot = new ItemFoodContainer(3, 0.3f, false, new ItemStack(TcItems.apricotSeed)).setUnlocalizedName("tofucraft:apricot");
		GameRegistry.register(TcItems.apricot, new ResourceLocation(TofuCraftCore.MODID, "apricot"));

        TcItems.okaraStick = new ItemTcFood(5, 0.6f, false).setUnlocalizedName("tofucraft:okaraStick")
        		.setEatingDuration(8);
        GameRegistry.register(TcItems.okaraStick, new ResourceLocation(TofuCraftCore.MODID, "okaraStic"));

        TcItems.riceNatto = new ItemTcFood(8, 0.8f, false).setUnlocalizedName("tofucraft:riceNatto");
        GameRegistry.register(TcItems.riceNatto, new ResourceLocation(TofuCraftCore.MODID, "riceNatto"));

        TcItems.riceNattoLeek = new ItemTcFood(9, 0.8f, false).setUnlocalizedName("tofucraft:riceNattoLeek");
        GameRegistry.register(TcItems.riceNattoLeek, new ResourceLocation(TofuCraftCore.MODID, "riceNattoLeek"));

		TcItems.yuba = new ItemYuba(1, 1.0f, false).setUnlocalizedName("tofucraft:yuba").setAlwaysEdible();
		GameRegistry.register(TcItems.yuba, new ResourceLocation(TofuCraftCore.MODID, "yuba"));

		//-- food set --//

		TcItems.foodSet = new ItemFoodSet();
		GameRegistry.register(TcItems.foodSet, new ResourceLocation(TofuCraftCore.MODID, "foodSet"));

	    if (event.getSide().isClient()) {
	    	ModelLoader.setCustomModelResourceLocation(TcItems.saltyMelon, 0, new ModelResourceLocation(TofuCraftCore.RES + "saltyMelon", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tastyStew, 0, new ModelResourceLocation(TofuCraftCore.RES + "tastyStew", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tastyBeefStew, 0, new ModelResourceLocation(TofuCraftCore.RES + "tastyBeefStew", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tofuCake, 0, new ModelResourceLocation(TofuCraftCore.RES + "tofuCake", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.yudofu, 0, new ModelResourceLocation(TofuCraftCore.RES + "yudofu", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.tttBurger, 0, new ModelResourceLocation(TofuCraftCore.RES + "tttBurger", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.misoSoup, 0, new ModelResourceLocation(TofuCraftCore.RES + "misoSoup", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.misoDengaku, 0, new ModelResourceLocation(TofuCraftCore.RES + "misoDengaku", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.edamameBoiled, 0, new ModelResourceLocation(TofuCraftCore.RES + "edamameBoiled", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.zundaManju, 0, new ModelResourceLocation(TofuCraftCore.RES + "zundaManju", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.nikujaga, 0, new ModelResourceLocation(TofuCraftCore.RES + "nikujaga", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.agedashiTofu, 0, new ModelResourceLocation(TofuCraftCore.RES + "agedashiTofu", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.kinakoManju, 0, new ModelResourceLocation(TofuCraftCore.RES + "kinakoManju", "inventory"));
	    	for (int i = 0; i < SoymilkFlavour.values().length; i++) {
		    	ModelLoader.setCustomModelResourceLocation(TcItems.bottleSoymilk, i, new ModelResourceLocation(TofuCraftCore.MODID + ":coloured_bottle", "inventory"));
	    	}
	    	ModelLoader.setCustomModelResourceLocation(TcItems.fukumeni, 0, new ModelResourceLocation(TofuCraftCore.RES + "fukumeni", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.koyadofuStew, 0, new ModelResourceLocation(TofuCraftCore.RES + "koyadofuStew", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.apricot, 0, new ModelResourceLocation(TofuCraftCore.RES + "apricot", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.okaraStick, 0, new ModelResourceLocation(TofuCraftCore.RES + "okaraStick", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.riceNatto, 0, new ModelResourceLocation(TofuCraftCore.RES + "riceNatto", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.riceNattoLeek, 0, new ModelResourceLocation(TofuCraftCore.RES + "riceNattoLeek", "inventory"));
	    	ModelLoader.setCustomModelResourceLocation(TcItems.yuba, 0, new ModelResourceLocation(TofuCraftCore.RES + "yuba", "inventory"));

			{
				EnumSetFood[] list= EnumSetFood.values();
				ModelBakery.registerItemVariants(TcItems.foodSet, TcItems.foodSet.getResourceLocation());
				for (int i = 0; i < list.length; i++) {
					ModelLoader.setCustomModelResourceLocation(TcItems.foodSet, i, new ModelResourceLocation("tofucraft:" + list[i].getName(), "inventory"));
				}
			}


	    }

	}

}
