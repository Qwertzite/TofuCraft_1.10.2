package remiliaMarine.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ItemDiamondTofuAxe extends ItemTcAxe {

    private DiamondTofuToolHandler impl;

    public ItemDiamondTofuAxe(ToolMaterial par2EnumToolMaterial, float damage, float speed)
    {
        super(par2EnumToolMaterial, damage, speed);
        this.impl = new DiamondTofuToolHandler(this);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer owner)
    {
		if (!owner.worldObj.isRemote) {
			Block blockDestroyed = owner.getEntityWorld().getBlockState(pos).getBlock();
			this.impl.onBlockStartBreak(stack, owner.worldObj, blockDestroyed, pos, owner);
		}
		return false;
    }
}
