package com.apress.myretro.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
@Data
public class RetroBoardEvent {

    private UUID retroBoardId;

    private RetroBoardEventAction action;

    private LocalDateTime happenAt;

}
