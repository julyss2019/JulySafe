package com.github.julyss2019.bukkit.plugins.julysafe.command.objects;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.utils.Util;
import com.github.julyss2019.mcsp.julylibrary.commandv2.JulyCommand;
import com.github.julyss2019.mcsp.julylibrary.commandv2.MainCommand;
import com.github.julyss2019.mcsp.julylibrary.commandv2.SenderType;
import com.github.julyss2019.mcsp.julylibrary.commandv2.SubCommand;
import org.bukkit.command.CommandSender;

@MainCommand(firstArg = "plugin", description = "插件相关", permission = Util.ADMIN_PER)
public class PluginCommand implements JulyCommand {
    @SubCommand(firstArg = "version", description = "显示插件版本", length = 0, senders = {SenderType.PLAYER, SenderType.CONSOLE})
    public void version(CommandSender sender, String[] args) {
        LangHelper.sendMsg(sender, "${prefix} 插件版本: " + JulySafe.getInstance().getDescription().getVersion() + ".");
    }

    @SubCommand(firstArg = "reload", description = "重载", length = 0, senders = {SenderType.PLAYER, SenderType.CONSOLE})
    public void reload(CommandSender sender, String[] args) {
        JulySafe.getInstance().reloadPlugin();

        LangHelper.sendMsg(sender, "${prefix} 重载插件完毕.");
    }
}
