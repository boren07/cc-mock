package com.borened.mock;

import com.alibaba.fastjson2.JSON;
import com.borened.mock.util.ParameterizedTypeImpl;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author cch
 * @since 2023-04-25
 */
public class TestBeanMock {

    @Test
    void get() throws Exception {
        StopWatch stopWatch =new StopWatch();
        for (int i = 0; i < 100; i++) {
            stopWatch.start();
            Object mock = CcMock.mock(Foo.class);
            System.out.println(mock);
            stopWatch.stop();
        }
        System.out.println(stopWatch.prettyPrint());
    }

    /**
     * 测试嵌套泛型
     * @throws Exception
     */
    @Test
    void get1() throws Exception {
        //Foo<List<Attr1<String>>, Attr2<Integer>> foo = new Foo<>();
        Type type = new ParameterizedTypeImpl(Foo.class, new ParameterizedTypeImpl(List.class, new ParameterizedTypeImpl(Attr1.class,String.class)),new ParameterizedTypeImpl(Attr2.class,Integer.class));
        for (int i = 0; i < 100; i++) {
            Object instance = CcMock.mock(type);
            System.out.println(JSON.toJSONString(instance));
        }

    }

    @Data
    public static class Foo<T,K> {

        private String username;
        private String password;
        private String nickName;

        private Integer age;
        private short gender;
        private LocalDateTime createTime;
        private Date birthday;
        private BigDecimal account;

        private T info;
        private Set<K> list;
        private HashMap<String, BigDecimal> map;

        private Map<K, T> map2;

    }

    @Data
    public static class Attr1<S>{
        private Integer age1;
        private S name1;
    }

    @Data
    public static class Attr2<A>{
        private Integer age2;
        private A name2;
    }

}
