package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.objects;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.BaseEntityMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.EntityMatcherType;
import com.github.julyss2019.mcsp.julylibrary.utilv2.ValidateUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * 匹配展示名
 */
public class CustomNameEntityMatcher extends BaseEntityMatcher {
    private Set<String> regexes;

    public CustomNameEntityMatcher(@NotNull ConfigurationSection section) {
        this(new HashSet<>(section.getStringList("regexes")));
    }

    public CustomNameEntityMatcher(@NotNull Set<String> regexes) {
        super(EntityMatcherType.CUSTOM_NAME);
        ValidateUtil.notNullElement(regexes);

        this.regexes = new HashSet<>(regexes);
    }

    public Set<String> getRegexes() {
        return new HashSet<>(this.regexes);
    }

    @Override
    public boolean isMatched(@NotNull Entity entity) {
        String entityCustomName = entity.getCustomName();

        // 特别注意，没名字可能是空字符串
        if (entityCustomName == null || entityCustomName.equals("")) {
            return false;
        }

        for (String regex : this.regexes) {
            if (entityCustomName.matches(regex)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "CustomNameEntityMatcher{" +
                "regexes=" + regexes +
                '}';
    }
}
