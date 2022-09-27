package remiliaMarine.tofu.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class DimensionTeleportation {
    public void transferPlayerToDimension(EntityPlayerMP par1EntityPlayerMP, int dest)
    {
        par1EntityPlayerMP.mcServer.getPlayerList().transferPlayerToDimension(
                par1EntityPlayerMP, dest, new TofuTeleporter(par1EntityPlayerMP.mcServer.worldServerForDimension(dest))
                );
    }
    
    public Entity transferEntityToDimension(Entity entity, int dest)
    {
        if (!entity.worldObj.isRemote && !entity.isDead)
        {
            entity.worldObj.theProfiler.startSection("changeDimension");
            
            MinecraftServer minecraftserver = FMLCommonHandler.instance().getMinecraftServerInstance();
            int origin = entity.dimension;
            WorldServer worldserverIn = minecraftserver.worldServerForDimension(origin);
            WorldServer worldserver1 = minecraftserver.worldServerForDimension(dest);
            

            entity.dimension = dest;
            worldserverIn.removeEntityDangerously(entity);

            entity.isDead = false;

            this.transferEntityToWorld(entity, origin, worldserverIn, worldserver1);
            entity.worldObj.theProfiler.endSection();
            return entity;
            

//            int j = entity.dimension;
//            entity.dimension = dest;
//
//            entity.worldObj.removeEntity(entity);
//            entity.isDead = false;
//            entity.worldObj.theProfiler.startSection("reposition");
//            this.transferEntityToWorld(entity, j, worldserverIn, worldserver1);
//            entity.worldObj.theProfiler.endStartSection("reloading");
//            Entity entity2 = EntityList.createEntityByName(EntityList.getEntityString(entity), worldserver1);
//
//            if (entity2 != null)
//            {
//                entity2.copyDataFrom(entity, true);
//
//                worldserver1.spawnEntityInWorld(entity2);
//            }
//
//            entity.isDead = true;
//            entity.worldObj.theProfiler.endSection();
//            worldserverIn.resetUpdateEntityTick();
//            worldserver1.resetUpdateEntityTick();
//            return entity2;
        }
        return null;
    }
    
    
    
    
    
    /**
     * Transfers an entity from a world to another world.
     */
    private void transferEntityToWorld(Entity par1Entity, int par2, WorldServer oldWorldServer, WorldServer worldServerTo)
    {
    	FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().transferEntityToWorld(
                par1Entity, par2, oldWorldServer, worldServerTo, new TofuTeleporter(worldServerTo));
    }
    
}
