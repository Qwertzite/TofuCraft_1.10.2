package remiliaMarine.tofu.block;

import net.minecraft.block.BlockLadder;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.item.TofuMaterial;

public class BlockTofuLadder extends BlockLadder implements IBlockTofuMaterial {

    private TofuMaterial tofuMaterial;

    public BlockTofuLadder(TofuMaterial tofuMaterial)
    {
        tofuMaterial.getBlockInfo().setBasicFeature(this);
        this.setSoundType(tofuMaterial.getBlockInfo().getSoundType());
        this.tofuMaterial = tofuMaterial;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return this.tofuMaterial.getBlock().getBlockLayer() == BlockRenderLayer.TRANSLUCENT ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
    }

	@Override
	public TofuMaterial getTofuMaterial() {
        return this.tofuMaterial;
	}

}
