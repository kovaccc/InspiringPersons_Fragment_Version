package com.example.inspiringpersons.repositories

import com.example.inspiringpersons.model.PersonWithQuotes
import com.example.inspiringpersons.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getPersonsAndQuotes() : Flow<List<PersonWithQuotes>>

    suspend fun insertQuotes(quotes: List<Quote>)

    fun getQuotes() : Flow<List<Quote>>
}