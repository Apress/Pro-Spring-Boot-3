package com.apress.myretro.retro

import com.apress.myretro.user.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/retros")
class RetroBoardController (val retroBoardService: RetroBoardService){

    @GetMapping
    fun getAllRetroBoards():Iterable<RetroBoard> = this.retroBoardService.getAllAvailableBoards()

    @RequestMapping(method = [RequestMethod.POST, RequestMethod.PUT])
    fun saveBoard(@RequestBody retroBoard: RetroBoard):RetroBoard = this.retroBoardService.saveBoard(retroBoard)

    @DeleteMapping("/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBoard(@PathVariable boardId:String) = this.retroBoardService.deleteBoardById(boardId)

    @GetMapping("/{boardId}")
    fun getRetroBoard(@PathVariable boardId:String): RetroBoard? = this.retroBoardService.findBoardById(boardId)

    // Cards

    @GetMapping("/{boardId}/cards")
    fun getCardsFromBoard(@PathVariable boardId: String) = this.retroBoardService.findBoardById(boardId)?.cards

    @RequestMapping(path = ["/{boardId}/cards"], method = [RequestMethod.PUT, RequestMethod.POST])
    fun saveCardToBoard(@RequestBody card:Card, @PathVariable boardId: String): Card? = this.retroBoardService.saveCardTo(boardId,card)

    @DeleteMapping("/{boardId}/cards/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeCardFromBoard(@PathVariable boardId: String, @PathVariable cardId: String) = this.retroBoardService.removeCardFromBoard(cardId,boardId)

    // Users

    @PutMapping("/{boardId}/users/{email}")
    fun addUserToBoard(@PathVariable boardId: String, @PathVariable email: String) = this.retroBoardService.assignUserToBoard(boardId,email)

    @GetMapping("/{boardId}/users")
    fun getUsersFromBoardId(@PathVariable boardId: String?): Iterable<User?>? = boardId?.let {
        this.retroBoardService.findBoardById(
            it
        )?.assignedUsers?.values
    }

    @DeleteMapping("/{boardId}/users/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeUserFromBoard(@PathVariable boardId: String?, @PathVariable email: String?) = this.retroBoardService.removeUserFromBoard(boardId, email)

}