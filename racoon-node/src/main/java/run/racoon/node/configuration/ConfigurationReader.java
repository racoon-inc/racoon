package run.racoon.node.configuration;

import run.racoon.node.configuration.exceptions.ConfigValidationException;
import run.racoon.node.configuration.exceptions.ParseException;
import run.racoon.node.configuration.sources.ConfigurationSource;
import run.racoon.node.configuration.validation.ConfigValidator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Optional.ofNullable;

public class ConfigurationReader {
    private final List<ConfigurationSource> sources;
    private final ConfigValidator validator;

    public ConfigurationReader(List<ConfigurationSource> sources, ConfigValidator validator) {
        this.sources = sources;
        this.validator = validator;
    }

    public <T> T readConfiguration(Class<T> configurationClass) throws ConfigValidationException {
        T instance = getInstanceOrThrow(configurationClass);

        Field[] fields = configurationClass.getDeclaredFields();

        for (Field field : fields) {
            Config configDescription = field.getAnnotation(Config.class);
            String propertyName = ofNullable(configDescription.name()).orElse(field.getName());
            sources.stream()
                    .map(src -> src.getProperty(propertyName))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .map(val -> convert(val, field.getType()))
                    .map(val -> {
                        setFieldValue(instance, field, val);
                        return null;
                    });
        }

        this.validator.validate(instance);
        return instance;
    }

    private Object mapToObject(Class<?> typeClass, Map<String, Object> map) {
        var instance = getInstanceOrThrow(typeClass);
        for (Field field : typeClass.getDeclaredFields()) {
            Config configDescription = field.getAnnotation(Config.class);
            String propertyName = ofNullable(configDescription).map(Config::name).orElse(field.getName());
            var val = convert(map.get(propertyName), field.getType());
            setFieldValue(instance, field, val);
        }

        return instance;
    }

    private <T> T getInstanceOrThrow(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Empty constructor not found ", e);
        }
    }

    private void setFieldValue(Object instance, Field field, Object val) {
        try {
            field.setAccessible(true);
            field.set(instance, val);
        } catch (IllegalAccessException e) {
            throw new ParseException("Failed setting property " + field.getName() + ", check access", e);
        }
    }

    private Object convert(Object value, Class<?> typeClass) {
        if (typeClass.isAssignableFrom(value.getClass())) {
            return value;
        }

        String strValue = value.toString();
        if (typeClass.isAssignableFrom(Boolean.class)) {
            return Boolean.valueOf(strValue);
        } else if (typeClass == Long.class) {
            return Long.valueOf(strValue);
        } else if (typeClass == Integer.class) {
            return Integer.valueOf(strValue);
        } else if (value instanceof Map) {
            return mapToObject(typeClass, (Map<String, Object>) value);
        }

        throw new ParseException("Unsupported type " + typeClass);
    }
}
