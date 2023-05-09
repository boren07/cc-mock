package com.borened.mock.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class Foo {

    private String username;
    private String password;
    private String nickName;

    private Integer age;
    private short gender;
    private LocalDateTime createTime;
    private BigDecimal account;

    private List<String> list;
    private Map<String, BigDecimal> map;
    private List<Attr> attrs;

    @Data
    public static class Attr{
        private String name;
        private int gender;
        private short age;
    }

}