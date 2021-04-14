package com.example.inspiringpersons.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class InspiringPerson(
    @PrimaryKey(autoGenerate = true) var personId: Long = 0,
    val imageLink: String,
    val description: String,
    var birthDate: Long,
    var deathDate: Long
): Serializable