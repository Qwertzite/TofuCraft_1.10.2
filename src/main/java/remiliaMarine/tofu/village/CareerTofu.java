package remiliaMarine.tofu.village;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;

public class CareerTofu extends VillagerCareer{

    private ProfessionTofu profession;
    private String name;
    private int id;
    private List<List<ITradeList>> trades = Lists.newArrayList();

    public CareerTofu(ProfessionTofu parent, String name)
    {
    	super(parent, name);
    	System.out.println("ato");
        this.profession = parent;
        this.name = name;
        parent.register(this);
    }

    public String getName()
    {
        return this.name;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public int getId() {
    	return this.id;
    }

    public CareerTofu addTrade(int level, ITradeList... trades)
    {
        if (level <= 0)
            throw new IllegalArgumentException("Levels start at 1");

        List<ITradeList> levelTrades = level <= this.trades.size() ? this.trades.get(level - 1) : null;
        if (levelTrades == null)
        {
            while (this.trades.size() < level)
            {
                levelTrades = Lists.newArrayList();
                this.trades.add(levelTrades);
            }
        }
        if (levelTrades == null) //Not sure how this could happen, but screw it
        {
            levelTrades = Lists.newArrayList();
            this.trades.set(level - 1, levelTrades);
        }
        for (ITradeList t : trades)
            levelTrades.add(t);
        return this;
    }

    @Override
    public List<ITradeList> getTrades(int level)
    {
        return level >= 0 && level < this.trades.size() ? Collections.unmodifiableList(this.trades.get(level)) : null;
    }
    
    public CareerTofu init(EntityVillager.ITradeList[][] trades)
    {
        for (int x = 0; x < trades.length; x++)
            this.trades.add(Lists.newArrayList(trades[x]));
        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }
        if (!(o instanceof CareerTofu))
        {
            return false;
        }
        CareerTofu oc = (CareerTofu)o;
        return name.equals(oc.name) && profession == oc.profession;
    }
}
