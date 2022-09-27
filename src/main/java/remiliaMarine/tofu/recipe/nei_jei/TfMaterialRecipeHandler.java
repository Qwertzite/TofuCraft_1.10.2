package remiliaMarine.tofu.recipe.nei_jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class TfMaterialRecipeHandler implements IRecipeHandler<TfMaterialRecipeWrapper> {

	@Override
	public Class<TfMaterialRecipeWrapper> getRecipeClass() {
		return TfMaterialRecipeWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return RecipeCategoryTfMaterial.UID;
	}

	@Override
	public String getRecipeCategoryUid(TfMaterialRecipeWrapper recipe) {
		return RecipeCategoryTfMaterial.UID;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(TfMaterialRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(TfMaterialRecipeWrapper recipe) {
		return true;
	}

}
