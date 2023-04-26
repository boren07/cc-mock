package com.borened.mock;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author cch
 * @since 2023-04-25
 */
public class TestBaseMock {

    @Test
    void get(){
        for (int i = 0; i < 10000; i++) {
            System.out.println(CcMock.mock(int.class));
            System.out.println(CcMock.mock(LocalDateTime.class));
            System.out.println(CcMock.mock(double.class));
        }
    }

}
