package com.apress.myretro.web;

import com.apress.myretro.board.Card;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.service.RetroBoardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/retros")
public class RetroBoardController {

    private RetroBoardService retroBoardService;

    @GetMapping
    public ResponseEntity<Iterable<RetroBoard>> getAllRetroBoards(){
        return ResponseEntity.ok(retroBoardService.findAll());
    }

    @PostMapping
    public ResponseEntity<RetroBoard> saveRetroBoard(@Valid @RequestBody RetroBoard retroBoard){
        RetroBoard result = retroBoardService.save(retroBoard);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(result.getId().toString())
                .toUri();
        return ResponseEntity.created(location).body(result);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RetroBoard> findRetroBoardById(@PathVariable UUID uuid){
        return ResponseEntity.ok(retroBoardService.findById(uuid));
    }

    @GetMapping("/{uuid}/cards")
    public ResponseEntity<Iterable<Card>> getAllCardsFromBoard(@PathVariable UUID uuid){
        return ResponseEntity.ok(retroBoardService.findAllCardsFromRetroBoard(uuid));
    }

    @PutMapping("/{uuid}/cards")
    public ResponseEntity<Card> addCardToRetroBoard(@PathVariable UUID uuid,@Valid @RequestBody Card card){
        Card result = retroBoardService.addCardToRetroBoard(uuid,card);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{uuid}/cards/{uuidCard}")
                .buildAndExpand(uuid.toString(),result.getId().toString())
                .toUri();
        return ResponseEntity.created(location).body(result);
    }

    @GetMapping("/{uuid}/cards/{uuidCard}")
    public ResponseEntity<Card> getCardByUUID(@PathVariable UUID uuid,@PathVariable UUID uuidCard){
        return ResponseEntity.ok(retroBoardService.findCardByUUID(uuid,uuidCard));
    }

    @PutMapping("/{uuid}/cards/{uuidCard}")
    public ResponseEntity<Card> updateCardByUUID(@PathVariable UUID uuid,@PathVariable UUID uuidCard, @RequestBody Card card){
        Card result = retroBoardService.findCardByUUID(uuid,uuidCard);
        result.setComment(card.getComment());
        return ResponseEntity.ok(retroBoardService.saveCard(uuid,result));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}/cards/{uuidCard}")
    public void deleteCardFromRetroBoard(@PathVariable UUID uuid,@PathVariable UUID uuidCard){
        retroBoardService.removeCardByUUID(uuid,uuidCard);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("msg","There is an error");
        response.put("code",HttpStatus.BAD_REQUEST.value());
        response.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        response.put("errors",errors);

        return response;
    }
}
