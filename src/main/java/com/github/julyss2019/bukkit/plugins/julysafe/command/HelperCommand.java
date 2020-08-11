package com.github.julyss2019.bukkit.plugins.julysafe.command;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.util.Util;
import com.github.julyss2019.mcsp.julylibrary.commandv2.JulyCommand;
import com.github.julyss2019.mcsp.julylibrary.commandv2.MainCommand;
import com.github.julyss2019.mcsp.julylibrary.commandv2.SenderType;
import com.github.julyss2019.mcsp.julylibrary.commandv2.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@MainCommand(firstArg = "helper", description = "帮助者相关", permission = Util.ADMIN_PER)
public class HelperCommand implements JulyCommand {
    private final JulySafe plugin = JulySafe.getInstance();
    private final LangHelper langHelper = plugin.getLangHelper();
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @SubCommand(firstArg = "findEntities", description = "查找实体", length = 3, subArgs = {"<世界>", "<类型>", "<条数(-1)>"}, permission = Util.ADMIN_PER, senders = {SenderType.PLAYER, SenderType.CONSOLE})
    public void findEntities(CommandSender sender, String[] args) {
        String argWorld = args[0];
        String argType = args[1];
        String argAmount = args[2];

        EntityType entityType;

        try {
            entityType = EntityType.valueOf(argType);
        } catch (Exception e) {
            langHelper.sendMsg(sender, "&c实体类型不合法: " + argType + ".");
            return;
        }

        int amount;

        try {
            amount = Integer.parseInt(argAmount);
        } catch (Exception e) {
            langHelper.sendMsg(sender, "&c数量不合法: " + argAmount + ".");
            return;
        }

        if (amount != -1 && amount <= 0) {
            langHelper.sendMsg(sender, "&c数量不合法: " + argAmount + ".");
            return;
        }

        World world = Bukkit.getWorld(argWorld);

        if (world == null) {
            langHelper.sendMsg(sender, "&c数世界不合法: " + argWorld + ".");
            return;
        }

        langHelper.sendMsg(sender, "以下: ");

        List<Entity> filterEntities = world.getEntities().stream().filter(entity -> entity.getType() == entityType).collect(Collectors.toList());
        Map<Chunk, Integer> counterMap = new HashMap<>();
        Map<Chunk, Location> sampleLocationMap = new HashMap<>();

        filterEntities.forEach(entity -> {
            Chunk chunk = entity.getLocation().getChunk();

            counterMap.put(chunk, counterMap.getOrDefault(chunk, 0) + 1);

            if (!sampleLocationMap.containsKey(chunk)) {
                sampleLocationMap.put(chunk, entity.getLocation());
            }
        });


        int counter = 0;

        for (Chunk chunk : counterMap.keySet().stream().sorted((o1, o2) -> counterMap.get(o2).compareTo(counterMap.get(o1))).collect(Collectors.toList())) {
            if (amount == -1 || counter++ < amount) {
                Location loc = sampleLocationMap.get(chunk);

                langHelper.sendMsg(sender, "世界 = " + argWorld + ", 区块(" + chunk.getX() + ", " + chunk.getZ() + "), 数量 = " + counterMap.get(chunk) + ", 样本位置 = (" + (int) loc.getX() + ", " + (int) loc.getY() + ", " + (int) loc.getZ() + ")");
            }
        }
    }

    @SubCommand(firstArg = "tps", description = "查看 Tps", length = 0, permission = Util.ADMIN_PER, senders = {SenderType.PLAYER, SenderType.CONSOLE})
    public void tps(CommandSender sender, String[] args) {
        langHelper.sendMsg(sender, "&f当前 Tps: " + decimalFormat.format(JulySafe.getInstance().getAverageTps()));
    }

    @SubCommand(firstArg = "entities", description = "查看实体情况", length = 0, permission = Util.ADMIN_PER, senders = {SenderType.PLAYER, SenderType.CONSOLE})
    public void entities(CommandSender sender, String[] args) {
        langHelper.sendMsg(sender, "颜色指示: &6动物 &c怪物 &dNPC &e其他");
        langHelper.sendMsg(sender, "以下: ");

        for (World world : Bukkit.getWorlds()) {
            StringBuilder finalMsg = new StringBuilder();
            finalMsg.append("[").append(world.getName()).append("]").append(" ");

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

            langHelper.sendMsg(sender, finalMsg.toString());
        }
    }
}
