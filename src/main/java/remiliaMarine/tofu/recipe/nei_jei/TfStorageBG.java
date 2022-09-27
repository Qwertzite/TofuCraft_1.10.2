package remiliaMarine.tofu.recipe.nei_jei;

import net.minecraft.client.Minecraft;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;

public class TfStorageBG extends TcDrawableStatic {

	public TfStorageBG(int width, int height) {
		super(width, height, 0, 0, 0, 0);
	}

	public TfStorageBG(int width, int height, int paddingTop, int paddingBottom, int paddingLeft, int paddingRight) {
		super(width, height, paddingTop, paddingBottom, paddingLeft, paddingRight);
	}
	
	@Override
	public void draw(Minecraft minecraft, int xOffset, int yOffset, int maskTop, int maskBottom, int maskLeft, int maskRight) {
		
		minecraft.getTextureManager().bindTexture(GUI_TEXTURE);

		this.drawStandartBasePanel(xOffset, yOffset);
        
		this.drawGuiPart(xOffset, yOffset, 27, 6, TfMachineGuiParts.itemSlotL1);
        this.drawGuiPart(xOffset, yOffset, 34, 28, TfMachineGuiParts.smallArrowDown);
		this.drawGuiPart(xOffset, yOffset, 25, 33, TfMachineGuiParts.itemSlotL2);
		this.drawGuiPart(xOffset, yOffset, 52, 20, TfMachineGuiParts.progressArrowBg);
	}

}
