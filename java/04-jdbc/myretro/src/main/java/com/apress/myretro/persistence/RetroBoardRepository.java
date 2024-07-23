package com.apress.myretro.persistence;

import com.apress.myretro.board.Card;
import com.apress.myretro.board.RetroBoard;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.*;

@AllArgsConstructor
@Repository
public class RetroBoardRepository implements SimpleRepository<RetroBoard, UUID> {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<RetroBoard> findById(UUID uuid) {
        String sql = """
                SELECT r.ID AS id, r.NAME, c.ID AS card_id, c.CARD_TYPE AS card_type, c.COMMENT AS comment
                FROM RETRO_BOARD r
                LEFT JOIN CARD c ON r.ID = c.RETRO_BOARD_ID
                WHERE r.ID = ?
                """;
        List<RetroBoard> results = jdbcTemplate.query(sql, new Object[]{uuid}, new int[]{Types.OTHER}, new RetroBoardRowMapper());
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Iterable<RetroBoard> findAll() {
        String sql = """
                SELECT r.ID AS id, r.NAME, c.ID AS card_id, c.CARD_TYPE, c.COMMENT
                FROM RETRO_BOARD r
                LEFT JOIN CARD c ON r.ID = c.RETRO_BOARD_ID
                """;
        return jdbcTemplate.query(sql, new RetroBoardRowMapper());
    }

    @Override
    @Transactional
    public RetroBoard save(RetroBoard retroBoard) {

        if (retroBoard.getId() == null) {
            retroBoard.setId(UUID.randomUUID());
        }
        String sql = "INSERT INTO RETRO_BOARD (ID, NAME) VALUES (?, ?)";
        jdbcTemplate.update(sql, retroBoard.getId(), retroBoard.getName());

        Map<UUID, Card> mutableMap = new HashMap<>(retroBoard.getCards());

        for (Card card : retroBoard.getCards().values()) {
            card.setRetroBoardId(retroBoard.getId());
            card = saveCard(card);
            mutableMap.put(card.getId(), card);
        }
        retroBoard.setCards(mutableMap);
        return retroBoard;
    }

    @Override
    @Transactional
    public void deleteById(UUID uuid) {
        String sql = "DELETE FROM CARD WHERE RETRO_BOARD_ID = ?";
        jdbcTemplate.update(sql, uuid);

        sql = "DELETE FROM RETRO_BOARD WHERE ID = ?";
        jdbcTemplate.update(sql, uuid);
    }

    private Card saveCard(Card card) {
        if (card.getId() == null) {
            card.setId(UUID.randomUUID());
        }

        String sql = "INSERT INTO CARD (ID, CARD_TYPE, COMMENT, RETRO_BOARD_ID) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, card.getId(), card.getCardType().name(), card.getComment(), card.getRetroBoardId());

        return card;
    }

}
