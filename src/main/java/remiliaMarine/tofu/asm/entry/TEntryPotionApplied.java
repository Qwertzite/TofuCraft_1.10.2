package remiliaMarine.tofu.asm.entry;

import java.util.EnumSet;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraftforge.fml.relauncher.Side;
import remiliaMarine.tofu.asm.AsmPetitUtil;
import remiliaMarine.tofu.asm.ITransformerEntry;

public class TEntryPotionApplied implements Opcodes, ITransformerEntry {

    @Override
    public String getTargetClass()
    {
        return "net.minecraft.entity.EntityLivingBase";
    }

    @Override
    public String getTargetMethodDeobf()
    {
        return "addPotionEffect";
    }

	@Override
	public String getTargetMethodObf() {
		return "func_70690_d";
	}

	@Override
	public String getTargetMethodDesc() {
		return "(Lnet/minecraft/potion/PotionEffect;)V";
	}


    @Override
    public void transform(MethodNode mnode, ClassNode cnode)
    {
        String potionEffect = AsmPetitUtil.getActualClass("net/minecraft/potion/PotionEffect");

        InsnList overrideList = new InsnList();

        overrideList.add(new VarInsnNode(ALOAD, 0));
        overrideList.add(new VarInsnNode(ALOAD, 1));

        overrideList.add(new MethodInsnNode(INVOKESTATIC,
                "remiliaMarine/tofu/eventhandler/PotionEventHook",
                "onPotionEffectApplied",
                "(L" + cnode.name + ";L" + potionEffect + ";)L" + potionEffect + ";"));
        overrideList.add(new VarInsnNode(ASTORE, 1));

        mnode.instructions.insert(mnode.instructions.get(1), overrideList);

    }

    @Override
    public EnumSet<Side> getSide()
    {
        return EnumSet.of(Side.CLIENT, Side.SERVER);
    }

}
