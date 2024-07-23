package com.apress.myretro.retro

import java.util.UUID

data class Card(var id: UUID?, var cardType: CardType?, var comment:String?, var userEmail:String?){
    constructor() : this(UUID.randomUUID(),null,null,null)
}
