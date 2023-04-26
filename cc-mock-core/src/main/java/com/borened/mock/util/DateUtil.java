package com.borened.mock.util;

import java.time.LocalDateTime;

/**
 * 日期工具
 *
 * @author cch
 * @since 2023-04-25
 */
public class DateUtil {

    public static int thisYear(){
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        return year;
    }

}
