package remiliaMarine.tofu.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import remiliaMarine.tofu.item.TofuMaterial;

public class BlockTofuIshi extends BlockTofu {

    public BlockTofuIshi(TofuMaterial material)
    {
        super(material, SoundType.WOOD);
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return true;
    }
}
