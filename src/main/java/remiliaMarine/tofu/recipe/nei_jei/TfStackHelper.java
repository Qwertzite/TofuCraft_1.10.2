package remiliaMarine.tofu.recipe.nei_jei;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Objects;

import mezz.jei.api.ingredients.IIngredientHelper;
import remiliaMarine.tofu.TofuCraftCore;

public class TfStackHelper implements IIngredientHelper<TfStack> {

	@Override
	public List<TfStack> expandSubtypes(List<TfStack> ingredients) {
		return ingredients;
	}

	@Override
	public TfStack getMatch(Iterable<TfStack> ingredients, TfStack ingredientToMatch) {
		for (TfStack tfStack : ingredients) {
			if (ingredientToMatch.getTfAmount() == tfStack.getTfAmount()) {
				return tfStack;
			}
		}
		return null;
	}

	@Override
	public String getDisplayName(TfStack ingredient) {
		return ingredient.getLocalisedName();
	}

	@Override
	public String getUniqueId(TfStack ingredient) {
		return "tofuforce:" + String.valueOf(ingredient.getTfAmount());
	}

	@Override
	public String getWildcardId(TfStack ingredient) {
		return this.getUniqueId(ingredient);
	}

	@Override
	public String getModId(TfStack ingredient) {
		return TofuCraftCore.MODID;
	}

	@Override
	public Iterable<Color> getColors(TfStack ingredient) {
		return Collections.emptyList();
	}

	@Override
	public String getErrorInfo(TfStack ingredient) {
		Objects.ToStringHelper toStringHelper = Objects.toStringHelper(TfStack.class);

		toStringHelper.add("Amount", ingredient.getTfAmount());

		return toStringHelper.toString();
	}

}
