package remiliaMarine.tofu.world.loot;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class WeightedLootEntry {
	
	public ItemStack theItemStack;
	public int stackSizeMin;
	public int stackSizeMax;
	public int weight;
	
	public WeightedLootEntry(ItemStack stack, int stackSizeMin, int stackSizeMax, int weight) {
		this.theItemStack = stack;
		this.stackSizeMin = stackSizeMin;
		this.stackSizeMax = stackSizeMax;
		this.weight = weight;
	}
	
	public WeightedLootEntry(Item item, int meta, int stackSizeMin, int stackSizeMax, int weight) {
		this(new ItemStack(item, 1, meta), stackSizeMin, stackSizeMax, weight);
	}
	
	public ItemStack getItemStack(Random rand) {
		ItemStack result = this.theItemStack.copy();
		ItemStackAdapter.setSize(result, MathHelper.getRandomIntegerInRange(rand, this.stackSizeMin, this.stackSizeMax));
		return result;
	}

}
