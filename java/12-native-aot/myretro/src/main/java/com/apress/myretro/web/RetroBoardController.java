package com.apress.myretro.web;

import com.apress.myretro.board.Card;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.service.RetroBoardAndCardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
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

    @GetMapping("/{uuid}")
    public ResponseEntity<RetroBoard> findRetroBoardById(@PathVariable UUID uuid){
        return ResponseEntity.ok(this.retroBoardAndCardService.findRetroBoardById(uuid));
    }

    @RequestMapping(method = { RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity<RetroBoard>saveRetroBoard(@RequestBody RetroBoard retroBoard, UriComponentsBuilder componentsBuilder){
        RetroBoard saveOrUpdateRetroBoard = this.retroBoardAndCardService.saveOrUpdateRetroBoard(retroBoard);
        URI uri = componentsBuilder.path("/{uuid}").buildAndExpand(saveOrUpdateRetroBoard.getRetroBoardId()).toUri();
        return ResponseEntity.created(uri).body(saveOrUpdateRetroBoard);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteRetroBoardById(@PathVariable UUID uuid){
        this.retroBoardAndCardService.deleteRetroBoardById(uuid);
        return ResponseEntity.noContent().build();
    }


    // External Call
    @GetMapping("/users")
    public ResponseEntity<Map<String,Object>> getAllUsers(){
        this.retroBoardAndCardService.getAllUsers();
        return ResponseEntity.ok(Map.of
                ("message","Users will be printed out in the console",
                        "status","success","timestamp", LocalDateTime.now()));
    }

}
