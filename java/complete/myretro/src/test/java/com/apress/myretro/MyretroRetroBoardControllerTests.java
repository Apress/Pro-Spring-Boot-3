package com.apress.myretro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
public class MyretroRetroBoardControllerTests {

    @Test
    void retrosTest(@Autowired WebTestClient webClient) throws Exception {
         webClient
                .get().uri("/retros")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isOk()
                .expectBody()
                    .jsonPath("$[0].id").isNotEmpty()
                    .jsonPath("$[0].name").isEqualTo("Uno")
                    .jsonPath("$[1].cards").isEmpty();

    }
}
