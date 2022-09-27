package remiliaMarine.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class ItemYuba extends ItemTcFood {

    public ItemYuba(int par2, float par3, boolean par4)
    {
        super(par2, par3, par4);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stackIn, World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
    {
        RayTraceResult rayTrace = this.rayTrace(par2World, par3EntityPlayer, true);

        if (rayTrace == null)
        {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, stackIn);
        }
        else
        {
            if (rayTrace.typeOfHit == RayTraceResult.Type.BLOCK)
            {
            	BlockPos pos = rayTrace.getBlockPos();

                if (!par2World.isBlockModifiable(par3EntityPlayer, pos))
                {
                    return new ActionResult<ItemStack>(EnumActionResult.PASS, stackIn);
                }

                if (!par3EntityPlayer.canPlayerEdit(pos, rayTrace.sideHit, stackIn))
                {
                    return new ActionResult<ItemStack>(EnumActionResult.PASS, stackIn);
                }

            	IBlockState state = par2World.getBlockState(pos);
                if (state.getMaterial() == Material.WATER)
                {
                	Block block = state.getBlock();
                	if (state.equals(block.getDefaultState()) && par2World.isAirBlock(pos.up())){
                		par2World.setBlockState(pos.up(), TcBlocks.yuba.getDefaultState());

                    	if (!par3EntityPlayer.capabilities.isCreativeMode)
                    	{
                    		ItemStackAdapter.preDecr(stackIn);
                    	}
                	}
                }
            }

            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stackIn);
        }
    }

}
