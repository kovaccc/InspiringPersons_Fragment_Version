package com.example.inspiringpersons.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    foreignKeys = [
        ForeignKey(entity = InspiringPerson::class, parentColumns = ["personId"],
            childColumns = ["inspiringPersonId"], onDelete = CASCADE) // cascade for triggering child entity when parent is modified
    ],
    indices = [Index("inspiringPersonId")],
)
data class Quote(
    @PrimaryKey(autoGenerate = true) val quoteId: Long = 0,
    val inspiringPersonId: Long,
    val quoteText: String
)