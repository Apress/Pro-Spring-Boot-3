package com.apress.myretro.metrics;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// Uncomment this to see the effect of the @Observed annotation (Custom Observation))
@Slf4j
//@Component
public class RetroBoardObservationHandler implements ObservationHandler<Observation.Context> {


    @Override
    public void onStart(Observation.Context context) {
        log.info("Observation started with context: {}", context);
    }

    @Override
    public void onStop(Observation.Context context) {
        log.info("Observation stopped with context: {}", context);
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }
}
