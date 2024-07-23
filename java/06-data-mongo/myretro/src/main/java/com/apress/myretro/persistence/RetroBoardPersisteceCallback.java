package com.apress.myretro.persistence;

import com.apress.myretro.board.RetroBoard;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;


@Component
public class RetroBoardPersisteceCallback implements BeforeConvertCallback<RetroBoard> {

        @Override
        public RetroBoard onBeforeConvert(RetroBoard retroBoard, String s) {
            if (retroBoard.getId() == null)
                retroBoard.setId(java.util.UUID.randomUUID());
            return retroBoard;
        }
}
