package com.example.inspiringpersons.di

import android.content.Context
import android.content.SharedPreferences
import com.example.inspiringpersons.utilities.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier


@InstallIn(SingletonComponent::class)
@Module
class SharedPrefModule {

    @Qualifier // we add qualifier in case we add other shared preferences
    @Retention(AnnotationRetention.BINARY)
    annotation class DateSharedPreferences


    @DateSharedPreferences
    @Provides
    fun provideDatePreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            Constants.DATE_PREFERENCES_FILE_KEY,
            Context.MODE_PRIVATE
        )

}