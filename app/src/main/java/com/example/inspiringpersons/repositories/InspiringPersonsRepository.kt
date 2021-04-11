package com.example.inspiringpersons.repositories

import com.example.inspiringpersons.model.InspiringPerson


interface InspiringPersonsRepository {
    suspend fun insertPerson(person: InspiringPerson): Long

}