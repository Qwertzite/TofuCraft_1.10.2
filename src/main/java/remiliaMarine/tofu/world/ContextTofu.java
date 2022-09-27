package remiliaMarine.tofu.world;

import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent.Context;

public class ContextTofu extends Context {
	
    private NoiseGeneratorPerlin  height;
    private NoiseGeneratorOctaves mobSpawner;

	public ContextTofu(NoiseGeneratorOctaves lperlin1, NoiseGeneratorOctaves lperlin2, NoiseGeneratorOctaves perlin, NoiseGeneratorPerlin height, NoiseGeneratorOctaves scale, NoiseGeneratorOctaves depth, NoiseGeneratorOctaves mobSpawner) {
		super(lperlin1, lperlin2, perlin, scale, depth);
        this.height = height;
        this.mobSpawner = mobSpawner;
	}
	@Override
    public ContextTofu clone() {
		return new ContextTofu(getLPerlin1(), getLPerlin2(), getPerlin(), height, getScale(), getDepth(), mobSpawner);
	}
    public NoiseGeneratorPerlin  getHeight()   { return this.height;   }
    public NoiseGeneratorOctaves getMobSpawner()   { return this.mobSpawner;   }

    public void getHeight  (NoiseGeneratorPerlin  value) { this.height   = value; }
    public void getForest  (NoiseGeneratorOctaves value) { this.mobSpawner   = value; }

}
