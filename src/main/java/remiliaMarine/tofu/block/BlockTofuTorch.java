package remiliaMarine.tofu.block;

import net.minecraft.block.BlockTorch;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.item.TofuMaterial;
import remiliaMarine.tofu.util.Utils;

public class BlockTofuTorch extends BlockTorch implements IBlockTofuMaterial {

    private TofuMaterial tofuMaterial;

    public BlockTofuTorch(TofuMaterial tofuMaterial)
    {
        this.tofuMaterial = tofuMaterial;
        this.tofuMaterial.getBlockInfo().setBasicFeature(this);
        this.setSoundType(tofuMaterial.getBlockInfo().getSoundType());
    }

	@Override
	public TofuMaterial getTofuMaterial() {
		return this.tofuMaterial;
	}

	@SuppressWarnings("deprecation")
	@Override
    public String getLocalizedName() {
        String tofuMaterialName = this.tofuMaterial.getName();
        tofuMaterialName = I18n.translateToLocal("item.tofucraft:tofu" + Utils.capitalize(tofuMaterialName) + ".name");
        return tofuMaterialName;
        		//(I18n.translateToLocalFormatted("tile.tofucraft:tofuTorch.name",
        		//I18n.translateToLocal("item.tofucraft:tofu" + Utils.capitalize(tofuMaterialName) + ".name"))).trim();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return this.tofuMaterial.getBlock().getBlockLayer() == BlockRenderLayer.TRANSLUCENT ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
    }
}
