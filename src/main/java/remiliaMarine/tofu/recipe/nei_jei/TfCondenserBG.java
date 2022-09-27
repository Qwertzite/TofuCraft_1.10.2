package remiliaMarine.tofu.recipe.nei_jei;

import net.minecraft.client.Minecraft;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;

public class TfCondenserBG extends TcDrawableStatic {
	
	public TfCondenserBG(int width, int height) {
		super(width, height, 0, 0, 0, 0);
	}

	public TfCondenserBG(int width, int height, int paddingTop, int paddingBottom, int paddingLeft, int paddingRight) {
		super(width, height, paddingTop, paddingBottom, paddingLeft, paddingRight);
	}
	
	@Override
	public void draw(Minecraft minecraft, int xOffset, int yOffset, int maskTop, int maskBottom, int maskLeft, int maskRight) {
		minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
		
		this.drawStandartBasePanel(xOffset, yOffset);
		
		this.drawGuiPart(xOffset, yOffset,  30,  8, TfMachineGuiParts.gaugeV2Frame);
		this.drawGuiPart(xOffset, yOffset,  50,  8, TfMachineGuiParts.gaugeV2Frame);
		this.drawGuiPart(xOffset, yOffset,  70, 21, TfMachineGuiParts.progressArrowBg);
		this.drawGuiPart(xOffset, yOffset, 100, 19, TfMachineGuiParts.itemSlotL2);// 18 - 20
	}
}
