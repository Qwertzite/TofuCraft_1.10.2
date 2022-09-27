package remiliaMarine.tofu.util;

import com.google.common.base.Strings;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


/**
 * Utility Methods
 *
 * @author Tsuteto
 *
 */
public class Utils {

    public static NBTTagCompound getNBTPlayerPersisted(EntityPlayer player)
    {
        NBTTagCompound nbt = player.getEntityData();

        if (!nbt.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
        {
            nbt.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
        }

        return nbt.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
    }

    /**
     * Get Tofu World seed from the world seed
     *
     * @param world
     * @return
     */
    public static long getSeedForTofuWorld(World world)
    {
        long upper = (world.getSeed() & 31) << 60;
        long lower = world.getSeed() >>> 4;
        return upper | lower;
    }

    public static long generateRandomFromCoord(BlockPos pos)
    {
        long i1 = (long) (pos.getX() * 3129871) ^ (long) pos.getY() * 116129781L ^ (long) pos.getZ();
        i1 = i1 * i1 * 42317861L + i1 * 11L + i1;
        return i1 >> 16;
    }

    public static String capitalize(String s)
    {
        if (Strings.isNullOrEmpty(s))
        {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + (s.length() >= 2 ? s.substring(1) : "");
    }




    // === === //
    /***/
    public static int getIdFromEquipmentSlot(EntityEquipmentSlot slot) {
    	switch(slot) {
    	case HEAD: return 0;
    	case CHEST: return 1;
    	case LEGS: return 2;
    	case FEET: return 3;
    	default: throw new IllegalArgumentException("not wearable slot");
    	}
    }

    public static EntityEquipmentSlot getSlotFromInt(int i) {
    	switch(i){
    	case 0:
    		return EntityEquipmentSlot.HEAD;
    	case 1:
    		return EntityEquipmentSlot.CHEST;
    	case 2:
    		return EntityEquipmentSlot.LEGS;
    	default:
    		return EntityEquipmentSlot.FEET;
    	}

    }

}