package my.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectionUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CollectionUtils.class);

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(List<T> list, Class<T> targetClass) {
        try {
            T[] array = (T[]) Array.newInstance(targetClass, list.size());
            return list.toArray(array);
        } catch (Exception e) {
            String errorMessage = "Failed to convert the list into an array";
            LOG.error(errorMessage);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <TargetType, CurrentType> List<TargetType> cast(List<CurrentType> list, Class<TargetType> targetClass) {
        return list
            .stream()
            .map(e -> (TargetType) e)
            .collect(Collectors.toList());
    }

    public static <T> List<T> difference(Collection<T> list, Collection<T> otherList) {
        ArrayList<T> difference = new ArrayList<T>();
        for (T object : list) {
            boolean found = otherList.contains(object);
            if(!found) {
                difference.add(object);
            }
        }

        return difference;
    }

    public static <T> List<T> unique(List<T> list) {
        ArrayList<T> uniqueList = new ArrayList<T>();

        for(T object : list) {
            boolean existsInResult = uniqueList.contains(object);
            if(!existsInResult) {
                uniqueList.add(object);
            }
        }

        return uniqueList;
    }

    public static <T> T firstOfType(List<Object> list, Class<T> targetClass) {
        for (Object object : list) {
            boolean isTargetClass = object.getClass().equals(targetClass);
            if (isTargetClass) {
                return targetClass.cast(object);
            }
        }

        return null;
    }

    public static <T> List<T> ofType(List<Object> list, Class<T> targetClass) {
        List<T> result = new ArrayList<T>();

        for (Object object : list) {
            boolean isTargetClass = object.getClass().equals(targetClass);
            if (isTargetClass) {
                result.add(targetClass.cast(object));
            }
        }

        return result;
    }

    public static <T> String[] toStringArray(T[] array) {
        if (array == null) {
            return null;
        }

        String[] result = new String[array.length];
        for (int index = 0; index < array.length; index++) {
            result[index] = array[index].toString();
        }

        return result;
    }

    public static <T> T getSingle(List<T> collection) {
        boolean hasAnyElements = collection != null && !collection.isEmpty();
        if (!hasAnyElements) {
            throw new RuntimeException("Collection is null or empty");
        }

        boolean hasMultipleResults = collection.size() > 1;
        if (hasMultipleResults) {
            throw new RuntimeException("Collection contains multiple elements");
        }

        return collection.get(0);
    }

    public static <T> T getSingleOrDefault(List<T> collection, T defaultValue) {
        boolean hasAnyElements = collection != null && !collection.isEmpty();
        if (!hasAnyElements) {
            LOG.error("getSingle - Collection is null or empty");
            return defaultValue;
        }

        boolean hasMultipleResults = collection.size() > 1;
        if (hasMultipleResults) {
            LOG.error("getSingle - Collection contains multiple elements");
            return defaultValue;
        }

        return collection.get(0);
    }

    public static boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.isEmpty();
    }
}
