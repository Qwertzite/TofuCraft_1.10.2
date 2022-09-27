package remiliaMarine.tofu.tileentity;

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
import net.minecraftforge.fluids.FluidStack;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;

public class TcFluidStackRenderer implements IIngredientRenderer<FluidStack> {
	
	public static ResourceLocation RESOURCE = new ResourceLocation(TofuCraftCore.MODID, "textures/gui/tfMachine.png");
	
	private TfMachineGuiParts gauge;
	private TfMachineGuiParts frame;
	private int width;
	private int height;
	private int colour = 0xFFFFFF;
	private float limit = -1.0f;
	
	public TcFluidStackRenderer() {
		this(TfMachineGuiParts.gaugeTfCharged);
	}
	
	public TcFluidStackRenderer(TfMachineGuiParts guiParts) {
		this(guiParts, 0xFFFFFF);
	}
	
	public TcFluidStackRenderer(TfMachineGuiParts guiParts, int colour) {
		this.width = guiParts.xSize + 2;
		this.height = guiParts.ySize + 2;
		this.gauge = guiParts;
		
		this.colour = colour;
	}
	
	public TcFluidStackRenderer(TfMachineGuiParts gauge, TfMachineGuiParts frame, int colour) {
		this.width = frame.xSize;
		this.height = frame.ySize;
		this.gauge = gauge;
		this.frame = frame;
		
		this.colour = colour;
	}
	
	public TcFluidStackRenderer(int width, int height, TfMachineGuiParts gauge, TfMachineGuiParts frame, int colour) {
		this.width = width;
		this.height = height;
		this.gauge = gauge;
		this.frame = frame;
		
		this.colour = colour;
	}
	
	@Override
	public void render(Minecraft minecraft, int xPosition, int yPosition, FluidStack ingredient) {
		
		if (ingredient != null) {
			GlStateManager.enableBlend();
			GlStateManager.enableAlpha();
	        GlStateManager.enableRescaleNormal();
	        
	        int colour= ingredient.getFluid().getColor();
	        
	        GlStateManager.color((colour >> 16 & 255) / 255.0f, (colour >> 8 & 255) / 255.0f, (colour & 255 / 255) / 255.0f);
			this.drawGauge(minecraft, xPosition, yPosition, ingredient);
			
	        GlStateManager.disableRescaleNormal();
			GlStateManager.disableAlpha();
			GlStateManager.disableBlend();
		}
	}
	
	public TcFluidStackRenderer setLimit(float limit) {
		this.limit = limit;
		return this;
	}
	
	public void drawGauge(Minecraft minecraft, int xPos, int yPos, FluidStack ingredient) {
		minecraft.renderEngine.bindTexture(RESOURCE);
		
        float uScale = 1f / 0x100;
        float vScale = 1f / 0x100;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        
        if (this.frame != null) {
        	vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        	vertexbuffer.pos(xPos        , yPos + height, 100).tex( frame.ox                * uScale, ((frame.oy + frame.ySize) * vScale)).endVertex();
        	vertexbuffer.pos(xPos + width, yPos + height, 100).tex((frame.ox + frame.xSize) * uScale, ((frame.oy + frame.ySize) * vScale)).endVertex();
        	vertexbuffer.pos(xPos + width, yPos         , 100).tex((frame.ox + frame.xSize) * uScale, ( frame.oy                * vScale)).endVertex();
        	vertexbuffer.pos(xPos        , yPos         , 100).tex( frame.ox                * uScale, ( frame.oy                * vScale)).endVertex();
        	tessellator.draw();
        }
        
        if (this.gauge == null) return;
        float s;
        if (this.limit > 0) s = 1.0f - ingredient.amount / this.limit;
        else s = 0.0f;
        //s = 0.0f;
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(xPos             , yPos + height - 2  , 100).tex( gauge.ox                * uScale, ((gauge.oy + gauge.ySize  ) * vScale)).endVertex();
        vertexbuffer.pos(xPos + width - 2 , yPos + height - 2  , 100).tex((gauge.ox + gauge.xSize) * uScale, ((gauge.oy + gauge.ySize  ) * vScale)).endVertex();
        vertexbuffer.pos(xPos + width - 2 , yPos + (height-2)*s, 100).tex((gauge.ox + gauge.xSize) * uScale, ((gauge.oy + gauge.ySize*s) * vScale)).endVertex();
        vertexbuffer.pos(xPos             , yPos + (height-2)*s, 100).tex( gauge.ox                * uScale, ((gauge.oy + gauge.ySize*s) * vScale)).endVertex();
        tessellator.draw();
	}

	@Override
	public List<String> getTooltip(Minecraft minecraft, FluidStack ingredient) {
		List<String> tooltip = new ArrayList<String>();
		
		try {
			String name = ingredient.getLocalizedName();
			tooltip.add(name);
			
			double tfAmount = ingredient.amount;
			String amount;
			if (this.limit > 0) amount = String.format("%.1f\n/%.1f", tfAmount, this.limit);
			else amount = String.format("%.1f", tfAmount);
			tooltip.add(TextFormatting.GRAY + amount);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tooltip;
	}

	@Override
	public FontRenderer getFontRenderer(Minecraft minecraft, FluidStack ingredient) {
		return minecraft.fontRendererObj;
	}
}
