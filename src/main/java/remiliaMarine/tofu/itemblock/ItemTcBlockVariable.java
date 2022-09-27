package remiliaMarine.tofu.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.block.ITcBlockVariable;

public class ItemTcBlockVariable extends ItemTcBlock {
    public ItemTcBlockVariable(Block block)
    {
        super(block);
        if(!(block instanceof ITcBlockVariable)) throw new IllegalArgumentException("ItemTcBlockVariable must be an ItemBlock of ITcBlockVariable. block class name: " + block.getClass().getName());
        this.setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata(int damage) {
        return damage;
    }
    
    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack stack)
    {
        int meta = stack.getItemDamage();
        
        ITcBlockVariable block = (ITcBlockVariable)this.getBlock();
        
        return super.getUnlocalizedName() + block.getVariantName(meta);
    }
}
