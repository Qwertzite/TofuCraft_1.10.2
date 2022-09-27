package remiliaMarine.tofu.versionAdapter;

import net.minecraft.tileentity.TileEntity;

/**
 * for 1.10.2
 */
public class TileEntityAdapter {
	/**
	 * 1.10.2: boolean {@link TileEntity#hasWorldObj()}<br>
	 * 1.12.2: boolean {@link TileEntity#hasWorld()}
	 * @param te
	 * @return
	 */
	public static boolean hasWorld(TileEntity te) { return te.hasWorldObj(); }
}
