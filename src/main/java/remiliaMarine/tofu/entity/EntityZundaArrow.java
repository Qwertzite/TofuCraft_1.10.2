package remiliaMarine.tofu.entity;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleCrit;
import net.minecraft.client.particle.ParticleSpell;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.SidedProxy;
import remiliaMarine.tofu.achievement.TcAchievementMgr;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.network.PacketDispatcher;
import remiliaMarine.tofu.network.packet.PacketZundaArrowHit;

public class EntityZundaArrow extends EntityArrowBase {

    @SidedProxy(serverSide = "remiliaMarine.tofu.entity.EntityZundaArrow$CommonProxy", clientSide = "remiliaMarine.tofu.entity.EntityZundaArrow$ClientProxy")
    public static CommonProxy sidedProxy;

    public EntityZundaArrow(World worldIn)
    {
        super(worldIn);
    }

    public EntityZundaArrow(World worldIn, EntityLivingBase shooter)
    {
        super(worldIn, shooter);
    }

    public EntityZundaArrow(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    protected void emitCriticalEffect()
    {
        sidedProxy.emitCriticalEffect(this);
    }

    public static void emitArrowHitEffect(double x, double y, double z)
    {
        sidedProxy.emitArrowHitEffect(x, y, z);
    }

	@Override
	protected void onHitEntity(RayTraceResult raytraceresult) {

        if (raytraceresult.entityHit instanceof EntitySlime)
        {
            EntitySlime slime = (EntitySlime)raytraceresult.entityHit;
            if (!worldObj.isRemote)
            {
                for (int i = 0; i < slime.getSlimeSize(); i++)
                {
                    slime.dropItem(TcItems.tofuZunda, 1);
                }
            }
            slime.setDead();

            if (this.shootingEntity instanceof EntityPlayer)
            {
            	TcAchievementMgr.achieve((EntityPlayer)this.shootingEntity, TcAchievementMgr.Key.zundaAttack);
            }
        }
        else if (raytraceresult.entityHit instanceof EntityLivingBase)
        {
            EntityLivingBase living = (EntityLivingBase)raytraceresult.entityHit;
            living.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, this.getIsCritical() ? 200 : 100, 1));
        }

        this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

        if (!(raytraceresult.entityHit instanceof EntityEnderman))
        {
            this.setDead();
        }

        PacketDispatcher.packet(new PacketZundaArrowHit(raytraceresult.entityHit.posX, raytraceresult.entityHit.posY, raytraceresult.entityHit.posZ))
                .sendToAllInDimension(raytraceresult.entityHit.dimension);

	}

	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(TcItems.zundaArrow);
	}

    public static class CommonProxy
    {
        public void emitCriticalEffect(EntityZundaArrow arrow)
        {
        }
        public void emitArrowHitEffect(double var8, double var10, double var12)
        {
        }
    }

    public static class ClientProxy extends CommonProxy
    {
        @Override
        public void emitCriticalEffect(EntityZundaArrow arrow)
        {
            if (arrow.worldObj.isRemote)
            {
                Minecraft mc = FMLClientHandler.instance().getClient();
                for (int var9 = 0; var9 < 4; ++var9)
                {
                    Particle fx = new ParticleCrit.Factory().createParticle(0, arrow.worldObj, arrow.posX + arrow.motionX * var9 / 4.0D,
                            arrow.posY + arrow.motionY * var9 / 4.0D,
                            arrow.posZ + arrow.motionZ * var9 / 4.0D,
                            -arrow.motionX, -arrow.motionY + 0.2D, -arrow.motionZ);
                    fx.setRBGColorF(0.4f, 1.0f, 0.2f);
                    mc.effectRenderer.addEffect(fx);
                }
            }
        }

        @Override
        public void emitArrowHitEffect(double var8, double var10, double var12)
        {
            Minecraft mc = FMLClientHandler.instance().getClient();
            Random rand = mc.theWorld.rand;
            int var15 = 0x8dd746;
            float var16 = (float)(var15 >> 16 & 255) / 255.0F;
            float var17 = (float)(var15 >> 8 & 255) / 255.0F;
            float var18 = (float)(var15 >> 0 & 255) / 255.0F;
            int var20;
            double var23;
            double var25;
            double var27;
            double var29;
            double var39;

            for (var20 = 0; var20 < 100; ++var20)
            {
                var39 = rand.nextDouble() * 4.0D;
                var23 = rand.nextDouble() * Math.PI * 2.0D;
                var25 = Math.cos(var23) * var39;
                var27 = 0.01D + rand.nextDouble() * 0.5D;
                var29 = Math.sin(var23) * var39;
                Particle var31 = new ParticleSpell.Factory().createParticle(0, mc.theWorld, var8 + var25 * 0.1D, var10 + 0.3D, var12 + var29 * 0.1D, var25, var27, var29);

                float var32 = 0.75F + rand.nextFloat() * 0.25F;
                var31.setRBGColorF(var16 * var32, var17 * var32, var18 * var32);
                var31.multiplyVelocity((float)var39);
                mc.effectRenderer.addEffect(var31);
            }
        }
    }

}
