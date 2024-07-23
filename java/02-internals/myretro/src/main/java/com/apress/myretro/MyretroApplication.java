package com.apress.myretro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MyretroApplication {

	public static void main(String[] args) {
        SpringApplication.run(MyretroApplication.class,args);
	}

}

/** Spring Boot Features
@Slf4j
@SpringBootApplication
public class MyretroApplication {

	public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(MyretroApplication.class)
                .logStartupInfo(true)
                .bannerMode(Banner.Mode.CONSOLE)
                .lazyInitialization(true)
                .web(WebApplicationType.NONE)
                .profiles("cloud")
                .listeners(event -> log.info("Event: {}",event.getClass().getCanonicalName()))
                .run(args);
	}

}
 */
