package dev.jacob6707.carrentalsystemjavafx.util.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * A generic utility class for mapping database rows (represented as maps) to Java objects (including records)
 * through reflection. This class supports automatic detection and injection of constructor arguments or setter
 * methods based on field mappings.
 *
 * @param <T> The type of the object that this mapper works with.
 */
public class ObjectMapper<T> {
    private static final Logger log = LoggerFactory.getLogger(ObjectMapper.class);
    private final Class<T> clazz;
    private final Map<String, Method> setters = new HashMap<>();
    private final Map<Field, ObjectMapper<?>> nestedMappers = new HashMap<>();

    /**
     * Constructs an instance of {@link ObjectMapper}.
     * @param clazz Class to map
     */
    public ObjectMapper(Class<T> clazz) {
        this.clazz = clazz;
        initializeMappings(clazz);
    }

    private void initializeMappings(Class<T> startClass) {
        Map<String, Field> fieldMap = new HashMap<>();
        Class<? super T> current = startClass;

        while (current != null && current != Object.class) {
            processFields(current, fieldMap);
            processMethods(current, fieldMap);
            current = current.getSuperclass();
        }
    }

    private void processFields(Class<? super T> current, Map<String, Field> fieldMap) {
        for (Field field : current.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) || field.isAnnotationPresent(DatabaseTransient.class)) continue;

            fieldMap.putIfAbsent(field.getName().toLowerCase(), field);

            if (field.getType().isRecord()) {
                nestedMappers.put(field, new ObjectMapper<>(field.getType()));
            }
        }
    }
    /**
     * Processes fields to extract setters from them
     *
     * @param current Current class being processed
     * @param fieldMap Temporary Map of fields
     */
    private void processMethods(Class<? super T> current, Map<String, Field> fieldMap) {
        for (Method method : current.getDeclaredMethods()) {
            if (method.getName().startsWith("set") && method.getParameterCount() == 1) {
                String propertyName = method.getName().substring(3).toLowerCase();
                Field matchingField = fieldMap.get(propertyName);

                if (matchingField != null) {
                    DatabaseColumn annotation = matchingField.getAnnotation(DatabaseColumn.class);
                    String dbColumnName = annotation != null ? annotation.value() : matchingField.getName();
                    setters.put(dbColumnName.toLowerCase(), method);
                }
            }
        }
    }

    /**
     * Maps a single database row to a record via reflection
     * @param row Database row
     * @return Mapped record
     * @throws SQLException when mapping fails due to missing setters
     */
    public T map(Map<String, Object> row) throws SQLException {
        Map<String, Object> lowercaseRow = new HashMap<>();
        for (Map.Entry<String, Object> entry : row.entrySet()) {
            lowercaseRow.put(entry.getKey().toLowerCase(), entry.getValue());
        }

        try {
            if (clazz.isRecord()) {
                return mapRecord(lowercaseRow);
            }

            T obj = clazz.getConstructor().newInstance();
            // Iterates row; invokes setter when the column is present
            for (Map.Entry<String, Object> entry : lowercaseRow.entrySet()) {
                Object value = entry.getValue();
                if (value == null) continue;

                Method setter = setters.get(entry.getKey().toLowerCase());
                if (setter != null) {
                    Object convertedValue = convertValue(entry.getValue(), setter.getParameterTypes()[0]);
                    setter.invoke(obj, convertedValue);
                }
            }

            // Map nested Records
            for (Map.Entry<Field, ObjectMapper<?>> entry : nestedMappers.entrySet()) {
                Field field = entry.getKey();
                Object nestedObj = entry.getValue().map(lowercaseRow);

                if (setters.containsKey(field.getName().toLowerCase())) {
                    setters.get(field.getName().toLowerCase()).invoke(obj, nestedObj);
                } else log.warn("No setter found for nested object {}", field.getName());
            }

            return obj;
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new SQLException("Failed to map database row to object via setters", e);
        }
    }

    /**
     * Maps a list of database rows to a list of records
     * @param rows List of database rows
     * @return List of mapped records
     * @throws SQLException when mapping fails due to missing setters
     */
    public List<T> map(List<Map<String, Object>> rows) throws SQLException {
        List<T> list = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            list.add(map(row));
        }

        return list;
    }

    /**
     * Maps database row to record via reflection
     * @param row Database row
     */
    private T mapRecord(Map<String, Object> row) throws SQLException {
        RecordComponent[] components = clazz.getRecordComponents();
        Object[] args = new Object[components.length];
        Class<?>[] argTypes = new Class<?>[components.length];

        for (int i = 0; i < components.length; i++) {
            RecordComponent comp = components[i];
            DatabaseColumn ann = comp.getAnnotation(DatabaseColumn.class);
            String colName = (ann != null) ? ann.value() : comp.getName();

            Object rawValue = row.get(colName.toLowerCase());
            args[i] = convertValue(rawValue, comp.getType());
            argTypes[i] = comp.getType();
        }

        try {
            return clazz.getConstructor(argTypes).newInstance(args);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new SQLException("Failed to map record", e);
        }
    }

    /**
     * Converts value to the target type if needed
     * @param value Value to convert
     * @param targetType Target type
     */
    private Object convertValue(Object value, Class<?> targetType) {
        if (value == null) return null;
        if (targetType.isAssignableFrom(value.getClass())) return value;
        if (targetType == BigDecimal.class) {
            if (value instanceof Number n) return BigDecimal.valueOf(n.doubleValue());
            return new BigDecimal(value.toString());
        }
        return switch (value) {
            case Number n when (targetType == Long.class || targetType == long.class) -> n.longValue();
            case Number n when (targetType == Double.class || targetType == double.class) -> n.doubleValue();
            case Timestamp ts when (targetType == LocalDateTime.class) -> ts.toLocalDateTime();
            case Date date when (targetType == LocalDate.class) -> date.toLocalDate();
            default -> value;
        };
    }
}
