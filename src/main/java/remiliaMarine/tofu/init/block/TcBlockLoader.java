package remiliaMarine.tofu.init.block;

import net.minecraft.block.Block;
import remiliaMarine.tofu.init.registry.BlockRegister;

public class TcBlockLoader {
    public <T extends Block> BlockRegister<T> $(String name, T block)
    {
        return new BlockRegister<T>(name, block);
    }
}
