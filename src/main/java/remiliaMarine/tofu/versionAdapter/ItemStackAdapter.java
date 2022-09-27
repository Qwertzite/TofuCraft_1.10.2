package remiliaMarine.tofu.versionAdapter;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
/**
 * for MC 1.10.2
 */
public class ItemStackAdapter {
	public static int getSize(ItemStack stack) {
		return stack.stackSize;
	}
	
	public static void setSize(ItemStack stack, int num) {
		stack.stackSize = num;
	}
	
	public static void addSize(ItemStack stack, int num) {
		stack.stackSize += num;
	}
	
	public static int preDecr(ItemStack stack) {
		ItemStackAdapter.addSize(stack, -1);
		return ItemStackAdapter.getSize(stack);
	}
	
	/**
	 * 1.10.2: S ItemStack {@link ItemStack#loadItemStackFromNBT(NBTTagCompound)}<br>
	 * 1.12.2: C ItemStack {@link ItemStack#ItemStack(NBTTagCompound)}
	 * @param nbt
	 * @return new ItemStack
	 */
	public static ItemStack loadFromNBT(NBTTagCompound nbt) { return ItemStack.loadItemStackFromNBT(nbt); }
}
