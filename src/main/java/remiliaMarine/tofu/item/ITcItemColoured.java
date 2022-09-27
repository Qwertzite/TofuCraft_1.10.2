package remiliaMarine.tofu.item;

public interface ITcItemColoured{
	/** register IItemColor() using "Minecraft.getMinecraft().getItemColors().registerItemColorHandler( new IItemColour())" */
	public int getItemColour(int meta);
}
