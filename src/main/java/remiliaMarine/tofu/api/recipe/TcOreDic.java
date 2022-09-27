package remiliaMarine.tofu.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemFoodSet;
import remiliaMarine.tofu.item.ItemFoodSet.EnumSetFood;
import remiliaMarine.tofu.item.ItemGelatin.EnumGelatin;
import remiliaMarine.tofu.item.ItemTcMaterials;
import remiliaMarine.tofu.soymilk.SoymilkFlavour;
import remiliaMarine.tofu.util.ItemUtils;

public enum TcOreDic {
    // === Blocks ===
    blockSalt(TcBlocks.salt),
    blockNatto(TcBlocks.natto),

	blockTofuKinu(TcBlocks.tofuKinu),
    blockTofuMomen(TcBlocks.tofuMomen),
    blockTofuIshi(TcBlocks.tofuIshi),
    blockTofuMetal(TcBlocks.tofuMetal),
    blockTofuGrilled(TcBlocks.tofuGrilled),
    blockTofuDried(TcBlocks.tofuDried),
    blockTofuFriedPouch(TcBlocks.tofuFriedPouch),
    blockTofuFried(TcBlocks.tofuFried),
    blockTofuEgg(TcBlocks.tofuEgg),
    blockTofuAnnin(TcBlocks.tofuAnnin),
    blockTofuSesame(TcBlocks.tofuSesame),
    blockTofuZunda(TcBlocks.tofuZunda),
    blockTofuStrawberry(TcBlocks.tofuStrawberry),
    blockTofuMiso(TcBlocks.tofuMiso),
    blockTofuHell(TcBlocks.tofuHell),
    blockTofuGlow(TcBlocks.tofuGlow),
    blockTofuDiamond(TcBlocks.tofuDiamond),
    blockTofuMinced(TcBlocks.tofuMinced),

    oreTofu(TcBlocks.oreTofu),
    oreTofuDiamond(TcBlocks.oreTofuDiamond),

    leavesApricot(new ItemStack(TcBlocks.tcLeaves, 1, 0)),
    leavesApricotF(new ItemStack(TcBlocks.tcLeaves, 1, 1)),
    leavesTofu(new ItemStack(TcBlocks.tcLeaves, 1, 2)),
//  logApricot(new ItemStack(TcBlock.tcLog, 1, 0)),
    saplingApricot(new ItemStack(TcBlocks.tcSapling, 1, 0)),
    saplingTofu(new ItemStack(TcBlocks.tcSapling, 1, 1)),

    blockTfMachineCase(TcBlocks.tfMachineCase),
    blockAdvTofuGem(new ItemStack(TcBlocks.advTofuGem)),

    // === Items ===
    salt(TcItems.salt),
    nigari(TcItems.nigari),
    leek(TcItems.leek),
    miso(TcItems.miso),
    natto(TcItems.natto),

    soybeans(TcItems.soybeans),
    soybeansParched(TcItems.soybeansParched),
    soybeansHell(TcItems.soybeansHell),
    kinako(TcItems.kinako),
    edamame(TcItems.edamame),
    edamameBoiled(TcItems.edamameBoiled),
    zunda(TcItems.zunda),
    okara(TcItems.okara),

    tofuKinu(TcItems.tofuKinu),
    tofuMomen(TcItems.tofuMomen),
    tofuIshi(TcItems.tofuIshi),
    tofuMetal(TcItems.tofuMetal),
    tofuGrilled(TcItems.tofuGrilled),
    tofuDried(TcItems.tofuDried),
    tofuFriedPouch(TcItems.tofuFriedPouch),
    tofuFried(TcItems.tofuFried),
    tofuEgg(TcItems.tofuEgg),
    tofuAnnin(TcItems.tofuAnnin),
    tofuSesame(TcItems.tofuSesame),
    tofuZunda(TcItems.tofuZunda),
    tofuStrawberry(TcItems.tofuStrawberry),
    tofuMiso(TcItems.tofuMiso),
    tofuHell(TcItems.tofuHell),
    tofuGlow(TcItems.tofuGlow),
    tofuDiamond(TcItems.tofuDiamond),
    tofuMinced(EnumSetFood.TOFU_MINCED.getStack()),

