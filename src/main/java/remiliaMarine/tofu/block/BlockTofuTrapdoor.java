package remiliaMarine.tofu.block;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import remiliaMarine.tofu.item.TofuMaterial;
import remiliaMarine.tofu.util.TofuBlockUtils;

public class BlockTofuTrapdoor extends BlockTrapDoor implements IBlockTofuMaterial {

    private TofuMaterial tofuMaterial;

    public BlockTofuTrapdoor(TofuMaterial tofuMaterial)
    {
        super(tofuMaterial.getBlockInfo().material);
        tofuMaterial.getBlockInfo().setBasicFeature(this);
        this.setSoundType(tofuMaterial.getBlockInfo().getSoundType());
        this.tofuMaterial = tofuMaterial;
    }

    @Override
    public void onFallenUpon(World par1World, BlockPos pos, Entity par5Entity, float par6)
    {
        if (tofuMaterial == TofuMaterial.KINU)
        {
            TofuBlockUtils.onFallenUponFragileTofu(par1World, par5Entity, this, par6);
        }
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
