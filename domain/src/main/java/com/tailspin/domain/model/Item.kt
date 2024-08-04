package com.tailspin.domain.model

data class Item (
    var id : Int,
    var name : String,
    var time : Long,
    var tags : List<Tag>,
    var amount : Int
)