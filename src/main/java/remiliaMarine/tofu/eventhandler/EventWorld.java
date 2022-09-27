package remiliaMarine.tofu.eventhandler;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.data.MorijioManager;

public class EventWorld {
    @SubscribeEvent
    public void onDataSave(WorldEvent.Save event)
    {
        MorijioManager morijioManager = TofuCraftCore.getMorijioManager();
        TofuCraftCore.getSaveHandler().saveMorijioData(morijioManager);
    }
}
