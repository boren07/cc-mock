package com.borened.mock.config;

import lombok.Data;

/**
 * 整数范围
 *
 * @author cch
 * @since 2023-04-24
 */
@Data
public class IntRange {

    private int min;

    private int max;

    public IntRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

}
