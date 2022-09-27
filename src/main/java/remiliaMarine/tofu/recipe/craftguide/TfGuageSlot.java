package remiliaMarine.tofu.recipe.craftguide;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import uristqwerty.CraftGuide.api.NamedTexture;
import uristqwerty.CraftGuide.api.Renderer;
import uristqwerty.CraftGuide.api.Util;

public class TfGuageSlot extends TfAmountSlot
{
    private static NamedTexture containerTexture = null;

    public TfGuageSlot(int x, int y)
    {
        super(x, y);

        if(containerTexture == null)
        {
            containerTexture = Util.instance.getTexture("liquidFilterContainer");
        }
    }

    @Override
    public int getWidth()
    {
        return 16;
    }

    @Override
    public int getHeight()
    {
        return 16;
    }

    @Override
    public void draw(Renderer renderer, int recipeX, int recipeY, Object[] data, int dataIndex, boolean isMouseOver)
    {
        int x = recipeX + this.x;
        int y = recipeY + this.y;

		GlStateManager.disableDepth();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(770, 771);
		GlStateManager.disableLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2i(x + 3, y + 1);
        GL11.glVertex2i(x + 3, y + 15);
        GL11.glVertex2i(x + 13, y + 15);
        GL11.glVertex2i(x + 13, y + 1);
        GL11.glEnd();
        
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.enableTexture2D();
        
        renderer.renderRect(x - 1, y - 1, 18, 18, containerTexture);
    }
}
