package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.objects;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.BaseEntityMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.EntityMatcherType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 *
 */
public class ClassEntityMatcher extends BaseEntityMatcher {
    private final Set<Class<? extends Entity>> classes;
    private final Set<EntityType> entityTypes = new HashSet<>();

    public ClassEntityMatcher(@NotNull ConfigurationSection section) {
        this(new HashSet<>(getClasses(new HashSet<>(section.getStringList("classes")))));
    }

    private static Set<Class<? extends Entity>> getClasses(@NotNull Set<String> classNames) {
        Set<Class<? extends Entity>> classes = new HashSet<>();

        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName("org.bukkit.entity." + className);

                if (!Entity.class.isAssignableFrom(clazz)) {
                    throw new RuntimeException("class name " + className + " is not a valid Entity class name.");
                }

                //noinspection unchecked
                classes.add((Class<? extends Entity>) clazz);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return classes;
    }

    public ClassEntityMatcher(@NotNull Set<Class<? extends Entity>> classes) {
        super(EntityMatcherType.CLASS);
        this.classes = classes;

        for (EntityType entityType : EntityType.values()) {
            for (Class<? extends Entity> clazz : classes) {
                if (Optional.ofNullable(entityType.getEntityClass()).map(clazz::isAssignableFrom).orElse(false)) {
                    this.entityTypes.add(entityType);
                }
            }
        }
    }

    public Set<Class<? extends Entity>> getClasses() {
        return new HashSet<>(this.classes);
    }

    @Override
    public boolean isMatched(@NotNull Entity entity) {
        return entityTypes.contains(entity.getType());
    }

    @Override
    public String toString() {
        return "ClassEntityMatcher{" +
                "classes=" + classes +
                ", entityTypes=" + entityTypes +
                '}';
    }
}
