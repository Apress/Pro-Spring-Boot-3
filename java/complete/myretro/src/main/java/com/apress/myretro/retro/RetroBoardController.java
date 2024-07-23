package com.apress.myretro.retro;

import com.apress.myretro.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/retros")
public class RetroBoardController {

    private RetroBoardService retroBoardService;

    // Boards
    @GetMapping
    Iterable<RetroBoard> getAllRetroBoards(){
        return this.retroBoardService.getAllAvailableBoars();
    }

    @RequestMapping(method = {RequestMethod.POST,RequestMethod.PUT})
    RetroBoard saveBoard(@RequestBody RetroBoard retroBoard){
        return this.retroBoardService.saveBoard(retroBoard);
    }

    @DeleteMapping("/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBoard(@PathVariable String boardId){
        this.retroBoardService.deleteBoardById(boardId);
    }

    @GetMapping("/{boardId}")
    RetroBoard getRetroBoard(@PathVariable String boardId){
        return this.retroBoardService.findBoardById(boardId).get();
    }

    // Cards

    @GetMapping("/{boardId}/cards")
    Iterable<Card> getCardsFromBoard(@PathVariable String boardId){
        return this.retroBoardService.getCardsFromBoardId(boardId);
    }

    @RequestMapping(path="/{boardId}/cards",method = {RequestMethod.PUT,RequestMethod.POST})
    Card saveCardToBoard(@RequestBody Card card, @PathVariable String boardId){
        return this.retroBoardService.saveCardTo(boardId,card);
    }

    @DeleteMapping("/{boardId}/cards/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeCardFromBoard(@PathVariable String boardId, @PathVariable String cardId){
        this.retroBoardService.removeCardFromBoard(cardId,boardId);
    }

    // Users

    @PutMapping("/{boardId}/users/{email}")
    User addUserToBoard(@PathVariable String boardId, @PathVariable String email){
        return this.retroBoardService.assignUserToBoard(boardId,email);
    }

    @GetMapping("/{boardId}/users")
    Iterable<User> getUsersFromBoardId(@PathVariable String boardId){
        RetroBoard retroBoard = this.retroBoardService.findBoardById(boardId).get();
        return retroBoard.getAssignedUsers().values();
    }

    @DeleteMapping("/{boardId}/users/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeUserFromBoard(@PathVariable String boardId, @PathVariable String email){
        this.retroBoardService.removeUserFromBoard(boardId,email);
    }
}
