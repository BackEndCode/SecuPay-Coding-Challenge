package com.noministic.secupaycodingtest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noministic.secupaycodingtest.data.DefaultRepository
import com.noministic.secupaycodingtest.others.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val defaultRepository: DefaultRepository) :
    ViewModel() {
    private val _userLoggedIn = MutableLiveData<Boolean>()
    val userLoggedIn: LiveData<Boolean> = _userLoggedIn

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun authenticateUser(username: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            val response = defaultRepository.userLogin(username, password)
            when (response.status) {
                Status.ERROR -> {
                    Log.e("NOmi", "ERROOOORRR")
                    _loading.value=false
                    response.message?.let { _errorMessage.value = it }
                }
                Status.SUCCESS -> {
                    Log.e("NOmi", "SUCCESS" + response.data?.user)
                    _loading.value=false
                    _userLoggedIn.value = true
                }
                else -> {
                    Log.e("NOmi", "don't know")
                    _loading.value=false
                    response.message?.let { _errorMessage.value = it }
                }
            }
        }
    }
}