package com.apress.myretro.web;

import com.apress.myretro.board.Card;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.service.RetroBoardAndCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/retros")
public class RetroBoardController {

    private RetroBoardAndCardService retroBoardAndCardService;

    @GetMapping
    public ResponseEntity<Iterable<RetroBoard>> getAllRetroBoards(){
        return ResponseEntity.ok(retroBoardAndCardService.getAllRetroBoards());
    }

    @GetMapping("/{uuid}/cards")
    public ResponseEntity<Iterable<Card>> getAllCardsFromBoard(@PathVariable UUID uuid){
        return null;
    }
}
