package remiliaMarine.tofu.block;

import java.util.Random;

import remiliaMarine.tofu.material.TcMaterial;

public class BlockTcOreDiamond extends BlockTcOre {

    public BlockTcOreDiamond(int minXp, int maxXp)
    {
        super(TcMaterial.TOFU, minXp, maxXp);
    }

    public int quantityDropped(Random par1Random)
    {
        return par1Random.nextInt(3) + 1;
    }
}
