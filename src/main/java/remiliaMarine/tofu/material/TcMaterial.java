package remiliaMarine.tofu.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;

public class TcMaterial extends Material {
    public TcMaterial(MapColor par1MapColor)
    {
        super(par1MapColor);
    }
    public static final Material TOFU = new TcMaterial(MapColor.CLOTH);
    public static final Material SOYMILK = new MaterialLiquid(MapColor.WATER); // Applied noPushMobility by MaterialLiquid
    public static final Material NATTO = new MaterialNatto(MapColor.WOOD);
    
}
