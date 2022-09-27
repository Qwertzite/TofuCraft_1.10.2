package remiliaMarine.tofu.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderFukumame extends Render<EntityFukumame> {

    public RenderFukumame(RenderManager renderManager) {
		super(renderManager);
	}

	public void renderFukumame(EntityFukumame fukumame, double par2, double par4, double par6, float par8, float partialTicks)
    {
		GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();

        GlStateManager.translate((float)par2, (float)par4, (float)par6);
        GlStateManager.rotate((fukumame.prevRotationYaw + (fukumame.rotationYaw - fukumame.prevRotationYaw) * partialTicks) - 90F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(fukumame.prevRotationPitch + (fukumame.rotationPitch - fukumame.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        float f8 = 0.1625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        GlStateManager.enableRescaleNormal();
        GlStateManager.disableTexture2D();

        GlStateManager.rotate(45F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(f8, f8, f8);
        GlStateManager.translate(-4F, 0.0F, 0.0F);

        GlStateManager.glNormal3f(f8, 0.0F, 0.0F);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        //vertexbuffer.color(1.0F, 0.9F, 0.5F, 1.0F);
        vertexbuffer.pos(4.5D, -0.5D, 0.0D).color(1.0F, 0.9F, 0.5F, 1.0F).endVertex();
        vertexbuffer.pos(4.5D, 0.0D, -0.5D).color(1.0F, 0.9F, 0.5F, 1.0F).endVertex();
        vertexbuffer.pos(4.5D, 0.5D, 0.0D).color(1.0F, 0.9F, 0.5F, 1.0F).endVertex();
        vertexbuffer.pos(4.5D, 0.0D, 0.5D).color(1.0F, 0.9F, 0.5F, 1.0F).endVertex();
        tessellator.draw();

        GlStateManager.glNormal3f(-f8, 0.0F, 0.0F);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        //vertexbuffer.color(1.0F, 0.9F, 0.5F, 1.0F);
        vertexbuffer.pos(4.5D, 0.0D, 0.5D).color(1.0F, 0.9F, 0.5F, 1.0F).endVertex();
        vertexbuffer.pos(4.5D, 0.5D, 0.0D).color(1.0F, 0.9F, 0.5F, 1.0F).endVertex();
        vertexbuffer.pos(4.5D, 0.0D, -0.5D).color(1.0F, 0.9F, 0.5F, 1.0F).endVertex();
        vertexbuffer.pos(4.5D, -0.5D, 0.0D).color(1.0F, 0.9F, 0.5F, 1.0F).endVertex();
        tessellator.draw();

        for (int j = 0; j < 4; j++)
        {
        	GlStateManager.rotate(90F, 1.0F, 0.0F, 0.0F);
        	GlStateManager.glNormal3f(0.0F, 0.0F, f8);
        	vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
        	//vertexbuffer.color(1.0F, 0.9F, 0.5F, 1.0F);
        	vertexbuffer.pos(4.5D, -0.5D, 0.0D).color(1.0F, 0.9F, 0.5F, 1.0F).endVertex();
        	vertexbuffer.pos(6.5D, -0.5D, 0.0D).color(1.0F, 0.9F, 0.5F, 1.0F).endVertex();
        	vertexbuffer.pos(6.5D, 0.5D, 0.0D).color(1.0F, 0.9F, 0.5F, 1.0F).endVertex();
        	vertexbuffer.pos(4.5D, 0.5D, 0.0D).color(1.0F, 0.9F, 0.5F, 1.0F).endVertex();
            tessellator.draw();
        }

        GlStateManager.enableTexture2D();
        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GL11.glPopMatrix();

        super.doRender(fukumame, par2, par4, par6, par8, partialTicks);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function register does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(EntityFukumame par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderFukumame((EntityFukumame)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFukumame entity)
    {
        return null;
    }
}
