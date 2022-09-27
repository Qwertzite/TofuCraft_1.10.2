package remiliaMarine.tofu.item;

public class ItemAltIcon extends TcItem {
	private String iconName;

	public ItemAltIcon() {
		super();
	}

	public TcItem setIconName(String name)
	{
		this.iconName = name;
		return this;
	}

//	@Override
//    @SideOnly(Side.CLIENT)
//    public void registerIcons(IIconRegister par1IconRegister)
//    {
//    	this.itemIcon = par1IconRegister.registerIcon(iconName);
//    }
}
