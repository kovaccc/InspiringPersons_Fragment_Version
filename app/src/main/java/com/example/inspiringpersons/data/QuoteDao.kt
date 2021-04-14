package com.example.inspiringpersons.data

import androidx.room.*
import com.example.inspiringpersons.model.PersonWithQuotes
import com.example.inspiringpersons.model.Quote
import kotlinx.coroutines.flow.Flow


@Dao
interface QuoteDao {
    @Query("SELECT * FROM quote")
    fun getQuotes(): Flow<List<Quote>>

    @Transaction // transaction because two queries needs to be done atomically
    @Query("SELECT * FROM InspiringPerson")
    fun getPersonsWithQuotes(): Flow<List<PersonWithQuotes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quotes: List<Quote>)

}