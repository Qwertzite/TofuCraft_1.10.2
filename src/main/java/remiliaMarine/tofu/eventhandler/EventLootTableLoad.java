package remiliaMarine.tofu.eventhandler;

import org.apache.logging.log4j.Level;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.util.ModLog;

public class EventLootTableLoad {
	public static ResourceLocation[] TARGETS = new ResourceLocation[] {
		LootTableList.CHESTS_SIMPLE_DUNGEON,
		LootTableList.CHESTS_ABANDONED_MINESHAFT,
		LootTableList.CHESTS_JUNGLE_TEMPLE,
		LootTableList.CHESTS_STRONGHOLD_CORRIDOR,
		LootTableList.CHESTS_STRONGHOLD_CROSSING,
		LootTableList.CHESTS_STRONGHOLD_LIBRARY,
		LootTableList.CHESTS_VILLAGE_BLACKSMITH
	};

	@SubscribeEvent
	public void onLootTableLoad(LootTableLoadEvent event) {
		ResourceLocation name = event.getName();
		LootTable table = event.getTable();
		int targ = -1;
		for(int i = 0; i < TARGETS.length; i++) {
			if(name == TARGETS[i]){
				targ = i;
				break;
			}
		}
		switch(targ) {
		case 0://dungeon
		case 1://mine shaft
			//return ctx.poolCount == 1 ? "main" : "pool" + (ctx.poolCount - 1);

			LootPool bestpool = table.getPool("main");
			if( bestpool == null) {
				ModLog.log(Level.WARN, "no such pool as \"main\" in " + TARGETS[targ].toString());
			} else {
				this.addDefattingPotionToPool(bestpool);
			}

			LootPool miscpool = table.getPool("pool1");
			if( miscpool == null) {
				ModLog.log(Level.WARN, "no such pool as \"pool1\" in " + TARGETS[targ].toString());
			} else {
				this.addSaplingToPool(miscpool);
			}

			LootPool rubbish = table.getPool("pool2");
			if( rubbish == null) {
				ModLog.log(Level.WARN, "no such pool as \"pool2\" in " + TARGETS[targ].toString());
			} else {
				this.addDoubanjiang(rubbish);
			}
			break;
		case 2://jungle temple
		case 3://stronghold corridor
		case 4://stronghold crossing
		case 5://stronghold library
			LootPool pool = table.getPool("main");
			if( pool == null) {
				ModLog.log(Level.WARN, "no such pool as \"main\" in " + TARGETS[targ].toString());
			} else {
				this.addDefattingPotionToPool(pool);
				this.addSaplingToPool(pool);
				this.addDoubanjiang(pool);
			}
			break;
		case 6://village
			LootPool vil = table.getPool("main");
			if( vil == null) {
				ModLog.log(Level.WARN, "no such pool as \"main\" in " + TARGETS[targ].toString());
			} else {
				this.addBuggle(vil);
			}
		}

	}

	public LootPool addDefattingPotionToPool(LootPool pool) {
		pool.addEntry(new LootEntryItem(TcItems.defattingPotion, 8, 0, new LootFunction[] {}, null, "tofucraft:defattingPotion"));
		return pool;
	}

	public LootPool addSaplingToPool(LootPool pool) {
		pool.addEntry(new LootEntryItem(Item.getItemFromBlock(TcBlocks.tcSapling), 8, 0,
				new LootFunction[] {
						new SetCount(null, new RandomValueRange(1, 4)),
						new SetMetadata(null, new RandomValueRange(0, 0)),
				}, null, "tofucraft:tcSapling"));
		return pool;
	}

	public LootPool addDoubanjiang(LootPool pool) {
		pool.addEntry(new LootEntryItem(TcItems.doubanjiang, 4, 0, new LootFunction[] {}, null, "tofucraft:doubanjiang"));
		return pool;
	}

	public LootPool addBuggle(LootPool pool) {
		pool.addEntry(new LootEntryItem(TcItems.bugle, 5, 0, new LootFunction[] {}, null, "tofucraft:buggle"));
		return pool;
	}
}
