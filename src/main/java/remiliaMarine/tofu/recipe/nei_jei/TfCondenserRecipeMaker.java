package remiliaMarine.tofu.recipe.nei_jei;

import java.util.ArrayList;
import java.util.List;

import remiliaMarine.tofu.api.recipe.TfCondenserRecipe;

public class TfCondenserRecipeMaker {
	
	public static List<TfCondenserRecipeWrapper> getRecipe(List<TfCondenserRecipe> registry) {
		List<TfCondenserRecipeWrapper> ret = new ArrayList<TfCondenserRecipeWrapper>();
		for (TfCondenserRecipe elem : registry) {
			ret.add(new TfCondenserRecipeWrapper(elem));
		}
		return ret;
	}
}
