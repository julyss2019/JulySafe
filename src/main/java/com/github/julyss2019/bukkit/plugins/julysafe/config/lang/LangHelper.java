package com.github.julyss2019.bukkit.plugins.julysafe.config.lang;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.mcsp.julylibrary.message.JulyMessage;
import com.github.julyss2019.mcsp.julylibrary.text.JulyText;
import com.github.julyss2019.mcsp.julylibrary.text.PlaceholderContainer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class LangHelper {
    private Lang rootLang = JulySafe.getInstance().getLang();

    public void sendMsg(@NotNull CommandSender sender, @NotNull String msg) {
        JulyMessage.sendColoredMessage(sender, handleText(msg));
    }

    public void sendConsoleMsg(@NotNull String msg) {
        sendMsg(Bukkit.getConsoleSender(), msg);
    }

    public void broadcastMsg(@NotNull String msg) {
        JulyMessage.broadcastColoredMessage(handleText(msg));
    }

    public String handleText(@NotNull String text) {
        return JulyText.setPlaceholders(text, new PlaceholderContainer().add("prefix", rootLang.getString("prefix")));
    }
}
