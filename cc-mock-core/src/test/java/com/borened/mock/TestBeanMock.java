package com.borened.mock;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author cch
 * @since 2023-04-25
 */
public class TestBeanMock {

    @Test
    void get(){
        StopWatch stopWatch =new StopWatch();
        for (int i = 0; i < 100; i++) {
            stopWatch.start();
            Foo<String> foo = new Foo<>();
            System.out.println(CcMock.mock(foo.getClass()));
            stopWatch.stop();
        }
        System.out.println(stopWatch.prettyPrint());
    }
    @Data
    public static class Foo<T> {

        private String username;
        private String password;
        private String nickName;

        private Integer age;
        private short gender;
        private LocalDateTime createTime;
        private BigDecimal account;

        private T info;
        private List<String> list;
        private Map<String, BigDecimal> map;


    }
}