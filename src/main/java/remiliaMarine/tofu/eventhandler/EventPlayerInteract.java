package remiliaMarine.tofu.eventhandler;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcVillages;

public class EventPlayerInteract {

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickItem event)
    {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack equippedItem = event.getItemStack();
        if (equippedItem != null && equippedItem.getItem() == Items.GLASS_BOTTLE)
        {
        	RayTraceResult raytrace = rayTrace(player.getEntityWorld(), player, true);

        	if(raytrace != null && raytrace.typeOfHit == RayTraceResult.Type.BLOCK)
        	{
	            Block block = player.worldObj.getBlockState(raytrace.getBlockPos()).getBlock();
	            if (block == TcBlocks.soymilkStill
	                    || block == TcBlocks.soymilkHellStill
	                    || block == TcBlocks.soySauceStill)
	            {
	                event.setCanceled(true);
	            }
        	}
        }
    }

	@SubscribeEvent
    public void onPlayerEntityInteract(PlayerInteractEvent.EntityInteractSpecific event)
    {
        if (event.getTarget() instanceof EntityVillager)
        {
            EntityVillager villager = (EntityVillager)event.getTarget();
            if (villager.getProfessionForge() == TcVillages.ProfessionTofuCook)
            {
                TcAchievementMgr.achieve(event.getEntityPlayer(), TcAchievementMgr.Key.tofuCook);
            }
            if (villager.getProfessionForge() == TcVillages.ProfessionTofunian)
            {
                TcAchievementMgr.achieve(event.getEntityPlayer(), TcAchievementMgr.Key.tofunian);
            }
        }
    }

    protected RayTraceResult rayTrace(World worldIn, EntityPlayer playerIn, boolean useLiquids)
    {
        float pitch = playerIn.rotationPitch;
        float yaw = playerIn.rotationYaw;
        double posx = playerIn.posX;
        double posy = playerIn.posY + (double)playerIn.getEyeHeight();
        double posz = playerIn.posZ;
        Vec3d eyevec = new Vec3d(posx, posy, posz);
        float f2 = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f3 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f4 = -MathHelper.cos(-pitch * 0.017453292F);
        float f5 = MathHelper.sin(-pitch * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double range = 5.0D;
        if (playerIn instanceof net.minecraft.entity.player.EntityPlayerMP)
        {
            range = ((net.minecraft.entity.player.EntityPlayerMP)playerIn).interactionManager.getBlockReachDistance();
        }
        Vec3d vec3d1 = eyevec.addVector((double)f6 * range, (double)f5 * range, (double)f7 * range);
        return worldIn.rayTraceBlocks(eyevec, vec3d1, useLiquids, !useLiquids, false);
    }
}
