package remiliaMarine.tofu.init.item;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import remiliaMarine.tofu.util.ItemUtils;
import remiliaMarine.tofu.init.TcItems;

public class LoaderExternalItem {
    /**
     * === External Mod Items ===
     */
	public void load(FMLPreInitializationEvent event) {
        if (Loader.isModLoaded("IC2"))
        {
            TcItems.plantBall = ItemUtils.getIc2Item("plantBall");
        }

        if (Loader.isModLoaded("BambooMod"))
        {
            TcItems.bambooBasket = ItemUtils.getExternalModItemWithRegex("(?i)bamboobasket");
            TcItems.bambooFood = ItemUtils.getExternalModItemWithRegex("(?i)bamboofoods?");
        }
		
	}

}
