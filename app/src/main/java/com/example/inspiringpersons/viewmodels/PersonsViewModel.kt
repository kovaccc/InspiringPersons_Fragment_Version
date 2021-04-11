package com.example.inspiringpersons.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.inspiringpersons.model.PersonsWithQuotes
import com.example.inspiringpersons.repositories.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


private const val TAG = "PersonsViewModel"

@HiltViewModel
class PersonsViewModel @Inject constructor(quoteRepository: QuoteRepository) : ViewModel() {

    val personsWithQuotes: LiveData<List<PersonsWithQuotes>> = quoteRepository.getPersonsAndQuotes().asLiveData() // kotlin flow it will automatically collect updates from database

    override fun onCleared() {
        Log.d(TAG, "onCleared: starts")
        super.onCleared()
        Log.d(TAG, "onCleared: ends")
    }
}