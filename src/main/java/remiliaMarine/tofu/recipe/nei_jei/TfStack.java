package remiliaMarine.tofu.recipe.nei_jei;

import net.minecraft.client.resources.I18n;

public class TfStack {
	
	private static String localisedName = I18n.format("tofucraft.tofuForce");
	
	private double tfAmount;
	
	public TfStack(double amount) {
		this.tfAmount = amount;
	}
	
	public double getTfAmount() {
		return this.tfAmount;
	}
	
	public TfStack setTfAmount(double amount) {
		this.tfAmount = amount;
		return this;
	}
	
	public String getLocalisedName() {
		return TfStack.localisedName;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof TfStack) {
			return ((TfStack)other).getTfAmount() == this.getTfAmount();
		} else return false;
	}
}
