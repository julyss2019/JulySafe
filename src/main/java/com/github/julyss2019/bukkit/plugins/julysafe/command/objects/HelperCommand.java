package com.github.julyss2019.bukkit.plugins.julysafe.command.objects;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.utils.Util;
import com.github.julyss2019.mcsp.julylibrary.commandv2.JulyCommand;
import com.github.julyss2019.mcsp.julylibrary.commandv2.MainCommand;
import com.github.julyss2019.mcsp.julylibrary.commandv2.SenderType;
import com.github.julyss2019.mcsp.julylibrary.commandv2.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.text.DecimalFormat;
import java.util.*;

@MainCommand(firstArg = "helper", description = "帮助者相关", permission = Util.ADMIN_PER)
public class HelperCommand implements JulyCommand {
    private final JulySafe plugin = JulySafe.getInstance();
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @SubCommand(firstArg = "toggleEntityHelper", description = "开/关实体帮助者", length = 0, senders = SenderType.PLAYER)
    public void onEntityHelper(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (plugin.isEntityHelperPlayer(player)) {
            plugin.removeEntityHelperPlayer(player);
            LangHelper.sendMsg(player, "已关闭.");
        } else {
            plugin.addEntityHelperPlayer(player);
            LangHelper.sendMsg(player, "已开启.");
        }
    }

    @SubCommand(firstArg = "tps", description = "查看 Tps", length = 0, permission = Util.ADMIN_PER, senders = {SenderType.PLAYER, SenderType.CONSOLE})
    public void tps(CommandSender sender, String[] args) {
        LangHelper.sendMsg(sender, "${prefix} &f当前 Tps: " + decimalFormat.format(JulySafe.getInstance().getAverageTps()));
    }

    @SubCommand(firstArg = "entities", description = "查看实体情况", length = 0, permission = Util.ADMIN_PER, senders = {SenderType.PLAYER, SenderType.CONSOLE})
    public void entities(CommandSender sender, String[] args) {
        LangHelper.sendMsg(sender, "${prefix} 颜色指示: &6动物 &c怪物 &dNPC &e其他");
        LangHelper.sendMsg(sender, "${prefix} 以下: ");

        for (World world : Bukkit.getWorlds()) {
            StringBuilder finalMsg = new StringBuilder();
            finalMsg.append("${prefix} [").append(world.getName()).append("]").append(" ");

            Map<EntityType, Integer> counterMap = new HashMap<>();

            for (Entity entity : world.getEntities()) {
                counterMap.put(entity.getType(), counterMap.getOrDefault(entity.getType(), 0) + 1);
            }

            Iterator<Map.Entry<EntityType, Integer>> entryIterator = counterMap.entrySet().stream().sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue())).iterator();
            boolean first = true;

            while (entryIterator.hasNext()) {
                Map.Entry<EntityType, Integer> entry = entryIterator.next();
                EntityType entityType = entry.getKey();

                if (first) {
                    first = false;
                } else {
                    finalMsg.append("&7, ");
                }

                String entityTypeName = entityType.name();
                Class<?> entityClass = entityType.getEntityClass();

                if (entityClass == null) {
                    finalMsg.append("&e");
                } else if (Animals.class.isAssignableFrom(entityClass)) {
                    finalMsg.append("&6");
                } else if (Monster.class.isAssignableFrom(entityClass)) {
                    finalMsg.append("&c");
                } else if (NPC.class.isAssignableFrom(entityClass)) {
                    finalMsg.append("&d");
                } else {
                    finalMsg.append("&e");
                }

                finalMsg.append(entityTypeName).append(" = ").append(entry.getValue());
            }

            LangHelper.sendMsg(sender, finalMsg.toString());
        }
    }
}
