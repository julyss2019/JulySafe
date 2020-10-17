package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.objects;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.BaseDropMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.DropMatcherType;
import com.github.julyss2019.mcsp.julylibrary.utilv2.ItemUtil;
import com.github.julyss2019.mcsp.julylibrary.utilv2.ValidateUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoreDropMatcher extends BaseDropMatcher {
    private final Set<String> regexes;

    public LoreDropMatcher(@NotNull ConfigurationSection section) {
        this(new HashSet<>(section.getStringList("regexes")));
    }

    public LoreDropMatcher(@NotNull Set<String> regexes) {
        super(DropMatcherType.LORE);
        ValidateUtil.notNullElement(regexes);

        this.regexes = new HashSet<>(regexes);
    }

    public Set<String> getRegexes() {
        return new HashSet<>(this.regexes);
    }

    @Override
    public boolean isMatched(@NotNull Item item) {
        List<String> lores = ItemUtil.getLores(item.getItemStack());

        for (String lore : lores) {
            for (String regex : regexes) {
                if (lore.matches(regex)) {
                    return true;
                }
            }
        }

        return false;
    }
}
