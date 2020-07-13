package com.sfl.scma.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "admin")
public class AdminProperties {

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;

}
