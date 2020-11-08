package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.objects;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.BaseDropMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.DropMatcherType;
import com.github.julyss2019.mcsp.julylibrary.utilv2.ValidateUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class EnumDropMatcher extends BaseDropMatcher {
    private final Set<String> regexes;
    private final Set<Material> materials = new HashSet<>();

    public EnumDropMatcher(@NotNull ConfigurationSection section) {
        this(new HashSet<>(section.getStringList("regexes")));
    }

    public EnumDropMatcher(@NotNull Set<String> regexes) {
        super(DropMatcherType.ENUM);
        ValidateUtil.notNullElement(regexes);

        this.regexes = regexes;

        for (Material material : Material.values()) {
            for (String regex : regexes) {
                if (material.name().matches(regex)) {
                    this.materials.add(material);
                }
            }
        }
    }

    public Set<String> getRegexes() {
        return new HashSet<>(this.regexes);
    }

    @Override
    public boolean isMatched(@NotNull Item Item) {
        return this.materials.contains(Item.getItemStack().getType());
    }

    @Override
    public String toString() {
        return "EnumDropMatcher{" +
                "regexes=" + regexes +
                ", materials=" + materials +
                '}';
    }
}
