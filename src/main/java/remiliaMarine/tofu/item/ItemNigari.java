package remiliaMarine.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class ItemNigari extends Item {
    public ItemNigari()
    {
        super();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        RayTraceResult var4 = this.rayTrace(worldIn, playerIn, true);

        if (var4 == null)
        {
            return new ActionResult(EnumActionResult.PASS, itemStackIn);
        }
        else
        {
            if (var4.typeOfHit == RayTraceResult.Type.BLOCK)
            {
            	BlockPos targetPos = var4.getBlockPos();
                Block var11 = worldIn.getBlockState(targetPos).getBlock();
                Block var13 = null;
                
                if (var11 == TcBlocks.soymilkStill)
                {
                    var13 = TcBlocks.tofuKinu;
                }
                else if (var11 == TcBlocks.soymilkHellStill)
                {
                    var13 = TcBlocks.tofuHell;
                }
                
                if (var13 != null)
                {
                	worldIn.playSound(playerIn, targetPos.add(0.5, 0.5, 0.5), var13.getSoundType().getBreakSound(), SoundCategory.BLOCKS, (var13.getSoundType().getVolume() + 1.0F) / 2.0F, var13.getSoundType().getPitch() * 0.8F);
    
                	worldIn.setBlockState(targetPos, var13.getDefaultState());
                    
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                    	ItemStackAdapter.preDecr(itemStackIn);

                        ItemStack container = new ItemStack(this.getContainerItem());
        
                        if (ItemStackAdapter.getSize(itemStackIn) <= 0)
                        {
                            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, container);
                        }
        
                        if (!playerIn.inventory.addItemStackToInventory(container))
                        {
                        	playerIn.dropItem(container, false);
                        }
                    }
                }
            }
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
        }
    }
    
    public int getColorFromItemStack(ItemStack stack, int renderPass){
        switch(renderPass){
            case 0:return 0x809cff;
            default:return 0x809cff;
        }
    }
    
    
}
