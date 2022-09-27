package remiliaMarine.tofu.item;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.network.PacketDispatcher;
import remiliaMarine.tofu.network.packet.PacketSomenScooping;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class ItemSomenTsuyuBowl extends ItemSoupBase {

    public ItemSomenTsuyuBowl(int par2, float par3, boolean par4)
    {
        super(par2, par3, par4);
        this.setAlwaysEdible();
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.DRINK;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        return new ItemStack(TcItems.materials, 1, ItemTcMaterials.EnumTcMaterialInfo.glassBowl.getMetadata());
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if (worldIn.isRemote)
        {
            RayTraceResult mop = this.getEntityItemPlayerPointing(1.0F);
            //ModLog.debug(mop == null ? null : mop.entityHit);
            if (mop != null && mop.entityHit != null)
            {
                PacketDispatcher.packet(new PacketSomenScooping(mop.entityHit.getEntityId())).sendToServer();
                stackIn = scoopSomen(mop.entityHit, stackIn, playerIn, true);
            }
            else
            {
                return super.onItemRightClick(stackIn, worldIn, playerIn, hand);
            }
        }
        else
        {
            return super.onItemRightClick(stackIn, worldIn, playerIn, hand);
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, stackIn);
    }

    public static ItemStack scoopSomen(Entity entity, ItemStack itemHeld, EntityPlayer player, boolean isRemote)
    {
        if (entity instanceof EntityItem)
        {
            ItemStack scooped = ((EntityItem) entity).getEntityItem();

            if (itemHeld != null && itemHeld.getItem() == TcItems.somenTsuyuBowl
                    && scooped.getItem() == TcItems.materials && scooped.getItemDamage() == ItemTcMaterials.EnumTcMaterialInfo.tofuSomen.getMetadata())
            {
                if (!isRemote)
                {
                    if (ItemStackAdapter.preDecr(scooped) <= 0)
                    {
                        entity.setDead();
                    }
                }

                ItemStackAdapter.addSize(itemHeld, -1);

                ItemStack newItem = new ItemStack(TcItems.foodSet, 1, ItemFoodSet.EnumSetFood.TOFU_SOMEN.getId());

                if (ItemStackAdapter.getSize(itemHeld) <= 0)
                {
                    return newItem;
                }

                if (!player.inventory.addItemStackToInventory(newItem))
                {
                    player.dropItem(newItem, false);
                }
            }
        }
        return itemHeld;
    }

    public RayTraceResult getEntityItemPlayerPointing(float par1)
    {
        Minecraft mc = FMLClientHandler.instance().getClient();

        mc.pointedEntity = null;
        double d0 = (double) mc.playerController.getBlockReachDistance();
        mc.objectMouseOver = mc.getRenderViewEntity().rayTrace(d0, par1);
        double d1 = d0;
        Vec3d vec3d = mc.getRenderViewEntity().getPositionEyes(par1);

        if (mc.playerController.extendedReach())
        {
            d0 = 6.0D;
            d1 = 6.0D;
        } else
        {
            if (d0 > 3.0D)
            {
                d1 = 3.0D;
            }

            d0 = d1;
        }

        if (mc.objectMouseOver != null)
        {
            d1 = mc.objectMouseOver.hitVec.distanceTo(vec3d);
        }

        Vec3d vec31 = mc.getRenderViewEntity().getLook(par1);
        Vec3d vec32 = vec3d.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
        Entity pointedEntity = null;
        Vec3d vec33 = null;
        float f1 = 1.0F;
        List<EntityItem> list = mc.theWorld.getEntitiesWithinAABB(EntityItem.class, mc.getRenderViewEntity().getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double) f1, (double) f1, (double) f1));
        double d2 = d1;

        for (int i = 0; i < list.size(); ++i)
        {
            Entity entity = (Entity) list.get(i);

            float f2 = entity.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expandXyz((double) f2);
            RayTraceResult rayTraceResult = axisalignedbb.calculateIntercept(vec3d, vec32);

            if (axisalignedbb.isVecInside(vec3d))
            {
                if (d2 >= 0.0D)
                {
                    pointedEntity = entity;
                    vec33 = rayTraceResult == null ? vec3d : rayTraceResult.hitVec;
                    d2 = 0.0D;
                }
            } else if (rayTraceResult != null)
            {
                double d3 = vec3d.distanceTo(rayTraceResult.hitVec);

                if (d3 < d2 || d2 == 0.0D)
                {
                    pointedEntity = entity;
                    vec33 = rayTraceResult.hitVec;
                    d2 = d3;
                }
            }
        }

        if (pointedEntity != null && (d2 < d1))
        {
            return new RayTraceResult(pointedEntity, vec33);
        }
        return null;
    }


}
