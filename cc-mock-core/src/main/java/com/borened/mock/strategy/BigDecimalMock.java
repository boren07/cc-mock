package com.borened.mock.strategy;

import com.borened.mock.config.MockConfig;
import com.borened.mock.util.RandomUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author cch
 * @since 2023-04-24
 */
public class BigDecimalMock implements MockStrategy {

    @Override
    public List<Class<?>> supportTypes() {
       return Arrays.asList(double.class, float.class,
               Double.class, Float.class,BigDecimal.class);
    }

    @Override
    public <T> T mock(MockConfig mockConfig, Class<T> tClass) {
        MockConfig.Number number = mockConfig.getNumber();
        BigDecimal decimal = RandomUtil.randomDecimal(number.getRange(),number.getDecimalPrecision());
        if (tClass.isAssignableFrom(double.class) || tClass.isAssignableFrom(Double.class)) {
            return (T) Double.valueOf(decimal.doubleValue());
        }
        if (tClass.isAssignableFrom(float.class) || tClass.isAssignableFrom(Float.class)) {
            return (T) Float.valueOf(decimal.floatValue());
        }
        else  {
            return (T) decimal;
        }
    }


}
