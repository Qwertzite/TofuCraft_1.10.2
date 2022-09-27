package remiliaMarine.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import remiliaMarine.tofu.block.IBlockTofuMaterial;
import remiliaMarine.tofu.itemblock.ItemTcBlock;
import remiliaMarine.tofu.util.Utils;

@SuppressWarnings("deprecation")
public class ItemBlockTofuMaterial extends ItemTcBlock {
    public ItemBlockTofuMaterial(Block par1)
    {
        super(par1);
    }

	@Override
    public String getItemStackDisplayName(ItemStack p_77653_1_)
    {
        String tofuMaterialName = ((IBlockTofuMaterial)this.block)
        		.getTofuMaterial()
        		.getName();
        return (I18n.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(p_77653_1_) + ".name",
        		I18n.translateToLocal("item.tofucraft:tofu" + Utils.capitalize(tofuMaterialName) + ".name"))).trim();
    }
}
