package com.example.inspiringpersons.model

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable


data class PersonWithQuotes(
    @Embedded
    val inspiringPerson: InspiringPerson,

    @Relation(parentColumn = "personId", entityColumn = "inspiringPersonId")
    val personQuotes: List<Quote> = emptyList()
) : Serializable



