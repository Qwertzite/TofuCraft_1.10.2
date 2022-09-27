package remiliaMarine.tofu.init.block;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.util.ItemUtils;
import remiliaMarine.tofu.util.ModLog;

public class LoaderExternalBlock {

	public void load(FMLPreInitializationEvent event) {
        if (Loader.isModLoaded("BambooMod"))
        {
            TcBlocks.sakuraLeaves = ItemUtils.getExternalModBlockWithRegex("(?i)sakuraleaves");
            ModLog.debug("sakuraLeaves: " + TcBlocks.sakuraLeaves);
        }
	}

}
