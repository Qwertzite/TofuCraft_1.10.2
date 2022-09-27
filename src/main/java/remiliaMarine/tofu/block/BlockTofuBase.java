package remiliaMarine.tofu.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.item.TofuMaterial;
import remiliaMarine.tofu.material.TcMaterial;
import remiliaMarine.tofu.versionAdapter.block.TcBlock;

public abstract class BlockTofuBase extends TcBlock {
    public BlockTofuBase()
    {
        super(TcMaterial.TOFU);
    }
    
    public BlockTofuBase(TofuMaterial tofuMaterial)
    {
        super(tofuMaterial.getBlockInfo().material);
        tofuMaterial.getBlockInfo().setBasicFeature(this);
        TcBlocks.tofuBlockMap.put(tofuMaterial, this);
    }
    
    /** Whether the tofu can be scooped with Tofu Scoop */
    private boolean scoopable = true;
    
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 4;
    }
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    abstract public Item getItemDropped(IBlockState state, Random par2Random, int par3);
    
    public BlockTofuBase setScoopable(boolean b)
    {
        this.scoopable = b;
        return this;
    }

    public boolean isScoopable()
    {
        return this.scoopable;
    }

    public ItemStack createScoopedBlockStack()
    {
        return new ItemStack(this);
    }
}
