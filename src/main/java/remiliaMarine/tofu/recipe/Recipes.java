package remiliaMarine.tofu.recipe;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import remiliaMarine.tofu.api.recipe.TcOreDic;
import remiliaMarine.tofu.api.recipe.TfReformerRecipe;
import remiliaMarine.tofu.api.recipe.TfReformerRecipeRegistry;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcEntity;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemFoodSet;
import remiliaMarine.tofu.item.ItemGelatin.EnumGelatin;
import remiliaMarine.tofu.item.ItemTcMaterials;
import remiliaMarine.tofu.item.TofuMaterial;
import remiliaMarine.tofu.soymilk.SoymilkFlavour;
import remiliaMarine.tofu.tileentity.TileEntityTfReformer;
import remiliaMarine.tofu.util.ItemUtils;

public class Recipes {
    private static final int DMG_WILDCARD = OreDictionary.WILDCARD_VALUE;
    private static final String oredic_cookingRice = "cookingRice";
    private static final String oredic_cookedMochi = "cookedMochi";
    private static final String oredic_cropStraw = "cropStraw";
    private static final String oredic_foodTea = "foodTea";

    public static void unifyOreDicItems()
    {
        ItemUtils.integrateOreItems("salt", "itemSalt", "Salt");
        ItemUtils.integrateOreItems("tofuMomen", "tofu", "Tofu", "itemTofu");
        ItemUtils.integrateOreItems("blockTofuMomen", "blockTofu");
        ItemUtils.integrateOreItems("rollingPin", "itemRollingPin");
        ItemUtils.integrateOreItems("cookingRice", "cookedRice", "riceCooked", "itemRiceCooked", "wheatRice");
    }

