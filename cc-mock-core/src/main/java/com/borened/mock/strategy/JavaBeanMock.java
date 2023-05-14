package com.borened.mock.strategy;

import com.borened.mock.CcMock;
import com.borened.mock.annotation.MockIgnore;
import com.borened.mock.config.MockConfig;
import com.borened.mock.exception.CcMockException;
import com.borened.mock.util.ReflectUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 自定义javabean 的mock策略,支持嵌套泛型等复杂类型的Bean结构。
 *
 * @author cch
 * @since 2023-04-25
 */
@Slf4j
public class JavaBeanMock implements MockStrategy {
    @Override
    public List<Class<?>> supportTypes() {
        return Arrays.asList(Object.class);
    }

    @Override
    public <T> T mock(MockConfig mockConfig, Class<T> tClass) {
        T t;
        try {
            t = tClass.newInstance();
        } catch (Exception e) {
            throw new CcMockException("Mock Java Bean use newInstance() make a mistake! {}", e.getMessage());
        }
        Field[] declaredFields = tClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (Modifier.isFinal(field.getModifiers()) || field.isAnnotationPresent(MockIgnore.class)) {
                continue;
            }
            field.setAccessible(true);
            Type genericType = field.getGenericType();
            //根据字段的Type类来mock
            Object mock = mock(mockConfig, genericType);
            try {
                field.set(t, mock);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.error("Mock Java Bean set field `{}` error,mock continue! reason is {}", field.getName(), e.getMessage());
            }
            log.debug("field `{}` mock successful! mock value:{}", field.getName(), mock);
        }

