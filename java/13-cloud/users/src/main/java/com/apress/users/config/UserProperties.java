package com.apress.users.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
//@RefreshScope
@ConfigurationProperties(prefix = "user")
public class UserProperties {

    private String reportFormat;
    private String emailSubject;
    private String emailFrom;
    private String emailTemplate;

}


