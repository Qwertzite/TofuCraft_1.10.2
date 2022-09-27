package remiliaMarine.tofu.itemblock;

import net.minecraft.block.Block;

public class ItemBlockTcLeaves extends ItemTcBlockVariable {

    public ItemBlockTcLeaves(Block par1)
    {
        super(par1);
    }
    
    /**
     * Returns the metadata of the block register this Item (ItemBlock) can place
     */
    @Override
    public int getMetadata(int par1)
    {
        return par1 | 4;
    }
}
