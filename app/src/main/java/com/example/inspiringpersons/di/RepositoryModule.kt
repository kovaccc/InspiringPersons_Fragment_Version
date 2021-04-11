package com.example.inspiringpersons.di

import com.example.inspiringpersons.repositories.InspiringPersonsRepository
import com.example.inspiringpersons.repositories.QuoteRepository
import com.example.inspiringpersons.repositories.implementation.InspiringPersonsRepositoryImpl
import com.example.inspiringpersons.repositories.implementation.QuoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton // so it won't create new instance inside singletonComponent
    @Binds // use Binds not Provides when you own the class
    abstract fun bindInspiringPersonsRepository(
        inspiringPersonsRepositoryImpl: InspiringPersonsRepositoryImpl
    ): InspiringPersonsRepository

    @Singleton
    @Binds
    abstract fun bindQuotesRepository(
        quoteRepositoryImpl: QuoteRepositoryImpl
    ): QuoteRepository

}