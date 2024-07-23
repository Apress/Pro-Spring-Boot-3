package com.apress.myretro.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@AllArgsConstructor
@Slf4j
public class RetroBoardMetricsInterceptor implements HandlerInterceptor {

    private MeterRegistry meterRegistry;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String URI = request.getRequestURI();
        String METHOD = request.getMethod();

        if (!URI.contains("prometheus")) {
            log.info("URI: " + URI + " METHOD: " + METHOD);
            meterRegistry.counter("retro_board_api", "URI", URI, "METHOD", METHOD).increment();
        }
    }
}
