package com.borened.mock.util;

import com.borened.mock.config.IntRange;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机工具
 *
 * @author cch
 * @since 2023-04-24
 */
public class RandomUtil {

    /**
     * 用于随机选的数字
     */
    public static final String BASE_NUMBER = "0123456789";
    /**
     * 用于随机选的字符
     */
    public static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
    /**
     * 用于随机选的字符和数字
     */
    public static final String BASE_CHAR_NUMBER = BASE_CHAR + BASE_NUMBER;
    /**
     * 按范围返回一个随机数字
     * @param limit 生成范围
     * @return
     */
    public static int randomNumber(int limit){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int i =random.nextInt(limit);
        return i;
    }
    /**
     * 按范围返回一个随机数字
     * @param range 生成范围
     * @return
     */
    public static int randomNumber(IntRange range){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int i =random.nextInt(range.getMin(), range.getMax());
        return i;
    }

    /**
     * 按范围返回一个随机数字
     * @param range 生成范围
     * @return
     */
    public static long randomLong(IntRange range){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long i =random.nextLong(range.getMin(), range.getMax());
        return i;
    }
    /**
     * 返回指定范围的小数
     * @param range 生成范围
     * @return
     */
    public static BigDecimal randomDecimal(IntRange range){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double nextDouble = random.nextDouble(range.getMin(), range.getMax());
        return BigDecimal.valueOf(nextDouble);
    }

    /**
     * 返回指定范围的小数
     * @param range 生成范围
     * @param decimalPrecision 小数精度，保留几位
     * @return
     */
    public static BigDecimal randomDecimal(IntRange range, int decimalPrecision){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double nextDouble = random.nextDouble(range.getMin(), range.getMax());
        if(decimalPrecision >0 ) {
            String format = String.format("%."+decimalPrecision+"f", nextDouble);
            return new BigDecimal(format);
        }
        return BigDecimal.valueOf(nextDouble);
    }

    /**
     * 按范围返回一个随机数字的字符串
     * @param range 生成范围
     * @return
     */
    public static String randomNumberStr(IntRange range){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int i =random.nextInt(range.getMin(), range.getMax());
        return String.valueOf(randomNumber(range));
    }
    /**
     * 返回一个指定长度的随机字符串,包含数字和字符
     * @param length 生成字符串长度
     * @return
     */
    public static String randomStr(int length){
        if (length==0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int charIndex = randomNumber(BASE_CHAR_NUMBER.length());
            char c = BASE_CHAR_NUMBER.charAt(charIndex);
            builder.append(c);
        }
        return builder.toString();
    }
    /**
     * 返回一个指定长度的随机字符串,从指定的字符串中随机返回字符组装而成。
     * @param metaString 元字符
     * @param length 生成字符串长度
     * @return
     */
    public static String randomStr(String metaString,int length){
        if (length==0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int charIndex = randomNumber(metaString.length());
            char c = metaString.charAt(charIndex);
            builder.append(c);
        }
        return builder.toString();
    }
    /**
     * 获取一个随机的汉字
     * @return
     */
    public static String getRandomChinese() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        // 生成高位编码，范围为176~215
        int highCode = (176 + Math.abs(random.nextInt(39)));
        // 生成低位编码，范围为161~254
        int lowCode = (161 + Math.abs(random.nextInt(93)));
        byte[] bytes = new byte[2];
        bytes[0] = (byte) highCode;
        bytes[1] = (byte) lowCode;
        try {
            return new String(bytes, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取一个指定长度随机的汉字字符串
     * @return
     */
    public static String getRandomChinese(int length) {
        if (length==0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String chinese = getRandomChinese();
            builder.append(chinese);
        }
        return builder.toString();
    }
}
