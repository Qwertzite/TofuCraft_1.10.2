package remiliaMarine.tofu.recipe.nei_jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import remiliaMarine.tofu.api.recipe.TfReformerRecipe;
import remiliaMarine.tofu.api.recipe.TfReformerRecipe.IngInfo;
import remiliaMarine.tofu.gui.guiparts.HoverTextPosition;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;

public class TfReformerRecipeWrapper extends TcBlankRecipeWrapper {

    public final TfStack tfAmountNeeded;
    public List<List<ItemStack>> input = new ArrayList<List<ItemStack>>();
    public ItemStack output;
    
	public TfReformerRecipeWrapper(TfReformerRecipe registry) {
		this.tfAmountNeeded = new TfStack(registry.tfAmountNeeded);
		this.input.add(registry.containerItem.getApplicableItems());
		for (IngInfo<?> ing : registry.getIngredients()){
			List<ItemStack> list = ing.ingredient.getApplicableItems();
			this.input.add(list);
		}
		this.output = registry.result;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(TfStack.class, this.tfAmountNeeded);
		ingredients.setInputLists(ItemStack.class, this.input);
		ingredients.setOutput(ItemStack.class, this.output);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
//		this.fontRendererObj = minecraft.fontRendererObj;
//		this.mc = minecraft;
//		
//		//minecraft.fontRendererObj.drawString(this.amountString, 55, 44, Color.gray.getRGB());
//		
//		if (mouseX > 81 && mouseY > 39 && mouseX < 81 + TfMachineGuiParts.gaugeTfCharged.xSize && mouseY < 39 + 13) {
//			this.drawTfHoveringTipFixedSize(mouseX, mouseY, 57, 11, HoverTextPosition.LOWER_CENTER);
//		}
	}
	
	@Override
    public void drawText(int mousex, int mousey, int fw, int fh) {
        String s;
        s = String.format("%.0f", this.tfAmountNeeded.getTfAmount());
        this.fontRendererObj.drawString(s, mousex + 33 - fontRendererObj.getStringWidth(s), mousey + 2, 0xf1f2e6);

        this.printTfSign(mousex + 33, mousey + 2, 0xf1f2e6);
	}

}
