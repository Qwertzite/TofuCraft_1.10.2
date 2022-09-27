package remiliaMarine.tofu.block;

import java.util.List;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import remiliaMarine.tofu.init.TcItems;

public final class BlockMisoTofuBarrel extends BlockBarrelBase {

    public BlockMisoTofuBarrel(Material par3Material)
    {
        super(par3Material);
        this.setSoundType(SoundType.WOOD);
    }
    
    @Override
    public ItemStack getItem(World par1World, BlockPos pos, IBlockState state)
    {
        return new ItemStack(TcItems.barrelMisoTofu);
    }

    @Override
    public void addFermentedItem(List<ItemStack> list)
    {
        list.add(new ItemStack(TcItems.tofuMiso, 3));
        list.add(new ItemStack(TcItems.miso, 2));
    }

    @Override
    public void addIngredients(List<ItemStack> list)
    {
        list.add(new ItemStack(TcItems.tofuMomen, 3));
        list.add(new ItemStack(TcItems.miso, 3));
    }
	
	@Override
	public boolean checkEnvironment(IBlockAccess blockAccess, BlockPos pos) {
		return true;
	}

}
