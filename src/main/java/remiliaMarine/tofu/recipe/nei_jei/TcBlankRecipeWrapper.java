package remiliaMarine.tofu.recipe.nei_jei;

import java.awt.Point;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import remiliaMarine.tofu.gui.guiparts.HoverTextPosition;
import remiliaMarine.tofu.init.TcTextures;

public abstract class TcBlankRecipeWrapper extends BlankRecipeWrapper {

	protected FontRenderer fontRendererObj;
	protected Minecraft mc;
	protected float zLevel;
	
    public void drawTfHoveringTipFixedSize(int ox, int oy, int fw, int fh)
    {
        this.drawTfHoveringTipFixedSize(ox, oy, fw, fh, HoverTextPosition.UPPER_CENTER);
    }
	
    public void drawTfHoveringTipFixedSize(int ox, int oy, int fw, int fh, HoverTextPosition pos)
    {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        //RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        this.zLevel = 300.0F;

        //ox += this.getToolTipOffsetX();
        //oy += this.getToolTipOffsetY();

        Point mousePos = new Point(ox, oy);
        pos.applyOffset(mousePos, fw, fh);

        this.doDrawTfHoveringTextBg(mousePos.x, mousePos.y, fw, fh, 0);

        this.drawText(mousePos.x, mousePos.y, fw, fh);

        this.zLevel = 0.0F;
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        //RenderHelper.enableStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }
    
    private void doDrawTfHoveringTextBg(int ox, int oy, int fw, int fh, int p)
    {
//        if (ox + fw > this.width)
//        {
//            ox -= 28 + fw;
//        }
//
//        if (oy + fh + 6 > this.height)
//        {
//            oy = this.height - fh - 6;
//        }

        int color;
        // Border
        color = 0xff878a7c;
        this.drawGradientRect(ox - p,      oy - p - 1,  ox + fw + p,     oy - p,          color, color);
        this.drawGradientRect(ox - p,      oy + fh + p, ox + fw + p,     oy + fh + p + 1, color, color);
        this.drawGradientRect(ox - p - 1,  oy - p,      ox - p,          oy + fh + p,     color, color);
        this.drawGradientRect(ox + fw + p, oy - p,      ox + fw + p + 1, oy + fh + p,     color, color);
        // Background
        color = 0xff5c5e54;
        this.drawGradientRect(ox - p, oy - p, ox + fw + p, oy + fh + p, color, color);
    }
    
    /**
     * Draws a rectangle with a vertical gradient between the specified colors (ARGB format). Args : x1, y1, x2, y2,
     * topColor, bottomColor
     */
    protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
    {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos((double)right, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
        vertexbuffer.pos((double)left, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
        vertexbuffer.pos((double)left, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
        vertexbuffer.pos((double)right, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public abstract void drawText(int mousex, int mousey, int fw, int fh);
    
    public void printTfSign(int x, int y, int color)
    {
        this.mc.getTextureManager().bindTexture(TcTextures.tfSign);
        if ((color & -67108864) == 0)
        {
            color |= -16777216;
        }
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        float a = (color >> 24 & 255) / 255.0F;

        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbufer = tessellator.getBuffer();
        vertexbufer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        vertexbufer.pos(x + 0d, y + 8d, this.zLevel).tex(0d, 1d).color(r, g, b, a).endVertex();
        vertexbufer.pos(x + 8d, y + 8d, this.zLevel).tex(1d, 1d).color(r, g, b, a).endVertex();
        vertexbufer.pos(x + 8d, y + 0d, this.zLevel).tex(1d, 0d).color(r, g, b, a).endVertex();
        vertexbufer.pos(x + 0d, y + 0d, this.zLevel).tex(0d, 0d).color(r, g, b, a).endVertex();
        tessellator.draw();
    }
}
