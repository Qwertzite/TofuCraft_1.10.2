package remiliaMarine.tofu.versionAdapter;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * for 1.10.2
 */
public class WorldAdapter {
	/**
	 * 1.10.2: boolean {@link World#spawnEntityInWorld(Entity)}<br>
	 * 1.12.2: boolean {@link World#spawnEntity(Entity)}
	 * @param world
	 * @param entityIn
	 * @return
	 */
	public static boolean spawnEntity(World world, Entity entityIn) {
		return world.spawnEntityInWorld(entityIn);
	}
}
