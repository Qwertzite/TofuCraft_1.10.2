package remiliaMarine.tofu.entity;

import net.minecraft.client.particle.ParticlePortal;
import net.minecraft.world.World;

public class ParticleTofuPortal extends ParticlePortal {

	public ParticleTofuPortal(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        float f = this.rand.nextFloat() * 0.3F + 0.7F;
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F * f;
        this.particleGreen *= 1.0F;
        this.particleRed *= 1.0F;
        this.particleBlue *= 0.9F;
	}

}
