package com.example.inspiringpersons.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.inspiringpersons.model.InspiringPerson
import com.example.inspiringpersons.model.Quote
import com.example.inspiringpersons.repositories.InspiringPersonsRepository
import com.example.inspiringpersons.repositories.QuoteRepository
import com.example.inspiringpersons.utilities.notifyObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "UpdatePersonViewModel"

@HiltViewModel
class UpdatePersonViewModel @Inject constructor(private val quoteRepository: QuoteRepository,
                                                private val inspiringPersonsRepository: InspiringPersonsRepository) : ViewModel() {

    private val _currentQuotesMLD = MutableLiveData<ArrayList<String>>()
    val currentQuotesLD: LiveData<ArrayList<String>>
        get() = _currentQuotesMLD


    private val _birthDateMLD = MutableLiveData<Long>()
    val birthDateLD: LiveData<Long>
        get() = _birthDateMLD


    private val _deathDateMLD = MutableLiveData<Long>()
    val deathDateLD: LiveData<Long>
        get() = _deathDateMLD


    fun updatePerson(inspiringPerson: InspiringPerson) {

        Log.d(TAG, "updatePerson: starts with $inspiringPerson, and quotes ${_currentQuotesMLD.value}")

        viewModelScope.launch {
            val quotes = arrayListOf<Quote>()
            inspiringPersonsRepository.updatePerson(inspiringPerson)
            _currentQuotesMLD.value?.let { it ->
                if(it.isNotEmpty()) {
                    it.forEach {quote ->
                        quotes.add(Quote(inspiringPersonId = inspiringPerson.personId , quoteText = quote))
                    }
                }
            }
            quoteRepository.insertQuotes(quotes)
        }

        Log.d(TAG, "saveInspiringPerson: ends")

    }


    fun deleteQuote(position: Int) {
        _currentQuotesMLD.value?.removeAt(position)
        _currentQuotesMLD.notifyObserver()
    }

    fun addQuote(quote: String) {
        Log.d(TAG, "addQuote: starts with $quote")
        if(_currentQuotesMLD.value == null) {
            _currentQuotesMLD.value = arrayListOf() // initialize if not exist
        }
        _currentQuotesMLD.value?.add(quote)
        _currentQuotesMLD.notifyObserver() // if you didn't assign its own value it wouldn't notify activity
        Log.d(TAG, "addQuote: ends with ${_currentQuotesMLD.value}")
    }


    fun updatePersonBirth(birthDate: Long) {
        _birthDateMLD.value = birthDate
    }

    fun updatePersonDeath(deathDate: Long) {
        _deathDateMLD.value = deathDate

    }


    override fun onCleared() {
        Log.d(TAG, "onCleared: starts")
        super.onCleared()
        Log.d(TAG, "onCleared: ends")
    }

}