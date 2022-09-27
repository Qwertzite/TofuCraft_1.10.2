package remiliaMarine.tofu.recipe.nei_jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;

public class TfStackRenderer implements IIngredientRenderer<TfStack> {

	public static ResourceLocation RESOURCE = new ResourceLocation(TofuCraftCore.MODID, "textures/gui/tfMachine.png");
	
	private TfMachineGuiParts gauge;
	private TfMachineGuiParts frame;
	private int width;
	private int height;
	
	public TfStackRenderer() {
		this(TfMachineGuiParts.gaugeTfCharged);
	}
	
	public TfStackRenderer(TfMachineGuiParts guiParts) {
		this.width = guiParts.xSize;
		this.height = guiParts.ySize;
		
		this.frame = guiParts;
	}
	
	public TfStackRenderer(int width, int height, TfMachineGuiParts gauge, TfMachineGuiParts frame) {
		this.width = width;
		this.height = height;
		
		this.gauge = gauge;
		this.frame = frame;
	}
	
	@Override
	public void render(Minecraft minecraft, int xPosition, int yPosition, TfStack ingredient) {
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
        GlStateManager.enableRescaleNormal();
        GlStateManager.color(1, 1, 1, 1);
		this.drawGauge(minecraft, xPosition, yPosition, ingredient);
		
        GlStateManager.disableRescaleNormal();
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
	}
	
	public void drawGauge(Minecraft minecraft, int xPos, int yPos, TfStack ingredient) {
		
		minecraft.renderEngine.bindTexture(RESOURCE);
		
        float uScale = 1f / 0x100;
        float vScale = 1f / 0x100;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(xPos        , yPos + height, 100).tex( frame.ox                * uScale, ((frame.oy + frame.ySize) * vScale)).endVertex();
        vertexbuffer.pos(xPos + width, yPos + height, 100).tex((frame.ox + frame.xSize) * uScale, ((frame.oy + frame.ySize) * vScale)).endVertex();
        vertexbuffer.pos(xPos + width, yPos         , 100).tex((frame.ox + frame.xSize) * uScale, ( frame.oy                * vScale)).endVertex();
        vertexbuffer.pos(xPos        , yPos         , 100).tex( frame.ox                * uScale, ( frame.oy                * vScale)).endVertex();
        tessellator.draw();
        
        if (this.gauge == null) return; 
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(xPos + 2         , yPos + height - 2 , 100).tex( gauge.ox                * uScale, ((gauge.oy + gauge.ySize) * vScale)).endVertex();
        vertexbuffer.pos(xPos + width - 2 , yPos + height - 2 , 100).tex((gauge.ox + gauge.xSize) * uScale, ((gauge.oy + gauge.ySize) * vScale)).endVertex();
        vertexbuffer.pos(xPos + width - 2 , yPos + 2          , 100).tex((gauge.ox + gauge.xSize) * uScale, ( gauge.oy                * vScale)).endVertex();
        vertexbuffer.pos(xPos + 2         , yPos + 2          , 100).tex( gauge.ox                * uScale, ( gauge.oy                * vScale)).endVertex();
        tessellator.draw();
	}

	@Override
	public List<String> getTooltip(Minecraft minecraft, TfStack ingredient) {
		List<String> tooltip = new ArrayList<String>();
		
		String name = ingredient.getLocalisedName();
		tooltip.add(name);
		
		double tfAmount = ingredient.getTfAmount();
		String amount = String.format("%.1f", tfAmount);
		tooltip.add(TextFormatting.GRAY + amount);
		return tooltip;
	}

	@Override
	public FontRenderer getFontRenderer(Minecraft minecraft, TfStack ingredient) {
		return minecraft.fontRendererObj;
	}

}
