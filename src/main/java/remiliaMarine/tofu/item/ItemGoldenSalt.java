package remiliaMarine.tofu.item;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class ItemGoldenSalt extends TcItem {

	private Map<Integer, Entity> watchMobMap = new HashMap<Integer, Entity>();

	public ItemGoldenSalt()
	{
		super();
        this.setMaxStackSize(1);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity ownerEntity, int par4, boolean par5)
	{
		if (ownerEntity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)ownerEntity;
			ItemStack mainHand = player.getHeldItemMainhand();
			ItemStack offHand = player.getHeldItemOffhand();
			boolean isMainHandSalt = mainHand != null && mainHand.getItem() == this;
			boolean isOffHandSalt = offHand != null && offHand.getItem() == this;

			if ((isOffHandSalt && offHand.equals(itemstack) && !isMainHandSalt) ||
					(isMainHandSalt && mainHand.equals(itemstack)))
			{
				List<Entity> mobsNearby = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(8.0D, 4.0D, 8.0D));

				for (int i = 0; i < mobsNearby.size(); i++)
				{
                    Entity entity = (Entity)mobsNearby.get(i);

                    // Register the mobs nearby to the watch list
                    if (entity instanceof EntityMob || entity instanceof EntitySlime)
                    {
            	        watchMobMap.put(entity.getEntityId(), entity);
                    }
                }

                // Make mobs keep away
                Iterator<Integer> itrId = watchMobMap.keySet().iterator();
                while (itrId.hasNext())
                {
                	Integer id = itrId.next();
                	Entity entity = world.getEntityByID(id);

                	if (entity == null || !entity.isEntityAlive() || player.getDistanceToEntity(entity) >= 17.0D)
                	{
                		itrId.remove();
                		continue;
                	}

//                	double mobSpeed = MathHelper.sqrt_double(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);
//                	System.out.println("speed: " + mobSpeed);

                    double diffX = entity.posX - player.posX;
                    double diffZ = entity.posZ - player.posZ;
                    double diff1 = MathHelper.abs_max(diffX, diffZ);

                    if (diff1 >= 0.01D)
                    {
                    	diff1 = (double)MathHelper.sqrt_double(diff1);
                    	diffX /= diff1;
                    	diffZ /= diff1;
                        double var8 = 1.0D / (diff1 / 8.0D);

                        if (var8 > 1.0D)
                        {
                            var8 = 1.0D;
                        }

                        diffX *= var8 * 0.05D;
                        diffZ *= var8 * 0.05D;
                        double vecY = entity.isCollidedHorizontally ? 0.1D : 0.0D;
                        entity.addVelocity(diffX, vecY, diffZ);
                    }
                }

                // Damage the item
                if (world.getWorldTime() % 20 == 0) {
                	itemstack.damageItem(1, player);
                    if (ItemStackAdapter.getSize(itemstack) <= 0)
                    {
                    	EnumHand hand = isMainHandSalt ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
                        net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, itemstack, hand);
                        player.setHeldItem(hand, (ItemStack)null);
                    }
                }
			}
		}
	}

	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }
}
