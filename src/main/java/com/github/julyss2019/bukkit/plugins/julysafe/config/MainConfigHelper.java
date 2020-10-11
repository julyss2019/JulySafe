package com.github.julyss2019.bukkit.plugins.julysafe.config;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.DropMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.EntityMatcher;
import com.github.julyss2019.mcsp.julylibrary.utilv2.ValidateUtil;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;

public class MainConfigHelper {
    private final MainConfig mainConfig = JulySafe.getInstance().getMainConfig();

    public AntiEntityFarmLimit matchAntiEntityFarmLimit(@NotNull Entity entity) {
        for (AntiEntityFarmLimit limit : mainConfig.getAntiEntityFarmLimits()) {
            if (limit.getTarget().isTarget(entity)) {
                return limit;
            }
        }

        return null;
    }

    public boolean isAntiEntityFarmWorld(@NotNull World world) {
        return isTargetWorld(world, mainConfig.getAntiEntityFarmWorlds());
    }

    public boolean isRedstoneLimitWorld(@NotNull World world) {
        return isTargetWorld(world, mainConfig.getRedstoneLimitWorlds());
    }

    public boolean isCleanDropWorld(@NotNull World world) {
        return isTargetWorld(world, mainConfig.getCleanDropWorlds());
    }

    public boolean isCleanEntityWorld(@NotNull World world) {
        return isTargetWorld(world, mainConfig.getCleanEntityWorlds());
    }

    /**
     * 是否为目标世界
     * @param world 世界
     * @param regexWorlds 正则世界名
     * @return
     */
    private boolean isTargetWorld(@NotNull World world, @NotNull Collection<String> regexWorlds) {
        ValidateUtil.notNullElement(regexWorlds);

        for (String regex : regexWorlds) {
            if (world.getName().matches(regex)) {
                return true;
            }
        }

        return false;
    }
}
