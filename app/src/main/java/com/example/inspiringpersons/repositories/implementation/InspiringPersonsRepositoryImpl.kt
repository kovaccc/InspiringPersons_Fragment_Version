package com.example.inspiringpersons.repositories.implementation

import com.example.inspiringpersons.data.InspiringPersonDao
import com.example.inspiringpersons.model.InspiringPerson
import com.example.inspiringpersons.repositories.InspiringPersonsRepository
import javax.inject.Inject

class InspiringPersonsRepositoryImpl @Inject constructor (private val inspiringPersonDao: InspiringPersonDao) : InspiringPersonsRepository {
    override suspend fun insertPerson(person: InspiringPerson) : Long {
        return inspiringPersonDao.insertInspiringPerson(person)
    }
}