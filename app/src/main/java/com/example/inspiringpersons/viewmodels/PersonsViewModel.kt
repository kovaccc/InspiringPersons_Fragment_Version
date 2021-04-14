package com.example.inspiringpersons.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.inspiringpersons.model.InspiringPerson
import com.example.inspiringpersons.model.PersonWithQuotes
import com.example.inspiringpersons.repositories.InspiringPersonsRepository
import com.example.inspiringpersons.repositories.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "PersonsViewModel"

@HiltViewModel
class PersonsViewModel @Inject constructor(quoteRepository: QuoteRepository,
                                           private val inspiringPersonsRepository: InspiringPersonsRepository) : ViewModel() {

    val personWithQuotes: LiveData<List<PersonWithQuotes>> = quoteRepository.getPersonsAndQuotes().asLiveData() // kotlin flow it will automatically collect updates from database


    fun deletePerson(inspiringPerson: InspiringPerson) {
        viewModelScope.launch {
            inspiringPersonsRepository.deletePerson(inspiringPerson) // deleting inspiringPerson we are deleting quotes from database also because of onDelete = CASCADE
        }
    }


    override fun onCleared() {
        Log.d(TAG, "onCleared: starts")
        super.onCleared()
        Log.d(TAG, "onCleared: ends")
    }
}