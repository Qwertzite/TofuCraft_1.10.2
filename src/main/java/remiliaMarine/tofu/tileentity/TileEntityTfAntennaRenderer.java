package remiliaMarine.tofu.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import remiliaMarine.tofu.block.BlockTfAntenna;
import remiliaMarine.tofu.model.ModelTfAntenna;

public class TileEntityTfAntennaRenderer extends TileEntitySpecialRenderer<TileEntity> {
    public static TileEntityTfAntennaRenderer renderer;
    private final ModelTfAntenna model = new ModelTfAntenna(0, 0, 64, 32);
    private final ResourceLocation[] textures = new ResourceLocation[]{
            new ResourceLocation("tofucraft:textures/entity/tfAntenna.png"),
            new ResourceLocation("tofucraft:textures/entity/tfAntennaH.png"),
            new ResourceLocation("tofucraft:textures/entity/tfAntennaU.png")
    };
    private final RenderManager renderManager = FMLClientHandler.instance().getClient().getRenderManager();

    public void renderTileEntityMorijioAt(TileEntityTfAntenna tileentity, double par2, double par4, double par6, float par8)
    {
        this.func_82393_a((float)par2, (float)par4, (float)par6, tileentity.getAntennaType());
    }
    
    /**
     * Associate a TileEntityRenderer with this TileEntitySpecialRenderer
     */
    @Override
    public void setRendererDispatcher(TileEntityRendererDispatcher p_147497_1_)
    {
        super.setRendererDispatcher(p_147497_1_);
        renderer = this;
    }

    public void func_82393_a(float par1, float par2, float par3, BlockTfAntenna.WaveFreq waveFreq)
    {
        this.bindTexture(textures[waveFreq.id]);

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glTranslatef(par1 + 0.5F, par2, par3 + 0.5F);

        float var10 = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        this.model.render(null, 0.0F, 0.0F, 0.0F, this.renderManager.playerViewY, 0.0F, var10);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double par2, double par4, double par6, float par8, int destroyStage)
    {
        this.renderTileEntityMorijioAt((TileEntityTfAntenna)tileentity, par2, par4, par6, par8);
    }
}
