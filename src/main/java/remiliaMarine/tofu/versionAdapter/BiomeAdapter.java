package remiliaMarine.tofu.versionAdapter;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

/**
 * for 1.10.2
 */
public class BiomeAdapter {
	
	/**
     * Gets a floating point representation of this biome's temperature (1.10.2)<br>
     * Gets the current temperature at the given location, based off of the default for this biome, the elevation of the
     * position, and {@linkplain #TEMPERATURE_NOISE} some random perlin noise. (1.12.2)<br>
     * 1.10.2: {@link Biome#getFloatTemperature(BlockPos)}<br>
     * 1.12.2: {@link Biome#getTemperature(BlockPos)}
	 */
	public static float getTamperature(Biome biome, BlockPos pos) {
		return biome.getFloatTemperature(pos);
	}
}
