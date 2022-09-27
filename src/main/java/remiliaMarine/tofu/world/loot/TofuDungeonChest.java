package remiliaMarine.tofu.world.loot;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityChest;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemTcMaterials;

public class TofuDungeonChest {

    public static final WeightedLootEntry[] chestContent = new WeightedLootEntry[] {
        new WeightedLootEntry(TcItems.tofuKinu, 0, 1, 64, 20),
        new WeightedLootEntry(TcItems.tofuDiamond, 0, 1, 4, 3),
        new WeightedLootEntry(TcItems.materials, ItemTcMaterials.EnumTcMaterialInfo.tofuGem.getMetadata(), 1, 32, 15),
        new WeightedLootEntry(TcItems.materials, ItemTcMaterials.EnumTcMaterialInfo.tofuDiamondNugget.getMetadata(), 1, 8, 5),
        new WeightedLootEntry(Item.getItemFromBlock(TcBlocks.tcSapling), 1, 8, 16, 10),
        new WeightedLootEntry(TcItems.armorDiamond[0], 0, 1, 1, 1),
        new WeightedLootEntry(TcItems.armorDiamond[1], 0, 1, 1, 1),
        new WeightedLootEntry(TcItems.armorDiamond[2], 0, 1, 1, 1),
        new WeightedLootEntry(TcItems.armorDiamond[3], 0, 1, 1, 1),
        new WeightedLootEntry(TcItems.swordDiamond, 0, 1, 1, 1),
        new WeightedLootEntry(TcItems.armorMetal[0], 0, 1, 1, 5),
        new WeightedLootEntry(TcItems.armorMetal[1], 0, 1, 1, 5),
        new WeightedLootEntry(TcItems.armorMetal[2], 0, 1, 1, 5),
        new WeightedLootEntry(TcItems.armorMetal[3], 0, 1, 1, 5),
        new WeightedLootEntry(TcItems.swordMetal, 0, 1, 1, 8),
//        new WeightedLootEntry(TcItems.tofuCake, 0, 1, 1, 10),
//        new WeightedLootEntry(TcItems.zundaBow, 0, 1, 1, 2),
   };

    public static final LootContentGen lootGen = new LootContentGen(chestContent, 8, 15);

    public static void generateDangeonChestContent(Random rand, TileEntityChest chest)
    {
    	lootGen.generateChestContent(rand, chest);
    }
}
