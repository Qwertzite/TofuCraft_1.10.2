package remiliaMarine.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.util.ModLog;

public class ItemTofuHoe extends ItemHoe {

    public ItemTofuHoe(ToolMaterial material)
    {
        super(material);
        MinecraftForge.EVENT_BUS.register(this);
        ModLog.debug("TofuHoe event has been registered");
    }

    @SubscribeEvent
    public void onUseTofuHoe(UseHoeEvent event)
    {
        ItemStack hoe = event.getCurrent();
        World world = event.getWorld();
        BlockPos pos = event.getPos();

        if (hoe.getItem() == TcItems.tofuHoe)
        {
            Block block = world.getBlockState(pos).getBlock();

            if (world.isAirBlock(pos.up()) && (block == TcBlocks.tofuMomen || block == TcBlocks.tofuTerrain))
            {
                Block block1 = TcBlocks.tofuFarmland;
                IBlockState state1 = block1.getDefaultState();
                SoundType soundType = block1.getSoundType(state1, world, pos, null);
                world.playSound(event.getEntityPlayer(), pos,
                		soundType.getStepSound(), SoundCategory.BLOCKS,
                        (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);

                if (world.isRemote)
                {
                    event.setResult(Event.Result.DENY);
                }
                else
                {
                    world.setBlockState(pos, state1);
                    event.setResult(Event.Result.ALLOW);
                }
            }
        }
    }

    @Override
    public void onCreated(ItemStack p_77622_1_, World p_77622_2_, EntityPlayer p_77622_3_)
    {
        TcAchievementMgr.achieve(p_77622_3_, TcAchievementMgr.Key.tofunianHoe);
    }

}
