package remiliaMarine.tofu.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.util.TcMathHelper;
import remiliaMarine.tofu.versionAdapter.block.TcBlock;

public class BlockTcOre extends TcBlock {
	
    private Item itemContained;
    private int itemDmgContained;
    @SuppressWarnings("unused")
	private int minXp;
    @SuppressWarnings("unused")
	private int maxXp;

    public BlockTcOre(int minXp, int maxXp)
    {
        this(Material.ROCK, minXp, maxXp);
    }
    
    public BlockTcOre(Material material, int minXp, int maxXp)
    {
        super(material);
        this.minXp = minXp;
        this.maxXp = maxXp;
    }
    
    public BlockTcOre setItemContained(ItemStack itemstack)
    {
        this.itemContained = itemstack.getItem();
        this.itemDmgContained = itemstack.getItemDamage();
        return this;
    }

    public BlockTcOre setItemContained(Item item)
    {
        this.itemContained = item;
        this.itemDmgContained = 0;
        return this;
    }
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.itemContained;
    }
    
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random rand)
    {
        return 1;
    }
    
    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    @Override
    public int quantityDroppedWithBonus(int fortune, Random rand)
    {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped((IBlockState)this.getBlockState().getValidStates().iterator().next(), rand, fortune))
        {
            int j = rand.nextInt(fortune + 2) - 1;

            if (j < 0)
            {
                j = 0;
            }

            return this.quantityDropped(rand) * (j + 1);
        }
        else
        {
            return this.quantityDropped(rand);
        }
    }
    
    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);

        if (this.getItemDropped(state, worldIn.rand, fortune) != Item.getItemFromBlock(this))
        {
            int j1 = TcMathHelper.getInt(worldIn.rand, 2, 5);
            this.dropXpOnBlockBreak(worldIn, pos, j1);
        }
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(IBlockState par1)
    {
        return itemDmgContained;
    }

}
