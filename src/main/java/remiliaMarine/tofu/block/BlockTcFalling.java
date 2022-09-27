package remiliaMarine.tofu.block;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTcFalling extends BlockFalling {

    public BlockTcFalling(Material materialIn)
    {
        super(materialIn);
    }

    @Override
    public BlockTcFalling setSoundType(SoundType sound) {
    	super.setSoundType(sound);
    	return this;
    }

}
