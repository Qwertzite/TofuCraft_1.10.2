package remiliaMarine.tofu.fishing;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.entity.EntityTofuCreeper;
import remiliaMarine.tofu.entity.EntityTofuSlime;
import remiliaMarine.tofu.fishing.TofuFishingLoot.TofuFishingEntryEntity;
import remiliaMarine.tofu.fishing.TofuFishingLoot.TofuFishingEntryEntity.ISpawnImpl;
import remiliaMarine.tofu.fishing.TofuFishingLoot.TofuFishingEntryItem;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemFoodSet;

public class TofuFishing {

    public TofuFishing()
    {
        TofuFishingLoot.addItem(new ItemStack(TcItems.tofuMomen), 50);
        TofuFishingLoot.addItem(new ItemStack(TcItems.tofuKinu), 50);
        TofuFishingLoot.addItem(new ItemStack(TcItems.tofuIshi), 30);
        TofuFishingLoot.addItem(new ItemStack(TcItems.tofuMetal), 10);
        TofuFishingLoot.addItem(new ItemStack(TcItems.tofuGrilled), 20);
        TofuFishingLoot.addItem(new ItemStack(TcItems.tofuDried), 20);
        TofuFishingLoot.addItem(new ItemStack(TcItems.tofuDiamond), 3);
        TofuFishingLoot.addItem(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.EnumSetFood.TOFUFISH_RAW.getId()), 20);

        TofuFishingLoot.addItem(new ItemStack(TcItems.swordKinu), 6);
        TofuFishingLoot.addItem(new ItemStack(TcItems.swordMomen), 6);
        TofuFishingLoot.addItem(new ItemStack(TcItems.swordSolid), 4);
        TofuFishingLoot.addItem(new ItemStack(TcItems.swordMetal), 2);
        TofuFishingLoot.addItem(new ItemStack(TcItems.swordDiamond), 1);

        TofuFishingLoot.addItem(new ItemStack(TcItems.toolKinu[2]), 6);
        TofuFishingLoot.addItem(new ItemStack(TcItems.toolMomen[2]), 6);
        TofuFishingLoot.addItem(new ItemStack(TcItems.toolSolid[2]), 4);
        TofuFishingLoot.addItem(new ItemStack(TcItems.toolMetal[2]), 2);
        TofuFishingLoot.addItem(new ItemStack(TcItems.toolDiamond[2]), 1);

        TofuFishingLoot.addEntity(20, new ISpawnImpl() {
            @Override
            public Entity spawnEntity(World world)
            {
                EntityTofuSlime mob = new EntityTofuSlime(world);
                return mob;
            }
        });
        TofuFishingLoot.addEntity(7, new ISpawnImpl() {
            @Override
            public Entity spawnEntity(World world)
            {
                EntityTofuCreeper mob = new EntityTofuCreeper(world);
                return mob;
            }
        });
    }
    
    @SubscribeEvent
    public void onEntityJoinWorldEvent(EntityJoinWorldEvent event)
    {
        if (event.getEntity().dimension != TofuCraftCore.TOFU_DIMENSION.getId())
        {
            return;
        }
        if (event.getEntity() instanceof EntityItem)
        {
            // Avoid error when the entity contains no item
            if (((EntityItem)event.getEntity()).getEntityItem() == null)
            {
                return;
            }

            ItemStack is = ((EntityItem) event.getEntity()).getEntityItem();
            if (is.getItem() != Items.FISH)
            {
                return;
            }

            for (EnumFacing dir : EnumFacing.values())
            {
                for (int j = 0; j <= 1; ++j)
                {
                    Block block = event.getWorld().getBlockState(new BlockPos((int) Math.round(event.getEntity().posX) + dir.getFrontOffsetX(),
                            (int) Math.round(event.getEntity().posY) + dir.getFrontOffsetY() - j, (int) Math.round(event.getEntity().posZ) + dir.getFrontOffsetZ())).getBlock();
                    if (block == TcBlocks.soymilkStill)
                    {
                        TofuFishingLoot entry = TofuFishingLoot.lot();

                        if (entry instanceof TofuFishingEntryItem)
                        {
                            EntityItem entityitem = new EntityItem(event.getWorld(), event.getEntity().posX, event.getEntity().posY,
                                    event.getEntity().posZ, ((TofuFishingEntryItem) entry).item.copy());
                            entityitem.motionX = event.getEntity().motionX;
                            entityitem.motionY = event.getEntity().motionY;
                            entityitem.motionZ = event.getEntity().motionZ;
                            event.getWorld().spawnEntityInWorld(entityitem);
                        }
                        else if (entry instanceof TofuFishingEntryEntity)
                        {
                            Entity mob = ((TofuFishingEntryEntity) entry).spawnImpl.spawnEntity(event.getWorld());
                            mob.setLocationAndAngles(event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ,
                                    event.getWorld().rand.nextFloat() * 360.0F, 0.0F);
                            mob.motionX = event.getEntity().motionX * 2;
                            mob.motionY = event.getEntity().motionY * 2;
                            mob.motionZ = event.getEntity().motionZ * 2;
                            event.getWorld().spawnEntityInWorld(mob);
                        }

                        event.getEntity().setDead();
                        event.setResult(Event.Result.DENY);
                        return;
                    }
                }
            }
        }
    }
    
}
