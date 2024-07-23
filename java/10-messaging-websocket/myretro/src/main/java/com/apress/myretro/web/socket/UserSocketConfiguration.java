package com.apress.myretro.web.socket;


import com.apress.myretro.config.RetroBoardProperties;
import lombok.AllArgsConstructor;
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
import java.util.List;

@AllArgsConstructor
@Configuration
public class UserSocketConfiguration {

    RetroBoardProperties retroBoardProperties;

    @Bean
    public WebSocketStompClient webSocketStompClient(WebSocketClient webSocketClient, UserSocketMessageConverter userSocketMessageConverter,
                                                     StompSessionHandler userSocketClient) {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(webSocketClient);
        webSocketStompClient.setMessageConverter(userSocketMessageConverter);
        webSocketStompClient.connect(retroBoardProperties.getUsersService().getHostname() + retroBoardProperties.getUsersService().getBasePath(), userSocketClient);
        return webSocketStompClient;
    }

    @Bean
    public WebSocketClient webSocketClient() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());
        return new SockJsClient(transports);
    }
}
