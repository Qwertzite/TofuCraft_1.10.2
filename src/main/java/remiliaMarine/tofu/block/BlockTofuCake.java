package remiliaMarine.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.init.TcItems;

public class BlockTofuCake extends BlockCake {

    public static final PropertyInteger BITES = PropertyInteger.create("bites", 0, 6);

    public BlockTofuCake(SoundType sound)
    {
        super();
        this.setSoundType(sound);
    }

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    @Override
    public ItemStack getItem(World par1World, BlockPos pos, IBlockState state)
    {
        return new ItemStack(TcItems.tofuCake);
    }

    @Override
    public Block disableStats()
    {
        return super.disableStats();
    }


}
