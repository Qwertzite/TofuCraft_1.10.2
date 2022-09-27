package remiliaMarine.tofu.block;

import java.util.Random;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import remiliaMarine.tofu.creativetabs.TcCreativeTabs;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.item.ItemFoodSet;
import remiliaMarine.tofu.material.TcMaterial;

public class BlockTofuMinced extends BlockFalling implements ITofuScoopable {
	
    public BlockTofuMinced()
    {
        super(TcMaterial.TOFU);
        this.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
        this.setSoundType(SoundType.SNOW);
    }

    @Override
    public int quantityDropped(Random par1Random)
    {
        return 4;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return TcItems.foodSet;
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    @Override
    public int damageDropped(IBlockState state)
    {
        return ItemFoodSet.EnumSetFood.TOFU_MINCED.getId();
    }
}
