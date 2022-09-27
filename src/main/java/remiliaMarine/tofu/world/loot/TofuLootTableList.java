package remiliaMarine.tofu.world.loot;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;
import remiliaMarine.tofu.TofuCraftCore;

public class TofuLootTableList {
	
    private static final Set<ResourceLocation> LOOT_TABLES = Sets.<ResourceLocation>newHashSet();

    public static final ResourceLocation EMPTY = TofuLootTableList.register("empty");
    public static final ResourceLocation TOFU_DUNGEON = TofuLootTableList.register("chests/tofu_dungeon");

    
    
    //Entity
    public static final ResourceLocation TOFU_CREEPER = TofuLootTableList.register("entities/tofu_creeper");
    public static final ResourceLocation TOFU_SLIME = TofuLootTableList.register("entities/tofu_slime");
    
    private static ResourceLocation register(String id)
    {
        return register(new ResourceLocation(TofuCraftCore.MODID, id));
    }

    public static ResourceLocation register(ResourceLocation id)
    {
        if (LOOT_TABLES.add(id))
        {
            return id;
        }
        else
        {
            throw new IllegalArgumentException(id + " is already a registered built-in loot table. tc");
        }
    }

}
