package com.apress.myretro.config;


import com.apress.myretro.board.Card;
import com.apress.myretro.board.CardType;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.persistence.CardRepository;
import com.apress.myretro.persistence.RetroBoardRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@EnableConfigurationProperties({MyRetroProperties.class})
@Configuration
public class MyRetroConfiguration {


    @Bean
    ApplicationListener<ApplicationReadyEvent> ready(CardRepository cardRepository, RetroBoardRepository retroBoardRepository) {
        return applicationReadyEvent -> {
            RetroBoard retroBoard = retroBoardRepository.save(RetroBoard.builder()
                    .name("Spring Boot Conference")
                    .build());

            cardRepository.saveAll(new ArrayList<>() {{
                add(Card.builder().comment("Spring Boot Rocks!").cardType(CardType.HAPPY).retroBoard(retroBoard).build());
                add(Card.builder().comment("Meet everyone in person").cardType(CardType.HAPPY).retroBoard(retroBoard).build());
                add(Card.builder().comment("When is the next one?").cardType(CardType.MEH).retroBoard(retroBoard).build());
                add(Card.builder().comment("Not enough time to talk to everyone").cardType(CardType.SAD).retroBoard(retroBoard).build());
            }});
        };
    }
}
