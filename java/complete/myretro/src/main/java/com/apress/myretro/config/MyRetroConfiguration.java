package com.apress.myretro.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRetroConfiguration {

    @Bean
    CommandLineRunner init(){
        return args -> {
            //System.out.println(GravatarImage.getGravatarUrlFromEmail("norma@email.com"));
            //System.out.println(GravatarImage.getGravatarUrlFromEmail("ximena@email.com"));
        };
    }
}
