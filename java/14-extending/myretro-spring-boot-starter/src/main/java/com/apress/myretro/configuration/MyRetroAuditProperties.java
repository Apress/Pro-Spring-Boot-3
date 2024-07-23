package com.apress.myretro.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "myretro.audit")
public class MyRetroAuditProperties {
    /**
     * The prefix to use for all audit messages.
     */
    private String prefix = "[AUDIT] ";
    /**
     * The file to use for audit messages.
     */
    private String file = "myretro.events";
    /*
     * User logger instead of standard print out.
     */
    private Boolean useLogger = false;
}
