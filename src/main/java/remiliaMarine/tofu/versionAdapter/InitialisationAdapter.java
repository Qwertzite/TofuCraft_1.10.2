package remiliaMarine.tofu.versionAdapter;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.EnumHelper;

/**
 * for MC 1.12.2
 */
public class InitialisationAdapter {
	
	public static Type getBiomeTypeTofu() {
		return EnumHelper.addEnum(BiomeDictionary.Type.class, "TOFU", new Class[] { BiomeDictionary.Type[].class }, new Object[]{ new BiomeDictionary.Type[0] });
	}
	
	public static WorldServer getServerForDimention(MinecraftServer server, int dim) {
		return server.worldServerForDimension(dim);
	}
}
