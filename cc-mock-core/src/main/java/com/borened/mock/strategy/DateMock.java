package com.borened.mock.strategy;

import com.borened.mock.config.IntRange;
import com.borened.mock.config.MockConfig;
import com.borened.mock.util.RandomUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author cch
 * @since 2023-04-24
 */
public class DateMock implements MockStrategy {

    @Override
    public List<Class<?>> supportTypes() {
        return Arrays.asList(Date.class, LocalDate.class, LocalTime.class, LocalDateTime.class);
    }

    @Override
    public <T> T mock(MockConfig mockConfig, Class<T> tClass) {
        IntRange yearRange = mockConfig.getDate().getYearRange();
        IntRange monthRange =mockConfig.getDate().getMonthRange();
        IntRange dayRange =mockConfig.getDate().getDayRange();
        IntRange hourRange =mockConfig.getDate().getHourRange();
        IntRange minRange =mockConfig.getDate().getMinRange();
        IntRange secRange =mockConfig.getDate().getSecRange();
        if (tClass.isAssignableFrom(Date.class)) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(RandomUtil.randomNumber(yearRange), RandomUtil.randomNumber(monthRange), RandomUtil.randomNumber(dayRange),
                    RandomUtil.randomNumber(hourRange), RandomUtil.randomNumber(minRange),RandomUtil.randomNumber(secRange));
            return (T)calendar.getTime();
        }else {
            LocalDateTime dateTime = LocalDateTime.of(RandomUtil.randomNumber(yearRange), RandomUtil.randomNumber(monthRange), RandomUtil.randomNumber(dayRange),
                    RandomUtil.randomNumber(hourRange), RandomUtil.randomNumber(minRange), RandomUtil.randomNumber(secRange));
            if (tClass.isAssignableFrom(LocalDate.class)) {
                return (T) dateTime.toLocalDate();
            }else if (tClass.isAssignableFrom(LocalTime.class)) {
                return (T) dateTime.toLocalTime();
            }else {
                return (T) dateTime;
            }
        }
    }
}
