package my.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);
    private static final Gson GSON = new Gson();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String serialize(Object object) {
        try {
            return GSON.toJson(object);

        } catch (Exception exception) {
            throw new RuntimeException("Failed to serialize json", exception);
        }
    }

    public static <T> String serialize(Map<T, List<T>> multivaluedMap) {
        Map<T, Object> jsonMap = new HashMap<>();

        for (Map.Entry<T, List<T>> entry : multivaluedMap.entrySet()) {
            List<T> values = entry.getValue();

            boolean isEmpty = CollectionUtils.isNullOrEmpty(values);
            if (isEmpty) {
                continue;
            }

            boolean hasSingleValue = values.size() == 1;
            if (hasSingleValue) {
                jsonMap.put(entry.getKey(), values.get(0));
                continue;
            }

            jsonMap.put(entry.getKey(), values);
        }

        return serialize(jsonMap);
    }

    public static String serialize(Object object, String defaultJson) {
        try {
            return GSON.toJson(object);

        } catch (Exception exception) {
            LOG.error("Failed to deserialize json", exception);
            return defaultJson;
        }
    }

    public static <T> T deserialize(String json, Type type) {
        try {
            return GSON.fromJson(json, type);

        } catch (Exception exception) {
            throw new RuntimeException("Failed to deserialize json", exception);
        }
    }

    public static <T> T deserialize(String json, Type type, T defaultValue) {
        try {
            return GSON.fromJson(json, type);

        } catch (Exception exception) {
            LOG.error("Failed to deserialize json", exception);
            return defaultValue;
        }
    }

    public static <T> T deserialize(String json, Class<T> targetClass) {
        try {
            return GSON.fromJson(json, targetClass);

        } catch (Exception exception) {
            throw new RuntimeException("Failed to deserialize json", exception);
        }
    }

    public static <T> T deserialize(String json, Class<T> targetClass, T defaultValue) {
        try {
            return GSON.fromJson(json, targetClass);

        } catch (Exception exception) {
            LOG.error("Failed to deserialize json", exception);
            return defaultValue;
        }
    }

    public static <T> T deserialize(String json, Class<T> targetClass, boolean strict) {
        if (!strict) {
            return deserialize(json, targetClass);
        }

        try {
            return MAPPER.readValue(json, targetClass);

        } catch (Exception exception) {
            throw new RuntimeException("Failed to deserialize json", exception);
        }
    }

    public static <T> T deserialize(String json, Class<T> targetClass, T defaultValue, boolean strict) {
        if (!strict) {
            return deserialize(json, targetClass, defaultValue);
        }

        try {
            return MAPPER.readValue(json, targetClass);

        } catch (Exception exception) {
            LOG.error("Failed to deserialize json", exception);
            return defaultValue;
        }
    }

    public static <T> List<T> deserializeList(String json, Class<T> targetClass) {
        try {
            CollectionType type = MAPPER.getTypeFactory().constructCollectionType(List.class, targetClass);
            return MAPPER.readValue(json, type);

        } catch (Exception exception) {
            throw new RuntimeException("Failed to deserialize json", exception);
        }
    }

    public static <T> List<T> deserializeList(String json, Class<T> targetClass, List<T> defaultList) {
        try {
            CollectionType type = MAPPER.getTypeFactory().constructCollectionType(List.class, targetClass);
            return MAPPER.readValue(json, type);

        } catch (Exception exception) {
            LOG.error("Failed to deserialize json", exception);
            return defaultList;
        }
    }
}
