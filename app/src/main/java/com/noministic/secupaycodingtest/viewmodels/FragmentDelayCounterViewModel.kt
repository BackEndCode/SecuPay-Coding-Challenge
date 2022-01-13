package com.noministic.secupaycodingtest.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noministic.secupaycodingtest.R
import com.noministic.secupaycodingtest.data.DefaultRepository
import com.noministic.secupaycodingtest.data.models.DelayCounterResponse
import com.noministic.secupaycodingtest.others.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FragmentDelayCounterViewModel @Inject constructor(
    private val application: Application,
    private val defaultRepository: DefaultRepository
) :
    ViewModel() {
    private val _delayCounterResponse = MutableLiveData<DelayCounterResponse>()
    val delayCounterResponse: LiveData<DelayCounterResponse> = _delayCounterResponse

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _currentCounter = MutableLiveData<Int>()
    val currentCounter: LiveData<Int> = _currentCounter

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private fun getDelayCounterResponse(counter: Int) {
        viewModelScope.launch {
            _loading.value = true
            val response = defaultRepository.getDelayCounterResponse(counter)
            when (response.status) {
                Status.ERROR -> {
                    _loading.value = false
                    response.message.let { _errorMessage.value = it }

                }
                Status.SUCCESS -> {
                    _loading.value = false
                    response.data.let {
                        _delayCounterResponse.value = it
                        saveCounterToPrefs(counter + 1)
                        _currentCounter.value = counter + 1
                    }
                }
                Status.LOADING -> {
                    _loading.value = true
                }
            }
        }
    }

    fun getDelayCounterResponse() {
        getDelayCounterResponse(getCounterFromPrefs())
    }

    fun getCurrentCounter() {
        val context = application.applicationContext
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.counter_pref),
            Context.MODE_PRIVATE
        )
        _currentCounter.value = sharedPref.getInt(context.getString(R.string.counter_key), 0)
    }

    private fun getCounterFromPrefs(): Int {
        val context = application.applicationContext
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.counter_pref),
            Context.MODE_PRIVATE
        )
        return sharedPref.getInt(context.getString(R.string.counter_key), 0)
    }

    fun saveCounterToPrefs(counter: Int) {
        val context = application.applicationContext
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.counter_pref),
            Context.MODE_PRIVATE
        )
        sharedPref.edit().putInt(context.getString(R.string.counter_key), counter).apply()
    }
}