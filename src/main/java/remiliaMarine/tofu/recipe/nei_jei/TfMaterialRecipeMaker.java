package remiliaMarine.tofu.recipe.nei_jei;

import java.util.ArrayList;
import java.util.List;

import remiliaMarine.tofu.api.TfMaterialRegistry;

public class TfMaterialRecipeMaker {

	public static List<TfMaterialRecipeWrapper> getRecipe(List<TfMaterialRegistry> registry) {
		List<TfMaterialRecipeWrapper> ret = new ArrayList<TfMaterialRecipeWrapper>();
		
		for (TfMaterialRegistry elem : registry) {
			ret.add(new TfMaterialRecipeWrapper(elem));
		}
		
		return ret;
	}
	
}
