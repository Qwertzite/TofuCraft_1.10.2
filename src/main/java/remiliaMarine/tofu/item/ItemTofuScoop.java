package remiliaMarine.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import remiliaMarine.tofu.block.BlockTofuBase;
import remiliaMarine.tofu.block.ITofuScoopable;

public class ItemTofuScoop extends TcItem {

    public ItemTofuScoop()
    {
        super();
        this.setMaxDamage(352);
        this.setMaxStackSize(1);
        this.setFull3D();
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world, EntityPlayer player, EnumHand hand)
    {
        RayTraceResult mpos = this.rayTrace(world, player, false);

        if (mpos != null && mpos.typeOfHit == RayTraceResult.Type.BLOCK)
        {
        	BlockPos pos = mpos.getBlockPos();
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if (world.canMineBlockBody(player, pos) && block != null)
            {
                boolean isScoopable;
                if (block instanceof BlockTofuBase)
                {
                    isScoopable = ((BlockTofuBase) block).isScoopable();
                }
                else
                {
                    isScoopable = block instanceof ITofuScoopable;
                }
                if (isScoopable)
                {
                    itemstack.damageItem(1, player);
                    world.setBlockToAir(pos);

                    if (!world.isRemote)
                    {
                        ItemStack stack;
                        if (block instanceof BlockTofuBase)
                        {
                            stack = ((BlockTofuBase) block).createScoopedBlockStack();
                        }
                        else
                        {
                            stack = new ItemStack(block);
                        }
                        EntityItem drop = new EntityItem(world, mpos.hitVec.xCoord, mpos.hitVec.yCoord, mpos.hitVec.zCoord, stack);
                        world.spawnEntityInWorld(drop);
                    }
                    SoundType sound = block.getSoundType(state, world, pos, player);
                    player.playSound(sound.getBreakSound(), (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
                    return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
                }
            }
        }
        return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
    }
}
