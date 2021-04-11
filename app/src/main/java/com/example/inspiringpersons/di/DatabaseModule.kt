package com.example.inspiringpersons.di

import android.content.Context
import com.example.inspiringpersons.data.InspiringPersonDao
import com.example.inspiringpersons.data.QuoteDao
import com.example.inspiringpersons.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class) // this elements below can be installed in whole app
@Module
class DatabaseModule {

    @Singleton
    @Provides // Use Provides when you don't own the class
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides // we leave this and below unscoped because we want new instance every time
    fun provideInspiringPersonsDao(appDatabase: AppDatabase): InspiringPersonDao {
        return appDatabase.inspiringPersonDao()
    }

    @Provides
    fun provideQuoteDao(appDatabase: AppDatabase): QuoteDao {
        return appDatabase.quoteDao()
    }

}