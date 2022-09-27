package remiliaMarine.tofu.recipe.nei_jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class TfCondenserRecipeHandler implements IRecipeHandler<TfCondenserRecipeWrapper>{

	@Override
	public Class<TfCondenserRecipeWrapper> getRecipeClass() {
		return TfCondenserRecipeWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return RecipeCategoryTfCondenser.UID;
	}

	@Override
	public String getRecipeCategoryUid(TfCondenserRecipeWrapper recipe) {
		return RecipeCategoryTfCondenser.UID;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(TfCondenserRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(TfCondenserRecipeWrapper recipe) {
		return true;
	}

}
