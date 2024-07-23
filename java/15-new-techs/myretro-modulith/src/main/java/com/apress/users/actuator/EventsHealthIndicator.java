package com.apress.users.actuator;


import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class EventsHealthIndicator implements HealthIndicator {

    private LogEventEndpoint logEventEndpoint;

    @Override
    public Health health() {
        if (check() )
            return Health.up().build();
        else
            return Health.status(new Status("EVENTS-DOWN","Events are turned off!")).build();
    }

    private boolean check(){
        return this.logEventEndpoint.isEnable();
    }
}
