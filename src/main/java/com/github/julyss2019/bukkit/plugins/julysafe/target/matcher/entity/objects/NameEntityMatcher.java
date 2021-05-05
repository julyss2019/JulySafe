package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.objects;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.BaseEntityMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.EntityMatcherType;
import com.github.julyss2019.mcsp.julylibrary.utilv2.ValidateUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class NameEntityMatcher extends BaseEntityMatcher {
    private Set<String> regexes;

    public NameEntityMatcher(@NotNull ConfigurationSection section) {
        this(new HashSet<>(section.getStringList("regexes")));
    }

    public NameEntityMatcher(@NotNull Set<String> regexes) {
        super(EntityMatcherType.NAME);
        ValidateUtil.notNullElement(regexes);

        this.regexes = new HashSet<>(regexes);
    }

    public Set<String> getRegexes() {
        return new HashSet<>(this.regexes);
    }

    @Override
    public boolean isMatched(@NotNull Entity entity) {
        String name = entity.getName();

        if (name.equals("")) {
            return false;
        }

        for (String regex : this.regexes) {
            if (name.matches(regex)) {
                return true;
            }

        }

        return false;
    }
}
