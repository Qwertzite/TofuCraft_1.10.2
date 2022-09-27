package remiliaMarine.tofu.world.gen;

import net.minecraft.crash.ICrashReportDetail;

class CrashReportDetailStructureType implements ICrashReportDetail<String> {

    final TcMapGenStructure theMapStructureGenerator;

    CrashReportDetailStructureType(TcMapGenStructure par1MapGenStructure)
    {
        this.theMapStructureGenerator = par1MapGenStructure;
    }

    public String callStructureType()
    {
        return this.theMapStructureGenerator.getClass().getCanonicalName();
    }

    @Override
    public String call()
    {
        return this.callStructureType();
    }
}
