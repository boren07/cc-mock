package com.borened.mock.config;

import com.borened.mock.enums.StringType;
import com.borened.mock.util.DateUtil;
import lombok.Data;

/**
 * mock配置
 *
 * @author cch
 * @since 2023-04-24
 */
@Data
public class MockConfig {

    private Number number = new Number();

    private Date date = new Date();

    private String string = new String();


    @Data
    public static class Number {

        private IntRange range=new IntRange(1,1000);

        /**
         * 小数的精度
         */
        private int decimalPrecision=2;
    }

    @Data
    public static class Date {

        /**
         * 年度范围
         */
        private IntRange yearRange = new IntRange(1970, DateUtil.thisYear());
        /**
         * 月份范围
         */
        private IntRange monthRange = new IntRange(1,12);
        /**
         * 日期范围
         */
        private IntRange dayRange = new IntRange(1,31);
        /**
         * 小时范围
         */
        private IntRange hourRange = new IntRange(0,23);
        /**
         * 分钟范围
         */
        private IntRange minRange = new IntRange(0,59);
        /**
         * 秒范围
         */
        private IntRange secRange = new IntRange(0,59);

    }
    @Data
    public static class String {

        /**
         * 字符串长度
         */
        private int length=10;

        /**
         * 字符串类型
         */
        private StringType stringType = StringType.CHINESE;

    }



}
