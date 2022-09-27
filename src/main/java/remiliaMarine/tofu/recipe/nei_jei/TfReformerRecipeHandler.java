package remiliaMarine.tofu.recipe.nei_jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class TfReformerRecipeHandler implements IRecipeHandler<TfReformerRecipeWrapper> {
	
	@Override
	public Class<TfReformerRecipeWrapper> getRecipeClass() {
		return TfReformerRecipeWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return RecipeCategoryTfReformer.UID;
	}

	@Override
	public String getRecipeCategoryUid(TfReformerRecipeWrapper recipe) {
		return RecipeCategoryTfReformer.UID;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(TfReformerRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(TfReformerRecipeWrapper recipe) {
		return true;
	}

}
