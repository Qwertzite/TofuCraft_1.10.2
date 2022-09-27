package remiliaMarine.tofu.init;

import java.util.Random;

import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import remiliaMarine.tofu.eventhandler.EntityJoinWorldEventHandler;
import remiliaMarine.tofu.eventhandler.GetVillageBlockIDEventHandler;
import remiliaMarine.tofu.item.ItemTcMaterials.EnumTcMaterialInfo;
import remiliaMarine.tofu.soymilk.SoymilkFlavour;
import remiliaMarine.tofu.village.ComponentVillageHouseTofu;
import remiliaMarine.tofu.village.TofuCookHouseHandler;

public class TcVillages {

	public static int PROFESSION_FARMER_ID = 0;
	public static int CAREER_FARMER_ID = 0;

	public static VillagerProfession ProfessionTofuCook;
	public static VillagerCareer CareerTofuCook;

	public static VillagerProfession ProfessionTofunian;
	public static VillagerCareer CareerTofunian1;
	public static VillagerCareer CareerTofunian2;
	public static VillagerCareer CareerTofunian3;

    @SuppressWarnings("deprecation")
	public static void register()
    {
        // Tofu Village handler
        BiomeManager.addVillageBiome(TcBiomes.TOFU_PLAINS, true);
        BiomeManager.addVillageBiome(TcBiomes.TOFU_LEEK_PLAINS, true);
        BiomeManager.addVillageBiome(TcBiomes.TOFU_FOREST, true);
        MinecraftForge.TERRAIN_GEN_BUS.register(new GetVillageBlockIDEventHandler());//TODO
        MinecraftForge.EVENT_BUS.register(new EntityJoinWorldEventHandler());

        VillagerRegistry vill = VillagerRegistry.instance();
        // Register the profession of Tofu Cook
    	TcVillages.ProfessionTofuCook = new VillagerProfession("tofucraft:tofuCook",
    			new ResourceLocation("tofucraft", "textures/mob/tofucook.png").toString(),//new ResourceLocation("tofucraft", "textures/mob/tofucook.png").toString(),
        		"minecraft:textures/entity/zombie_villager/zombie_villager.png");
    	TcVillages.CareerTofuCook = new VillagerCareer(TcVillages.ProfessionTofuCook, "tofuCook")
				.addTrade(1, new TradeListTCookLv1())
				.addTrade(2, new TradeListTCookLv2())
				.addTrade(3, new TradeListTCookLv3())
				.addTrade(4, new TradeListTCookLv4())
				.addTrade(5, new TradeListTCookLv5());
    	VillagerRegistry.instance().register(TcVillages.ProfessionTofuCook);

        // Register the profession of Tofunian
    	TcVillages.ProfessionTofunian = new VillagerProfession("tofucraft:tofunian",
    			new ResourceLocation("tofucraft", "textures/mob/tofunian.png").toString(),
    			"minecraft:textures/entity/zombie_villager/zombie_villager.png") {
    		@Override
            public int getRandomCareer(Random rand) { return 0; }
    	};
    	TcVillages.CareerTofunian1 = new VillagerCareer(TcVillages.ProfessionTofunian, "tofunian")
    			.addTrade(1, new TradeListTofunianLv1());
    	TcVillages.CareerTofunian2 = new VillagerCareer(TcVillages.ProfessionTofunian, "tofunianFriend")
    			.addTrade(1, new TradeListTofunianLv1());// rest of the trades will be added from EntityTofunian#useRecipe(). 
    	TcVillages.CareerTofunian3 = new VillagerCareer(TcVillages.ProfessionTofunian, "tofunianCloseFriend")
    			.addTrade(1, new TradeListTofunianLv1());
    	VillagerRegistry.instance().register(TcVillages.ProfessionTofunian);

        // Add Trade Recipes
        VillagerProfession professionFarmer = VillagerRegistry.getById(TcVillages.PROFESSION_FARMER_ID);
        VillagerCareer careerFarmer = professionFarmer.getCareer(CAREER_FARMER_ID);
        careerFarmer.addTrade(1, new TradeListFarmerLv1());
        careerFarmer.addTrade(2, new TradeListFarmerLv2());

        // Tofu cook's house
        vill.registerVillageCreationHandler(new TofuCookHouseHandler());
        MapGenStructureIO.registerStructureComponent(ComponentVillageHouseTofu.class, "ViTfH");

        // Blacksmith chest
        //moved to remiliamarine.tofu.eventhandler.EventLootTableLoad
    }

