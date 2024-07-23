package com.apress.myretro.config;


import com.apress.myretro.persistence.RetroBoardRepository;
import com.apress.myretro.service.RetroBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@EnableConfigurationProperties({MyRetroProperties.class})
@Configuration
public class MyRetroConfiguration {
    @Bean
    RetroBoardService retroBoardService() {
        return new RetroBoardService(new RetroBoardRepository());
    }
}

/** Spring Boot Features
@Slf4j
@Configuration
public class MyRetroConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
            log.info("[CLR] Args: {}", Arrays.toString(args));
        };
    }

    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {
            log.info("[AR] Option Args: {}", args.getOptionNames());
            log.info("[AR] Option Arg Values: {}", args.getOptionValues("option"));
            log.info("[AR] Non Option: {}",args.getNonOptionArgs());
        };
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(){
        return event -> {
            log.info("[AL] Im ready to interact...");
        };
    }

    @Bean
    RetroBoardService retroBoardService() {
        return new RetroBoardService(new RetroBoardRepository());
    }
}
*/
