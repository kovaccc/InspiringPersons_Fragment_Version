package com.example.inspiringpersons.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InspiringPerson(
    @PrimaryKey(autoGenerate = true) val personId: Long = 0,
    val imageLink: String,
    val description: String,
    val birthDate: Long,
    val deathDate: Long
)