package com.apress.myretro.config;


import com.apress.myretro.board.Card;
import com.apress.myretro.board.CardType;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.service.RetroBoardAndCardService;
import com.apress.myretro.web.socket.UserSocketMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@EnableConfigurationProperties(RetroBoardProperties.class)
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
