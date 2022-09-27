package remiliaMarine.tofu.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.init.TcBlocks;

public class ItemTofuStick extends TcItem {
    public ItemTofuStick()
    {
        super();
        this.setMaxDamage(1);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        int currDim = playerIn.dimension;
        if (currDim == TofuCraftCore.TOFU_DIMENSION.getId() || currDim == 0)
        {
            if (!worldIn.isRemote)
            {
                RayTraceResult mpos = this.rayTrace(worldIn, playerIn, false);

                if (mpos != null && mpos.typeOfHit == RayTraceResult.Type.BLOCK)
                {
                	BlockPos hitPos = mpos.getBlockPos();
                    @SuppressWarnings("unused")
					boolean isSuccess = this.activate(itemStackIn, playerIn, worldIn, hitPos, mpos.sideHit);
                    //if(isSuccess) itemStackIn.stackSize--;
                }
            }
            else
            {
                // Emit particles
                for (int var1 = 0; var1 < 16; ++var1)
                {
                    double mx = (itemRand.nextFloat() * 2.0F - 1.0F);
                    double my = (itemRand.nextFloat() * 2.0F - 1.0F);
                    double mz = (itemRand.nextFloat() * 2.0F - 1.0F);
                    if (mx * mx + my * my + mz * mz <= 1.0D)
                    {
                        Vec3d lookVec = playerIn.getLookVec();
                        worldIn.spawnParticle(EnumParticleTypes.CRIT, playerIn.posX + lookVec.xCoord, playerIn.posY + lookVec.yCoord + playerIn.getEyeHeight(), playerIn.posZ + lookVec.zCoord, mx, my + 0.2f, mz);
                    }
                }
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }
    
    public boolean activate(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, BlockPos pos, EnumFacing par7)
    //var4 = x, var5 = y, var6 = z
    {
        /**
         * Get the Index of this Facing (0-5). The order is D-U-N-S-W-E
         */
    	pos = pos.offset(par7);

        if (!par2EntityPlayer.canPlayerEdit(pos, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
        	boolean flag = false;
            if (par3World.isAirBlock(pos))
            {
                flag = TcBlocks.tofuPortal.tryToCreatePortal(par3World, pos);
            }
            return flag;
        }
    }
    
    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.UNCOMMON;
    }
}
