package com.apress.myretro.metrics;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

@Configuration
public class RetroBoardMetrics {

    @Bean
    ObservedAspect observedAspect(ObservationRegistry registry) {
        return new ObservedAspect(registry);
    }

    @Bean
    public Counter retroBoardCounter(MeterRegistry registry){
        return  Counter.builder("retro_boards").description("Number of Retro Boards").register(registry);
    }

    @Bean
    public MappedInterceptor metricsInterceptor(MeterRegistry registry){
        return new MappedInterceptor(new String[]{"/**"},new RetroBoardMetricsInterceptor(registry));
    }
}
