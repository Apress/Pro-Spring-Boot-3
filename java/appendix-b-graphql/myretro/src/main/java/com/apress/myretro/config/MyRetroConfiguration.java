package com.apress.myretro.config;


import com.apress.myretro.board.Card;
import com.apress.myretro.board.CardType;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.service.RetroBoardService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.UUID;

@EnableConfigurationProperties({MyRetroProperties.class})
@Configuration
public class MyRetroConfiguration {

    @Bean
    ApplicationListener<ApplicationReadyEvent> ready(RetroBoardService retroBoardService) {
        return applicationReadyEvent -> {
            UUID retroBoardId = UUID.fromString("9dc9b71b-a07e-418b-b972-40225449aff2");
            RetroBoard retroBoard = retroBoardService.save(RetroBoard.builder()
                    .id(retroBoardId)
                    .name("Spring Boot Conference")
                    .build());


           retroBoardService.addMultipleCardsToRetroBoard(retroBoardId,new ArrayList<>() {{
                add(Card.builder().id(UUID.fromString("bb2a80a5-a0f5-4180-a6dc-80c84bc014c9")).comment("Spring Boot Rocks!").cardType(CardType.HAPPY).build());
                add(Card.builder().comment("Meet everyone in person").cardType(CardType.HAPPY).build());
                add(Card.builder().comment("When is the next one?").cardType(CardType.MEH).build());
                add(Card.builder().comment("Not enough time to talk to everyone").cardType(CardType.SAD).build());
            }});
        };
    }
}