    bucketSoySauce(TcItems.bucketSoySauce),
    bucketSoymilk(TcItems.bucketSoymilk),
    bucketSoymilkHell(TcItems.bucketSoymilkHell),

    defattingPotion(TcItems.defattingPotion),
    dashi(new ItemStack(TcItems.dashi, 1, OreDictionary.WILDCARD_VALUE)),
    soupStock(dashi),
    soyOil(new ItemStack(TcItems.soyOil, 1, OreDictionary.WILDCARD_VALUE)),
    bottleSoySauce(new ItemStack(TcItems.bottleSoySauce, 1, OreDictionary.WILDCARD_VALUE)),

    barrel(TcItems.barrelEmpty),
    barrelEmpty(TcItems.barrelEmpty),
    barrelMiso(TcItems.barrelMiso),
    barrelMisoTofu(TcItems.barrelMisoTofu),
    barrelGlowtofu(TcItems.barrelGlowtofu),

    kouji(TcItems.kouji),
    mincedPotato(TcItems.mincedPotato),
    filterCloth(TcItems.filterCloth),
    apricot(TcItems.apricot),
    apricotSeed(TcItems.apricotSeed),
    gelatin(EnumGelatin.GELATIN.getStack()),
    kyoninso(TcItems.kyoninso),
    sesame(TcItems.sesame),
    starch(TcItems.starch),
    doubanjiang(new ItemStack(TcItems.doubanjiang, 1, OreDictionary.WILDCARD_VALUE)),
    strawberryJam(new ItemStack(TcItems.strawberryJam, 1, OreDictionary.WILDCARD_VALUE)),
    onigiri(ItemFoodSet.EnumSetFood.ONIGIRI.getStack()),
    onigiriSalt(ItemFoodSet.EnumSetFood.ONIGIRI_SALT.getStack()),
    onigiriMiso(ItemFoodSet.EnumSetFood.YAKIONIGIRI_MISO.getStack()),
    onigiriSoySauce(ItemFoodSet.EnumSetFood.YAKIONIGIRI_SHOYU.getStack()),
    sprouts(ItemFoodSet.EnumSetFood.SPROUTS.getStack()),
    oage(ItemFoodSet.EnumSetFood.OAGE.getStack()),
    tofuChikuwa(ItemFoodSet.EnumSetFood.TOFU_CHIKUWA.getStack()),
    tofuDiamondNugget(ItemTcMaterials.EnumTcMaterialInfo.tofuDiamondNugget.getStack()),
    tofuGem(ItemTcMaterials.EnumTcMaterialInfo.tofuGem.getStack()),
    yuba(TcItems.yuba),
    tofuHamburgRaw(ItemTcMaterials.EnumTcMaterialInfo.tofuHamburgRaw.getStack()),
    tofuHamburg(ItemFoodSet.EnumSetFood.TOFU_HAMBURG.getStack()),
    hamburg(ItemFoodSet.EnumSetFood.TOFU_HAMBURG.getStack()),
    tofuCookie(ItemFoodSet.EnumSetFood.TOFU_COOKIE.getStack()),
    cookie(ItemFoodSet.EnumSetFood.TOFU_COOKIE.getStack()),
    tofufishRaw(ItemFoodSet.EnumSetFood.TOFUFISH_RAW.getStack()),
    tofufishCooked(ItemFoodSet.EnumSetFood.TOFUFISH_COOKED.getStack()),
    rollingPin(TcItems.rollingPin),
    tofuSomen(ItemTcMaterials.EnumTcMaterialInfo.tofuSomen.getStack()),
    glassBowl(ItemTcMaterials.EnumTcMaterialInfo.glassBowl.getStack()),
    somenTsuyuBowl(TcItems.somenTsuyuBowl),
    chikuwa(ItemFoodSet.EnumSetFood.CHIKUWA.getStack()),
    bottleSoymilk(SoymilkFlavour.PLAIN.getStack()),
    zundaMochi(ItemFoodSet.EnumSetFood.ZUNDA_MOCHI.getStack()),
    kinakoMochi(ItemFoodSet.EnumSetFood.KINAKO_MOCHI.getStack()),
    tofuSteak(ItemFoodSet.EnumSetFood.TOFU_STEAK.getStack()),
    inari(ItemFoodSet.EnumSetFood.INARI.getStack()),
    zundama(TcItems.zundama),
    okaraStick(TcItems.okaraStick),
    tofuScoop(new ItemStack(TcItems.tofuScoop, 1, OreDictionary.WILDCARD_VALUE)),
    zundaArrow(TcItems.zundaArrow),
    zundaBow(TcItems.zundaBow),
    goheimochi(ItemFoodSet.EnumSetFood.GOHEIMOCHI.getStack()),
    misoSoup(TcItems.misoSoup),
    TTTBurger(TcItems.tttBurger),
    morijio(TcItems.morijio),
    melonSalt(TcItems.saltyMelon),
//    rice(TcItems.rice), TODO

