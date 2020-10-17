package com.github.julyss2019.bukkit.plugins.julysafe.target;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.DropMatcher;
import com.github.julyss2019.mcsp.julylibrary.utilv2.ValidateUtil;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class DropTarget {
    private Set<DropMatcher> includes;
    private Set<DropMatcher> excludes;

    public DropTarget(@NotNull Set<DropMatcher> includes, @NotNull Set<DropMatcher> excludes) {
        this.includes = new HashSet<>(ValidateUtil.notNullElement(includes));
        this.excludes = new HashSet<>(ValidateUtil.notNullElement(excludes));
    }

    public Set<DropMatcher> getIncludes() {
        return new HashSet<>(includes);
    }

    public Set<DropMatcher> getExcludes() {
        return new HashSet<>(excludes);
    }

    public boolean isTarget(@NotNull Item item) {
        for (DropMatcher dropMatcher : excludes) {
            if (dropMatcher.isMatched(item)) {
                return false;
            }
        }

        for (DropMatcher dropMatcher : includes) {
            if (dropMatcher.isMatched(item)) {
                return true;
            }
        }

        return false;
    }
}
