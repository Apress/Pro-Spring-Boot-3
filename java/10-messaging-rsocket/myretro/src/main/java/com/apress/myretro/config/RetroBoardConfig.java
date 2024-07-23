package com.apress.myretro.config;


import com.apress.myretro.board.Card;
import com.apress.myretro.board.CardType;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.service.RetroBoardAndCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@EnableConfigurationProperties(RetroBoardProperties.class)
@Configuration
public class RetroBoardConfig {


    @Bean
    CommandLineRunner init(RetroBoardAndCardService service){
        return args -> {
            RetroBoard retroBoard = new RetroBoard();
            retroBoard.setRetroBoardId(UUID.randomUUID());

            Card happyCard = new Card();
            happyCard.setCardId(UUID.randomUUID());
            happyCard.setComment("Nice to meet everybody");
            happyCard.setCardType(CardType.HAPPY);

            Card mehCard = new Card();
            mehCard.setCardId(UUID.randomUUID());
            mehCard.setComment("When are we going to travel?");
            mehCard.setCardType(CardType.MEH);

            Card sadCard = new Card();
            sadCard.setCardId(UUID.randomUUID());
            sadCard.setComment("When are we going to travel?");
            sadCard.setCardType(CardType.SAD);

            retroBoard.setName("Spring Boot 3 Retro");

            retroBoard.setCards(Arrays.asList(
                happyCard, mehCard, sadCard
            ));

            RetroBoard result = service.saveRetroBoard(retroBoard).block(Duration.ofSeconds(5));


            //result.getCards().forEach(System.out::println);
            //service.deleteRetroBoardById(result.getRetroBoardId());

        };
    }

}
