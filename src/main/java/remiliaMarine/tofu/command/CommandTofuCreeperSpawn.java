package remiliaMarine.tofu.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.biome.Biome;
import remiliaMarine.tofu.entity.EntityTofuCreeper;

public class CommandTofuCreeperSpawn extends CommandBase {

    @Override
    public String getCommandName()
    {
        return "tofucreeperspawn";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_)
    {
        return "commands.tofucreeperspawn.usage";
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		StringBuilder sb = new StringBuilder();
        int[] biomeIds = EntityTofuCreeper.getSpawnBiomeIds(sender.getEntityWorld());
        for (int id : biomeIds)
        {
            if (sb.length() != 0)
            {
                sb.append(", ");
            }
            sb.append(Biome.getBiome(id).getBiomeName());
        }
        sender.addChatMessage(new TextComponentTranslation("commands.tofucreeperspawn.result", sb.toString()));
	}

}
