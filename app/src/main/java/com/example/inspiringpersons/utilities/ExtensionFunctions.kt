package com.example.inspiringpersons.utilities

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}