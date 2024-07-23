package com.apress.myretro.retro

import com.apress.myretro.client.UserClient
import com.apress.myretro.persistence.Repository
import com.apress.myretro.user.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class RetroBoardService(val repository: Repository<RetroBoard, UUID>, val userClient: UserClient) {

    fun getAllAvailableBoards(): Iterable<RetroBoard> = this.repository.findAll()

    fun saveBoard(retroBoard: RetroBoard): RetroBoard = this.repository.save(retroBoard)

    fun findBoardById(boardId:String):RetroBoard? = this.repository.findById(UUID.fromString(boardId))

    fun deleteBoardById(boardId: String) = this.repository.delete(UUID.fromString(boardId))

    fun getCardsFromBoardId(boardId:String): Iterable<Card>? {
        val board = findBoardById(boardId) ?: return emptyList()
        return board.cards?.values
    }

    fun removeCardFromBoard(cardId:String, boardId: String){
        val board = findBoardById(boardId) ?: return
        board.cards?.remove(UUID.fromString(cardId))
    }

    fun saveCardTo(boardId: String, card: Card) : Card? {
        val retroBoard = findBoardById(boardId) ?: return null

        if (null == card.id) {
            val uuid = UUID.randomUUID()
            card.id = uuid
            retroBoard.cards?.set(uuid, card)
            return card
        }

        val updatedCard = retroBoard.cards?.get(card.id)
        updatedCard!!.comment = card.comment
        return updatedCard
    }

    fun assignUserToBoard(boardId: String, email: String): User?{
        val board = findBoardById(boardId) ?: return null
        val user = this.userClient.getUserByEmail(email)

        if (user != null) board.assignedUsers?.put(email,user)

        return user
    }

    fun getHappyCardsFromRetroBoardId(boardId: String?): Iterable<Card?>? {
        return getCardsFromBoardIdAndCardType(boardId!!, CardType.HAPPY)
    }

    fun getMehCardsFromRetroBoardId(boardId: String?): Iterable<Card?>? {
        return getCardsFromBoardIdAndCardType(boardId!!, CardType.MEH)
    }

    fun getSadCardsFromRetroBoardId(boardId: String?): Iterable<Card?>? {
        return getCardsFromBoardIdAndCardType(boardId!!, CardType.SAD)
    }

    fun getCardsFromBoardIdAndCardType(boardId:String, cardType: CardType): Iterable<Card>? {
        val board = findBoardById(boardId) ?: return emptyList()
        return board.cards?.values?.filter { card: Card -> card.cardType == cardType}
    }

    fun removeUserFromBoard(boardId: String?, email: String?) {
        findBoardById(boardId!!)?.assignedUsers?.remove(email)
    }

}