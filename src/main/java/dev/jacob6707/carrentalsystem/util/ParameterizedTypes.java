package dev.jacob6707.carrentalsystem.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * Utility class for creating parameterized types at runtime.
 * Useful for JSON deserialization with generic types.
 */
public class ParameterizedTypes {

    /**
     * Creates a ParameterizedType for List&lt;T&gt;
     *
     * @param elementType the type of elements in the list
     * @return a ParameterizedType representing List&lt;elementType&gt;
     */
    public static ParameterizedType listOf(Class<?> elementType) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{elementType};
            }

            @Override
            public Type getRawType() {
                return List.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

    /**
     * Creates a ParameterizedType for Set&lt;T&gt;
     *
     * @param elementType the type of elements in the set
     * @return a ParameterizedType representing Set&lt;elementType&gt;
     */
    public static ParameterizedType setOf(Class<?> elementType) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{elementType};
            }

            @Override
            public Type getRawType() {
                return Set.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

    /**
     * Creates a ParameterizedType for Map&lt;K, V&gt;
     *
     * @param keyType the type of keys in the map
     * @param valueType the type of values in the map
     * @return a ParameterizedType representing Map&lt;keyType, valueType&gt;
     */
    public static ParameterizedType mapOf(Class<?> keyType, Class<?> valueType) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{keyType, valueType};
            }

            @Override
            public Type getRawType() {
                return Map.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

    /**
     * Creates a ParameterizedType for any generic type with one type argument
     *
     * @param rawType the raw type (e.g., Optional.class)
     * @param typeArgument the type argument
     * @return a ParameterizedType representing rawType&lt;typeArgument&gt;
     */
    public static ParameterizedType of(Class<?> rawType, Type typeArgument) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{typeArgument};
            }

            @Override
            public Type getRawType() {
                return rawType;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
}