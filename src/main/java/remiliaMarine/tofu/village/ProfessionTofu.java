package remiliaMarine.tofu.village;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public class ProfessionTofu extends VillagerProfession {
	
    private List<CareerTofu> careers = Lists.newArrayList();
    
	public ProfessionTofu(String texture){
        super("tofucraft:tofucraftsperson",
        		texture,//new ResourceLocation("tofucraft", "textures/mob/tofucook.png").toString(),
        		"minecraft:textures/entity/zombie_villager/zombie_villager.png");
    }
 
    public ProfessionTofu register(CareerTofu career){
        this.careers.add(career);
        career.setId(this.careers.size() -1);
        return this;
    }
 
    @Override
    public CareerTofu getCareer(int id){
        return careers.get(id);
    }
 
    @Override
    public int getRandomCareer(Random rand){
        return rand.nextInt(careers.size());
    }
    
}
