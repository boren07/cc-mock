package com.borened.mock.model;

import com.borened.mock.CcMock;
import com.borened.mock.config.MockConfig;
import com.borened.mock.enums.StringType;
import lombok.Data;

import java.util.List;

/**
 * @author cch
 * @since 2023-05-14
 */
@Data
public class User {

    private String username;
    private String password;
    private String nickName;

    private List<String> roles;

    public static void main(String[] args) {
        MockConfig mockConfig = new MockConfig();
        MockConfig.String string = new MockConfig.String();
        string.setLength(5);
        string.setStringType(StringType.NUMBER_CHAR_MIX);
        mockConfig.setString(string);
        System.out.println(CcMock.mock(mockConfig,User.class));
    }
}
