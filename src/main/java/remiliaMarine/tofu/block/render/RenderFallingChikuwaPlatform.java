package remiliaMarine.tofu.block.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import remiliaMarine.tofu.entity.EntityFallingChikuwaPlatform;

public class RenderFallingChikuwaPlatform extends Render<EntityFallingChikuwaPlatform> {

    public RenderFallingChikuwaPlatform(RenderManager manager)
    {
    	super(manager);
        this.shadowSize = 0.5F;
    }

    @Override
    public void doRender(EntityFallingChikuwaPlatform entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        IBlockState block = entity.getContainedBlock();
	    if (block != null) {

            World world = entity.getWorldObj();

	        if (block.getBlock() != world.getBlockState(new BlockPos(entity)).getBlock())
	        {
                this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                GlStateManager.pushMatrix();
                GlStateManager.disableLighting();

	            Tessellator tessellator = Tessellator.getInstance();
	            VertexBuffer vertexbuffer = tessellator.getBuffer();

                if (this.renderOutlines)
                {
                    GlStateManager.enableColorMaterial();
                    GlStateManager.enableOutlineMode(this.getTeamColor(entity));
                }

                vertexbuffer.begin(7, DefaultVertexFormats.BLOCK);
                BlockPos blockpos = new BlockPos(entity.posX, entity.getEntityBoundingBox().maxY, entity.posZ);
                GlStateManager.translate((float)(x - (double)blockpos.getX() - 0.5D), (float)(y - (double)blockpos.getY()), (float)(z - (double)blockpos.getZ() - 0.5D));
                BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
                BlockPos home = entity.getHome();
                if (home == null) home = BlockPos.ORIGIN;
                blockrendererdispatcher.getBlockModelRenderer().renderModel(world, blockrendererdispatcher.getModelForState(block), block, blockpos, vertexbuffer, false, MathHelper.getPositionRandom(home));
                tessellator.draw();

                if (this.renderOutlines)
                {
                    GlStateManager.disableOutlineMode();
                    GlStateManager.disableColorMaterial();
                }

                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
                super.doRender(entity, x, y, z, entityYaw, partialTicks);
	        }
	    }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFallingChikuwaPlatform p_110775_1_)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
