package remiliaMarine.tofu.recipe.nei_jei;

import java.util.ArrayList;
import java.util.List;

import remiliaMarine.tofu.api.recipe.TfReformerRecipe;

public class TfReformerRecipeMaker {

	public static List<TfReformerRecipeWrapper> getRecipe(List<TfReformerRecipe> registry) {
		List<TfReformerRecipeWrapper> ret = new ArrayList<TfReformerRecipeWrapper>();
		
		for (TfReformerRecipe elem : registry) {
			ret.add(new TfReformerRecipeWrapper(elem));
		}
		
		return ret;
	}
}
