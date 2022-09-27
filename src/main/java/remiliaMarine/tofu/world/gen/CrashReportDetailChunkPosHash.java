package remiliaMarine.tofu.world.gen;

import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.util.math.ChunkPos;

class CrashReportDetailChunkPosHash implements ICrashReportDetail<String>
 {
	final int field_85165_a;

	final int field_85163_b;

	final TcMapGenStructure theMapStructureGenerator;

	CrashReportDetailChunkPosHash(TcMapGenStructure par1MapGenStructure, int par2, int par3)
	    {
	        this.theMapStructureGenerator = par1MapGenStructure;
	        this.field_85165_a = par2;
	        this.field_85163_b = par3;
	    }

	public String callChunkPositionHash() {
		return String.valueOf(ChunkPos.asLong(this.field_85165_a, this.field_85163_b));
	}

	@Override
	public String call() {
		return this.callChunkPositionHash();
	}
}