        return t;
    }

    @Override
    public Object mock(MockConfig mockConfig, Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            Class<?> rawClass = (Class<?>) rawType;

            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            //Collection处理
            if (Collection.class.isAssignableFrom(rawClass)) {
                return createMockCollection(mockConfig, rawClass, actualTypeArguments[0]);
                //Map处理
            } else if (Map.class.isAssignableFrom(rawClass)) {
                return createMockMap(mockConfig, rawClass, actualTypeArguments[0], actualTypeArguments[1]);
            } else {
                Object instance;
                try {
                    instance = rawClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new CcMockException("Mock Java Bean use newInstance() make a mistake! {}", e.getMessage());
                }
                Field[] fields = rawClass.getDeclaredFields();
                for (Field field : fields) {
                    if (Modifier.isFinal(field.getModifiers()) || field.isAnnotationPresent(MockIgnore.class) ) {
                        continue;
                    }
                    field.setAccessible(true);
                    Object mockData = mock(mockConfig, ReflectUtils.convertActualType(parameterizedType, field.getGenericType()));
                    try {
                        field.set(instance, mockData);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        log.error("Mock Java Bean set field `{}` error,mock continue! reason is {}", field.getName(), e.getMessage());
                    }
                    log.debug("Field {} mock successful! mock value:{}", field.getName(), mockData);
                }

                return instance;
            }
        } else if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            return CcMock.mock(mockConfig,clazz);
        }
        throw new CcMockException("Unsupported mock type:%s", type.getTypeName());
    }


    /**
     * 根据Map的K,V类型来创建一个模拟的Map对象
     *
     * @param keyType   K实际类型参数
     * @param valueType V实际类型参数
     * @return
     */
    private Map<Object, Object> createMockMap(MockConfig mockConfig, Class<?> rawClass, Type keyType, Type valueType) {
        Map<Object, Object> map;
        if (rawClass.isInterface()) {
            if (rawClass == Map.class) {
                map = new HashMap<>();
            } else {
                throw new CcMockException("Unsupported Map type:%s", rawClass.getName());
            }
        } else {
            try {
                map = (Map<Object, Object>) rawClass.newInstance();
            } catch (Exception e) {
                throw new CcMockException("Reflect error! Unsupported map type:%s,error detail:%s", rawClass.getName(), e.getMessage());
            }
        }

        //这个10条可以根据配置调整
        for (int i = 0; i < mockConfig.getCollectionSize(); i++) {
            Object mockKey = mock(mockConfig, keyType);
            Object mockValue = mock(mockConfig, valueType);
            map.put(mockKey, mockValue);
        }
        return map;
    }

    /**
     * 根据集合元素类型创建一个模拟的List对象
     *
     * @param mockConfig
     * @param itemType
     * @return
     */
    private Object createMockCollection(MockConfig mockConfig, Class<?> rawClass, Type itemType) {
        Collection<Object> list;
        if (rawClass.isInterface()) {
            if (List.class.isAssignableFrom(rawClass)) {
                list = new ArrayList<>();
            } else if (Set.class.isAssignableFrom(rawClass)) {
                list = new LinkedHashSet<>();
            } else if (Queue.class.isAssignableFrom(rawClass)) {
                list = new ArrayDeque<>();
            } else if (Collection.class.isAssignableFrom(rawClass)) {
                list = new ArrayList<>();
            } else {
                throw new CcMockException("Unsupported Collection type:%s", rawClass.getName());
            }
        } else {
            try {
                list = (Collection<Object>) rawClass.newInstance();
            } catch (Exception e) {
                throw new CcMockException("Reflect error! Unsupported collection type:%s,error detail:%s", rawClass.getName(), e.getMessage());
            }
        }
        for (int i = 0; i < mockConfig.getCollectionSize(); i++) {
            Object item;
            //mock集合的元素
            item = mock(mockConfig, itemType);
            /*
            // 嵌套泛型
            if (itemType instanceof ParameterizedType) {
                ParameterizedType nestParameterizedType = (ParameterizedType) itemType;
                Type rawType = nestParameterizedType.getRawType();
                Class<?> nestRawClass = (Class<?>) rawType;
                Type[] itemTypes = nestParameterizedType.getActualTypeArguments();
                item = mock(mockConfig, nestRawClass,itemTypes[0]);
            }else {
                item = mock(mockConfig, itemType);
            }*/
            list.add(item);
        }
        return list;
    }

    /*
     *//**
     * 创建List对象，仅支持字段类型为List 或Set
     *
     * @param field
     * @return
     *//*

    private Object createMockList(Field field) {
        Collection<Object> list;
        Class<?> fieldType = field.getType();
        if (fieldType == List.class) {
            list = new ArrayList<>();
        } else if (fieldType == Set.class) {
            list = new HashSet<>();
        } else {
            log.debug("Collection类型字段【{}】-使用了不支持的类型{}，请使用List或Set类型！", field.getName(), fieldType.getName());
            throw new UnsupportedOperationException("不支持的JavaBean的字段类型:" + fieldType.getName());
        }
        // 获取字段上的List的泛型类型
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] args = parameterizedType.getActualTypeArguments();
            if (args.length > 0 && args[0] instanceof Class) {
                Class<?> argType = (Class<?>) args[0];
                //随机生成10条
                for (int i = 0; i < 10; i++) {
                    Object mock = CcMock.mock(argType);
                    list.add(mock);
                }
*//*              不支持List<Map<?,?>> 等嵌套复杂的对象直接mock
                if (argType == Map.class) {
                    // 创建Map对象
                    Map<String, Object> map = new HashMap<>();
                    // 添加Map元素
                    map.put("key", "value");
                    // 添加Map对象到List中
                    list.add(map);
                }*//*
            }
        }
        return list;
    }

    *//**
     * 创建Map对象
     *
     * @param field
     * @return
     *//*
    private static Map<Object, Object> createMockMap(Field field) {
        Map<Object, Object> map = new HashMap<>();
        // 获取Map的参数类型
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] args = parameterizedType.getActualTypeArguments();
            if (args.length > 1 && args[0] instanceof Class && args[1] instanceof Class) {
                Class<?> keyType = (Class<?>) args[0];
                Class<?> valueType = (Class<?>) args[1];
                for (int i = 0; i < 10; i++) {
                    Object mockKey = CcMock.mock(keyType);
                    Object mockValue = CcMock.mock(valueType);
                    map.put(mockKey, mockValue);
                }
            }
        } else {
            log.debug("Map类型字段【{}】-未指定泛型参数类型，不进行mock处理！", field.getName());
        }
        return map;
    }*/

}
