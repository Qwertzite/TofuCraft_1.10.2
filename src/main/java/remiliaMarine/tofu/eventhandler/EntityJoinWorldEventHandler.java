package remiliaMarine.tofu.eventhandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.entity.EntityTofunian;
import remiliaMarine.tofu.init.TcVillages;

public class EntityJoinWorldEventHandler {
	
	@SubscribeEvent
	public void EntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(event.getEntity().dimension == TofuCraftCore.TOFU_DIMENSION.getId())
		{
			Entity entity = event.getEntity();
			if(entity.getClass() == EntityVillager.class)
			{
				World world = event.getWorld();
                EntityLiving tofunian = new EntityTofunian(world);
                tofunian.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
                tofunian.rotationYawHead = tofunian.rotationYaw;
                tofunian.renderYawOffset = tofunian.rotationYaw;
                world.spawnEntityInWorld(tofunian);
                event.setCanceled(true);
			}
		} else {
			Entity entity = event.getEntity();
			if(entity.getClass() == EntityVillager.class)
			{
				EntityVillager villager = (EntityVillager)entity;
				if (villager.getProfessionForge() == TcVillages.ProfessionTofunian) {
					World world = event.getWorld();
	                EntityLiving tofunian = new EntityTofunian(world);
	                tofunian.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
	                tofunian.rotationYawHead = tofunian.rotationYaw;
	                tofunian.renderYawOffset = tofunian.rotationYaw;
	                world.spawnEntityInWorld(tofunian);
	                event.setCanceled(true);
				}
			}
		}
	}
}
