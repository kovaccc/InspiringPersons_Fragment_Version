package com.example.inspiringpersons.repositories.implementation

import com.example.inspiringpersons.data.QuoteDao
import com.example.inspiringpersons.model.Quote
import com.example.inspiringpersons.repositories.QuoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor (private val quoteDao: QuoteDao) : QuoteRepository {

    override fun getPersonsAndQuotes() = quoteDao.getPersonsWithQuotes()

    override suspend fun insertQuotes(quotes: List<Quote>) {
        quoteDao.insertAll(quotes)
    }

    override fun getQuotes(): Flow<List<Quote>> = quoteDao.getQuotes()
}