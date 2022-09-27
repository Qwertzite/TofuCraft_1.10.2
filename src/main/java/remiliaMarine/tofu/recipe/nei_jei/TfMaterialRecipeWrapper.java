package remiliaMarine.tofu.recipe.nei_jei;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.api.TfMaterialRegistry;
import remiliaMarine.tofu.gui.guiparts.HoverTextPosition;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;

public class TfMaterialRecipeWrapper extends TcBlankRecipeWrapper {

	private final ItemStack ingredient;
	private final ItemStack container;
	private final double tfAmount;
	private final String amountString;
	
	public TfMaterialRecipeWrapper(TfMaterialRegistry registry) {
		this.ingredient = registry.getItemStack();
		this.container = this.getIngredient().getItem().getContainerItem(getIngredient());
		this.tfAmount = registry.tfAmount;
		this.amountString = String.format("%s : %.1f", I18n.format("tofucraft.tofuForce"), this.tfAmount);
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, this.getIngredient());
		ingredients.setOutput(TfStack.class, new TfStack(this.tfAmount));
		ingredients.setOutput(ItemStack.class, this.getContainer());
	}
	
	public List<List<ItemStack>> getInputs() {
		return Collections.singletonList(Collections.singletonList(this.ingredient));
	}
	
	public List<ItemStack> getOutputs() {
		return Collections.singletonList(this.getContainer());
	}
	
	public ItemStack getIngredient() {
		return this.ingredient;
	}
	
	public ItemStack getContainer() {
		return this.container;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TfMaterialRecipeWrapper)) {
			return false;
		}
		TfMaterialRecipeWrapper other = (TfMaterialRecipeWrapper) obj;

		if (!other.ingredient.equals(this.ingredient)) {
			return false;
		}

		if (other.tfAmount != this.tfAmount) {
			return false;
		}

		return true;
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
//		this.fontRendererObj = minecraft.fontRendererObj;
//		this.mc = minecraft;
//		
//		minecraft.fontRendererObj.drawString(this.amountString, 55, 44, Color.gray.getRGB());
//		
//		if (mouseX > 85 && mouseY > 23 && mouseX < 85 + TfMachineGuiParts.gaugeFrame.xSize && mouseY < 23 + 12) {
//			this.drawTfHoveringTipFixedSize(mouseX, mouseY, 57, 11, HoverTextPosition.LOWER_CENTER);
//		}
	}
	
	@Override
    public void drawText(int mousex, int mousey, int fw, int fh) {
        String s;
        s = String.format("%.0f", this.tfAmount);
        this.fontRendererObj.drawString(s, mousex + 33 - fontRendererObj.getStringWidth(s), mousey + 2, 0xf1f2e6);

//        s = String.format("/%.0f", this.tfCapacity);
//        fontRendererObj.drawString(s, mousex + 41 - fontRendererObj.getStringWidth(s), mousey + 12, 0xb4b5aa);

        this.printTfSign(mousex + 33, mousey + 2, 0xf1f2e6);
//        printTfSign(mousex + 41, oy + 12, 0xb4b5aa);
	}
	
	

}
