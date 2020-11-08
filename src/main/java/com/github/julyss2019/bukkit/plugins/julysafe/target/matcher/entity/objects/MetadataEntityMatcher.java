package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.objects;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.BaseEntityMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.EntityMatcherType;
import com.github.julyss2019.mcsp.julylibrary.utilv2.ValidateUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class MetadataEntityMatcher extends BaseEntityMatcher {
    private final Set<String> names;

    public MetadataEntityMatcher(@NotNull ConfigurationSection section) {
        this(new HashSet<>(section.getStringList("names")));
    }

    public MetadataEntityMatcher(@NotNull Set<String> names) {
        super(EntityMatcherType.METADATA);
        ValidateUtil.notNullElement(names);

        this.names = new HashSet<>(names);
    }

    public Set<String> getNames() {
        return new HashSet<>(this.names);
    }

    @Override
    public boolean isMatched(@NotNull Entity entity) {
        for (String name : this.names) {
            if (entity.hasMetadata(name)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "MetadataEntityMatcher{" +
                "names=" + names +
                '}';
    }
}
