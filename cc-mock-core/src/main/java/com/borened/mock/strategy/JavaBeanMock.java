package com.borened.mock.strategy;

import com.borened.mock.CcMock;
import com.borened.mock.config.MockConfig;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 自定义javabean 的mock策略
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
        T t = null;
        try {
            t = tClass.newInstance();
        }catch(Exception e){
                log.error("Mock Java Bean make a mistake!", e);
            }
        try {
            Field[] fields = tClass.getDeclaredFields();
            for (Field field : fields) {
                if(Modifier.isFinal(field.getModifiers())){
                    continue;
                }
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                //Collection字段处理
                if (Collection.class.isAssignableFrom(fieldType) ) {
                    Object mockList = createMockList(field);
                    field.set(t, mockList);
                //Map处理
                } else if (Map.class.isAssignableFrom(fieldType) ) {
                    Map<Object, Object> map = createMockMap(field);
                    field.set(t, map);
                }else {//其他基础类型，使用内置mock策略自动适配
                    field.set(t, CcMock.mock(fieldType));
                }
                log.debug("字段{}mock成功！", field.getName());
            }
        } catch (Exception e) {
            log.error("Mock Java Bean make a mistake!", e);
        }
        return t;
    }
    /**
     * 创建Map对象
     * @param field
     * @return
     */
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
        }else {
            log.debug("Map类型字段【{}】-未指定泛型参数类型，不进行mock处理！",field.getName());
        }
        return map;
    }

    /**
     * 创建List对象，仅支持字段类型为List 或Set
     * @param field
     * @return
     */

    private Object createMockList(Field field){
        Collection<Object> list;
        Class<?> fieldType = field.getType();
        if (fieldType == List.class) {
            list = new ArrayList<>();
        } else if (fieldType == Set.class) {
            list = new HashSet<>();
        }  else {
            log.debug("Collection类型字段【{}】-使用了不支持的类型{}，请使用List或Set类型！",field.getName(),fieldType.getName());
            throw new UnsupportedOperationException("不支持的JavaBean的字段类型:"+fieldType.getName());
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
/*              不支持List<Map<?,?>> 等嵌套复杂的对象直接mock
                if (argType == Map.class) {
                    // 创建Map对象
                    Map<String, Object> map = new HashMap<>();
                    // 添加Map元素
                    map.put("key", "value");
                    // 添加Map对象到List中
                    list.add(map);
                }*/
            }
        }
        return list;
    }

}
