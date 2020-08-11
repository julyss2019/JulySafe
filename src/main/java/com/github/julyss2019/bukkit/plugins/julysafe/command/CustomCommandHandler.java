package com.github.julyss2019.bukkit.plugins.julysafe.command;

import com.github.julyss2019.mcsp.julylibrary.commandv2.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CustomCommandHandler extends CommandHandler {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return super.onTabComplete(commandSender, command, s, args);
    }
}
