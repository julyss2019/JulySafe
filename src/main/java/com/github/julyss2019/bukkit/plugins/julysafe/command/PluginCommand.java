package com.github.julyss2019.bukkit.plugins.julysafe.command;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.util.Util;
import com.github.julyss2019.mcsp.julylibrary.commandv2.JulyCommand;
import com.github.julyss2019.mcsp.julylibrary.commandv2.SenderType;
import com.github.julyss2019.mcsp.julylibrary.commandv2.SubCommand;
import org.bukkit.command.CommandSender;

@com.github.julyss2019.mcsp.julylibrary.commandv2.MainCommand(firstArg = "plugin", description = "插件相关", permission = Util.ADMIN_PER)
public class PluginCommand implements JulyCommand {
    private final JulySafe plugin = JulySafe.getInstance();
    private final LangHelper langHelper = plugin.getLangHelper();

    @SubCommand(firstArg = "reload", description = "重载", length = 0, senders = {SenderType.PLAYER, SenderType.CONSOLE})
    public void reload(CommandSender sender, String[] args) {
        JulySafe.getInstance().reloadPlugin();
        langHelper.sendMsg(sender, "重载插件完毕.");
    }
}
