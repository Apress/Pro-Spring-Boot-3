package com.apress.myretro.user

data class User(
    var name:String,
    var email:String,
    var gravatarUrl:String?,
    var password: String,
    var userRole: List<UserRole>,
    var active: Boolean)
