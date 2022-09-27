package remiliaMarine.tofu.recipe.nei_jei;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;

public class RecipeCategoryTfMaterial extends BlankRecipeCategory<TfMaterialRecipeWrapper> {
	
	public static final String UID = "RecipeCategoryTfMaterial";
	public static String localisedName = I18n.format("gui.jei.category.tfMaterial");
	
	private final IDrawable backGround;
	private final IDrawableAnimated arrow;
	
	public RecipeCategoryTfMaterial(IGuiHelper guiHelper) {
		ResourceLocation arrowLoc = new ResourceLocation(TofuCraftCore.MODID, "textures/gui/tfMachine.png");
		this.backGround =  new TfStorageBG(140, 60, 0, 0, 0, 20);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(arrowLoc, TfMachineGuiParts.progressArrow.ox, TfMachineGuiParts.progressArrow.oy, TfMachineGuiParts.progressArrow.xSize, TfMachineGuiParts.progressArrow.ySize);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 20, StartDirection.LEFT, false);
	}
	
	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return localisedName;
	}

	@Override
	public IDrawable getBackground() {
		return this.backGround;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		arrow.draw(minecraft, 52, 20);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, TfMaterialRecipeWrapper recipeWrapper) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiIngredientGroup<TfStack> guiTfStacks = recipeLayout.getIngredientsGroup(TfStack.class);
		
		guiItemStacks.init(0, true, 28, 8);
		guiTfStacks.init(1, false, new TfStackRenderer(TfMachineGuiParts.gaugeFrame.xSize, 12, TfMachineGuiParts.gauge, TfMachineGuiParts.gaugeFrame), 85, 23, TfMachineGuiParts.gaugeFrame.xSize, 12, 0, 0);
		guiItemStacks.init(1, false, 28, 36);
		
		List<ItemStack> inputs = Collections.singletonList(recipeWrapper.getIngredient());
		guiItemStacks.set(0, inputs);
		guiItemStacks.set(1, recipeWrapper.getOutputs());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, TfMaterialRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemstacks = recipeLayout.getItemStacks();
		IGuiIngredientGroup<TfStack> guiTfStacks = recipeLayout.getIngredientsGroup(TfStack.class);
		
		itemstacks.init(0, true, 28, 7);
		guiTfStacks.init(1, false, new TfStackRenderer(TfMachineGuiParts.gaugeFrame.xSize, 12, TfMachineGuiParts.gauge, TfMachineGuiParts.gaugeFrame), 85, 23, TfMachineGuiParts.gaugeFrame.xSize, 12, 0, 0);
		itemstacks.init(1, false, 28, 36);
		
		itemstacks.set(ingredients);
		guiTfStacks.set(ingredients);
	}
}
