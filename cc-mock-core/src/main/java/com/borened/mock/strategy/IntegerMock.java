package com.borened.mock.strategy;

import com.borened.mock.config.IntRange;
import com.borened.mock.config.MockConfig;
import com.borened.mock.util.RandomUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author cch
 * @since 2023-04-24
 */
public class IntegerMock implements MockStrategy {

    @Override
    public List<Class<?>> supportTypes() {
        return Arrays.asList(byte.class,short.class,int.class, long.class,
                Byte.class,Short.class,Integer.class, Long.class);
    }

    @Override
    public <T> T mock(MockConfig mockConfig, Class<T> tClass) {
        IntRange range = mockConfig.getNumber().getRange();
        Long number = RandomUtil.randomLong(range);
        Object res = new Object();
        if (tClass==byte.class || tClass == Byte.class) {
            res = number.byteValue();
        }
        else if (tClass==short.class || tClass == Short.class) {
            res = number.shortValue();
        }
        else if (tClass==int.class || tClass == Integer.class) {
            res = number.intValue();
        }
        else {
            res = number;
        }
        //直接强转
        return (T) res;
    }

    public static void main(String[] args) {
        System.out.println(Byte.class.isAssignableFrom(byte.class));
        System.out.println(byte.class.isAssignableFrom(Byte.class));
        System.out.println(Byte.class.isAssignableFrom(Byte.class));
    }
}
