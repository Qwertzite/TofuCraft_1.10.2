package remiliaMarine.tofu.versionAdapter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * for 1.10.2
 */
public class SlotAdapter {
	/**
	 * 1.10.2: void {@link Slot#onPickupFromSlot(EntityPlayer, ItemStack)}<br>
	 * 1.12.2: ItemStack {@link Slot#onTake(EntityPlayer, ItemStack)}
	 */
	public static void onPickUpFromSlot(Slot slot, EntityPlayer playerIn, ItemStack stack) { slot.onPickupFromSlot(playerIn, stack); }
	
	/**
	 * 1.10.2 int {@link Slot#xDisplayPosition}
	 * 1.12.2 int {@link Slot#xPos}
	 * @param slot
	 * @return display position of the inventory slot on the screen x axis 
	 */
	public static int getXDisplayPos(Slot slot) { return slot.xDisplayPosition; }
	
	/**
	 * 1.10.2 int {@link Slot#xDisplayPosition}
	 * 1.12.2 int {@link Slot#xPos}
	 * @param slot
	 * @param x
	 */
	public static void setXDisplayPos(Slot slot, int x) { slot.xDisplayPosition = x; }
	
	/**
	 * 1.10.2 int {@link Slot#yDisplayPosition}<br>
	 * 1.12.2 int {@link Slot#yPos}
	 * @param slot
	 * @return display position of the inventory slot on the screen y axis 
	 */
	public static int getYDisplayPos(Slot slot) { return slot.yDisplayPosition; }
	
	/**
	 * 1.10.2 int {@link Slot#yDisplayPosition}<br>
	 * 1.12.2 int {@link Slot#yPos}
	 * @param slot
	 * @param y
	 */
	public static void setYDisplayPos(Slot slot, int y) { slot.yDisplayPosition = y; }
}
