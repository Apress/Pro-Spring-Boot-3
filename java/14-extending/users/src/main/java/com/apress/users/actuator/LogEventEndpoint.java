package com.apress.users.actuator;


import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id="event-config")
public class LogEventEndpoint {

    private LogEventConfig config = new LogEventConfig();

    @ReadOperation
    public LogEventConfig config() {
        return config;
    }

    @WriteOperation
    public void eventConfig(@Nullable Boolean enabled,@Nullable String prefix, @Nullable String postfix) {
        if (enabled != null)
            this.config.setEnabled(enabled);
        if (prefix != null)
            this.config.setPrefix(prefix);
        if (postfix != null)
            this.config.setPostfix(postfix);
    }


    public Boolean isEnable() {
        return config.getEnabled();
    }

}
