package remiliaMarine.tofu.eventhandler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.data.DataType;
import remiliaMarine.tofu.data.EntityInfo;
import remiliaMarine.tofu.data.MorijioManager;
import remiliaMarine.tofu.data.PortalTripInfo;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.util.ModLog;

public class EventEntityLiving {

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event)
    {
        EntityInfo pinfo = EntityInfo.instance();
        Entity entity = event.getEntity();
        int entityId = entity.getEntityId();

        if (!entity.worldObj.isRemote && entity.worldObj instanceof WorldServer)
        {
            if (pinfo.doesDataExist(entityId, DataType.TicksPortalCooldown))
            {
                PortalTripInfo info = pinfo.get(entityId, DataType.TicksPortalCooldown);
                if (!(entity.worldObj instanceof WorldServer)) ModLog.debug(entity.worldObj.getClass().getSimpleName());
                if (entity.addedToChunk && entity.worldObj.isBlockLoaded(new BlockPos(entity.posX, entity.posY, entity.posZ)))
                {
                    int ticks = info.ticksCooldown;
                    if (ticks >= 20)
                    {
                        pinfo.remove(entityId, DataType.TicksPortalCooldown);
                    }
                    else
                    {
                        info.ticksCooldown += 1;
                    }
                    ModLog.debug("cooldown: %d", info.ticksCooldown);
                }
            }
        }
    }

    @SubscribeEvent
    public void onSpawn(LivingSpawnEvent.CheckSpawn event)
    {
        World world = event.getWorld();
        EntityLivingBase living = event.getEntityLiving();

        if (living instanceof IMob)
        {
            int tileX = (int)event.getX();
            int tileY = (int)event.getY();
            int tileZ = (int)event.getZ();

            MorijioManager morijioManager = TofuCraftCore.getMorijioManager();
            if (morijioManager.isInRangeOfMorijio(world, tileX, tileY, tileZ, living.dimension))
            {
                event.setResult(Event.Result.DENY);
            }
        }
    }


    @SubscribeEvent
    public void onHurt(LivingHurtEvent event)
    {
        if (event.getAmount() > 0.0f)
        {
            EntityLivingBase entityHurt = event.getEntityLiving();
            int diamondArmors = 0;

            for (ItemStack armor : entityHurt.getArmorInventoryList())
            {
                if (armor != null && ArrayUtils.contains(TcItems.armorDiamond, armor.getItem())) diamondArmors++;
            }

            if (diamondArmors > 0)
            {
                float f1 = event.getAmount();
                boolean isBlocking = entityHurt instanceof EntityPlayer && ((EntityPlayer)entityHurt).isActiveItemStackBlocking();

                if (!event.getSource().isUnblockable() && isBlocking)
                {
                    f1 = (1.0F + f1) * 0.5F;
                }

                ItemStack[] armorStack;
                {
                	List<ItemStack> tmp = new ArrayList<ItemStack>();
                	for(ItemStack stack : entityHurt.getArmorInventoryList()) {
                		tmp.add(stack);
                	}
                	armorStack = new ItemStack[tmp.size()];
                	int i = 0;
                	for(ItemStack stack : tmp) {
                		armorStack[i] = stack;
                		i++;
                	}
                }
                float f2 = ISpecialArmor.ArmorProperties.applyArmor(entityHurt, armorStack, event.getSource(), (double)f1);

                if (event.getSource().getSourceOfDamage() != null)
                {
                    float dmgReflected = (f1 - f2) * 0.5f;
                    ModLog.debug("Damage reflected: %f to %s", dmgReflected, event.getSource().getSourceOfDamage().getCommandSenderEntity().getName());
                    event.getSource().getSourceOfDamage().attackEntityFrom(
                            DamageSource.causeIndirectMagicDamage(event.getSource().getSourceOfDamage(), entityHurt),
                            dmgReflected);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEndermanTeleporting(EnderTeleportEvent event)
    {
        World world = event.getEntityLiving().worldObj;
        EntityLivingBase living = event.getEntityLiving();
        int tileX = (int)event.getTargetX();
        int tileY = (int)event.getTargetY();
        int tileZ = (int)event.getTargetZ();

        if (!world.isRemote)
        {
            MorijioManager morijioManager = TofuCraftCore.getMorijioManager();
            if (morijioManager.isInRangeOfMorijio(world, tileX, tileY, tileZ, living.dimension))
            {
                if (living instanceof EntityEnderman)
                {
                    ((EntityEnderman) living).setAttackTarget(null);
                }
                event.setCanceled(true);
            }
        }
    }
}
