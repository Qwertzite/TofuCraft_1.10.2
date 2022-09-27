package remiliaMarine.tofu.item;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import remiliaMarine.tofu.init.TcBlocks;

public class ItemSoybeansHell extends ItemTcSeeds implements IPlantable {

    public ItemSoybeansHell()
    {
        super(TcBlocks.soybeanHell, Blocks.SOUL_SAND);
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Nether;
    }
}