	public static void register()
    {
        // Ore Dictionary additions for common use
        OreDictionary.registerOre("logWood", new ItemStack(TcBlocks.tcLog, 1, DMG_WILDCARD));

        /*
         * Smelting
         */
        GameRegistry.addSmelting(TcItems.tofuKinu, new ItemStack(TcItems.tofuGrilled), 0.2f);
        GameRegistry.addSmelting(TcItems.tofuMomen, new ItemStack(TcItems.tofuGrilled), 0.2f);
        GameRegistry.addSmelting(TcBlocks.tofuKinu, new ItemStack(TcBlocks.tofuGrilled), 0.8f);
        GameRegistry.addSmelting(TcBlocks.tofuMomen, new ItemStack(TcBlocks.tofuGrilled), 0.8f);
        GameRegistry.addSmelting(TcItems.edamame, new ItemStack(TcItems.edamameBoiled, 12), 0.5f);
        GameRegistry.addSmelting(TcItems.soybeans, new ItemStack(TcItems.soybeansParched), 0.2f);
        GameRegistry.addSmelting(TcItems.starchRaw, new ItemStack(TcItems.starch), 0.5f);
        GameRegistry.addSmelting(TcBlocks.tcLog, new ItemStack(Items.COAL, 1, 1), 0.5f);
        GameRegistry.addSmelting(EnumGelatin.GELATIN_RAW.getStack(), EnumGelatin.GELATIN.getStack(), 0.5f);
        GameRegistry.addSmelting(ItemTcMaterials.EnumTcMaterialInfo.tofuHamburgRaw.getStack(), ItemFoodSet.EnumSetFood.TOFU_HAMBURG.getStack(), 0.8f);
        GameRegistry.addSmelting(ItemFoodSet.EnumSetFood.TOFUFISH_RAW.getStack(), ItemFoodSet.EnumSetFood.TOFUFISH_COOKED.getStack(), 1.5f);
        GameRegistry.addSmelting(TcItems.tofuIshi, ItemFoodSet.EnumSetFood.TOFU_STEAK.getStack(), 0.5f);

	    /*
	     * Crafting
	     */

	    // Soymilk

	    addSharedRecipe(new ItemStack(TcItems.bucketSoymilk),
	            "S",
	            "B",
	            'S', TcOreDic.soybeans,
	            'B', Items.BUCKET
	    );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                new ItemStack(TcItems.bucketSoymilk),
                40)
        );

	    // Soymilk and Okara
	    addSharedRecipe(new ItemStack(TcItems.bucketSoymilk),
	            "S",
	            "F",
	            "B",
	            'S', TcOreDic.soybeans,
	            'F', TcOreDic.filterCloth,
	            'B', Items.BUCKET
	    );

        // Hell Soymilk
        addSharedRecipe(new ItemStack(TcItems.bucketSoymilkHell),
                "S",
                "B",
                'S', TcOreDic.soybeansHell,
                'B', Items.BUCKET
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                new ItemStack(TcItems.bucketSoymilkHell),
                600)
                .addIngredients(TcOreDic.activatedHellTofu, true)
        );

	    // Tofu Blocks
	    addSharedRecipe(new ItemStack(TcBlocks.tofuMomen, 1),
	            "TT",
	            "TT",
	            'T', TcOreDic.tofuKinu
	    );

	    addSharedRecipe(new ItemStack(TcBlocks.tofuMomen, 1),
	            "TT",
	            "TT",
	            'T', TcOreDic.tofuMomen
	    );

    	addSharedRecipe(new ItemStack(TcBlocks.tofuIshi, 1),
    			"TT",
    			"TT",
    			'T', TcOreDic.tofuIshi
    	);

    	addSharedRecipe(new ItemStack(TcBlocks.tofuMetal, 1),
    			"TT",
    			"TT",
    			'T', TcOreDic.tofuMetal
    	);

        addSharedRecipe(new ItemStack(TcBlocks.tofuGrilled, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuGrilled
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuDried, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuDried
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuFriedPouch, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuFriedPouch
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuFried, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuFried
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuEgg, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuEgg
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuAnnin, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuAnnin
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuSesame, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuSesame
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuZunda, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuZunda
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuStrawberry, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuStrawberry
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuHell, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuHell
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuGlow, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuGlow
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuMiso, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuMiso
        );

    	addSharedRecipe(new ItemStack(TcBlocks.tofuDiamond, 1),
    			"TT",
    			"TT",
    			'T', TcOreDic.tofuDiamond
    	);

        addSharedRecipe(new ItemStack(TcBlocks.tofuMinced, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuMinced
        );

        // Minced Tofu
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.TOFU_MINCED.getStack(4),
                TcOreDic.blockTofuMomen,
                TcOreDic.rollingPin);
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.TOFU_MINCED.getStack(1),
                TcOreDic.tofuMomen,
                TcOreDic.rollingPin);

        // Fried Tofu Pouch
        addShapelessSharedRecipe(new ItemStack(TcItems.tofuFriedPouch),
                TcOreDic.tofuKinu,
                TcOreDic.starch,
                TcOreDic.soyOil
        );

        addShapelessSharedRecipe(new ItemStack(TcItems.tofuFriedPouch),
                TcOreDic.tofuMomen,
                TcOreDic.starch,
                TcOreDic.soyOil
        );

        // Fried Tofu
        addShapelessSharedRecipe(new ItemStack(TcItems.tofuFried),
                TcOreDic.tofuKinu,
                TcOreDic.soyOil
        );

        addShapelessSharedRecipe(new ItemStack(TcItems.tofuFried),
                TcOreDic.tofuMomen,
                TcOreDic.soyOil
        );

        // Egg Tofu
        addShapelessSharedRecipe(new ItemStack(TcItems.tofuEgg, 4),
                Items.EGG,
                TcOreDic.dashi
        );

    	// Salt Furnace
    	GameRegistry.addRecipe(new ItemStack(TcBlocks.saltFurnaceIdle),
    			"@ @",
    			"@ @",
    			"@@@",
    			'@', Blocks.COBBLESTONE
    	);

        // Golden Salt
        addShapelessSharedRecipe(new ItemStack(TcItems.goldenSalt),
                TcOreDic.salt,
                Items.GOLD_NUGGET,
                Items.GOLD_NUGGET,
                Items.GOLD_NUGGET
        );

        // Salty Melon
        addShapelessSharedRecipe(new ItemStack(TcItems.saltyMelon),
                TcOreDic.salt,
                Items.MELON
        );

        // Tasty Chicken Stew
        addShapelessSharedRecipe(new ItemStack(TcItems.tastyStew),
                Blocks.BROWN_MUSHROOM,
                Blocks.RED_MUSHROOM,
                Items.COOKED_CHICKEN,
                TcOreDic.salt,
                Items.MILK_BUCKET,
                Items.WHEAT,
                Items.BOWL
        );

        // Tasty Pork Stew
        addShapelessSharedRecipe(new ItemStack(TcItems.tastyStew),
                Blocks.BROWN_MUSHROOM,
                Blocks.RED_MUSHROOM,
                Items.COOKED_PORKCHOP,
                TcOreDic.salt,
                Items.MILK_BUCKET,
                Items.WHEAT,
                Items.BOWL
        );

        // Tasty Beef Stew
        addShapelessSharedRecipe(new ItemStack(TcItems.tastyBeefStew),
                Blocks.BROWN_MUSHROOM,
                Blocks.RED_MUSHROOM,
                Items.COOKED_BEEF,
                TcOreDic.salt,
                Items.MILK_BUCKET,
                Items.WHEAT,
                Items.BOWL
        );

        // Tofu Cake
        addSharedRecipe(new ItemStack(TcItems.tofuCake),
                "TTT",
                "SES",
                "WWW",
                'T', TcOreDic.tofuKinu,
                'S', Items.SUGAR,
                'E', Items.EGG,
                'W', Items.WHEAT);

        // Yudofu
        addShapelessSharedRecipe(new ItemStack(TcItems.yudofu),
                TcOreDic.tofuKinu,
                Items.POTIONITEM, // Water bottle
                Items.BOWL
        );
        addShapelessSharedRecipe(new ItemStack(TcItems.yudofu),
                TcOreDic.tofuMomen,
                Items.POTIONITEM, // Water bottle
                Items.BOWL
        );

        // TTT Burger
        addSharedRecipe(new ItemStack(TcItems.tttBurger),
                " B ",
                "TTT",
                " B ",
                'B', Items.BREAD,
                'T', TcOreDic.tofuFriedPouch
        );

        // Kouji Base
        addShapelessSharedRecipe(new ItemStack(TcItems.koujiBase),
                Items.WHEAT,
                TcOreDic.soybeans
        );

        // Morijio
        addSharedRecipe(new ItemStack(TcItems.morijio, 4),
                "D",
                "S",
                "B",
                'D', Items.DIAMOND,
                'S', TcOreDic.salt,
                'B', Items.BOWL
        );

        // Rappa
        GameRegistry.addRecipe(new ItemStack(TcItems.bugle),
                "I  ",
                "III",
                'I', Items.IRON_INGOT
        );

        // Miso Soup
        addShapelessSharedRecipe(new ItemStack(TcItems.misoSoup),
                TcOreDic.miso,
                TcOreDic.tofuKinu,
                TcOreDic.dashi,
                Items.BOWL
        );

        // Miso Soup
        addShapelessSharedRecipe(new ItemStack(TcItems.misoSoup),
                TcOreDic.miso,
                TcOreDic.tofuMomen,
                TcOreDic.dashi,
                Items.BOWL
        );

        // Miso Dengaku
        addSharedRecipe(new ItemStack(TcItems.misoDengaku),
                "M",
                "T",
                "|",
                'M', TcOreDic.miso,
                'T', TcOreDic.tofuMomen,
                '|', Items.STICK
        );

        // Zunda
        addSharedRecipe(new ItemStack(TcItems.zunda),
                "EEE",
                "ESE",
                "EEE",
                'E', TcOreDic.edamameBoiled,
                'S', Items.SUGAR
        );

        // Zunda Manju
        addSharedRecipe(new ItemStack(TcItems.zundaManju, 2),
                " Z ",
                "WWW",
                'Z', TcOreDic.zunda,
                'W', Items.WHEAT
        );

        // Kinako Manju
        addSharedRecipe(new ItemStack(TcItems.kinakoManju, 2),
                " K ",
                "WWW",
                'K', TcOreDic.kinako,
                'W', Items.WHEAT
        );

        // Barrel
        GameRegistry.addRecipe(new ItemStack(TcItems.barrelEmpty),
                "W W",
                "===",
                "WWW",
                'W', Blocks.PLANKS,
                '=', Items.REEDS);

        // Miso Barrel
        addSharedRecipe(new ItemStack(TcItems.barrelMiso),
                "SSS",
                "MMM",
                " B ",
                'S', TcOreDic.salt,
                'M', TcOreDic.kouji,
                'B', TcItems.barrelEmpty);

        // Nikujaga
        addShapelessSharedRecipe(new ItemStack(TcItems.nikujaga),
                Items.BOWL,
                Items.COOKED_BEEF,
                Items.POTATO,
                Items.CARROT,
                TcOreDic.bottleSoySauce,
                TcOreDic.dashi,
                Items.SUGAR
        );

        addShapelessSharedRecipe(new ItemStack(TcItems.nikujaga),
                Items.BOWL,
                Items.COOKED_PORKCHOP,
                Items.POTATO,
                Items.CARROT,
                TcOreDic.bottleSoySauce,
                TcOreDic.dashi,
                Items.SUGAR
        );
        // Soy Sauce Bucket
        // This is impossible because a soy sauce bottle always takes 1 point of damage when crafting

        // Soy Sauce Bottle
        addShapelessSharedRecipe(new ItemStack(TcItems.bottleSoySauce, 1, 0),
                Items.GLASS_BOTTLE,
                TcOreDic.bucketSoySauce
        );

        // Kinako
        addShapelessSharedRecipe(new ItemStack(TcItems.kinako),
                TcOreDic.soybeansParched,
                Items.SUGAR
        );

        // Kinako manju
        addSharedRecipe(new ItemStack(TcItems.kinakoManju, 2),
                " K ",
                "BBB",
                'K', TcOreDic.kinako,
                'B', Items.WHEAT
        );

        // Agedashi Tofu
        addShapelessSharedRecipe(new ItemStack(TcItems.agedashiTofu),
                TcOreDic.dashi,
                TcOreDic.bottleSoySauce,
                TcOreDic.tofuFriedPouch,
                Items.BOWL
        );

		// Soy Milk Bottle (Plain)
		addShapelessSharedRecipe(SoymilkFlavour.PLAIN.getStack(),
				Items.GLASS_BOTTLE,
				TcOreDic.bucketSoymilk
		);

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.PLAIN),
                20)
        );

        // Soy Milk Bottle (Kinako)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.KINAKO),
                Items.GLASS_BOTTLE,
                TcOreDic.bucketSoymilk,
                TcOreDic.kinako
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.KINAKO),
                20)
                .addIngredients(TcOreDic.kinako, false)
        );

        // Soy Milk Bottle (Cocoa)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.COCOA),
                Items.GLASS_BOTTLE,
                TcOreDic.bucketSoymilk,
                new ItemStack(Items.DYE, 1, 3),
                Items.SUGAR
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.COCOA),
                20)
                .addIngredients(new ItemStack(Items.DYE, 1, 3), false)
                .addIngredients(new ItemStack(Items.SUGAR), false)
        );

        // Soy Milk Bottle (Zunda)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.ZUNDA),
                Items.GLASS_BOTTLE,
                TcOreDic.bucketSoymilk,
                TcOreDic.zunda
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.ZUNDA),
                20)
                .addIngredients(TcOreDic.zunda, false)
        );

        // Soy Milk Bottle (Apple)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.APPLE),
                Items.GLASS_BOTTLE,
                TcOreDic.bucketSoymilk,
                Items.APPLE,
                Items.SUGAR
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                        TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.APPLE),
                        20)
                        .addIngredients(Items.APPLE, false)
                        .addIngredients(Items.SUGAR, false)
        );

        // Soy Milk Bottle (Pumpkin)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.PUMPKIN),
                Items.GLASS_BOTTLE,
                TcOreDic.bucketSoymilk,
                Blocks.PUMPKIN,
                Items.SUGAR
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                        TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.PUMPKIN),
                        20)
                        .addIngredients(Blocks.PUMPKIN, false)
                        .addIngredients(Items.SUGAR, false)
        );

        // Soy Milk Bottle (Ramune)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.RAMUNE),
                Items.GLASS_BOTTLE,
                TcOreDic.bucketSoymilk,
                new ItemStack(Items.DYE, 1, 12),
                Items.SUGAR
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                        TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.RAMUNE),
                        20)
                        .addIngredients(new ItemStack(Items.DYE, 1, 12), false)
                        .addIngredients(Items.SUGAR, false)
        );

        // Soy Milk Bottle (Strawberry)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.STRAWBERRY),
                Items.GLASS_BOTTLE,
                TcOreDic.bucketSoymilk,
                TcOreDic.strawberryJam,
                Items.SUGAR
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                        TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.STRAWBERRY),
                        20)
                        .addIngredients(TcOreDic.strawberryJam, false)
                        .addIngredients(Items.SUGAR, false)
        );

        // Soy Milk Bottle (Annin)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.ANNIN),
                Items.GLASS_BOTTLE,
                TcOreDic.bucketSoymilk,
                TcOreDic.kyoninso,
                Items.SUGAR
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                        TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.ANNIN),
                        20)
                        .addIngredients(TcOreDic.kyoninso, false)
                        .addIngredients(Items.SUGAR, false)
        );

        // Soy Milk Bottle (Pudding)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.PUDDING),
                Items.GLASS_BOTTLE,
                TcOreDic.bucketSoymilk,
                Items.EGG,
                Items.SUGAR
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                        TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.PUDDING),
                        20)
                        .addIngredients(Items.EGG, false)
                        .addIngredients(Items.SUGAR, false)
        );

        // Soy Milk Bottle (Tea)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.TEA),
                Items.GLASS_BOTTLE,
                TcOreDic.bucketSoymilk,
                oredic_foodTea,
                Items.SUGAR
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                        TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.TEA),
                        20)
                        .addIngredients(oredic_foodTea, false)
                        .addIngredients(Items.SUGAR, false)
        );

        // Dashi
        GameRegistry.addShapelessRecipe(new ItemStack(TcItems.dashi, 1, 0),
                Items.GLASS_BOTTLE,
                Items.WATER_BUCKET,
                Items.COOKED_FISH
        );

        // Soy Oil
        addShapelessSharedRecipe(new ItemStack(TcItems.soyOil),
                TcOreDic.defattingPotion,
                Items.GLASS_BOTTLE,
                TcOreDic.soybeans
        );

        // Koya Tofu fukumeni
        addShapelessSharedRecipe(new ItemStack(TcItems.fukumeni, 8),
                TcOreDic.tofuDried,
                TcOreDic.dashi,
                TcOreDic.salt
        );

        // Koya Tofu Stew
        addShapelessSharedRecipe(new ItemStack(TcItems.koyadofuStew),
                TcOreDic.tofuDried,
                TcOreDic.dashi,
                Blocks.BROWN_MUSHROOM,
                TcOreDic.bottleSoySauce,
                Items.BOWL
        );

        // Natto Bed Blocks
        addSharedRecipe(new ItemStack(TcBlocks.nattoBed),
                "BBB",
                "BBB",
                "WWW",
                'B', TcOreDic.soybeans,
                'W', Items.WHEAT
        );

        addSharedRecipe(new ItemStack(TcBlocks.nattoBed),
                "BBB",
                "BBB",
                "WWW",
                'B', TcOreDic.soybeans,
                'W', oredic_cropStraw
        );


        // Natto Hiyayakko
        addShapelessSharedRecipe(new ItemStack(TcItems.nattoHiyayakko),
                TcOreDic.tofuKinu,
                TcOreDic.natto,
                TcOreDic.leek,
                TcOreDic.bottleSoySauce,
                Items.BOWL
        );

        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.NATTO_HIYAYAKKO_GL.getStack(),
                TcOreDic.tofuKinu,
                TcOreDic.natto,
                TcOreDic.leek,
                TcOreDic.bottleSoySauce,
                TcOreDic.glassBowl
        );

        // Minced Potato
        GameRegistry.addShapelessRecipe(new ItemStack(TcItems.mincedPotato),
                Items.POTATO
        );

        // Raw Starch
        addShapelessSharedRecipe(new ItemStack(TcItems.starchRaw),
                TcOreDic.mincedPotato,
                TcOreDic.filterCloth
        );

        // Apricot Seed
        addShapelessSharedRecipe(new ItemStack(TcItems.apricotSeed),
                TcOreDic.apricot
        );

        // Kyoninso
        addShapelessSharedRecipe(new ItemStack(TcItems.kyoninso),
                TcOreDic.apricotSeed
        );

        // Annin Tofu
        addShapelessSharedRecipe(new ItemStack(TcItems.tofuAnnin, 4),
                TcOreDic.gelatin,
                Items.SUGAR,
                Items.MILK_BUCKET,
                TcOreDic.kyoninso
        );

        // Sesame Tofu
        addShapelessSharedRecipe(new ItemStack(TcItems.tofuSesame, 4),
                TcOreDic.starch,
                TcOreDic.sesame,
                TcOreDic.dashi,
                TcOreDic.salt
        );

        // Zunda Tofu
        addShapelessSharedRecipe(new ItemStack(TcItems.tofuZunda, 4),
                TcOreDic.starch,
                TcOreDic.zunda,
                TcOreDic.dashi,
                TcOreDic.salt
        );

        GameRegistry.addRecipe(new ItemStack(Blocks.PLANKS, 4, 0),
                "L",
                'L', new ItemStack(TcBlocks.tcLog, 1, 0)
        );

        // Filter Cloth
        GameRegistry.addRecipe(new ItemStack(TcItems.filterCloth, 32),
                "WWW",
                'W', new ItemStack(Blocks.WOOL, 1, DMG_WILDCARD)
        );

        // Okara Stick
        addSharedRecipe(new ItemStack(TcItems.okaraStick, 3),
                "O",
                "E",
                "W",
                'O', TcOreDic.okara,
                'E', Items.EGG,
                'W', Items.WHEAT
        );

        // Zundama
        addSharedRecipe(new ItemStack(TcItems.zundama),
                " Z ",
                "ZGZ",
                " Z ",
                'Z', TcOreDic.zunda,
                'G', Items.GLOWSTONE_DUST
        );

        // Zunda Bow
        GameRegistry.addRecipe(new ItemStack(TcItems.zundaBow),
                "O O",
                " B ",
                "O O",
                'O', TcItems.zundama,
                'B', Items.BOW
        );

        // Zunda Arrow
        addShapelessSharedRecipe(new ItemStack(TcItems.zundaArrow),
                TcOreDic.zunda,
                Items.ARROW
        );

        // Gelatin Base
        GameRegistry.addShapelessRecipe(EnumGelatin.GELATIN_RAW.getStack(),
                Items.LEATHER,
                Items.BONE
        );

        // Fukumame (Initial)
        addSharedRecipe(new ItemStack(TcItems.fukumame),
                "sss",
                "sss",
                " B ",
                's', TcOreDic.soybeansParched,
                'B', Items.BOWL
        );

        // Fukumame (Refill)
        addSharedRecipe(new ItemStack(TcItems.fukumame),
                "sss",
                "sss",
                " M ",
                's', TcOreDic.soybeansParched,
                'M', new ItemStack(TcItems.fukumame, 1, DMG_WILDCARD)
        );

        // Tofu Chikuwa
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.TOFU_CHIKUWA.getStack(),
                TcOreDic.tofuMomen,
                Items.COOKED_FISH
        );
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.TOFU_CHIKUWA.getStack(),
                TcOreDic.tofufishCooked
        );

        // Oage
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.OAGE.getStack(4),
                new ItemStack(TcBlocks.tofuSingleSlab1, 1, 1),
                TcOreDic.soyOil
        );
        // Natto -> Natto Block
        addSharedRecipe(new ItemStack(TcBlocks.natto, 1),
                "NNN",
                "NNN",
                "NNN",
                'N', TcOreDic.natto
        );

        // Natto Block -> Items
        addSharedRecipe(new ItemStack(TcItems.natto, 9),
                "N",
                'N', TcOreDic.blockNatto
        );

        // Salt -> Salt Block
        addSharedRecipe(new ItemStack(TcBlocks.salt, 1),
                "SSS",
                "SSS",
                "SSS",
                'S', TcOreDic.salt
        );

        // Salt Block -> Items
        addSharedRecipe(new ItemStack(TcItems.salt, 9),
                "S",
                'S', TcOreDic.blockSalt
        );

        // Moyashiitame
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.SPROUT_SAUTE.getStack(),
                TcOreDic.soyOil,
                TcOreDic.bottleSoySauce,
                TcOreDic.salt,
                TcOreDic.sprouts,
                Items.BOWL
        );

        // Moyashi no ohitashi
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.MOYASHI_OHITASHI.getStack(),
                TcOreDic.bottleSoySauce,
                TcOreDic.dashi,
                TcOreDic.sprouts,
                Items.BOWL
        );

        // Goheimochi
        addSharedRecipe(ItemFoodSet.EnumSetFood.GOHEIMOCHI.getStack(),
                "M",
                "O",
                "S",
                'M', TcOreDic.miso,
                'O', TcOreDic.onigiri,
                'S', Items.STICK
        );

        // Tofu Scoop
        GameRegistry.addRecipe(new ItemStack(TcItems.tofuScoop),
                "N",
                "S",
                "S",
                'N', Blocks.IRON_BARS,
                'S', Items.STICK
        );

        // Onigiri
        GameRegistry.addRecipe(ItemFoodSet.EnumSetFood.ONIGIRI.getStack(2),
                " W ",
                "WWW",
                'W', new ItemStack(Items.WHEAT)
        );

        // Salty Onigiri
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.ONIGIRI_SALT.getStack(),
                TcOreDic.salt,
                TcOreDic.onigiri
        );

        // Miso yakionigiri
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.YAKIONIGIRI_MISO.getStack(),
                TcOreDic.miso,
                TcOreDic.onigiri
        );

        // Shoyu yakionigiri
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.YAKIONIGIRI_SHOYU.getStack(),
                TcOreDic.bottleSoySauce,
                TcOreDic.onigiri
        );

        // Mabodofu (momen)
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.MABODOFU.getStack(),
                TcOreDic.tofuMomen,
                TcOreDic.starch,
                Items.PORKCHOP,
                TcOreDic.doubanjiang,
                TcOreDic.bottleSoySauce,
                Items.BOWL
        );

        // Mabodofu (kinu)
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.MABODOFU.getStack(),
                TcOreDic.tofuKinu,
                TcOreDic.starch,
                Items.PORKCHOP,
                TcOreDic.doubanjiang,
                TcOreDic.bottleSoySauce,
                Items.BOWL
        );

        // Tofu Creeper Egg
        {
        	ItemStack egg = new ItemStack(Items.SPAWN_EGG, 1);
        	Recipes.applyEntityIdToItemStack(egg, "tofucraft." + TcEntity.IdTofuCreeper);
	        addSharedRecipe(egg,
	                " G ",
	                "GTG",
	                " G ",
	                'G', TcOreDic.tofuGem,
	                'T', Blocks.TNT
	        );
        }

        // Tofu Diamond (Nuggets <-> piece)
        addSharedRecipe(new ItemStack(TcItems.tofuDiamond),
                "NNN",
                "NNN",
                "NNN",
                'N', TcOreDic.tofuDiamondNugget);

        addSharedRecipe(ItemTcMaterials.EnumTcMaterialInfo.tofuDiamondNugget.getStack(9),
                "D",
                'D', TcOreDic.tofuDiamond);

        // Tofu Slime Radar
        addSharedRecipe(new ItemStack(TcItems.tofuRadar, 1, TcItems.tofuRadar.getMaxDamage(null) + 1),
                "SR",
                "TT",
                'T', TcOreDic.tofuMetal,
                'S', Items.SLIME_BALL,
                'R', Items.REDSTONE
        );

        // Tofu Slime Radar (Charge)
        addShapelessSharedRecipe(new ItemStack(TcItems.tofuRadar, 1, 0),
                new ItemStack(TcItems.tofuRadar, 1, DMG_WILDCARD),
                TcOreDic.tofuGem
        );

        // Negi Hiyayakko
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.HIYAYAKKO.getStack(),
                TcOreDic.tofuKinu,
                TcOreDic.leek,
                TcOreDic.bottleSoySauce,
                Items.BOWL
        );

        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.HIYAYAKKO_GL.getStack(),
                TcOreDic.tofuKinu,
                TcOreDic.leek,
                TcOreDic.bottleSoySauce,
                TcOreDic.glassBowl
        );

        // Natto rice
        addShapelessSharedRecipe(new ItemStack(TcItems.riceNatto),
                TcOreDic.bottleSoySauce,
                TcOreDic.natto,
                oredic_cookingRice
        );

        // Natto rice with leek
        addShapelessSharedRecipe(new ItemStack(TcItems.riceNattoLeek),
                TcOreDic.leek,
                TcOreDic.bottleSoySauce,
                TcOreDic.natto,
                oredic_cookingRice
        );

        // Tofu Rice
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.RICE_TOFU.getStack(),
                TcOreDic.tofuKinu,
                TcOreDic.leek,
                TcOreDic.bottleSoySauce,
                oredic_cookingRice
        );

        // Tofu Hamburg (Raw)
        addSharedRecipe(ItemTcMaterials.EnumTcMaterialInfo.tofuHamburgRaw.getStack(3),
                "TTT",
                "MPB",
                "TTT",
                'T', TcOreDic.tofuMomen,
                'P', Items.PORKCHOP,
                'M', TcOreDic.miso,
                'B', Items.BREAD
        );

        // Tofu Hamburg Tempra
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.TOFU_HAMBURG_TEMPRA.getStack(),
                TcOreDic.tofuHamburgRaw,
                TcOreDic.soyOil,
                Items.WHEAT);

        // Tofu Hamburg Ankake Tempra
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.TOFU_HAMBURG_TEMPRA_ANKAKE.getStack(),
                ItemFoodSet.EnumSetFood.TOFU_HAMBURG_TEMPRA.getStack(),
                TcOreDic.starch,
                TcOreDic.bottleSoySauce,
                TcOreDic.soupStock);

        // Tofu Cookie
        addSharedRecipe(ItemFoodSet.EnumSetFood.TOFU_COOKIE.getStack(8),
                "WTW",
                'T', TcOreDic.tofuMomen,
                'W', Items.WHEAT
        );

        // Miso Barrel
        addSharedRecipe(TcItems.barrelMisoTofu,
                "MMM",
                "TTT",
                " B ",
                'M', TcOreDic.miso,
                'T', TcOreDic.tofuMomen,
                'B', TcOreDic.barrel);

        // Inari
        addSharedRecipe(ItemFoodSet.EnumSetFood.INARI.getStack(2),
                "#O*",
                " R ",
                '#', TcOreDic.bottleSoySauce,
                '*', Items.SUGAR,
                'O', TcOreDic.oage,
                'R', TcOreDic.onigiri
        );

        // Glowtofu Barrel
        addSharedRecipe(TcItems.barrelGlowtofu,
                "GGG",
                "TTT",
                " B ",
                'G', Items.GLOWSTONE_DUST,
                'T', TcOreDic.tofuMomen,
                'B', TcOreDic.barrel
        );

        // Glass bowl
        addSharedRecipe(ItemTcMaterials.EnumTcMaterialInfo.glassBowl.getStack(2),
                "P P",
                " P ",
                'P', Blocks.GLASS_PANE
        );

        // Tofu somen
        addShapelessSharedRecipe(ItemTcMaterials.EnumTcMaterialInfo.tofuSomen.getStack(4),
                TcOreDic.rollingPin,
                TcOreDic.tofuKinu,
                TcOreDic.starch,
                TcOreDic.salt
        );

        // Tofu somen bowl
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.TOFU_SOMEN.getStack(),
                TcOreDic.tofuSomen,
                TcOreDic.dashi,
                TcOreDic.bottleSoySauce,
                TcOreDic.glassBowl
        );

        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.TOFU_SOMEN.getStack(),
                TcOreDic.tofuSomen,
                TcOreDic.somenTsuyuBowl
        );

        // Somen tsuyu bowl
        addShapelessSharedRecipe(TcItems.somenTsuyuBowl,
                TcOreDic.dashi,
                TcOreDic.bottleSoySauce,
                TcOreDic.glassBowl
        );

        // Zunda mochi
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.ZUNDA_MOCHI.getStack(3),
                TcOreDic.zunda,
                oredic_cookedMochi);

        // Kinako mochi
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.KINAKO_MOCHI.getStack(3),
                TcOreDic.kinako,
                oredic_cookedMochi);

        addSharedRecipe(ItemFoodSet.EnumSetFood.CHIKUWA.getStack(4),
                " F ",
                "SET",
                " F ",
                'F', Items.COOKED_FISH,
                'S', TcOreDic.salt,
                'E', Items.EGG,
                'T', TcOreDic.starch
        );

        addSharedRecipe(ItemFoodSet.EnumSetFood.CHIKUWA.getStack(4),
                " F ",
                "TES",
                " F ",
                'F', Items.COOKED_FISH,
                'S', TcOreDic.salt,
                'E', Items.EGG,
                'T', TcOreDic.starch
        );

        // Chikuwa Platform
        addSharedRecipe(TcBlocks.chikuwaPlatformTofu,
                " C ",
                "C C",
                " C ",
                'C', TcOreDic.tofuChikuwa);

        addSharedRecipe(TcBlocks.chikuwaPlatformTofu,
                " C ",
                "C C",
                " C ",
                'C', TcOreDic.foodTofuChikuwa);

        addSharedRecipe(TcBlocks.chikuwaPlatformPlain,
                " C ",
                "C C",
                " C ",
                'C', TcOreDic.foodChikuwa);

        addSharedRecipe(TcBlocks.chikuwaPlatformPlain,
                " C ",
                "C C",
                " C ",
                'C', TcOreDic.chikuwa);

        // Soboro tofu rice
        addSharedRecipe(ItemFoodSet.EnumSetFood.RICE_SOBORO_TOFU.getStack(),
                " Y ",
                "STs",
                " R ",
                'Y', TcOreDic.leek,
                'S', TcOreDic.bottleSoySauce,
                'T', TcOreDic.tofuMinced,
                's', TcOreDic.salt,
                'R', oredic_cookingRice);

        // Soboro tofu saute
        addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.SOBORO_TOFU_SAUTE.getStack(),
                TcOreDic.tofuMinced,
                Items.COOKED_PORKCHOP,
                Items.CARROT,
                TcOreDic.bottleSoySauce,
                TcOreDic.dashi,
                TcOreDic.soyOil,
                Items.BOWL);

        // Salt pan
        addSharedRecipe(TcBlocks.saltPan,
                "/ /",
                " _ ",
                '/', Items.STICK,
                '_', new ItemStack(Blocks.STONE_SLAB, 1, 3)
                );

        /*
         * Tofu force series
         */

        // Tofu Gem Barrel
        addSharedRecipe(TcItems.barrelAdvTofuGem,
                "RRR",
                "GGG",
                " B ",
                'R', Items.REDSTONE,
                'G', TcOreDic.tofuGem,
                'B', TcOreDic.barrel
        );

        // TF Machine Case
        addSharedRecipe(TcBlocks.tfMachineCase,
                "TTT",
                "T T",
                "TTT",
                'T', TcOreDic.blockTofuMetal
        );
        
        addSharedRecipe(new ItemStack(TcBlocks.tofuMetal, 8),
                "C",
                'C', TcOreDic.blockTfMachineCase
        );
        
        // TF Capacitor
        addSharedRecipe(ItemTcMaterials.EnumTcMaterialInfo.tfCapacitor.getStack(1),
                " T ",
                "RGR",
                " T ",
                'T', TcOreDic.tofuMetal,
                'G', TcOreDic.tofuGem,
                'R', Items.REDSTONE
        );
        
        // TF Storage
        addSharedRecipe(TcBlocks.tfStorageIdle,
                "CCC",
                "GMG",
                'C', TcOreDic.tfCapacitor,
                'M', TcOreDic.blockTfMachineCase,
                'G', Blocks.GLASS
        );

        // Mineral soymilk
        addShapelessSharedRecipe(ItemTcMaterials.EnumTcMaterialInfo.mineralSoymilk.getStack(1),
                TcOreDic.tofuGem,
                TcOreDic.tofuGem,
                TcOreDic.tofuGem,
                Items.REDSTONE,
                Items.REDSTONE,
                Items.REDSTONE,
                Items.GLASS_BOTTLE
        );

        // Rolling Pin
        addSharedRecipe(new ItemStack(TcItems.rollingPin),
                "  /",
                " P ",
                "/  ",
                '/', Items.STICK,
                'P', Blocks.PLANKS
        );

        // TF Circuit Board
        addSharedRecipe(ItemTcMaterials.EnumTcMaterialInfo.tfCircuit.getStack(1),
                "RTR",
                "___",
                'R', Items.REDSTONE,
                'T', TcOreDic.tofuKinu,
                '_', TcOreDic.blockTofuIshi
        );

        // TF Coil
        addSharedRecipe(ItemTcMaterials.EnumTcMaterialInfo.tfCoil.getStack(1),
                "SSS",
                "TTT",
                "SSS",
                'S', TcOreDic.tofuSomen,
                'T', TcOreDic.tofuIshi
        );

        // TF Oscillator
        addSharedRecipe(ItemTcMaterials.EnumTcMaterialInfo.tfOscillator.getStack(1),
                "TQT",
                "M M",
                'T', TcItems.tofuKinu,
                'Q', Items.QUARTZ,
                'M', TcItems.tofuMetal
        );

        // Adv Tofu Gem Block
        addSharedRecipe(TcBlocks.advTofuGem,
                "GGG",
                "GGG",
                "GGG",
                'G', TcOreDic.advTofuGem
        );

        // TF Antenna
        addSharedRecipe(TcBlocks.tfAntennaMedium,
                " Y ",
                "CXA",
                "_B_",
                'Y', TcOreDic.leek,
                'C', TcOreDic.tfCoil,
                'X', TcOreDic.tfOscillator,
                'A', TcOreDic.tfCapacitor,
                'B', TcOreDic.tfCircuitBoard,
                '_', TcOreDic.tofuMetal
        );

        // TF Ultra Antenna
        addSharedRecipe(TcBlocks.tfAntennaUltra,
                " D ",
                "GYG",
                'Y', TcBlocks.tfAntennaMedium,
                'G', TcOreDic.advTofuGem,
                'D', TcOreDic.tofuDiamond
        );

        // TF Reformer
        addSharedRecipe(new ItemStack(TcBlocks.tfReformerIdle, 1, TileEntityTfReformer.EnumModel.SIMPLE.id),
                "CXC",
                "TBT",
                " M ",
                'C', TcOreDic.tfCoil,
                'X', TcOreDic.tfOscillator,
                'B', TcOreDic.tfCircuitBoard,
                'T', TcOreDic.blockTofuDried,
                'M', TcOreDic.blockTfMachineCase
                );

        // TF Mix Reformer
        addSharedRecipe(new ItemStack(TcBlocks.tfReformerIdle, 1, TileEntityTfReformer.EnumModel.MIX.id),
                " H ",
                "GTG",
                " M ",
                'H', Blocks.HOPPER,
                'T', Blocks.CRAFTING_TABLE,
                'G', TcOreDic.advTofuGem,
                'M', new ItemStack(TcBlocks.tfReformerIdle, 1, TileEntityTfReformer.EnumModel.SIMPLE.id)
        );

        // TF Condenser
        addSharedRecipe(TcBlocks.tfCondenserIdle,
                "PHP",
                "SDR",
                'H', Blocks.HOPPER,
                'P', Blocks.PISTON,
                'S', TcBlocks.tfStorageIdle,
                'D', TcOreDic.blockAdvTofuGem,
                'R', new ItemStack(TcBlocks.tfReformerIdle, 1, TileEntityTfReformer.EnumModel.SIMPLE.id)
        );

        // TF Oven
        addSharedRecipe(TcBlocks.tfOvenIdle,
                "XHA",
                "CGC",
                " M ",
                'H', TcOreDic.activatedHellTofu,
                'X', TcOreDic.tfOscillator,
                'A', TcOreDic.tfCapacitor,
                'C', Blocks.HARDENED_CLAY,
                'G', Blocks.GLASS,
                'M', TcOreDic.blockTfMachineCase
        );

        addSharedRecipe(TcBlocks.tfOvenIdle,
                "XHA",
                "CGC",
                " M ",
                'H', TcOreDic.activatedHellTofu,
                'X', TcOreDic.tfOscillator,
                'A', TcOreDic.tfCapacitor,
                'C', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, DMG_WILDCARD),
                'G', Blocks.GLASS,
                'M', TcOreDic.blockTfMachineCase
        );

        // TF Collector
        addSharedRecipe(TcBlocks.tfCollector,
                "H H",
                "GBG",
                " M ",
                'H', Blocks.HOPPER,
                'G', TcOreDic.activatedTofuGem,
                'B', TcOreDic.tfCircuitBoard,
                'M', TcOreDic.blockTfMachineCase
        );

        // TF Saturator
        addSharedRecipe(TcBlocks.tfSaturatorIdle,
                "TTT",
                "TGT",
                "TMT",
                'T', TcOreDic.blockTofuDried,
                'G', TcOreDic.advTofuGem,
                'M', TcOreDic.blockTfMachineCase
        );

        /*
         * Stairs Blocks
         */
        addStairsRecipes(TcOreDic.tofuKinu, TcBlocks.tofuStairsKinu);
        addStairsRecipes(TcOreDic.tofuMomen, TcBlocks.tofuStairsMomen);
        addStairsRecipes(TcOreDic.tofuIshi, TcBlocks.tofuStairsIshi);
        addStairsRecipes(TcOreDic.tofuMetal, TcBlocks.tofuStairsMetal);
        addStairsRecipes(TcOreDic.tofuGrilled, TcBlocks.tofuStairsGrilled);
        addStairsRecipes(TcOreDic.tofuDried, TcBlocks.tofuStairsDried);
        addStairsRecipes(TcOreDic.tofuFriedPouch, TcBlocks.tofuStairsFriedPouch);
        addStairsRecipes(TcOreDic.tofuFried, TcBlocks.tofuStairsFried);
        addStairsRecipes(TcOreDic.tofuEgg, TcBlocks.tofuStairsEgg);
        addStairsRecipes(TcOreDic.tofuAnnin, TcBlocks.tofuStairsAnnin);
        addStairsRecipes(TcOreDic.tofuSesame, TcBlocks.tofuStairsSesame);
        addStairsRecipes(TcOreDic.tofuZunda, TcBlocks.tofuStairsZunda);
        addStairsRecipes(TcOreDic.tofuStrawberry, TcBlocks.tofuStairsStrawberry);
        addStairsRecipes(TcOreDic.tofuHell, TcBlocks.tofuStairsHell);
        addStairsRecipes(TcOreDic.tofuGlow, TcBlocks.tofuStairsGlow);
        addStairsRecipes(TcOreDic.tofuDiamond, TcBlocks.tofuStairsDiamond);
        addStairsRecipes(TcOreDic.tofuMiso, TcBlocks.tofuStairsMiso);

        /*
         * Half Slabs
         */
        addSlabRecipe(TcOreDic.tofuKinu, TcBlocks.tofuSingleSlab1, 0);
        addSlabRecipe(TcOreDic.tofuMomen, TcBlocks.tofuSingleSlab1, 1);
        addSlabRecipe(TcOreDic.tofuIshi, TcBlocks.tofuSingleSlab1, 2);
        addSlabRecipe(TcOreDic.tofuMetal, TcBlocks.tofuSingleSlab1, 3);
        addSlabRecipe(TcOreDic.tofuGrilled, TcBlocks.tofuSingleSlabFaces, 0);
        addSlabRecipe(TcOreDic.tofuDried, TcBlocks.tofuSingleSlab1, 4);
        addSlabRecipe(TcOreDic.tofuFriedPouch, TcBlocks.tofuSingleSlab1, 5);
        addSlabRecipe(TcOreDic.tofuFried, TcBlocks.tofuSingleSlab1, 6);
        addSlabRecipe(TcOreDic.tofuEgg, TcBlocks.tofuSingleSlab1, 7);
        addSlabRecipe(TcOreDic.tofuAnnin, TcBlocks.tofuSingleSlab2, 0);
        addSlabRecipe(TcOreDic.tofuSesame, TcBlocks.tofuSingleSlab2, 1);
        addSlabRecipe(TcOreDic.tofuZunda, TcBlocks.tofuSingleSlab2, 2);
        addSlabRecipe(TcOreDic.tofuStrawberry, TcBlocks.tofuSingleSlab2, 3);
        addSlabRecipe(TcOreDic.tofuHell, TcBlocks.tofuSingleSlab2, 4);
        addSlabRecipe(TcOreDic.tofuGlow, TcBlocks.tofuSingleSlabGlow, 0);
        addSlabRecipe(TcOreDic.tofuDiamond, TcBlocks.tofuSingleSlab2, 5);
        addSlabRecipe(TcOreDic.tofuMiso, TcBlocks.tofuSingleSlab2, 6);

		// Armors
		addCombatItemRecipes(TcOreDic.blockTofuKinu, TcItems.armorKinu, TcItems.swordKinu);
		addCombatItemRecipes(TcOreDic.blockTofuMomen, TcItems.armorMomen, TcItems.swordMomen);
		addCombatItemRecipes(TcOreDic.blockTofuIshi, TcItems.armorSolid, TcItems.swordSolid);
		addCombatItemRecipes(TcOreDic.blockTofuMetal, TcItems.armorMetal, TcItems.swordMetal);
		addCombatItemRecipes(TcOreDic.blockTofuDiamond, TcItems.armorDiamond, TcItems.swordDiamond);

        // Tools
        addToolItemRecipes(TcOreDic.blockTofuKinu, TcItems.toolKinu);
        addToolItemRecipes(TcOreDic.blockTofuMomen, TcItems.toolMomen);
        addToolItemRecipes(TcOreDic.blockTofuIshi, TcItems.toolSolid);
        addToolItemRecipes(TcOreDic.blockTofuMetal, TcItems.toolMetal);
        addToolItemRecipes(TcOreDic.blockTofuDiamond, TcItems.toolDiamond);

        // Doors
        for (TofuMaterial tofuMaterial: TofuMaterial.values())
        {
            addSharedRecipe(new ItemStack(TcItems.tofuDoor, 1, tofuMaterial.id()),
                    "TT",
                    "TT",
                    "TT",
                    'T', tofuMaterial.getBlock());
        }

        // Walls
        for (Map.Entry<TofuMaterial, Block> entry: TcBlocks.tofuWalls.entrySet())
        {
            addSharedRecipe(new ItemStack(entry.getValue(), 6),
                    "TTT",
                    "TTT",
                    'T', entry.getKey().getBlock());
        }

        // Fence gates
        for (Map.Entry<TofuMaterial, Block> entry: TcBlocks.tofuFenceGates.entrySet())
        {
            addSharedRecipe(entry.getValue(),
                    "BIB",
                    "BIB",
                    'B', entry.getKey().getBlock(),
                    'I', entry.getKey().getItem());
        }

        // Torches
        for (Map.Entry<TofuMaterial, Block> entry: TcBlocks.tofuTorches.entrySet())
        {
            addSharedRecipe(new ItemStack(entry.getValue(), 4),
                    "C",
                    "T",
                    "T",
                    'T', entry.getKey().getItem(),
                    'C', new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE));
            addSharedRecipe(new ItemStack(entry.getValue(), 4),
                    "O",
                    "T",
                    "T",
                    'T', entry.getKey().getItem(),
                    'O', TcOreDic.soyOil);
        }

        // Ladders
        for (Map.Entry<TofuMaterial, Block> entry: TcBlocks.tofuLadders.entrySet())
        {
            addSharedRecipe(new ItemStack(entry.getValue(), 3),
                    "T T",
                    "TTT",
                    "T T",
                    'T', entry.getKey().getItem());
        }

        // Trapdoors
        for (Map.Entry<TofuMaterial, Block> entry: TcBlocks.tofuTrapdoors.entrySet())
        {
            addSharedRecipe(new ItemStack(entry.getValue(), 2),
                    "TTT",
                    "TTT",
                    'T', entry.getKey().getItem());
        }
    }

    private static void addCombatItemRecipes(TcOreDic tofu, Item[] armors, Item sword)
    {
        addSharedRecipe(new ItemStack(armors[0]),
            "TTT",
            "T T",
                'T', tofu
        );
        addSharedRecipe(new ItemStack(armors[1]),
            "T T",
            "TTT",
            "TTT",
                'T', tofu
        );
        addSharedRecipe(new ItemStack(armors[2]),
            "TTT",
            "T T",
            "T T",
                'T', tofu
        );
        addSharedRecipe(new ItemStack(armors[3]),
            "T T",
            "T T",
                'T', tofu
        );
        addSharedRecipe(new ItemStack(sword),
            "T",
            "T",
            "|",
                'T', tofu,
                '|', Items.STICK
        );
    }

    private static void addToolItemRecipes(TcOreDic tofu, Item[] tools)
    {
        addSharedRecipe(new ItemStack(tools[0]),
            "T",
            "|",
            "|",
                'T', tofu,
                '|', Items.STICK
        );
        addSharedRecipe(new ItemStack(tools[1]),
            "TTT",
            " | ",
            " | ",
                'T', tofu,
                '|', Items.STICK
        );
        addSharedRecipe(new ItemStack(tools[2]),
            "TT",
            "T|",
            " |",
                'T', tofu,
                '|', Items.STICK
        );
    }

    private static void addStairsRecipes(TcOreDic tofu, Block stairs)
    {
        // Stairs Blocks
        addSharedRecipe(new ItemStack(stairs),
                "  T",
                " TT",
                "TTT",
                'T', tofu
        );
        // Stairs Blocks
        addSharedRecipe(new ItemStack(stairs),
                "T  ",
                "TT ",
                "TTT",
                'T', tofu
        );
    }

    private static void addSlabRecipe(TcOreDic tofu, Block slab, int meta)
    {
        addSharedRecipe(new ItemStack(slab, 1, meta),
                "TT",
                'T', tofu
        );
    }
    
    /**
     * APplies the given entity ID to the given ItemStack's NBT data.
     */
    public static void applyEntityIdToItemStack(ItemStack stack, String entityId)
    {
        NBTTagCompound nbttagcompound = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.setString("id", entityId);
        nbttagcompound.setTag("EntityTag", nbttagcompound1);
        stack.setTagCompound(nbttagcompound);
    }
    
    /**
     * === External Mod Recipes ===
     */
    public static void registerExternalModRecipes()
    {
        if (TcItems.plantBall != null)
        {
            addShapelessSharedRecipe(new ItemStack(TcItems.plantBall, 1),
                    TcOreDic.okara,
                    TcOreDic.okara,
                    TcOreDic.okara,
                    TcOreDic.mincedPotato
            );
        }

        if (TcBlocks.sakuraLeaves != null)
        {
            // Soy Milk Bottle (Sakura)
            addShapelessSharedRecipe(new ItemStack(TcItems.bottleSoymilk, 1, SoymilkFlavour.SAKURA.getId()),
                    Items.GLASS_BOTTLE,
                    TcOreDic.bucketSoymilk,
                    new ItemStack(TcBlocks.sakuraLeaves, 1, 15)
            );
            TfReformerRecipeRegistry.register(new TfReformerRecipe(
                            TcItems.bottleSoymilk.getItemStack(SoymilkFlavour.SAKURA),
                            20)
                            .addIngredients(new ItemStack(TcBlocks.sakuraLeaves, 1, 15), false)
            );
        }

        if (TcItems.bambooFood != null)
        {
            if (OreDictionary.getOres(oredic_cookedMochi).size() == 0)
            {
                // Zunda mochi
                addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.ZUNDA_MOCHI.getStack(3),
                        TcOreDic.zunda,
                        new ItemStack(TcItems.bambooFood, 1, 23));

                // Kinako mochi
                addShapelessSharedRecipe(ItemFoodSet.EnumSetFood.KINAKO_MOCHI.getStack(3),
                        TcOreDic.kinako,
                        new ItemStack(TcItems.bambooFood, 1, 23));
            }
        }

    }

    /*
     * === Facade methods for Forge Ore Dictionary ===
     */
    private static void addSharedRecipe(Block block, Object... recipe)
    {
        decodeDicRecipe(recipe);
        GameRegistry.addRecipe(new ShapedOreRecipe(block, recipe));
    }

    private static void addSharedRecipe(Item item, Object... recipe)
    {
        decodeDicRecipe(recipe);
        GameRegistry.addRecipe(new ShapedOreRecipe(item, recipe));
    }

    private static void addSharedRecipe(ItemStack is, Object... recipe)
    {
        decodeDicRecipe(recipe);
        GameRegistry.addRecipe(new ShapedOreRecipe(is, recipe));
    }

    @SuppressWarnings("unused")
	private static void addShapelessSharedRecipe(Block block, Object... recipe)
    {
        decodeDicRecipe(recipe);
        GameRegistry.addRecipe(new ShapelessOreRecipe(block, recipe));
    }

    private static void addShapelessSharedRecipe(Item item, Object... recipe)
    {
        decodeDicRecipe(recipe);
        GameRegistry.addRecipe(new ShapelessOreRecipe(item, recipe));
    }

    private static void addShapelessSharedRecipe(ItemStack is, Object... recipe)
    {
        decodeDicRecipe(recipe);
        GameRegistry.addRecipe(new ShapelessOreRecipe(is, recipe));
    }

    private static void decodeDicRecipe(Object[] list)
    {
        for (int i = 0; i < list.length; i++)
        {
            Object Items = list[i];
            if (Items instanceof TcOreDic)
            {
                list[i] = ((TcOreDic)Items).name();
            }
        }
    }
}
