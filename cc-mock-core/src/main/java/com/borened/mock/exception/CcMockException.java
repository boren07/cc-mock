package com.borened.mock.exception;

/**
 * mock异常
 *
 * @author cch
 * @since 2023-05-12
 */
public class CcMockException extends RuntimeException {

    private static final long serialVersionUID = 6053227184363867815L;


    public CcMockException() {

    }

    public CcMockException(String message) {
        super(message);
    }

    /**
     * 使用jdk String.format, 使用%s占位替换参数。
     *
     * @param messageTemplate 字符串模板
     * @param params          替换参数
     */
    public CcMockException(String messageTemplate, Object... params) {
        this(String.format(messageTemplate, params));
    }

    public CcMockException(String message, Throwable cause) {
        super(message, cause);
    }

    public CcMockException(Throwable cause) {
        super(cause);
    }
}
