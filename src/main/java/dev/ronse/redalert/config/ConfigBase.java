package dev.ronse.redalert.config;

import dev.ronse.redalert.RedAlert;
import dev.ronse.redalert.config.annotations.ConfigEntry;
import dev.ronse.redalert.config.annotations.Key;
import dev.ronse.redalert.exceptions.EntryRequired;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class ConfigBase {
    private final Object NO_VALUE = new Object();
    protected final File file;
    protected final YamlConfiguration config;
    private final RedAlert plugin;

    public ConfigBase(RedAlert plugin, String path) {
        this(plugin, new File(plugin.getDataFolder(), path));
    }

    private ConfigBase(RedAlert plugin, File file) {
        this.file = file;
        config = new YamlConfiguration();

        this.plugin = plugin;

        reload();
    }

    public final void reload() {
        try {
            this.config.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        var entryFields = findEntryFields(this.getClass());

        try {
            for (var field : entryFields) reloadField(field);
        } catch (NotSerializableException | EntryRequired e) {
            throw new RuntimeException(e);
        }
    }

    private void reloadField(Field field) throws NotSerializableException, EntryRequired {
        final ConfigEntry entry = field.getAnnotation(ConfigEntry.class);
        final Key key = field.getAnnotation(Key.class);
        final String entryName = key == null ? field.getName() : key.name();

        final Method deserialize = getMethod(field.getType(), "deserialize", Map.class);
        final ConfigurationSection section = this.config.getConfigurationSection(entryName);

        // plugin.getSLF4JLogger().info(String.valueOf(section));
        // plugin.getSLF4JLogger().info(String.valueOf(this.config.get(entryName, NO_VALUE)));
        // plugin.getSLF4JLogger().info(NO_VALUE.toString());

        if(section == null) {  // is not custom type
            Object value = this.config.get(entryName, NO_VALUE);

            if(value == NO_VALUE && entry.required()) {
                throw new RuntimeException(new EntryRequired(entryName));
            }

            if(field.getType() == String.class) setFieldWithHandling(field, this, this.config.getString(entryName));
            else setFieldWithHandling(field, this, value == NO_VALUE ? null : value);
            return;
        }

        if(deserialize == null) throw new NotSerializableException(field.getType().getName());
        Optional<?> opt = invokeWithHandling(deserialize, true, null, section.getValues(true));
        if(opt.isEmpty() && entry.required()) throw new EntryRequired(entryName);

        setFieldWithHandling(field, this, opt.orElse(null));
    }

    @SuppressWarnings({"unchecked", "SameParameterValue"})
    private @NotNull <T> Optional<T> invokeWithHandling(Method method, boolean isStatic, Object target, Object... args) {
        try {
            boolean isAccessible = method.canAccess(isStatic ? null : this);

            try {
                method.setAccessible(true);
                T result = (T) method.invoke(target, args);
                return Optional.ofNullable(result);
            } finally {
                method.setAccessible(isAccessible);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFieldWithHandling(Field field, Object target, Object val) {
        try {
            boolean isAccessible = field.canAccess(this);

            try {
                field.setAccessible(true);
                field.set(target, val);
            } finally {
                field.setAccessible(isAccessible);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static @Nullable Method getMethod(Class<?> type, String name, Class<?>... paramTypes) {
        Method method = null;

        try {
            method = type.getMethod(name, paramTypes);
        } catch (NoSuchMethodException ignore) { }

        return method;
    }

    private static Set<Field> findEntryFields(Class<?> clazz) {
        Set<Field> set = new HashSet<>();
        Class<?> c = clazz;
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(ConfigEntry.class)) {
                    set.add(field);
                }
            }
            c = c.getSuperclass();
        }
        return set;
    }

}
