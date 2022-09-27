package remiliaMarine.tofu.recipe.nei_jei;

import mezz.jei.api.gui.IDrawableStatic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;
import remiliaMarine.tofu.init.TcTextures;

public abstract class TcDrawableStatic implements IDrawableStatic {

    protected static final ResourceLocation GUI_TEXTURE = TcTextures.tfMachineGui;
	
	private double zLevel = 0;
	
	private final int width;
	private final int height;
	private final int paddingTop;
	private final int paddingBottom;
	private final int paddingLeft;
	private final int paddingRight;
	
	public TcDrawableStatic(int width, int height) {
		this(width, height, 0, 0, 0, 0);
	}

	public TcDrawableStatic(int width, int height, int paddingTop, int paddingBottom, int paddingLeft, int paddingRight) {

		this.width = width;
		this.height = height;

		this.paddingTop = paddingTop;
		this.paddingBottom = paddingBottom;
		this.paddingLeft = paddingLeft;
		this.paddingRight = paddingRight;
	}
	
	@Override
	public int getWidth() {
		return width + paddingLeft + paddingRight;
	}

	@Override
	public int getHeight() {
		return height + paddingTop + paddingBottom;
	}

	@Override
	public void draw(Minecraft minecraft) {
		draw(minecraft, 0, 0);
	}

	@Override
	public void draw(Minecraft minecraft, int xOffset, int yOffset) {
		draw(minecraft, xOffset, yOffset, 0, 0, 0, 0);
	}
	
	public void drawStandartBasePanel(int xOffset, int yOffset) {
		this.drawBasePanel(xOffset, yOffset, 160, 60);
	}
	
    public void drawBasePanel(int xOffset, int yOffset, int w, int h)
    {
        int sx = xOffset;
        int sy = yOffset;
        // corner size
        int cw = 5;
        int ch = 5;

        this.drawGuiPart(xOffset, yOffset, 0, 0, TfMachineGuiParts.baseLeftTop);
        this.drawGuiPart(xOffset, yOffset, w - cw, 0, TfMachineGuiParts.baseRightTop);
        this.drawGuiPart(xOffset, yOffset, 0, h - ch, TfMachineGuiParts.baseLeftBottom);
        this.drawGuiPart(xOffset, yOffset, w - cw, h - ch, TfMachineGuiParts.baseRightBottom);

        int color;
        // Border
        color = 0xffb5b7a5;
        this.drawGradientRect(sx + cw,    sy        , sx + w - cw, sy + 1     , color, color);
        this.drawGradientRect(sx + cw,    sy + h - 1, sx + w - cw, sy + h     , color, color);
        this.drawGradientRect(sx,         sy + ch   , sx + 1,      sy + h - ch, color, color);
        this.drawGradientRect(sx + w - 1, sy + ch   , sx + w,      sy + h - ch, color, color);

        // BG
        color = 0xfff1f2e6;
        this.drawGradientRect(sx + cw, sy + 1,  sx + w - cw, sy + h - 1,  color, color);
        this.drawGradientRect(sx + 1 , sy + ch, sx + w - 1 , sy + h - ch, color, color);
    }
    
    public void drawGuiPart(int xOffset, int yOffset, int x, int y, TfMachineGuiParts part)
    {
        this.drawTexturedModalRect(xOffset + x, yOffset + y, part.ox, part.oy, part.xSize, part.ySize);
    }
    
    /**
     * Draws a textured rectangle at the current z-value.
     */
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
//        float f = 0.00390625F;
//        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(x + 0), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + 0), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
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

}
