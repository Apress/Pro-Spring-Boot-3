package com.apress.myretro.web;

import com.apress.myretro.board.Card;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.service.RetroBoardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@AllArgsConstructor
@Controller
public class RetroBoardController {

    private RetroBoardService retroBoardService;

    @QueryMapping
    public Iterable<RetroBoard> retros(){
        return retroBoardService.findAll();
    }

    @MutationMapping
    public RetroBoard createRetro(@Argument String name){
        return retroBoardService.save(RetroBoard.builder().id(UUID.randomUUID()).name(name).build());
    }

    @QueryMapping
    public RetroBoard retro(@Argument UUID retroId){
        return retroBoardService.findById(retroId);
    }

    @QueryMapping
    public Iterable<Card> cards(@Argument UUID retroId){
        return retroBoardService.findAllCardsFromRetroBoard(retroId);
    }

    @MutationMapping
    public Card createCard(@Argument UUID retroId,@Argument @Valid Card card){
        return retroBoardService.addCardToRetroBoard(retroId,card);
    }

    @QueryMapping
    public Card card(@Argument UUID cardId){
        return retroBoardService.findCardByUUID(cardId);
    }

    @MutationMapping
    public Card updateCard(@Argument UUID cardId, @Argument @Valid Card card){
        Card result = retroBoardService.findCardByUUID(cardId);
        result.setComment(card.getComment());
        return retroBoardService.saveCard(result);
    }

    @MutationMapping
    public Boolean deleteCard(@Argument UUID cardId){
        retroBoardService.removeCardByUUID(cardId);
        return true;
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
