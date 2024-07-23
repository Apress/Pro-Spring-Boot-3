package com.apress.myretro.security;


import com.apress.myretro.client.User;
import com.apress.myretro.client.UserClient;
import com.apress.myretro.client.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class RetroBoardSecurityConfig {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                  ReactiveAuthenticationManager reactiveAuthenticationManager,
                                                  CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeExchange( auth -> auth

                        .pathMatchers(HttpMethod.POST,"/retros/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE,"/retros/**").hasRole("ADMIN")
                        .pathMatchers("/retros/**").hasAnyRole("USER","ADMIN")
                        .pathMatchers("/","/webjars/**").permitAll()
                )
                .authenticationManager(reactiveAuthenticationManager)
                .formLogin(formLoginSpec -> formLoginSpec.authenticationSuccessHandler(serverAuthenticationSuccessHandler()))
                .httpBasic(Customizer.withDefaults());


        return http.build();
    }

    @Bean
    ServerAuthenticationSuccessHandler serverAuthenticationSuccessHandler(){
        return (webFilterExchange, authentication) -> webFilterExchange.getExchange().getSession()
                .flatMap(session -> {

                    User user = (User) authentication.getDetails();

                    var body = """
                {
                    "email": "%s",
                    "name": "%s",
                    "password": "%s",
                    "userRole": "%s",
                    "gravatarUrl": "%s",
                    "active": %s
                }
                """.formatted(
                            user.email(),
                            user.name(),
                            user.password(),
                            authentication.getAuthorities().stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .map(role -> role.replace("ROLE_",""))
                                    .collect(Collectors.joining(",")),
                            user.gravatarUrl(),
                            true);
                    webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatusCode.valueOf(200));
                    ReactiveHttpOutputMessage response = webFilterExchange.getExchange().getResponse();
                    response.getHeaders().add("Content-Type","application/json");
                    response.getHeaders().add("X-MYRETRO","SESSION="+session.getId()+"; Path=/; HttpOnly; SameSite=Lax");
                    DataBuffer dataBufferPublisher = response.bufferFactory().wrap(body.getBytes());
                    return response.writeAndFlushWith( Flux.just(dataBufferPublisher).windowUntilChanged());
                });
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        //configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("x-ijt","Set-Cookie","Cookie","Content-Type","X-MYRETRO","Allow","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials","Access-Control-Allow-Headers","Access-Control-Allow-Methods","Access-Control-Expose-Headers","Access-Control-Max-Age","Access-Control-Request-Headers","Access-Control-Request-Method","Origin","X-Requested-With","Accept","Accept-Encoding","Accept-Language","Host","Referer","Connection","User-Agent"));
        configuration.setExposedHeaders(Arrays.asList("x-ijt","Set-Cookie","Cookie","Content-Type","X-MYRETRO","Allow","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials","Access-Control-Allow-Headers","Access-Control-Allow-Methods","Access-Control-Expose-Headers","Access-Control-Max-Age","Access-Control-Request-Headers","Access-Control-Request-Method","Origin","X-Requested-With","Accept","Accept-Encoding","Accept-Language","Host","Referer","Connection","User-Agent"));
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(UserClient userClient){
        return authentication -> {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();

            Mono<User> userResult = userClient.getUserInfo(username);

            return userResult.flatMap( user -> {
                if(user.password().equals(password)){
                    List<GrantedAuthority> grantedAuthorities = user.userRole().stream()
                            .map(UserRole::name)
                            .map("ROLE_"::concat)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password,grantedAuthorities);
                    authenticationToken.setDetails(user);
                    return Mono.just(authenticationToken);
                }else{
                    return Mono.error(new BadCredentialsException("Invalid username or password"));
                }});

        };

    }

}
