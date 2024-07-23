package com.apress.myretro;

import com.apress.myretro.board.Card;
import com.apress.myretro.service.RetroBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MyretroApplicationTests {

    final UUID uuid = UUID.fromString("66D4A370-C312-4426-9C39-B411D0E43DAB");

    @Autowired
    RetroBoardService service;

	@Test
	void getHappyCardTest() {
        Iterable<Card> happyCards = this.service.findAllHappyCardsFromRetroBoardId(uuid);
        assertThat(happyCards).isNotEmpty();
	}

}
