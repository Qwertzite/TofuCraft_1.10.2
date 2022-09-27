package remiliaMarine.tofu.recipe.nei_jei;

import net.minecraft.client.Minecraft;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;

public class TfReformerBG extends TcDrawableStatic {

	public TfReformerBG(int width, int height) {
		super(width, height, 0, 0, 0, 0);
	}

	public TfReformerBG(int width, int height, int paddingTop, int paddingBottom, int paddingLeft, int paddingRight) {
		super(width, height, paddingTop, paddingBottom, paddingLeft, paddingRight);
	}
	
	@Override
	public void draw(Minecraft minecraft, int xOffset, int yOffset, int maskTop, int maskBottom, int maskLeft, int maskRight) {
		minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
		
		this.drawStandartBasePanel(xOffset, yOffset);
		
		this.drawGuiPart(xOffset, yOffset,  24,  6, TfMachineGuiParts.itemSlotL1);
		this.drawGuiPart(xOffset, yOffset,  77, 12, TfMachineGuiParts.itemSlotL1);
		this.drawGuiPart(xOffset, yOffset,  99, 12, TfMachineGuiParts.itemSlotL1);
		this.drawGuiPart(xOffset, yOffset, 121, 12, TfMachineGuiParts.itemSlotL1);
		this.drawGuiPart(xOffset, yOffset,  31, 28, TfMachineGuiParts.smallArrowDown);
		this.drawGuiPart(xOffset, yOffset,  50, 21, TfMachineGuiParts.progressArrowRevBg);
		this.drawGuiPart(xOffset, yOffset,  22, 33, TfMachineGuiParts.itemSlotL2);
	}

}
