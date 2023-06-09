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
                Byte.class,Short.class,Integer.class, Long.class,boolean.class,Boolean.class);
    }

    @Override
    public <T> T mock(MockConfig mockConfig, Class<T> tClass) {
        if (tClass==boolean.class || tClass == Boolean.class) {
            int bool = RandomUtil.randomNumber(new IntRange(0, 2));
            return (T) Boolean.valueOf(bool==1);
        }
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



}
