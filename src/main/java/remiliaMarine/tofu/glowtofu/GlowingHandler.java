package remiliaMarine.tofu.glowtofu;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import remiliaMarine.tofu.data.DataType;
import remiliaMarine.tofu.data.EntityInfo;


public class GlowingHandler {
	
    private static final GlowingHandler instance = new GlowingHandler();
    private int lightLevel;
    
    private GlowingHandler() {
        this.lightLevel = 12;
    }
    
    public static boolean lightTarget(World var1, EntityLivingBase var2)
    {
        return instance.doLightTarget(var1, var2);
    }
    
    public static void removeLight(World var1, EntityLivingBase var2)
    {
        instance.doRemoveLight(var1, var2);
    }
    
    public static void setHeadLightValue(int headLightValue)
    {
        instance.lightLevel = headLightValue;
    }
    
    private boolean doLightTarget(World world, EntityLivingBase living)
    {
        BlockPos prevPos = this.getLightPosition(world, living);
        int hx = (int)living.posX;
        int hy = (int)(living.posY + living.getEyeHeight());
        int hz = (int)living.posZ;
        BlockPos currPos = new BlockPos(hx, hy, hz);

        if (prevPos != null && !prevPos.equals(currPos))
        {
            this.setLightLevel(world, prevPos, 0);
        }

        this.setLightLevel(world, currPos, lightLevel);
       
        this.setLightPosition(world, living, currPos);
        return true;
    }
    
    private void setLightLevel(World world, BlockPos pos, int newVal)
    {
    	
        int currVal = world.getLightFor(EnumSkyBlock.BLOCK, pos);

        if (currVal < newVal || newVal == 0)
        {
            world.setLightFor(EnumSkyBlock.BLOCK, pos, newVal);
            world.checkLightFor(EnumSkyBlock.BLOCK, pos.west());
            world.checkLightFor(EnumSkyBlock.BLOCK, pos.east());
            world.checkLightFor(EnumSkyBlock.BLOCK, pos.down());
            world.checkLightFor(EnumSkyBlock.BLOCK, pos.down());
            world.checkLightFor(EnumSkyBlock.BLOCK, pos.north());
            world.checkLightFor(EnumSkyBlock.BLOCK, pos.south());
        }
    }

    private void doRemoveLight(World var1, EntityLivingBase var2)
    {
        BlockPos var3 = this.getLightPosition(var1, var2);

        if (var3 != null)
        {
            this.setLightLevel(var1, var3, 0);
            DataType dataType = var1.isRemote ? DataType.GlowTofuLightPositionCl : DataType.GlowTofuLightPositionSv;
            EntityInfo.instance().remove(var2.getEntityId(), dataType);
        }
    }
    
    private BlockPos getLightPosition(World world, EntityLivingBase living)
    {
        DataType dataType = world.isRemote ? DataType.GlowTofuLightPositionCl : DataType.GlowTofuLightPositionSv;
        return EntityInfo.instance().get(living.getEntityId(), dataType);
    }
    
    private void setLightPosition(World world, EntityLivingBase living, BlockPos pos)
    {
        DataType dataType = world.isRemote ? DataType.GlowTofuLightPositionCl : DataType.GlowTofuLightPositionSv;
        EntityInfo.instance().set(living.getEntityId(), dataType, pos);
    }

}
