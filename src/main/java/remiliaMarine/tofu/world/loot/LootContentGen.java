package remiliaMarine.tofu.world.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class LootContentGen {
	
	private List<WeightedLootEntry> entries = new ArrayList<WeightedLootEntry>();
	private int totalWeight;
	public int stackNumMin;
	public int stackNumMax;
	
	public LootContentGen(WeightedLootEntry[] entries, int stackNumMin, int stackNumMax) {
		
		for (WeightedLootEntry entry: entries) {
			this.addEntry(entry);
		}
		this.stackNumMin = stackNumMin;
		this.stackNumMax = stackNumMax;
	}
	
	public void addEntry(WeightedLootEntry entry) {
		this.entries.add(entry);
		this.totalWeight += entry.weight;
	}
	
	public void removeEntry(int index) {
		WeightedLootEntry removed = this.entries.remove(index);
		this.totalWeight -= removed.weight;
	}
	
	public void generateChestContent(Random rand, IInventory inventory) {
		
		int stackNum = MathHelper.getRandomIntegerInRange(rand, this.stackNumMin, this.stackNumMax);
		ItemStack[] lootArray = new ItemStack[stackNum];
		
		for(int i = 0; i < stackNum; i++) {
			lootArray[i] = this.getEntryWeighted(rand).getItemStack(rand);
		}
        for (ItemStack item : lootArray)
        {
            inventory.setInventorySlotContents(rand.nextInt(inventory.getSizeInventory()), item);
        }
	}
	
	public WeightedLootEntry getEntryWeighted(Random rand) {
		
		int threshhold = rand.nextInt(this.totalWeight);
		
		for(int i = 0, currentWeight = 0; i < this.entries.size(); i++) {
			currentWeight += this.entries.get(i).weight;
			if(threshhold < currentWeight) {
				return this.entries.get(i);
			}
		}
		System.out.println("flaw in getEntry");
		return this.entries.get(this.entries.size() - 1);
	}
	
}
