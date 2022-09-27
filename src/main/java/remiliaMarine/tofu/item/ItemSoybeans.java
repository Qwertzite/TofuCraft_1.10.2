package remiliaMarine.tofu.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.achievement.TcAchievementMgr.Key;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class ItemSoybeans extends TcItem implements IPlantable {
    public ItemSoybeans()
    {
        super();
    }
	
    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public EnumActionResult onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, BlockPos pos,EnumHand hand, EnumFacing facing, float par8, float par9, float par10)
    {
    	//BlockPos blockPos = new BlockPos(par4, par5, par6);
    	//EnumFacing facing = par7
        if (facing != EnumFacing.UP)
        {
            return EnumActionResult.PASS;
        }
        else if (par2EntityPlayer.canPlayerEdit(pos, facing, par1ItemStack) && par2EntityPlayer.canPlayerEdit(pos.up(), facing, par1ItemStack))
        {
            IBlockState soil = par3World.getBlockState(pos);

            if (soil != null && par3World.isAirBlock(pos.up()))
            {
                boolean isPlanted = false;
                if (soil.getBlock().canSustainPlant(soil, par3World, pos, EnumFacing.UP, this))
                {
                    par3World.setBlockState(pos.up(), TcBlocks.soybean.getDefaultState());
                    isPlanted = true;
                }
                else if (soil.getBlock() == Blocks.WOOL)
                {
                    par3World.setBlockState(pos.up(), TcBlocks.sprouts.getDefaultState());
                    
                    TcAchievementMgr.achieve(par2EntityPlayer, Key.sproutPlanting);
                    isPlanted = true;
                }
                if (isPlanted)
                {
                	ItemStackAdapter.preDecr(par1ItemStack);
                    return EnumActionResult.SUCCESS;
                }
                else
                {
                    return EnumActionResult.PASS;
                }
            }
            else
            {
                return EnumActionResult.PASS;
            }
        }
        else
        {
            return EnumActionResult.PASS;
        }
    }
    
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        return TcBlocks.soybean.getDefaultState();
    }

//    @Override
//    public int getPlantMetadata(IBlockAccess world, BlockPos pos)
//    {
//        return 0;
//    }
}
