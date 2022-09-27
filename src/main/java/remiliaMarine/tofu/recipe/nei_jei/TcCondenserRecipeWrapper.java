package remiliaMarine.tofu.recipe.nei_jei;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import remiliaMarine.tofu.api.recipe.TfCondenserRecipe;
import remiliaMarine.tofu.gui.guiparts.HoverTextPosition;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;

public class TcCondenserRecipeWrapper extends TcBlankRecipeWrapper {
	
    public TfStack tfAmountNeeded;
    public FluidStack ingredient;
    public ItemStack result;
    public int ticksNeeded;
    
	public TcCondenserRecipeWrapper(TfCondenserRecipe registry) {
		this.tfAmountNeeded = new TfStack(registry.tfAmountNeeded);
		this.ingredient = registry.ingredient;
		this.result = registry.result;
		this.ticksNeeded = registry.ticksNeeded;
	}
    
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(FluidStack.class, this.ingredient);
		ingredients.setInput(TfStack.class, this.tfAmountNeeded);
		ingredients.setOutput(ItemStack.class, this.result);
	}
	
	// TODO:
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		this.fontRendererObj = minecraft.fontRendererObj;
		this.mc = minecraft;
		
		//minecraft.fontRendererObj.drawString(this.amountString, 55, 44, Color.gray.getRGB());
		
		if (mouseX > 81 && mouseY > 39 && mouseX < 81 + TfMachineGuiParts.gaugeTfCharged.xSize && mouseY < 39 + 13) {
			this.drawTfHoveringTipFixedSize(mouseX, mouseY, 57, 11, HoverTextPosition.LOWER_CENTER);
		}
	}
	
	@Override
    public void drawText(int mousex, int mousey, int fw, int fh) {
        String s;
        s = String.format("%.0f", this.tfAmountNeeded.getTfAmount());
        this.fontRendererObj.drawString(s, mousex + 33 - fontRendererObj.getStringWidth(s), mousey + 2, 0xf1f2e6);

        this.printTfSign(mousex + 33, mousey + 2, 0xf1f2e6);
	}

}
