package com.example.inspiringpersons.model

import androidx.room.Embedded
import androidx.room.Relation


data class PersonsWithQuotes(
    @Embedded
    val inspiringPerson: InspiringPerson,

    @Relation(parentColumn = "personId", entityColumn = "inspiringPersonId")
    val personQuotes: List<Quote> = emptyList()
)



