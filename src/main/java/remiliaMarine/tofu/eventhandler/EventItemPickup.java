package remiliaMarine.tofu.eventhandler;

import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.achievement.TcAchievementMgr;

public class EventItemPickup {

    @SubscribeEvent
    public void onPlayerItemPickup(EntityItemPickupEvent event)
    {
        if (event.getItem().getEntityItem() != null)
        {
            TcAchievementMgr.achieveItemPickup(event.getItem().getEntityItem(), event.getEntityPlayer());
        }
    }
}
