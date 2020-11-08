package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.objects;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.BaseDropMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.DropMatcherType;
import com.github.julyss2019.mcsp.julylibrary.utilv2.ItemUtil;
import com.github.julyss2019.mcsp.julylibrary.utilv2.ValidateUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class CustomNameDropMatcher extends BaseDropMatcher {
    private final Set<String> regexes;

    public CustomNameDropMatcher(@NotNull ConfigurationSection section) {
        this(new HashSet<>(section.getStringList("regexes")));
    }

    public CustomNameDropMatcher(@NotNull Set<String> regexes) {
        super(DropMatcherType.CUSTOM_NAME);

        ValidateUtil.notNullElement(regexes);
        this.regexes = new HashSet<>(regexes);
    }

    public Set<String> getRegexes() {
        return new HashSet<>(this.regexes);
    }

    @Override
    public boolean isMatched(@NotNull Item item) {
        String displayName = ItemUtil.getDisplayName(item.getItemStack());

        // 特别注意，没名字可能是空字符串
        if (displayName == null || displayName.equals("")) {
            return false;
        }

        for (String regex : this.regexes) {
            if (displayName.matches(regex)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "CustomNameDropMatcher{" +
                "regexes=" + regexes +
                '}';
    }
}
