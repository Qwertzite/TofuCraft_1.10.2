package remiliaMarine.tofu.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import remiliaMarine.tofu.block.BlockChikuwaPlatform;
import remiliaMarine.tofu.util.ModLog;
import remiliaMarine.tofu.util.Utils;

public class TileEntityChikuwaPlatform extends TileEntity implements ITickable{

    private int timer = 20;
    private AxisAlignedBB fullBoundingBox;

    @Override
    public void update()
    {
        if (!this.worldObj.isRemote)
        {
            if (BlockChikuwaPlatform.canGoThrough(worldObj, this.pos.down()))
            {
                if (fullBoundingBox == null)
                {
                    fullBoundingBox = new AxisAlignedBB(pos, pos.add(1.0d, 1.0d, 1.0d));
                }

                List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, fullBoundingBox);
                if (list.size() > 0)
                {
                    timer--;
                }
                else
                {
                    timer = 20;
                }

                if (timer <= 0)
                {
                    int chunkUID = (int) (((worldObj.getTotalWorldTime() & 65535) << 16) | (Utils.generateRandomFromCoord(pos) & 65535));
                    ModLog.debug("Chikuwa Chunk UID: " + chunkUID);
                    this.triggerDropping(pos, chunkUID, 0);
                    timer = 20;
                }
            }
        }
    }

	private void triggerDropping(BlockPos pos, int chunkUID, int depth)
    {
        World worldObj = this.getWorld();

        List<Tuple<BlockPos, IBlockState>> droplist = new ArrayList<Tuple<BlockPos, IBlockState>>();
        BlockChikuwaPlatform.Dir blockDir;

    	IBlockState originState = worldObj.getBlockState(pos);
    	BlockChikuwaPlatform block = (BlockChikuwaPlatform)originState.getBlock();
    	blockDir = BlockChikuwaPlatform.getDirection(originState);
    	if (!block.canDropBlock(worldObj, pos)) return;
    	droplist.add(new Tuple<BlockPos, IBlockState>(pos, originState));

        //left
        BlockPos tmpPosL = pos;
        IBlockState tmpStateL = originState;
        for(int i = 1; i <= 127 ; i++) {
        	if (block.canChikuwaConnectTo(worldObj, tmpPosL, tmpStateL, blockDir.left)) {
        		tmpPosL = tmpPosL.offset(blockDir.left);
        		if(!block.canDropBlock(worldObj, tmpPosL)) break;
        		tmpStateL = worldObj.getBlockState(tmpPosL);
        		droplist.add(new Tuple<BlockPos, IBlockState>(tmpPosL, tmpStateL));
        	} else {
        		break;
        	}
        }

        //right
        BlockPos tmpPosR = pos;
        IBlockState tmpStateR = originState;
        for(int i = 1; i <= 127 ; i++) {
        	if (block.canChikuwaConnectTo(worldObj, tmpPosR, tmpStateR, blockDir.right)) {
        		tmpPosR = tmpPosR.offset(blockDir.right);
        		if(!block.canDropBlock(worldObj, tmpPosR)) break;
        		tmpStateR = worldObj.getBlockState(tmpPosR);
        		droplist.add(new Tuple<BlockPos, IBlockState>(tmpPosR, tmpStateR));
        	} else {
        		break;
        	}
        }

        for (Tuple<BlockPos, IBlockState> entry : droplist) {
        	block.dropBlockNoCheck(worldObj, entry.getFirst(), entry.getSecond(), chunkUID);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagIn)
    {
        super.writeToNBT(nbtTagIn);

        nbtTagIn.setShort("Timer", (short)timer);
        return nbtTagIn;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagIn)
    {
        super.readFromNBT(nbtTagIn);

        this.timer = nbtTagIn.getShort("Timer");
    }
}
