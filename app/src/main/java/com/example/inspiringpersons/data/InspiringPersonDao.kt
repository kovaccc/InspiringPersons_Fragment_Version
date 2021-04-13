package com.example.inspiringpersons.data

import androidx.room.*
import com.example.inspiringpersons.model.InspiringPerson
import kotlinx.coroutines.flow.Flow

@Dao
interface InspiringPersonDao {

    @Query("SELECT * FROM InspiringPerson ORDER BY birthDate")
    fun getInspiringPersons(): Flow<List<InspiringPerson>>


    @Query("SELECT * FROM InspiringPerson WHERE personId = :personId")
    fun getInspiringPerson(personId: Long): Flow<InspiringPerson>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(persons: List<InspiringPerson>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInspiringPerson(inspiringPerson: InspiringPerson): Long // return id of inserted person

    @Delete
    suspend fun deleteInspiringPerson(inspiringPerson: InspiringPerson)
}