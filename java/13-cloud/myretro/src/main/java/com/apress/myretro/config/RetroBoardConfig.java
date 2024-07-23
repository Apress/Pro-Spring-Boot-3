package com.apress.myretro.config;


import com.apress.myretro.board.Card;
import com.apress.myretro.board.CardType;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.service.RetroBoardAndCardService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@EnableFeignClients(basePackages = "com.apress.myretro.client")
@Configuration
public class RetroBoardConfig {

    @Bean
    CommandLineRunner init(RetroBoardAndCardService service){
        return args -> {
            RetroBoard retroBoard = new RetroBoard();

            Card happyCard = new Card();
            happyCard.setComment("Nice to meet everybody");
            happyCard.setCardType(CardType.HAPPY);

            Card mehCard = new Card();
            mehCard.setComment("When are we going to travel?");
            mehCard.setCardType(CardType.MEH);

            Card sadCard = new Card();
            sadCard.setComment("When are we going to travel?");
            sadCard.setCardType(CardType.SAD);

            retroBoard.setName("Spring Boot 3 Retro");

            retroBoard.setCards(Arrays.asList(
                happyCard, mehCard, sadCard
            ));

            RetroBoard result = service.saveOrUpdateRetroBoard(retroBoard);


            //result.getCards().forEach(System.out::println);
            //service.deleteRetroBoardById(result.getRetroBoardId());

        };
    }

}
