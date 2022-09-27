package remiliaMarine.tofu.recipe.nei_jei;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import remiliaMarine.tofu.api.recipe.TfCondenserRecipe;
import remiliaMarine.tofu.init.TcFluids;

public class TfCondenserRecipeWrapper extends TcBlankRecipeWrapper {

    public TfStack tfAmountNeeded;
    public FluidStack ingredient;
    public FluidStack bittern = new FluidStack(TcFluids.NIGARI, 5);
    public ItemStack result;
    public int ticksNeeded;
    
	public TfCondenserRecipeWrapper(TfCondenserRecipe registry) {
		this.tfAmountNeeded = new TfStack(registry.tfAmountNeeded);
		this.ingredient = registry.ingredient;
		this.result = registry.result;
		this.ticksNeeded = registry.ticksNeeded;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setOutput(ItemStack.class, this.result);
		ingredients.setInput(TfStack.class, this.tfAmountNeeded);
		ingredients.setInput(FluidStack.class, this.ingredient);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
	}
	
	@Override
    public void drawText(int mousex, int mousey, int fw, int fh) {
//        String s;
//        s = String.format("%dtick", this.ticksNeeded);
//        this.fontRendererObj.drawString(s, mousex + 33 - fontRendererObj.getStringWidth(s), mousey + 2, 0xf1f2e6);
	}

}
