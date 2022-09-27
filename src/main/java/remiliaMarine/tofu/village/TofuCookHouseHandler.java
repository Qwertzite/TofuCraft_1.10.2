package remiliaMarine.tofu.village;

import java.util.List;
import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;

public class TofuCookHouseHandler implements IVillageCreationHandler {

    @Override
    public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int i)
    {
        return new StructureVillagePieces.PieceWeight(ComponentVillageHouseTofu.class, 20, MathHelper.getRandomIntegerInRange(random, 0 + i, 1 + i));
    }

    @Override
    public Class<?> getComponentClass()
    {
    	return ComponentVillageHouseTofu.class;
    }

	@Override
	public Village buildComponent(PieceWeight villagePiece, Start startPiece, List<StructureComponent> pieces, Random random, int p1, int p2, int p3, EnumFacing facing, int p5) {
        StructureBoundingBox structureboundingbox = ComponentVillageHouseTofu.getStructureBoundingBox(p1, p2, p3, facing);
        return StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new ComponentVillageHouseTofu(startPiece, p5, random, structureboundingbox, facing) : null;
    
	}

}
