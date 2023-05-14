package com.borened.mock.strategy;

import com.borened.mock.config.MockConfig;
import com.borened.mock.enums.StringType;
import com.borened.mock.util.RandomUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author cch
 * @since 2023-04-26
 */
public class StringMock implements MockStrategy {
    @Override
    public List<Class<?>> supportTypes() {
        return Arrays.asList(String.class,char.class, Character.class);
    }

    @Override
    public <T> T mock(MockConfig mockConfig, Class<T> tClass) {
        StringType stringType = mockConfig.getString().getStringType();
        int length = mockConfig.getString().getLength();
        if (tClass == char.class || tClass == Character.class) {
            length = 1;
        }
        String res = "";
        switch (stringType){
            case CHAR:
                res = RandomUtil.randomStr(RandomUtil.BASE_CHAR,length);
                break;
            case NUMBER:
                res = RandomUtil.randomStr(RandomUtil.BASE_NUMBER,length);
                break;
            case NUMBER_CHAR_MIX:
                res = RandomUtil.randomStr(length);
                break;
            case CHINESE:
                res = RandomUtil.getRandomChinese(length);
                break;
            default:
                break;
        }
        Object result =  res;
        if (tClass == char.class || tClass == Character.class) {
            result = res.charAt(0);
        }
        return (T) result;
    }





}
