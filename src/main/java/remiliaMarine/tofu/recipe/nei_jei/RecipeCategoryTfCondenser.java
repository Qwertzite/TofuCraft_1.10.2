package remiliaMarine.tofu.recipe.nei_jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;
import remiliaMarine.tofu.tileentity.TcFluidStackRenderer;

public class RecipeCategoryTfCondenser extends BlankRecipeCategory<TfCondenserRecipeWrapper> {

	public static final String UID = "RecipeCategoryTfCondenser";
	public static String localisedName = I18n.format("gui.jei.category.tfCondenser");
	
	private final IDrawable backGround;
	private final IDrawableAnimated arrow;
	
	public RecipeCategoryTfCondenser(IGuiHelper guiHelper) {
		ResourceLocation arrowLoc = new ResourceLocation(TofuCraftCore.MODID, "textures/gui/tfMachine.png");
		this.backGround =  new TfCondenserBG(140, 60, 0, 0, 0, 20);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(arrowLoc, TfMachineGuiParts.progressArrow.ox, TfMachineGuiParts.progressArrow.oy, TfMachineGuiParts.progressArrow.xSize, TfMachineGuiParts.progressArrow.ySize);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 30, StartDirection.LEFT, false);
		
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
		return backGround;
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		arrow.draw(minecraft, 70, 21);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, TfCondenserRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiIngredientGroup<TfStack> tfstacks = recipeLayout.getIngredientsGroup(TfStack.class);
		IGuiFluidStackGroup fluidstacks = recipeLayout.getFluidStacks();
		IGuiItemStackGroup itemstacks = recipeLayout.getItemStacks();
		
		fluidstacks.init(0, true, new TcFluidStackRenderer(TfMachineGuiParts.gaugeV2, 0xb0f0a7).setLimit(10000.0f), 52, 11, TfMachineGuiParts.gaugeV2.xSize, TfMachineGuiParts.gaugeV2.ySize, 0, 0);
		tfstacks.init(1, true, new TfStackRenderer(TfMachineGuiParts.gaugeV2), 32, 11, TfMachineGuiParts.gaugeV2.xSize, TfMachineGuiParts.gaugeV2.ySize, 0, 0);
		itemstacks.init(2, false, 103,  22);
		
		fluidstacks.set(ingredients);
		tfstacks.set(ingredients);
		itemstacks.set(ingredients);		
	}
}
