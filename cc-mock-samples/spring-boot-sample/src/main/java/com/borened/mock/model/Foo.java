package com.borened.mock.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class Foo<V,K,S> {

    private String username;
    private String password;
    private String nickName;

    private Integer age;
    private short gender;
    private LocalDateTime createTime;
    private BigDecimal account;

    private List<K> list;
    private Map<K, V> map;
    private S attrs;

    @Data
    public static class Attr<S>{
        private String name;
        private int gender;
        private S attrType;
    }

}