    tfCapacitor(ItemTcMaterials.EnumTcMaterialInfo.tfCapacitor.getStack()),
    tfCoil(ItemTcMaterials.EnumTcMaterialInfo.tfCoil.getStack()),
    tfOscillator(ItemTcMaterials.EnumTcMaterialInfo.tfOscillator.getStack()),
    tfCircuitBoard(ItemTcMaterials.EnumTcMaterialInfo.tfCircuit.getStack()),
    advTofuGem(ItemTcMaterials.EnumTcMaterialInfo.advTofuGem.getStack()),
    activatedTofuGem(ItemTcMaterials.EnumTcMaterialInfo.activatedTofuGem.getStack()),
    activatedHellTofu(ItemTcMaterials.EnumTcMaterialInfo.activatedHellTofu.getStack()),


    // For Compatibility
    foodTofuKinu(tofuKinu),
    foodTofu(tofuMomen),
    foodTofuMomen(tofuMomen),
    foodTofuIshi(tofuIshi),
    foodTofuGrilled(tofuGrilled),
    foodTofuFriedPouch(tofuFriedPouch),
    foodTofuFried(tofuFried),
    foodTofuEgg(tofuEgg),
    foodTofuAnnin(tofuAnnin),
    foodTofuSesame(tofuSesame),
    foodTofuZunda(tofuZunda),
    foodTofuStrawberry(tofuStrawberry),
    foodTofuMiso(tofuMiso),
    foodTofuHell(tofuHell),
    foodTofuGlow(tofuGlow),
    foodTofuMinced(tofuMinced),

    foodEdamameBoiled(edamameBoiled),
    foodApricot(apricot),
    foodOnigiri(onigiri),
    foodOnigiriSalt(onigiriSalt),
    foodOnigiriMiso(onigiriMiso),
    foodOnigiriSoySauce(onigiriSoySauce),
    foodSprouts(sprouts),
    foodOage(oage),
    foodTofuChikuwa(tofuChikuwa),
    foodYuba(yuba),
    foodTofuHamburg(tofuHamburg),
    foodHamburg(tofuHamburg),
    foodTofuCookie(tofuCookie),
    foodCookie(tofuCookie),
    foodTofufishRaw(tofufishRaw),
    foodTofufishCooked(tofufishCooked),
    foodChikuwa(chikuwa),
    foodSoymilk(bottleSoymilk),
    foodZundaMochi(zundaMochi),
    foodKinakoMochi(kinakoMochi),
    foodTofuSteak(tofuSteak),
    foodInari(inari),
    foodOkaraStick(okaraStick),
    foodGoheimochi(goheimochi),
    foodMisoSoup(misoSoup),
    foodTTTBurger(TTTBurger),
    foodMelonSalt(melonSalt),
//    foodRice(), TODO

    seedSoybean(soybeans),
    seedSoybeanHell(soybeansHell),
    seedSesame(sesame),
    cropLeek(leek),
    cropSprouts(sprouts),
    cropEdamame(edamame),

    gemTofu(tofuGem),
    gemAdvTofu(advTofuGem)
    ;

    private ItemStack[] items;

    TcOreDic(Object... itemObjects)
    {
        this.items = new ItemStack[itemObjects.length];
        int i = 0;
        for (Object obj : itemObjects)
        {
            ItemStack itemstack = ItemUtils.getItemStack(obj);
            OreDictionary.registerOre(this.name(), itemstack);
            this.items[i++] = itemstack;
        }
    }

    TcOreDic(TcOreDic dic)
    {
        for (ItemStack item : dic.items)
        {
            OreDictionary.registerOre(this.name(), item);
        }
    }

}
