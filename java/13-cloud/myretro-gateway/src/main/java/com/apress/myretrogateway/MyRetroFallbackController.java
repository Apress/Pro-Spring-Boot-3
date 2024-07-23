package com.apress.myretrogateway;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class MyRetroFallbackController {

    @GetMapping("/users")
    public ResponseEntity userFallback() {
        return ResponseEntity.ok(Map.of(
                "status","Service Down",
                "message","/users endpoint is not available a this moment",
                "time",LocalDateTime.now(),
                "data", Map.of(
                        "email","dummy@email.com",
                        "name","Dummy",
                        "password","dummy",
                        "active",false)
                ));
    }

    @GetMapping("/retros")
    public ResponseEntity retroFallback() {
        return ResponseEntity.ok(Map.of("status","Service Down","message","/retros endpoint is not available a this moment","time",LocalDateTime.now()));
    }
}
