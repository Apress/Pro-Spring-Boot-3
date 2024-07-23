package com.apress.myretro.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@Configuration
public class RetroBoardSecurityConfig {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                  CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeExchange( auth -> auth
                        .pathMatchers(HttpMethod.POST,"/retros/**").hasAuthority("SCOPE_retros:write")
                        .pathMatchers(HttpMethod.DELETE,"/retros/**").hasAuthority("SCOPE_retros:write")
                        .pathMatchers("/retros/**").hasAnyAuthority("SCOPE_retros:read","SCOPE_retros:write")
                        .pathMatchers("/","/webjars/**").permitAll()
                )
                .oauth2Login(Customizer.withDefaults())
                .oauth2ResourceServer(config -> config.jwt(Customizer.withDefaults()));

        return http.build();
    }



    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("x-ijt","Set-Cookie","Cookie","Content-Type","X-MYRETRO","Allow","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials","Access-Control-Allow-Headers","Access-Control-Allow-Methods","Access-Control-Expose-Headers","Access-Control-Max-Age","Access-Control-Request-Headers","Access-Control-Request-Method","Origin","X-Requested-With","Accept","Accept-Encoding","Accept-Language","Host","Referer","Connection","User-Agent"));
        configuration.setExposedHeaders(Arrays.asList("x-ijt","Set-Cookie","Cookie","Content-Type","X-MYRETRO","Allow","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials","Access-Control-Allow-Headers","Access-Control-Allow-Methods","Access-Control-Expose-Headers","Access-Control-Max-Age","Access-Control-Request-Headers","Access-Control-Request-Method","Origin","X-Requested-With","Accept","Accept-Encoding","Accept-Language","Host","Referer","Connection","User-Agent"));
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
