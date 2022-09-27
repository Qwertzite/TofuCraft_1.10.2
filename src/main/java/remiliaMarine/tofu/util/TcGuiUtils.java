package remiliaMarine.tofu.util;

import java.lang.reflect.Field;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class TcGuiUtils {
    private static Field fldGuiLeft = ReflectionHelper.findField(GuiContainer.class, "field_147003_i", "guiLeft");
    private static Field fldGuiTop = ReflectionHelper.findField(GuiContainer.class, "field_147009_r", "guiTop");

    /**use {@link Gui#drawRect(int, int, int, int, int)} instead (I could find no difference.)*/
    @Deprecated
    public static void drawRectangle(int x1, int y1, int x2, int y2, int color, float zLevel)
    {
    	//Gui.drawRect(x1, y1, x2, y2, color);

        float f = (float)(color >> 24 & 255) / 255.0F;
        float f1 = (float)(color >> 16 & 255) / 255.0F;
        float f2 = (float)(color >> 8 & 255) / 255.0F;
        float f3 = (float)(color & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
        vertexbuffer.pos((double)x2, (double)y1, (double)zLevel).endVertex();
        vertexbuffer.pos((double)x2, (double)y1, (double)zLevel).endVertex();
        vertexbuffer.pos((double)x1, (double)y1, (double)zLevel).endVertex();
        vertexbuffer.pos((double)x1, (double)y2, (double)zLevel).endVertex();
        vertexbuffer.pos((double)x2, (double)y2, (double)zLevel).endVertex();
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }



    public static void drawFluidGuage(int x, int y, FluidTank fluidTank, int width, int height, GuiScreen gui)
    {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        FluidStack fluidStack = fluidTank.getFluid();
        ResourceLocation resLoc;
        resLoc = fluidStack.getFluid().getStill();
//        if (fluidStack.getFluid().getSpriteNumber() == 0)
//        {
//            resLoc = TextureMap.locationBlocksTexture;
//        }
//        else
//        {
//            resLoc = TextureMap.locationItemsTexture;
//        }
//
        gui.mc.getTextureManager().bindTexture(resLoc);
        TcGuiUtils.applyColorRGB(fluidStack.getFluid().getColor(fluidStack));

        int heightInd = (int) (height * ((float) fluidTank.getFluidAmount() / (float) fluidTank.getCapacity()));
        int texX = x;
        int texY = y + (height - heightInd);
//
        int texW, texH;
//        IIcon icon = fluidStack.getFluid().getIcon(fluidStack);
//
//        for (int i = 0; i < width; i += 16)
//        {
//
//            for (int j = 0; j < heightInd; j += 16)
//            {
//                texW = Math.min(width - i, 16);
//                texH = Math.min(heightInd - j, 16);
//                gui.drawTexturedModelRectFromIcon(texX + i, texY + j, icon, texW, texH);
//            }
//        }
//
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
    /**@param color 0xaarrggbb*/
    public static void applyColorRGBA(int color)
    {
        int a = color >> 24 & 255;
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        GL11.glColor4b((byte) r, (byte) g, (byte) b, (byte) a);
    }

    public static void applyColorRGB(int color)
    {
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        GL11.glColor3ub((byte) r, (byte) g, (byte) b);
    }

}
