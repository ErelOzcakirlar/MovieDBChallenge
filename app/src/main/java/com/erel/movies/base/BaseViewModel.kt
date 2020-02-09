package com.erel.movies.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erel.movies.domain.model.Failure
import com.erel.movies.util.SingleLiveEvent

open class BaseViewModel : ViewModel() {
    val progressLiveData = MutableLiveData<Boolean>()
    val errorLiveData = SingleLiveEvent<Failure>()

    fun handleFailure(failure: Failure) {
        progressLiveData.value = false
        errorLiveData.value = failure
    }
}