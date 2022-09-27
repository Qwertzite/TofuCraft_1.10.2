package remiliaMarine.tofu.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import remiliaMarine.tofu.block.BlockMorijio;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.tileentity.TileEntityMorijio;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class ItemMorijio extends TcItem {

    public ItemMorijio()
    {
        super();
        this.setMaxDamage(0);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (facing == EnumFacing.DOWN)
        {
            return EnumActionResult.PASS;
        }
        else if (!worldIn.getBlockState(pos).getMaterial().isSolid())
        {
            return EnumActionResult.PASS;
        }
        else
        {
            if (facing == EnumFacing.UP)
            {
                pos = pos.up();
            }

            if (facing == EnumFacing.NORTH)
            {
                pos = pos.north();
            }

            if (facing == EnumFacing.SOUTH)
            {
                pos = pos.south();
            }

            if (facing == EnumFacing.WEST)
            {
                pos = pos.west();
            }

            if (facing == EnumFacing.EAST)
            {
                pos = pos.east();
            }

            if (!playerIn.canPlayerEdit(pos, facing, stack))
            {
                return EnumActionResult.PASS;
            }
            else if (!TcBlocks.morijio.canPlaceBlockAt(worldIn, pos))
            {
                return EnumActionResult.PASS;
            }
            else
            {

            	worldIn.setBlockState(pos, TcBlocks.morijio.getDefaultState().withProperty(BlockMorijio.FACING, facing), 3);
                int var11 = 0;

                if (facing == EnumFacing.UP)
                {
                    var11 = MathHelper.floor_double((double)(playerIn.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15;
                }

                TileEntity var12 = worldIn.getTileEntity(pos);

                if (var12 != null && var12 instanceof TileEntityMorijio)
                {
                    ((TileEntityMorijio)var12).setRotation(var11);
                }
                
                ItemStackAdapter.preDecr(stack);
                return EnumActionResult.SUCCESS;
            }
        }
    }






}
