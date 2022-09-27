package remiliaMarine.tofu.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import remiliaMarine.tofu.entity.EntityTofuSlime;

public class CommandTofuSlimeCheck extends CommandBase {

    @Override
    public String getCommandName()
    {
        return "tofuslimecheck";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] var2) throws PlayerNotFoundException
    {
    	BlockPos pos = sender.getPosition();
        boolean isSpawnChunk = EntityTofuSlime.isSpawnChunk(sender.getEntityWorld(), pos.getX(), pos.getZ());

        TextComponentTranslation textcomponent;
        if (isSpawnChunk)
        {
        	textcomponent = new TextComponentTranslation("commands.tofuslimecheck.found");
        }
        else
        {
        	textcomponent = new TextComponentTranslation("commands.tofuslimecheck.notFound");
        }
        sender.addChatMessage(textcomponent);
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "commands.tofuslimecheck.usage";
    }
}
