package remiliaMarine.tofu.recipe.nei_jei;

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
import net.minecraft.util.ResourceLocation;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.gui.guiparts.TfMachineGuiParts;

public class RecipeCategoryTfReformer extends BlankRecipeCategory<TfReformerRecipeWrapper> {

	public static final String UID = "RecipeCategoryTfReformer";
	public static String localisedName = I18n.format("gui.jei.category.tfReformer");
	
	private final IDrawable backGround;
	private final IDrawableAnimated arrow;
	
	public RecipeCategoryTfReformer(IGuiHelper guiHelper) {
		ResourceLocation arrowLoc = new ResourceLocation(TofuCraftCore.MODID, "textures/gui/tfMachine.png");
		this.backGround =  new TfReformerBG(140, 60, 0, 0, 0, 20);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(arrowLoc, TfMachineGuiParts.progressArrowRev.ox, TfMachineGuiParts.progressArrowRev.oy, TfMachineGuiParts.progressArrowRev.xSize, TfMachineGuiParts.progressArrowRev.ySize);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 20, StartDirection.RIGHT, false);
	}
	
	@Override
	public String getUid() {
		return RecipeCategoryTfReformer.UID;
	}

	@Override
	public String getTitle() {
		return RecipeCategoryTfReformer.localisedName;
	}

	@Override
	public IDrawable getBackground() {
		return this.backGround;
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		arrow.draw(minecraft, 50, 21);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, TfReformerRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemstacks = recipeLayout.getItemStacks();
		IGuiIngredientGroup<TfStack> guiTfStacks = recipeLayout.getIngredientsGroup(TfStack.class);
		
		itemstacks.init(0, true, 25,  7);
		itemstacks.init(1, true, 78, 13);
		itemstacks.init(2, true,100, 13);
		itemstacks.init(3, true,122, 13);
		guiTfStacks.init(5, true, new TfStackRenderer(TfMachineGuiParts.gaugeTfCharged), 81, 39, TfMachineGuiParts.gaugeTfCharged.xSize, TfMachineGuiParts.gaugeTfCharged.ySize, 0, 0);
		itemstacks.init(4, false, 25, 36);
		
		itemstacks.set(ingredients);
		guiTfStacks.set(ingredients);
	}

}