    public static class TradeListFarmerLv1 implements ITradeList {
    	@Override
        public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
    		recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(TcItems.sesame, 4+ random.nextInt(4))));
    	}
    }

    public static class TradeListFarmerLv2 implements ITradeList {
    	@Override
        public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
    		recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(TcItems.strawberryJam)));
    	}
    }

    public static class TradeListTCookLv1 implements ITradeList {
    	@Override
        public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
    		//Buy
    		recipeList.add(new MerchantRecipe(new ItemStack(TcItems.soybeans,	18+random.nextInt(4)), new ItemStack(Items.EMERALD)));
    		recipeList.add(new MerchantRecipe(new ItemStack(TcItems.tofuMomen,	14+random.nextInt(4)), new ItemStack(Items.EMERALD)));
    		recipeList.add(new MerchantRecipe(new ItemStack(TcItems.barrelEmpty, 6+random.nextInt(4)), new ItemStack(Items.EMERALD)));
    		recipeList.add(new MerchantRecipe(new ItemStack(TcItems.edamame,	14+random.nextInt(4)), new ItemStack(Items.EMERALD)));
    		//Sell
    	}
    }

    public static class TradeListTCookLv2 implements ITradeList {
    	@Override
        public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
    		//Buy
    		recipeList.add(new MerchantRecipe(new ItemStack(TcItems.miso, 18+random.nextInt(4)), new ItemStack(Items.EMERALD)));
    		//Sell
    		recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(TcItems.tofuIshi,		17+random.nextInt(8))));
    		recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(TcItems.tofuFriedPouch,10+random.nextInt(4))));
    		recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(TcItems.tofuFried,	12+random.nextInt(4))));
    	}
    }

    public static class TradeListTCookLv3 implements ITradeList {
    	@Override
        public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
    		//Buy
    		recipeList.add(new MerchantRecipe(new ItemStack(TcItems.materials, 2+random.nextInt(3), EnumTcMaterialInfo.tofuGem.getMetadata()), new ItemStack(Items.EMERALD)));
    		//Sell
    		recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(TcItems.tofuEgg,		26+random.nextInt(8))));
    		recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(TcItems.bottleSoymilk, 8+random.nextInt(4), SoymilkFlavour.APPLE.getId())));
    		recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(TcItems.bottleSoymilk, 8+random.nextInt(4), SoymilkFlavour.PUMPKIN.getId())));
    		recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(TcItems.bottleSoymilk, 8+random.nextInt(4), SoymilkFlavour.KINAKO.getId())));
    	}
    }

    public static class TradeListTCookLv4 implements ITradeList {
    	@Override
        public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
    		//Sell
    		recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(TcItems.tofuMetal, 2+random.nextInt(2))));
    		recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(TcItems.tofuDried, 8+random.nextInt(4))));
    	}
    }

    public static class TradeListTCookLv5 implements ITradeList {
    	@Override
        public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
    		//Sell
    		recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(TcItems.defattingPotion)));
    		recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(TcItems.zundaManju, 9+random.nextInt(4))));
    	}
    }

    public static class TradeListTofunianLv1 implements ITradeList {
        public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
    		//exchange
        	int num = 3 + random.nextInt(6);
    		recipeList.add(new MerchantRecipe(new ItemStack(TcItems.tofuMomen, num), new ItemStack(TcItems.tofuKinu, num)));
    		recipeList.add(new MerchantRecipe(new ItemStack(TcItems.tofuKinu, num), new ItemStack(TcItems.tofuMomen, num)));
    	}
    }
}
