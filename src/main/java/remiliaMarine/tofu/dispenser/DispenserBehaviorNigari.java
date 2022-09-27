package remiliaMarine.tofu.dispenser;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.versionAdapter.ItemStackAdapter;

public class DispenserBehaviorNigari extends BehaviorDefaultDispenseItem {
	
    private final BehaviorDefaultDispenseItem defaultDispenserItemBehavior = new BehaviorDefaultDispenseItem();

    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    @SuppressWarnings("deprecation")
	@Override
    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {
        EnumFacing enumfacing = par1IBlockSource.getBlockState().getValue(BlockDispenser.FACING); // getFacing
        World world = par1IBlockSource.getWorld();
        BlockPos pos = par1IBlockSource.getBlockPos();
        BlockPos posoffset = pos.offset(enumfacing);//i, j, k
        Block block = world.getBlockState(posoffset).getBlock();
        //Material material = world.getBlock(i, j, k).getMaterial();
        //int l = world.getBlockMetadata(i, j, k);
        Item item;
        Block blockToSet = null;

        if (block == TcBlocks.soymilkStill)
        {
            blockToSet = TcBlocks.tofuKinu;
        }
        else if (block == TcBlocks.soymilkHellStill)
        {
            blockToSet = TcBlocks.tofuHell;
        }

        if (blockToSet != null)
        {
            world.setBlockState(posoffset, blockToSet.getDefaultState());
            item = Items.GLASS_BOTTLE;

            ItemStackAdapter.addSize(par2ItemStack, -1);
            if (ItemStackAdapter.getSize(par2ItemStack) == 0)
            {
                par2ItemStack.setItem(item); // setItem
                ItemStackAdapter.setSize(par2ItemStack, 1);
            }
            else if (((TileEntityDispenser)par1IBlockSource.getBlockTileEntity()).addItemStack(new ItemStack(item)) < 0) // addItem
            {
                this.defaultDispenserItemBehavior.dispense(par1IBlockSource, new ItemStack(item));
            }
        }
        else
        {
            super.dispenseStack(par1IBlockSource, par2ItemStack);
        }

        return par2ItemStack;
    }
}
