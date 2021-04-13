package com.example.inspiringpersons.viewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.inspiringpersons.di.SharedPrefModule
import com.example.inspiringpersons.utilities.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "MainViewModel"


@HiltViewModel
class MainViewModel @Inject constructor(
    @SharedPrefModule.DateSharedPreferences private val dateSharedPref: SharedPreferences)  : ViewModel() {


    fun updatePersonBirth(date: Long) {
        Log.d(TAG, "updatePersonBirth: starts with $date")
        dateSharedPref.edit().putLong(Constants.DATE_PERSON_BIRTH, date).apply()
    }

    fun updatePersonDeath(date: Long) {
        Log.d(TAG, "updatePersonDeath: starts with $date")
        dateSharedPref.edit().putLong(Constants.DATE_PERSON_DEATH, date).apply()
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: starts")
        super.onCleared()
        Log.d(TAG, "onCleared: ends")
    }

}