package remiliaMarine.tofu.versionAdapter.block;

import java.util.List;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * for 1.10.2
 */
public abstract class TcBlockLeaves extends BlockLeaves {
	
	/**
	 * 1.10.2: Block#createStackedBlock(IBlockstate state)<br>
	 * 1.12.2: Block#getSilkTouchDrop(IBlockState state)
	 */
    protected ItemStack getSilkTouchDrop(IBlockState state) {
    	return super.createStackedBlock(state);
    }
    
    @Override
    protected ItemStack createStackedBlock(IBlockState state) {
    	return this.getSilkTouchDrop(state);
    }
    
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)<br>
     * 1.10.2: Block#getSubBlocks(Item, CreativeTabs, List<ItemStack)<br>
     * 1.12.2: Block.getSubBlocks(CreativeTabs, NonNullList<ItemStack>)
     */
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, List<ItemStack> list)
    {
    	super.getSubBlocks(Item.getItemFromBlock(this), tab, list);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
    	this.getSubBlocks(tab, list);
    }

}
