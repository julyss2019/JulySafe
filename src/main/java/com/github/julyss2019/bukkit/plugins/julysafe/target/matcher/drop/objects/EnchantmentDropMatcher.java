package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.objects;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.BaseDropMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.DropMatcherType;
import com.github.julyss2019.mcsp.julylibrary.utilv2.ValidateUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class EnchantmentDropMatcher extends BaseDropMatcher {
    private final Set<String> regexes;
    private final Set<Enchantment> enchantments = new HashSet<>();

    public EnchantmentDropMatcher(@NotNull ConfigurationSection section) {
        this(new HashSet<>(section.getStringList("regexes")));
    }

    public EnchantmentDropMatcher(@NotNull Set<String> regexes) {
        super(DropMatcherType.ENCHANTMENT);

        ValidateUtil.notNullElement(regexes);

        this.regexes = new HashSet<>(regexes);

        for (String regex : regexes) {
            for (Enchantment enchantment : Enchantment.values()) {
                if (enchantment.getName().matches(regex)) {
                    enchantments.add(enchantment);
                }
            }
        }
    }

    public Set<String> getRegexes() {
        return regexes;
    }

    @Override
    public boolean isMatched(@NotNull Item item) {
        for (Enchantment enchantment : enchantments) {
            for (Enchantment itemEnchantment : item.getItemStack().getEnchantments().keySet()) {
                if (enchantment == itemEnchantment) {
                    return true;
                }
            }
        }

        return true;
    }
}
